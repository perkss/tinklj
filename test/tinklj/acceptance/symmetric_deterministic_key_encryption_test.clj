(ns tinklj.acceptance.symmetric-deterministic-key-encryption-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.core :refer [init!]]
            [tinklj.keys.keyset-handle :as keyset]
            [tinklj.primitives :refer [deterministic]]
            [tinklj.encryption.daead :as sut]))

(init! :daead)

(deftest symmetric-deterministic-key-encryption

  (testing "Deterministic Encryption and Decryption"
    (let [aad (.getBytes "salt")
          plain-text "Secret"
          handle (keyset/generate-new :aes256-siv)
          primitive (deterministic handle)
          encrypted-data (sut/encrypt primitive
                                      (.getBytes plain-text)
                                      aad)
          decrypted-data (sut/decrypt primitive
                                      encrypted-data
                                      aad)]
      (is (= plain-text
             (String. decrypted-data))))))

