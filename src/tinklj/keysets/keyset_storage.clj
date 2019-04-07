(ns tinklj.keysets.keyset-storage
  (:import (com.google.crypto.tink CleartextKeysetHandle JsonKeysetWriter JsonKeysetReader KeysetHandle KmsClient))
  (:require [clojure.java.io :as io]
            [tinklj.keysets.integration.kms-client :as client]))

(defn write-clear-text-keyset-handle
  [keyset-handle filename]
  (CleartextKeysetHandle/write
   keyset-handle
   (JsonKeysetWriter/withFile (io/file filename))))

(defn load-clear-text-keyset-handle
  [filename]
  (CleartextKeysetHandle/read
   (JsonKeysetReader/withFile
    (io/file filename))))

(defn write-remote-kms-keyset-handle
  [^KeysetHandle keyset-handle ^KmsClient kms-client filename master-key-uri]
  (.write keyset-handle
          (JsonKeysetWriter/withFile (io/file filename))
          (client/get-aead kms-client master-key-uri)))

(defn load-remote-kms-keyset-handle
  [^KmsClient kms-client filename master-key-uri]
  (KeysetHandle/read (JsonKeysetReader/withFile (io/file filename))
                     (client/get-aead kms-client master-key-uri)))