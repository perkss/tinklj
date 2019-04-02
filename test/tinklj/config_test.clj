(ns tinklj.config-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :as sut]))

(deftest register-test
  (doseq [thing [:aead :daead :mac :signature :streaming]]
    (testing (str "config with type " (name thing))
      (is (nil? (sut/register thing)))))

  (testing "Config without a type"
      (is (nil? (sut/register))))

  (testing "Defaults to Standard config"
      (is (nil? (sut/register :foobar)))))

