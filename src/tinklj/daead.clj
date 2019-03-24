(ns tinklj.daead
  (:require [tinklj.keys.keyset-handle :as keyset-handles])
  (:import (java.security Security)))

(Security/setProperty "crypto.policy" "unlimited")

(defn encrypt [handle data salt]
  (.encryptDeterministically handle
                             (.getBytes data)
                             (.getBytes salt)))

(defn decrypt [handle encrypted-data salt]
  (.decryptDeterministically handle
                             encrypted-data
                             (.getBytes salt)))
