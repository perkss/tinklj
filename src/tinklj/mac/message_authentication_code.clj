(ns tinklj.mac.message-authentication-code
  (:import (com.google.crypto.tink Mac)))

(defn compute-mac
  [^Mac mac data]
  (.computeMac mac data))

(defn verify-mac
  [^Mac mac tag data]
  (.verifyMac mac tag data))