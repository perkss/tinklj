(ns tinklj.primitives
  (:import (com.google.crypto.tink.aead AeadFactory)
           (com.google.crypto.tink.mac MacFactory)
           (com.google.crypto.tink.streamingaead StreamingAeadFactory)
           (com.google.crypto.tink.daead DeterministicAeadFactory)
           (com.google.crypto.tink.signature PublicKeySignFactory
                                             PublicKeyVerifyFactory)
           (com.google.crypto.tink.hybrid HybridDecryptFactory
                                          HybridEncryptFactory)))

(defn aead [handle]
  (AeadFactory/getPrimitive handle))

(defn mac [handle]
  (MacFactory/getPrimitive handle))

(defn streaming [handle]
  (StreamingAeadFactory/getPrimitive handle))

(defn deterministic [handle]
  (DeterministicAeadFactory/getPrimitive handle))

(defn digital-signature-verification [handle]
  (PublicKeyVerifyFactory/getPrimitive handle))

(defn digital-signature [handle]
  (PublicKeySignFactory/getPrimitive handle))

(defn hybrid-encryption [handle]
  (HybridEncryptFactory/getPrimitive handle))

(defn hybrid-decryption [handle]
  (HybridDecryptFactory/getPrimitive handle))
