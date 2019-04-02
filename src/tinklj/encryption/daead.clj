(ns tinklj.encryption.daead)

(defn encrypt [handle data salt]
  (.encryptDeterministically handle
                             (.getBytes data)
                             (.getBytes salt)))

(defn decrypt [handle encrypted-data salt]
  (.decryptDeterministically handle
                             encrypted-data
                             (.getBytes salt)))
