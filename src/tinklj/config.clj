(ns tinklj.config
  (:import (com.google.crypto.tink.config TinkConfig)
           (com.google.crypto.tink Registry)
           (com.google.crypto.tink.aead AeadConfig)
           (com.google.crypto.tink.daead DeterministicAeadConfig)
           (com.google.crypto.tink.mac MacConfig)
           (com.google.crypto.tink.signature SignatureConfig)
           (com.google.crypto.tink.streamingaead StreamingAeadConfig)
           (com.google.crypto.tink.hybrid HybridConfig)
           (com.google.crypto.tink.prf PrfConfig)))

(defn register-key-manager
  "Register custom implementation of key manager
  Example:
  (register-key-manager (reify KeyManager (getKeyType [this] \"my awesome manager\")))"
  [key-manager]
  (Registry/registerKeyManager key-manager))

(defn register
  "Initialise tink, optionally you can provide a type
  to only initialise a type, for example :aead
  A custom key manager can also be provided if needed
  Returns: nil
  Example:
    (register :mac)"
  [& [config-type]]
  (condp = config-type
    :aead (AeadConfig/register)
    :daead (DeterministicAeadConfig/register)
    :mac (MacConfig/register)
    :signature (SignatureConfig/register)
    :streaming (StreamingAeadConfig/register)
    :hybrid (HybridConfig/register)
    :prf (PrfConfig/register)
    nil (TinkConfig/register)
    (register-key-manager config-type)))


