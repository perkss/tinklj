(ns tinklj.keysets.integration.gcp-kms-client
  (:import (com.google.crypto.tink.integration.gcpkms GcpKmsClient)))

(defn gcp-kms-client
  ([] (GcpKmsClient.))
  ([uri] (GcpKmsClient. uri)))