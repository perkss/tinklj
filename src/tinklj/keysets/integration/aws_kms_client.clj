(ns tinklj.keysets.integration.aws-kms-client
  (:import (com.google.crypto.tink.integration.awskms AwsKmsClient)
           (com.google.crypto.tink KmsClients)
           (java.util Optional)))

(defn aws-kms-client
  ([^String uri ^String credential-path]
   (let [opUri (if (or (nil? uri) (clojure.string/blank? uri)) (Optional/empty) (Optional/of uri))
         opCred (if (or (nil? credential-path) (clojure.string/blank? uri)) (Optional/empty) (Optional/of credential-path))
         _ (AwsKmsClient/register opUri opCred)
         client (KmsClients/get (.orElse opUri ""))]
     client)))
