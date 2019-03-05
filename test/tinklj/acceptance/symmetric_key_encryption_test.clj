(ns tinklj.acceptance.symmetric-key-encryption-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer [register]]
            [tinklj.primitives :as primitives]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.encryption.aead :as sut]))

(deftest symmetric-key-encryption
  (testing "Symmetric key encryption"
    (register)
    (let [secret-data "Secret data"
          keyset-handle (keyset-handles/generate-new :aes128-gcm)
          primitive (primitives/aead keyset-handle)
          aad (.getBytes "Salt")
          encrypted (sut/encrypt primitive (.getBytes secret-data) aad)
          decrypted (sut/decrypt primitive encrypted aad)]
      (is (= secret-data (String. decrypted))))))
