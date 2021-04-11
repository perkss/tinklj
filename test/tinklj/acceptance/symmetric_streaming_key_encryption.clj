(ns tinklj.acceptance.symmetric-streaming-key-encryption
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.config :refer [register]]
            [tinklj.primitives :as primitives]
            [tinklj.keys.keyset-handle :as keyset-handles]
            [clojure.java.io :refer [file output-stream input-stream]]
            [tinklj.streaming :as streaming])
  (:import (java.io FileOutputStream FileInputStream ByteArrayInputStream File)
           (java.nio ByteBuffer)))

(register)

(deftest symmetric-streaming-key-encryption
  (testing "Symmetric streaming key encryption encrypt a stream of plain text
     and then decrypt it as a stream"

    (let [plain-text "Secret data"
          keyset-handle (keyset-handles/generate-new :aes128-ctr-hmac-sha256-4kb)
          streaming-primitive (primitives/streaming keyset-handle)
          message-size 11
          byte-buffer (ByteBuffer/allocate message-size)
          associated-data (.getBytes "Salt")
          my-temp-file (File/createTempFile "encrypted-streaming-file" "")]

      ;; Write the streaming encrypted data
      (with-open [^FileOutputStream file-output-stream (FileOutputStream. my-temp-file)
                  ciphertext-destination (.getChannel file-output-stream)
                  in (input-stream (ByteArrayInputStream. (.getBytes plain-text)))
                  encrypting-channel (streaming/encrypting-channel
                                      streaming-primitive
                                      ciphertext-destination
                                      associated-data)]
        (.read in (.array byte-buffer))
        (streaming/encrypting-channel-write encrypting-channel byte-buffer))

      ;; Read the decrypted streaming data
      (with-open [file-input-stream (FileInputStream. my-temp-file)
                  decrypting-channel (streaming/decrypting-channel
                                      streaming-primitive
                                      (.getChannel file-input-stream)
                                      associated-data)]
        (let [buf (.clear byte-buffer)
              decrypted-size (streaming/decrypting-channel-read decrypting-channel buf)
              decrypted-data (String. (.array buf))]
          (is (= decrypted-data plain-text))
          (is (= message-size decrypted-size)))))))