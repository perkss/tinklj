(ns tinklj.keysets.keytemplate-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :as config]
            [tinklj.keys.keyset-handle :as ksh])
  (:import (com.google.crypto.tink.aead AesGcmParameters AesGcmParameters$Variant)))

(deftest keytemplate-create-and-convert-test
  (testing "KeyTemplate.createFrom(Parameters) and toParameters roundtrip"
    (config/register :aead)
    (let [params (-> (AesGcmParameters/builder)
                     (.setKeySizeBytes 16)
                     (.setIvSizeBytes 12)
                     (.setTagSizeBytes 16)
                     (.setVariant AesGcmParameters$Variant/TINK)
                     (.build))
          kt (ksh/keytemplate-create-from-parameters params)
          params2 (ksh/keytemplate-to-parameters kt)
          handle (ksh/generate-new-from-keytemplate kt)]
      (is (.equals params params2))
      (is (= 1 (.size handle))))))
