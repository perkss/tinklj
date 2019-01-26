(ns tinklj.keys.keyset-handle
  (:import (com.google.crypto.tink KeysetHandle)
           (com.google.crypto.tink.aead AeadKeyTemplates AeadFactory)
           (com.google.crypto.tink Aead)))

(def AES128-GCM (AeadKeyTemplates/AES128_GCM))

(defn generate-new
  [keyTemplate]
  (KeysetHandle/generateNew keyTemplate))

(defn get-primitive
  [keyset-handle]
  (AeadFactory/getPrimitive keyset-handle))
