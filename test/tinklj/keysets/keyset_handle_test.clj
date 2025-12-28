(ns tinklj.keysets.keyset-handle-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :as config]
            [tinklj.keys.keyset-handle :as ksh]))

(deftest generate-from-parameters-name-test
  (testing "generate entry from named parameters and build keyset"
    ;; Ensure AEAD parameters are registered
    (config/register :aead)
    (let [handle (ksh/generate-new-from-parameters-name "AES128_GCM")]
      (is (= 1 (.size handle))))))
