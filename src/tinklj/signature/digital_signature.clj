(ns tinklj.signature.digital-signature
  (:require [tinklj.primitives :refer [digital-signature digital-signature-verification]])
  (:import (com.google.crypto.tink PublicKeyVerify PublicKeySign)))

(defn sign
  [handle data]
  (let [^PublicKeySign public-key-signer (digital-signature handle)]
    (.sign public-key-signer
           (.getBytes data))))

(defn verify
  [public-keyset-handle signature data]
  (let [^PublicKeyVerify public-key-verifier (digital-signature-verification
                                               public-keyset-handle)]
    (.verify public-key-verifier
             signature
             (.getBytes data))))

