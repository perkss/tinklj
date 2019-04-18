(ns tinklj.acceptance.digital-signature-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer :all]
            [tinklj.keys.keyset-handle :refer [get-public-keyset-handle]]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.signature.digital-signature :as sut])
  (:import (java.security GeneralSecurityException)))

(register :signature)

(deftest digital-signature-sign-and-verify

  (testing "How to compute or verify a Mac (Message Authentication Code)"
    (let [plain-text "Secret Data to be Signed"
          private-keyset-handle (keyset-handles/generate-new :ecdsa-p256)
          signature (sut/sign
                     private-keyset-handle
                     plain-text)
          public-key-set-handle (get-public-keyset-handle private-keyset-handle)
          verify (sut/verify public-key-set-handle
                             signature
                             plain-text)]
      (is (not (= GeneralSecurityException verify))))))