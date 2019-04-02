(ns tinklj.acceptance.symmetric-deterministic-key-encryption-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :refer [register]]
            [tinklj.keys.keyset-handle :as keyset]
            [tinklj.primitives :refer [deterministic]]
            [tinklj.encryption.daead :as sut]))

(register :daead)

(deftest symmetric-deterministic-key-encryption

  (testing "Deterministic Encryption and Decryption"
      (let [aad "salt"
            handle (keyset/generate-new :aes256-siv)
            primitive (deterministic handle)
            encrypted-data (sut/encrypt primitive
                                        "Secret"
                                        aad)
            decrypted-data (sut/decrypt primitive
                                        encrypted-data
                                        aad)]
        (is (= "Secret"
               (String. decrypted-data))))))

