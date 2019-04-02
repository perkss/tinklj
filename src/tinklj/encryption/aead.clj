(ns tinklj.encryption.aead)

(defn encrypt
  [handle data salt]
  (.encrypt handle data salt))

(defn decrypt
  [handle data salt]
  (.decrypt handle data salt))
