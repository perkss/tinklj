(ns tinklj.acceptance.symmetric-deterministic-key-encryption-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :refer [register]]
            [tinklj.daead :as sut]))

(register :daead)

(deftest symmetric-deterministic-key-encryption

  (testing "Encryption"
    (let [encryptable-data "Secret Data"
          aad (.getBytes "salt")
          encrypted (sut/encrypt :aes256-siv
                                 encryptable-data
                                 aad)]
      (is (= encrypted ""))))

  (testing "Decryption"
      (let [aad "salt"
            decryptable-data (sut/encrypt :aes256-siv
                                          "Secret"
                                          aad)
            decrypted-data (sut/decrypt :aes256-siv
                                        decryptable-data
                                        aad)]
        (is (= "Secret"
               decrypted-data)))))

