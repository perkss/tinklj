(ns tinklj.config.tink-config
  (:import (com.google.crypto.tink.config TinkConfig)
           (com.google.crypto.tink Registry)))

(defn register
  []
  (TinkConfig/register))

(defn register-key-manager
  "Register custom implementation of key manager"
  [key-manager]
  (Registry/registerKeyManager key-manager))
