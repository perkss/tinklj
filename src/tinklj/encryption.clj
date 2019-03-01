(ns tinklj.encryption)

(defn encrypt
  [primitive plaintext aad]
  (.encrypt primitive plaintext aad))

(defn decrypt
  [primitive plaintext aad]
  (.decrypt primitive plaintext aad))
