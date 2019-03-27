(ns tinklj.acceptance.digital-signature-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer :all]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.signature.digital-signature :as sut])
  (:import (java.security GeneralSecurityException)))

(register :signature)

(deftest digital-signature-sign-and-verify

  (testing "How to compute or verify a Mac (Message Authentication Code)"
    (let [data "Secret Data to be Signed"
          keyset-handle (keyset-handles/generate-new :ecdsa-p256)
          signature (sut/sign
                      keyset-handle
                      data)
          verify (sut/verify keyset-handle
                             signature
                             data)]
      (is (not (= GeneralSecurityException verify))))))