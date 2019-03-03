(ns tinklj.keysets.keyset-storage-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [tinklj.keysets.keyset-storage :as keyset-storage]
            [tinklj.config.tink-config :refer [register]]))

(deftest write-clear-text-handle-test
  (testing "Testing that a file is written and then read"
    (register)
    (let [keyset-handle (keyset-handles/generate-new keyset-handles/AES128-GCM)
          filename "my-test-keyset.json"]
      (keyset-storage/write-clear-text-keyset-handle keyset-handle filename))))
