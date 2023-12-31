(ns tinklj.keys.keyset-handle
  (:import (com.google.crypto.tink KeysetHandle)
           (com.google.crypto.tink.aead AeadKeyTemplates)
           (com.google.crypto.tink.streamingaead StreamingAeadKeyTemplates)
           (com.google.crypto.tink.daead DeterministicAeadKeyTemplates)
           (com.google.crypto.tink.mac MacKeyTemplates)
           (com.google.crypto.tink.signature SignatureKeyTemplates)
           (com.google.crypto.tink.hybrid HybridKeyTemplates)
           (com.google.crypto.tink.proto OutputPrefixType)))

(def key-templates {:aes128-gcm AeadKeyTemplates/AES128_GCM
                    :aes256-gcm AeadKeyTemplates/AES256_GCM
                    :aes128-eax AeadKeyTemplates/AES128_EAX
                    :aes256-eax AeadKeyTemplates/AES256_EAX
                    :aes128-ctr-hmac-sha256 AeadKeyTemplates/AES128_CTR_HMAC_SHA256
                    :aes256-ctr-hmac-sha256 AeadKeyTemplates/AES256_CTR_HMAC_SHA256
                    :chacha20-poly1305 AeadKeyTemplates/CHACHA20_POLY1305
                    :xchacha20-poly1305 AeadKeyTemplates/XCHACHA20_POLY1305
                    :hmac-sha256-128bittag MacKeyTemplates/HMAC_SHA256_128BITTAG
                    :hmac-sha256-256bittag MacKeyTemplates/HMAC_SHA256_256BITTAG
                    :aes128-ctr-hmac-sha256-4kb StreamingAeadKeyTemplates/AES128_CTR_HMAC_SHA256_4KB
                    :aes256-ctr-hmac-sha256-4kb StreamingAeadKeyTemplates/AES256_CTR_HMAC_SHA256_4KB
                    :aes128-gcm-hkdf-4kb StreamingAeadKeyTemplates/AES128_GCM_HKDF_4KB
                    :aes256-gcm-hkdf-4kb StreamingAeadKeyTemplates/AES256_GCM_HKDF_4KB
                    :aes256-siv DeterministicAeadKeyTemplates/AES256_SIV
                    :ecdsa-p256 SignatureKeyTemplates/ECDSA_P256
                    :ecdsa-p384 SignatureKeyTemplates/ECDSA_P384
                    :ecdsa-p521 SignatureKeyTemplates/ECDSA_P521
                    :ecdsa-p256-ieee-p1363 SignatureKeyTemplates/ECDSA_P256_IEEE_P1363
                    :ecdsa-p384-ieee-p1363 SignatureKeyTemplates/ECDSA_P384_IEEE_P1363
                    :ecdsa-p521-ieee-p1363 SignatureKeyTemplates/ECDSA_P521_IEEE_P1363
                    :ed25519 SignatureKeyTemplates/ED25519
                    :ecies-p256-hkdf-hmac-sha256-aes128-gcm HybridKeyTemplates/ECIES_P256_HKDF_HMAC_SHA256_AES128_GCM
                    :ecies-p256-hkdf-hmac-sha256-aes128-ctr-hmac-sha256 HybridKeyTemplates/ECIES_P256_HKDF_HMAC_SHA256_AES128_CTR_HMAC_SHA256})

(defn generate-new
  "Generates a new keyset based on the templates provided
  template is a type of key template that is one of :aes, :aes-deterministic, :mac,
    :streaming-aes :signature or :hybrid
  Example:
    (generate-new :aes128-gcm)
  Returns a com.google.crypto.tink.*.KeyTemplate"
  [template-name]
  (KeysetHandle/generateNew (get key-templates template-name)))

(defn get-public-keyset-handle
  [handle]
  (.getPublicKeysetHandle handle))

(defn create-aes-gcm-key-template
  [key-size]
  (AeadKeyTemplates/createAesGcmKeyTemplate key-size))

(defn create-aes-eax-key-template
  [key-size iv-size]
  (AeadKeyTemplates/createAesEaxKeyTemplate key-size iv-size))

(defn create-aes-ctr-hmac-aead-key-template
  [aes-key-size iv-size hmac-key-size tag-size hash-type]
  (AeadKeyTemplates/createAesCtrHmacAeadKeyTemplate
   aes-key-size iv-size hmac-key-size tag-size hash-type))

(defn create-kms-envelope-aead-key-template
  [kms-key-uri key-template]
  (AeadKeyTemplates/createKmsEnvelopeAeadKeyTemplate kms-key-uri key-template))

(defn create-hmac-key-template
  [key-size tag-size hash-type]
  (MacKeyTemplates/createHmacKeyTemplate key-size tag-size hash-type))

(defn create-aes-ctr-hmac-streaming-key-template
  [main-key-size
   hkdf-hash-type
   derived-key-size
   mac-hash-type
   tag-size
   ciphertext-segment-size]
  (StreamingAeadKeyTemplates/createAesCtrHmacStreamingKeyTemplate
   main-key-size hkdf-hash-type derived-key-size mac-hash-type tag-size ciphertext-segment-size))

(defn create-aes-gcm-hkdf-streaming-key-template
  [main-key-size hkdf-hash-type derived-key-size ciphertext-segment-size]
  (StreamingAeadKeyTemplates/createAesGcmHkdfStreamingKeyTemplate
   main-key-size hkdf-hash-type derived-key-size ciphertext-segment-size))

(defn create-aes-siv-key-template
  [key-size]
  (DeterministicAeadKeyTemplates/createAesSivKeyTemplate key-size))

(defn create-ecdsa-key-template
  [hash-type curve encoding ^OutputPrefixType output-type]
  (SignatureKeyTemplates/createEcdsaKeyTemplate hash-type curve encoding output-type))

(defn createEciesAeadHkdfParams
  [curve
   hash-type
   ec-point-format
   dem-key-template
   salt]
  (HybridKeyTemplates/createEciesAeadHkdfParams
   curve hash-type ec-point-format dem-key-template salt))
