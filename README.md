# tinklj
A clojure api for [Google Tink](https://github.com/google/tink) cryptographic library

# Symmetric Encryption

The best way to find the use cases for the encryption techniques is to check the [Acceptance Tests](https://github.com/perkss/tinklj/blob/master/test/tinklj/acceptance/symmetric_key_encryption_test.clj).

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
(:require [tinklj.keysets.keyset-storage :as keyset-storage])

(keyset-storage/write-clear-text-keyset-handle keyset-handle filename)
```

## Loading Existing Keysets

Once the keyset is stored it can be reloaded as follows:

```clojure
(:require [tinklj.keysets.keyset-storage :as keyset-storage])

(keyset-storage/load-clear-text-keyset-handle filename)
```


## Obtaining and Using Primitives

We then get the primitive of the keyset-handle and can use this to encrypt and decypt.
```clojure
(keyset-handles/get-primitive keyset-handle)
```


## Authenticated Encryption with Associated Data
```clojure
(:require [tinklj.encryption.aead :refer [encrypt decrypt])

(encrypt aead (.getBytes data-to-encrypt) aad)
(decrypt aead encrypted aad)
```

## Deterministic Authenticated Encryption with Associated Data
```clojure
(:require [tinklj.encryption.daead :refer [encrypt decrypt])

(encrypt aead (.getBytes data-to-encrypt) aad)
(decrypt aead encrypted aad)
```

## Message Authentication Code

How to compute or verify a MAC (Message Authentication Code)

```clojure
(:require [tinklj.mac.message-authentication-code :refer :as mac)


(mac/compute mac data)
(mac/verify mac tag data)
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
- [ ] Storing and loading encrypted keysets
- [ ] Deterministic symmetric key encryption
- [ ] Streaming symmetric key encryption
- [ ] MAC codes
- [ ] Digital signatures
- [ ] Hybrid encryption
- [ ] Envelope encryption
- [ ] Key rotation

# Contributions

Please feel free to pick up and issue or create issues and contribute.

# Contributors

Matt @lamp

Chris @minimal

Stuart @perkss
