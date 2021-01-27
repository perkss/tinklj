<img src="docs/logo/Tinklj-1st-font-blue-with padding-01.jpg" alt="Tinklj" width="1000"/>

Tinklj a Clojure api for [Google Tink](https://github.com/google/tink) cryptographic library, offering simplistic and secure cryptography for Clojure.

# Installation

[![Clojars Project](https://img.shields.io/clojars/v/tinklj.svg)](https://clojars.org/tinklj)

#### Leiningen/Boot
```
[tinklj "0.1.3-SNAPSHOT"]
```
#### Clojure CLI/deps.edn
```
tinklj {:mvn/version "0.1.3-SNAPSHOT"}
```
#### Gradle
```
compile 'tinklj:tinklj:0.1.3-SNAPSHOT'
```
#### Maven
``` xml
<dependency>
  <groupId>tinklj</groupId>
  <artifactId>tinklj</artifactId>
  <version>0.1.3-SNAPSHOT</version>
</dependency>
```

# Getting Started

The best way to find example use cases for the encryption techniques is to check the [Acceptance Tests](https://github.com/perkss/tinklj/blob/master/test/tinklj/acceptance/symmetric_key_encryption_test.clj).

## Initialize Tinklj

First we need to call register to register the encryption techniques.
If you want to use all implementations of all primitives in tinklj then you
call as follows:

```clojure
(:require [tinklj.core :refer [init!])

(init!)
```

If you wish to use a specific implementation such as AEAD you use:

```clojure
(:require [tinklj.core :refer [init!])

(register :aead)
```
Available options are:
  - `:aead`
  - `:daead`
  - `:mac`
  - `:signature`
  - `:streaming`

To do custom initialization then registration proceeds directly with a Registry class.

```clojure
(:require [tinklj.core :refer [register-key-manager])

(register-key-manager (MyAeadKeyManager.)
```


## Generating New Key(set)s

Below shows how you can generate a keyset containing a randomly generated AES128-GCM
specified.
```clojure
(:require [tinklj.keys.keyset-handle :as keyset-handles])

(keyset-handles/generate-new :aes128-gcm)
```

## Storing Keysets

Once you have generated the keyset you can store it down for future use.
Cleartext and encrypted keysets can be stored. Obviously encrypted is recommended.

```clojure
(:require [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.keysets.keyset-storage :as keyset-storage])

(def filename "my_keyset.json")
(def keyset-handle (keyset-handles/generate-new :aes128-gcm)

(keyset-storage/write-clear-text-keyset-handle keyset-handle filename)
```

Tinklj supports encrypting keysets with master keys stored in a remote key management systems. Lets see how to write these remote KMS keys.

```clojure
(:require [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.keysets.keyset-storage :as keyset-storage]
          [tinklj.keysets.integration.gcp-kms-client :refer [gcp-kms-client])


;; Generate the key material...
(def keyset-handle (keyset-handles/generate-new :aes128-gcm)

;; and write it to a file...
(def filename "my_keyset.json")
;; encrypted with the this key in GCP KMS
(def master-key-uri = "gcp-kms://projects/tinklj-examples/locations/global/keyRings/foo/cryptoKeys/bar")
(def kms-client (gcp-kms-client))

(keyset-storage/write-remote-kms-keyset-handle
                    keyset-handle
                    kms-client
                    filename
                    master-key-uri)
```

## Loading Existing Keysets

Once the keyset is stored it can be reloaded as follows:

```clojure
(:require [tinklj.keysets.keyset-storage :as keyset-storage])

(keyset-storage/load-clear-text-keyset-handle filename)
```

Loading remote encrypted keys:

```clojure
(:require [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.keysets.keyset-storage :as keyset-storage]
          [tinklj.keysets.integration.gcp-kms-client :refer [gcp-kms-client])


(def filename "my_keyset.json")
;; encrypted with the this key in GCP KMS
(def master-key-uri = "gcp-kms://projects/tinklj-examples/locations/global/keyRings/foo/cryptoKeys/bar")
(def kms-client (gcp-kms-client))

(keyset-storage/load-remote-kms-keyset-handle
                    kms-client
                    filename
                    master-key-uri)
```


## Obtaining and Using Primitives

We then get the primitive of the keyset-handle and can use this to encrypt and decypt.
```clojure
(keyset-handles/get-primitive keyset-handle)
```


## Symmetric Key Encryption
#### Authenticated Encryption with Associated Data
```clojure
(:require [tinklj.encryption.aead :refer [encrypt decrypt]
          [tinklj.keys.keyset-handle :as keyset-handle]
          [tinklj.primitives :as primitives])

;; 1. Generate the key material.
(def keyset-handle (keyset-handle/generate-new :aes128-gcm))

;; 2. Get the primitive.
(def aead (primitives/aead keyset-handle))

;; 3. Use the primitive to encrypt a plaintext,
(def ciphertext (encrypt aead (.getBytes data-to-encrypt) aad))

;; ... or to decrypt a ciphertext.
(def decrypted (decrypt aead ciphertext aad))
```

## Deterministic Symmetric Key Encryption
#### Deterministic Authenticated Encryption with Associated Data
```clojure
(:require [tinklj.encryption.daead :refer [encrypt decrypt]
          [tinklj.keys.keyset-handle :as keyset-handle]
          [tinklj.primitives :as primitives])


;; 1. Generate the key material.
(def keyset-handle (keyset-handle/generate-new :aes256-siv))

;; 2. Get the primitive.
(def daead (primitives/deterministic keyset-handle))

;; 3. Use the primitive to deterministically encrypt a plaintext,
(def ciphertext (encrypt daead (.getBytes data-to-encrypt) aad))

;; ... or to deterministically decrypt a ciphertext.
(def decrypted (decrypt daead ciphertext aad))
```

## Symmetric Key Encryption of Streaming Data
```clojure
;; 1. Generate the key material.
(def keyset-handle (keyset-handles/generate-new :aes128-ctr-hmac-sha256-4kb))

;; 2. Get the primitive.
(def streaming-primitive (primitives/streaming keyset-handle))

;; 3. Get the Encrypting Channel
(def encrypting-channel (streaming/encrypting-channel
                                      streaming-primitive
                                      ciphertext-destination
                                      associated-data))
                                      
;; 4. Write Encrypting Data
(streaming/encrypting-channel-write encrypting-channel byte-buffer)   

;; 5. Get the Decrypting Channel
(def decrypting-channel (streaming/decrypting-channel
                                           streaming-primitive
                                           (.getChannel file-input-stream)
                                           associated-data))
                                           
;; 6. Read the Decrypted Data
(streaming/decrypting-channel-read decrypting-channel buf)
```

## Message Authentication Code

How to compute or verify a MAC (Message Authentication Code)

```clojure
(:require [tinklj.primitives :as primitives]
          [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.mac.message-authentication-code :as mac])

;; 1. Generate the key material.
(def keyset-handle (keyset-handles/generate-new :hmac-sha256-128bittag))

;; 2. Get the primitive.
(def mac-primitive (primitives/mac keyset-handle))

;; 3. Use the primitive to compute a tag,
(mac/compute mac-primitive data)

;; ... or to verify a tag.
(mac/verify mac-primitive tag data)
```

## Digital Signatures

Here is an example of how to sign or verify a digital signature:

```clojure
(:require [tinklj.primitives :as primitives]
          [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.signature.digital-signature :refer [sign verify])

;; SIGNING

;; 1. Generate the private key material.
(def private-keyset-handle (keyset-handles/generate-new :ecdsa-p256))

;; 2. Sign the data.
(def signature (sign private-keyset-handle data)

;; VERIFYING

;; 1. Obtain a handle for the public key material.
(def public-key-set-handle (get-public-keyset-handle private-keyset-handle))

;; 2. Use the primitive to verify.
(verify public-key-set-handle
        signature
        data)
```

## Hybrid Encryption

To encrypt or decrypt using a [combination of public key encryption and symmetric key encryption](https://github.com/google/tink/blob/master/docs/PRIMITIVES.md#hybrid-encryption) one can use the following:

```clojure
(:require  [tinklj.core :refer [init!]]
           [tinklj.keys.keyset-handle :as keyset]
           [tinklj.primitives :as primitives]
           [tinklj.encryption.aead :refer [encrypt decrypt]])

(init!)

;; 1. Generate the private key material.
(def private-keyset-handle (keyset/generate-new :ecies-p256-hkdf-hmac-sha256-aes128-gcm))

;;  Obtain the public key material.
(def public-keyset-handle (keyset/get-public-keyset-handle private-keyset-handle))

;; ENCRYPTING

;; 2. Get the primitive.
(def hybrid-encrypt (primitives/hybrid-encryption public-keyset-handle))

;; 3. Use the primitive.
(def encrypted-data (encrypt hybrid-encrypt
                                 (.getBytes plain-text)
                                 aad))
;; DECRYPTING

;;  2. Get the primitive.
(def hybrid-decrypt (primitives/hybrid-decryption private-keyset-handle))

;; 3. Use the primitive.
(def decrypted-data (decrypt hybrid-decrypt
                                 encrypted-data
                                 aad))
```

## Envelope Encryption
```clojure
(:require  [tinklj.keys.keyset-handle :as keyset]
           [tinklj.primitives :as primitives]
           [tinklj.encryption.aead :refer [encrypt]]
           [tinklj.keysets.integration.kms-client :as client]
           [tinklj.keysets.integration.gcp-kms-client :refer [gcp-kms-client])

;; 1. Generate the key material.
    (def kmsKeyUri
        "gcp-kms://projects/tink-examples/locations/global/keyRings/foo/cryptoKeys/bar")
    
    (def keysetHandle (keyset/generate-new
        (keyset/create-kms-envelope-aead-key-template kmsKeyUri :aes128-gcm)))

;; 2. Register the KMS client.
    (def gcp-client (gcp-kms-client))
    (client/with-credentials gcp-client "credentials.json")
    (client/kms-client-add gcp-client)
    
;; 3. Get the primitive.
    (def aead (primitives/aead keyset-handle))

;; 4. Use the primitive.
    (def ciphertext (encrypt aead (.getBytes data-to-encrypt) aad))

```


## Key Rotation
To complete key rotation you need a keyset-handle that contains the keyset that should be rotated, and a specification of the new key via the KeyTemplate map for example :aes128-gcm.

```clojure
(:require [tinklj.keys.keyset-handle :as keyset-handles]
          [tinklj.keysets.keyset-storage :as keyset-storage])

(def keyset-handle (keyset-handles/generate-new :aes128-gcm)) ;; existing keyset
(def keyset-template (:hmac-sha256-128bittag keyset-handles/key-templates)) ;; template for the new key

(keyset-storage/rotate-keyset-handle keyset-handle keyset-template)
```

## FAQ

1. ### *Exception: java.security.InvalidKeyException: Illegal key size or default parameters* when getting a primitive
  - If you are running a version of java 8 below `1.8.0_162` you will need to download the (Java Cryptography Extension)[http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html]
  - If you are using a version of the JVM before `1.8.0_161` and `1.8.0_151` you will need to set `(Security/setProperty "crypto.policy" "unlimited")`
  - Above Java 8 Update 161 the default encryption strength is unlimited by default

  See (this stack overflow question for more)[https://stackoverflow.com/questions/24907530/java-security-invalidkeyexception-illegal-key-size-or-default-parameters-in-and]


# Feature List
Based on the available feature list defined [here](https://github.com/google/tink/blob/master/docs/JAVA-HOWTO.md)
- [x] Generation of keysets
- [x] Symmetric key encryption
- [x] Storing keysets
- [x] Loading existing keysets
- [x] Storing and loading encrypted keysets
- [x] Deterministic symmetric key encryption
- [x] Streaming symmetric key encryption
- [x] MAC codes
- [x] Digital signatures
- [x] Hybrid encryption
- [x] Envelope encryption
- [x] Key rotation

# Contributions

Please feel free to pick up and issue or create issues and contribute.

# Contributors

Thanks to all the developers involved!

Matt @lamp

Chris @minimal

Stuart @perkss


# Logo

Thanks and credit for the great Tinklj logo!

J.G Designer @newfinal100

# Disclaimer
This is not an official Google product or library.
