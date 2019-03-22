(ns tinklj.daead
  (:require [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.primitives :refer [deterministic]])
  (:import (java.security Security)))

(Security/setProperty "crypto.policy" "unlimited")

(defn encrypt [encryption-method data salt]
  (let [handle (keyset-handles/generate-new encryption-method)
        primitive (deterministic handle)]
    (.encryptDeterministically primitive
                               (.getBytes data)
                               (.getBytes salt))))

(defn decrypt [decryption-method encrypted-data salt]
  (let [handle (keyset-handles/generate-new decryption-method)
        primitive (deterministic handle)]
    (.decryptDeterministically primitive
                               encrypted-data
                               (.getBytes salt))))
