(ns tinklj.acceptance.symmetric-key-encryption-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.core :refer [init!]]
            [tinklj.primitives :as primitives]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.encryption.aead :as sut]))

(init! :aead)

(deftest symmetric-key-encryption
  (testing "Symmetric key encryption"

    (let [plain-text "Secret data"
          keyset-handle (keyset-handles/generate-new :aes128-gcm)
          primitive (primitives/aead keyset-handle)
          aad (.getBytes "Salt")
          encrypted (sut/encrypt primitive
                                 (.getBytes plain-text)
                                 aad)
          decrypted (sut/decrypt primitive
                                 encrypted
                                 aad)]
      (is (= plain-text (String. decrypted))))))
