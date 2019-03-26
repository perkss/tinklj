(ns tinklj.config-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :as sut])
  (:import (com.google.crypto.tink.config TinkConfig)
           (com.google.crypto.tink Registry)
           (com.google.crypto.tink.aead AeadConfig)
           (com.google.crypto.tink.daead DeterministicAeadConfig)
           (com.google.crypto.tink.mac MacConfig)
           (com.google.crypto.tink.signature SignatureConfig)
           (com.google.crypto.tink.streamingaead StreamingAeadConfig)))

(deftest register-test
  (doseq [thing [:aead :daead :mac :signature :streaming]]
    (testing (str "config with type " (name thing))
      (is (nil? (sut/register thing)))))

  (testing "Config without a type"
      (is (nil? (sut/register))))

  (testing "Defaults to Standard config"
      (is (nil? (sut/register :foobar)))))

