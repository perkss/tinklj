(ns tinklj.acceptance.symmetric-key-encryption-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer [register]]
            [tinklj.primitives :as primitives]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.encryption.aead :as sut]))

(register :aead)

(defn symmetric-key-encryption-test [the-key-template]
  (let [plain-text "Secret data"
        keyset-handle (keyset-handles/generate-new the-key-template)
        primitive (primitives/aead keyset-handle)
        aad (.getBytes "Salt")
        encrypted (sut/encrypt primitive
                               (.getBytes plain-text)
                               aad)
        decrypted (sut/decrypt primitive
                               encrypted
                               aad)]
    (is (= plain-text (String. decrypted)))))

(deftest AES128_GCM-gcm-symmetric-key-encryption
  (testing "AES128_GCM symmetric key encryption"

    (symmetric-key-encryption-test :aes128-gcm)))

(deftest XCHACHA20_POLY1305-gcm-symmetric-key-encryption
  (testing "XCHACHA20_POLY1305 symmetric key encryption"

    (symmetric-key-encryption-test :xchacha20-poly1305)))
