(ns tinklj.acceptance.symmetric-key-encryption-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config.tink-config :refer [register]]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.encryption :refer [encrypt decrypt]]))

(deftest symmetric-key-encryption
  (testing "Symmetic key encryption"
    (register)
    (let [secret-data "Secret data"
          keyset-handle (keyset-handles/generate-new keyset-handles/AES128-GCM)
          aead (keyset-handles/get-primitive keyset-handle)
          aad (.getBytes "Salt")
          encrypted (encrypt aead (.getBytes secret-data) aad)
          decrypted (decrypt aead encrypted aad)]
      (is (= secret-data (String. decrypted))))))
