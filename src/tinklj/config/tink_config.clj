(ns tinklj.config.tink-config
  (:import (com.google.crypto.tink.config TinkConfig)))

(defn register []
  (TinkConfig/register))
