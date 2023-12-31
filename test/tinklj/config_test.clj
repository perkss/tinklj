(ns tinklj.config-test
  (:require
   [clojure.test :refer [deftest is testing]]
   [tinklj.config :as sut]))

(deftest init-test
  (doseq [thing [:aead :daead :mac :signature :streaming]]
    (testing (str "config with type " (name thing))
      (is (nil? (sut/register thing)))))

  (testing "Config without a type"
    (is (nil? (sut/register)))))
