(ns tinklj.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.core :as sut])
  (:import (com.google.crypto.tink KeyManager)))

(defn key-manager [] (reify KeyManager
                       (getKeyType [this] "")))

(deftest init-test
  (doseq [thing [:aead :daead :mac :signature :streaming]]
    (testing (str "config with type " (name thing))
      (is (nil? (sut/init! thing)))))

  (testing "Config without a type"
    (is (nil? (sut/init!))))

  (testing "Can be provided with a custom key manager"
    (is (nil? (sut/init! (key-manager))))))

