(ns tinklj.acceptance.message-authentication-code-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer [register]]
            [tinklj.primitives :as primitives]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.mac.message-authentication-code :as sut])
  (:import (java.security GeneralSecurityException)))

(register :aead)

(deftest message-authentication-code

  (testing "How to compute or verify a Mac (Message Authentication Code)"
    (let [plain-text (.getBytes "Secret data")
          keyset-handle (keyset-handles/generate-new :hmac-sha256-128bittag)
          mac (primitives/mac keyset-handle)
          tag (sut/compute mac
                           plain-text)
          verify (sut/verify mac
                             tag
                             plain-text)]
      (is (not (= GeneralSecurityException verify))))))

(deftest invalid-message-authentication-code

  (testing "Verify a invalid Mac (Message Authentication Code)"
    (let [secret-data (.getBytes "Secret data")
          invalid-data (.getBytes "Invalid data")
          keyset-handle (keyset-handles/generate-new :hmac-sha256-128bittag)
          mac (primitives/mac keyset-handle)
          tag (sut/compute mac
                           secret-data)]
      (is (thrown? GeneralSecurityException (sut/verify mac
                                                        tag
                                                        invalid-data))))))
