# tinklj
A clojure api for [Google Tink](https://github.com/google/tink) cryptographic library

# Installation
#### Leiningen/Boot
```
[tinklj "0.1.0-SNAPSHOT"]
```
#### Clojure CLI/deps.edn
```
tinklj {:mvn/version "0.1.0-SNAPSHOT"}
```
#### Gradle
```
compile 'tinklj:tinklj:0.1.0-SNAPSHOT'
```
#### Maven
``` xml
<dependency>
  <groupId>tinklj</groupId>
  <artifactId>tinklj</artifactId>
  <version>0.1.0-SNAPSHOT</version>
</dependency>
```

# Getting Started

The best way to find example use cases for the encryption techniques is to check the [Acceptance Tests](https://github.com/perkss/tinklj/blob/master/test/tinklj/acceptance/symmetric_key_encryption_test.clj).

## Initialize Tinklj

First we need to call register to register the encryption techniques.
If you want to use all implementations of all primitives in tinklj then you
call as follows:

```clojure
(:require [tinklj.config :refer [register])

(register)
```

If you wish to use a specific implementation such as AEAD you use:

```clojure
(:require [tinklj.config :refer [register])

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
(:require [tinklj.config :refer [register-key-manager])

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
(def aead (deterministic handle))

;; 3. Use the primitive to encrypt a plaintext,
(def ciphertext (encrypt aead (.getBytes data-to-encrypt) aad))

;; ... or to decrypt a ciphertext.
(def decrypted (decrypt aead ciphertext aad))
```

## Deterministic Symmetric Key Encryption
#### Deterministic Authenticated Encryption with Associated Data
```clojure
(:require [tinklj.encryption.daead :refer [encrypt decrypt]
          [tinklj.keys.keyset-handle :as keyset-handle])


;; 1. Generate the key material.
(def keyset-handle (keyset-handle/generate-new :aes256-siv))

;; 2. Get the primitive.
(def daead (deterministic keyset-handle))

;; 3. Use the primitive to deterministically encrypt a plaintext,
(def ciphertext (encrypt daead (.getBytes data-to-encrypt) aad))

;; ... or to deterministically decrypt a ciphertext.
(def decrypted (decrypt daead ciphertext aad))
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
- [ ] Streaming symmetric key encryption
- [x] MAC codes
- [x] Digital signatures
- [ ] Hybrid encryption
- [ ] Envelope encryption
- [x] Key rotation

# Contributions

Please feel free to pick up and issue or create issues and contribute.

# Contributors

Matt @lamp

Chris @minimal

Stuart @perkss
