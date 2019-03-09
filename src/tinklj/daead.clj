(ns tinklj.daead
  (:require [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.primitives :refer [deterministic]]))

(defn encrypt [encryption-method data salt]
  (let [handle (keyset-handles/generate-new encryption-method)
        primitive (deterministic handle)]
    (.encryptDeterministically primitive
                               (.getBytes data)
                               salt)))

(defn decrypt [decryption-method encrypted-data salt]
  (let [handle (keyset-handles/generate-new decryption-method)
        primitive (deterministic handle)]
    (.decryptDeterministically primitive
                               encrypted-data
                               salt)))
