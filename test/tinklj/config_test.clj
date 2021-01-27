(ns tinklj.config-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.config :as sut])
  (:import (com.google.crypto.tink KeyManager)))

(defn key-manager [] (reify KeyManager
                       (getKeyType [this] "")))

(deftest init-test
  (doseq [thing [:aead :daead :mac :signature :streaming]]
    (testing (str "config with type " (name thing))
      (is (nil? (sut/register thing)))))

  (testing "Config without a type"
    (is (nil? (sut/register))))

  (testing "Can be provided with a custom key manager"
    (is (nil? (sut/register (key-manager))))))

