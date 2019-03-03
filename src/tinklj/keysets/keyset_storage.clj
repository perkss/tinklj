(ns tinklj.keysets.keyset-storage
  (:import (com.google.crypto.tink CleartextKeysetHandle JsonKeysetWriter JsonKeysetReader))
  (:require [clojure.java.io :as io] ))

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