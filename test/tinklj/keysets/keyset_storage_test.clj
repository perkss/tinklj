(ns tinklj.keysets.keyset-storage-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.keysets.keyset-storage :as keyset-storage]
            [tinklj.config :refer [register]]
            [clojure.java.io :as io])
  (:import (com.google.crypto.tink KeysetHandle)))

(deftest write-clear-text-handle-test
  (testing "Testing that a file is written and then read and it is of the
  expected type"
    (let [_ (register)
          keyset-handle (keyset-handles/generate-new :aes128-gcm)
          filename "my-test-keyset.json"
          _ (keyset-storage/write-clear-text-keyset-handle keyset-handle filename)
          keyset (keyset-storage/load-clear-text-keyset-handle filename)]
      (is (= KeysetHandle (type keyset)))
      (io/delete-file filename))))

(deftest rotate-should-add-new-key-and-set-primary-key-id
  (testing "Rotate key should add new and set a primary key id")
  (let [keyset-handle (keyset-handles/generate-new :aes128-gcm)
        keyset-template (:hmac-sha256-128bittag keyset-handles/key-templates)
        keyset-info (.getKeysetInfo (keyset-storage/rotate-keyset-handle keyset-handle keyset-template))]
    (is (= 2 (.getKeyInfoCount keyset-info)))))
