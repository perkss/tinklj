(ns tinklj.keysets.keyset-handle-parameters-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :as config]
            [tinklj.keys.keyset-handle :as ksh]
            [tinklj.keys.keyset-handle :refer [generate-new]])
  (:import (com.google.crypto.tink.aead AesGcmParameters AesGcmParameters$Variant)))

(deftest generate-from-parameters-test
  (testing "generate new using Parameters derived from existing KeyTemplate"
    (config/register :aead)
    ;; Build Parameters for AES-GCM directly via the Java Parameters builder
    (let [params (-> (AesGcmParameters/builder)
             (.setKeySizeBytes 16)
                     (.setIvSizeBytes 12)
                     (.setTagSizeBytes 16)
                     (.setVariant AesGcmParameters$Variant/TINK)
             (.build))
        handle (ksh/generate-new-from-parameters params)]
      (is (= 1 (.size handle))))))
