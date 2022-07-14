(ns tinklj.keysets.integration.gcp-kms-client
  (:import (com.google.crypto.tink.integration.gcpkms GcpKmsClient)
           (com.google.crypto.tink KmsClients)
           (java.util Optional)))

(defn gcp-kms-client
  ([^String uri ^String credential-path]
   (let [opUri (if (or (nil? uri) (clojure.string/blank? uri)) (Optional/empty) (Optional/of uri))
         opCred (if (or (nil? credential-path) (clojure.string/blank? uri)) (Optional/empty) (Optional/of credential-path))
         _ (GcpKmsClient/register opUri opCred)
         client (KmsClients/get (.orElse opUri ""))]
     client)))