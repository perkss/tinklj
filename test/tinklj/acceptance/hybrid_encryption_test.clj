(ns tinklj.acceptance.hybrid-encryption-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer [register]]
            [tinklj.keys.keyset-handle :as keyset]
            [tinklj.primitives :as primitives]
            [tinklj.encryption.aead :refer [encrypt decrypt]]))

(register)

(deftest hybrid-encryption-test

  (testing "Hybrid Encryption using Public and Private Key"
    (let [aad (.getBytes "salt")
          plain-text "Secret"
          private-keyset-handle (keyset/generate-new :ecies-p256-hkdf-hmac-sha256-aes128-gcm)
          public-keyset-handle (keyset/get-public-keyset-handle private-keyset-handle)
          hybrid-encrypt (primitives/hybrid-encryption public-keyset-handle)
          hybrid-decrypt (primitives/hybrid-decryption private-keyset-handle)
          encrypted-data (encrypt hybrid-encrypt
                                      (.getBytes plain-text)
                                      aad)
          decrypted-data (decrypt hybrid-decrypt
                                      encrypted-data
                                      aad)]
      (is (= plain-text
             (String. decrypted-data))))))


