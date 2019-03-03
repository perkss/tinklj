(ns tinklj.config.tink-config
  (:import (com.google.crypto.tink.config TinkConfig)
           (com.google.crypto.tink Registry)
           (com.google.crypto.tink.aead AeadConfig)
           (com.google.crypto.tink.daead DeterministicAeadConfig)
           (com.google.crypto.tink.mac MacConfig)
           (com.google.crypto.tink.signature SignatureConfig)
           (com.google.crypto.tink.streamingaead StreamingAeadConfig)))

(defn register
  []
  (TinkConfig/register))

(defn register-aead-config
  []
  (AeadConfig/register))

(defn register-daead-config
  []
  (DeterministicAeadConfig/register))

(defn register-mac-config
  []
  (MacConfig/register))

(defn register-signature-config
  []
  (SignatureConfig/register))

(defn register-streaming-aead-config
  []
  (StreamingAeadConfig/register))

(defn register-key-manager
  "Register custom implementation of key manager"
  [key-manager]
  (Registry/registerKeyManager key-manager))
