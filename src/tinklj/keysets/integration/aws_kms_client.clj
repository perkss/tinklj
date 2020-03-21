(ns tinklj.keysets.integration.aws-kms-client
  (:import (com.google.crypto.tink.integration.awskms AwsKmsClient)))

(defn aws-kms-client
  ([] (AwsKmsClient.))
  ([uri] (AwsKmsClient. uri)))
