(ns tinklj.acceptance.symmetric-deterministic-key-encryption-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :refer [register]]
            [tinklj.keys.keyset-handle :as keyset]
            [tinklj.primitives :refer [deterministic]]
            [tinklj.daead :as sut]))

(register :daead)

(deftest symmetric-deterministic-key-encryption

  (testing "Deterministic Encryption and Decryption"
      (let [aad "salt"
            handle (keyset/generate-new :aes256-siv)
            primitive (deterministic handle)
            decryptable-data (sut/encrypt primitive
                                          "Secret"
                                          aad)
            decrypted-data (sut/decrypt primitive
                                        decryptable-data
                                        aad)]
        (is (= "Secret"
               (String. decrypted-data))))))

