(ns tinklj.signature.digital-signature
  (:import (com.google.crypto.tink PublicKeySign PublicKeyVerify)))

(defn sign
  [handle data]
  (let [public-key-signer (.getPrimitive handle PublicKeySign)]
    (.sign public-key-signer data)))

(defn verify
  [public-keyset-handle signature data]
  (let [public-key-verifier (.getPrimitive public-keyset-handle PublicKeyVerify)]
    (.verify public-key-verifier signature data)))

