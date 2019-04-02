(ns tinklj.mac.message-authentication-code
  (:import (com.google.crypto.tink Mac)))

(defn compute
  [^Mac mac data]
  (.computeMac mac data))

(defn verify
  [^Mac mac tag data]
  (.verifyMac mac tag data))