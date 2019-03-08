(ns tinklj.config
  (:import (com.google.crypto.tink.config TinkConfig)
           (com.google.crypto.tink Registry)
           (com.google.crypto.tink.aead AeadConfig)
           (com.google.crypto.tink.daead DeterministicAeadConfig)
           (com.google.crypto.tink.mac MacConfig)
           (com.google.crypto.tink.signature SignatureConfig)
           (com.google.crypto.tink.streamingaead StreamingAeadConfig)))

(defn register
  ([& config-type]
   (prn config-type)
   (case (first config-type)
    :aead (AeadConfig/register)
    :daead (DeterministicAeadConfig/register)
    :mac (MacConfig/register)
    :signature (SignatureConfig/register)
    :streaming (StreamingAeadConfig/register)
    (TinkConfig/register))))

(defn register-key-manager
  "Register custom implementation of key manager"
  [key-manager]
  (Registry/registerKeyManager key-manager))
