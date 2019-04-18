(ns tinklj.encryption.daead)

(defn encrypt [handle data salt]
  (.encryptDeterministically handle
                             data
                             salt))

(defn decrypt [handle encrypted-data salt]
  (.decryptDeterministically handle
                             encrypted-data
                             salt))
