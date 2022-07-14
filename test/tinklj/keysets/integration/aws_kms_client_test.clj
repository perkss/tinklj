(ns tinklj.keysets.integration.aws-kms-client-test
  (:require [clojure.test :refer [deftest is testing]]
            [tinklj.keysets.integration.kms-client :as client]
            [tinklj.keysets.integration.aws-kms-client :refer [aws-kms-client]])
  (:import (com.google.crypto.tink.integration.awskms AwsKmsClient)))

(deftest aws-kms-client-register-test
  (testing "A new instance of the AWS client with uri is created"
    (is (= AwsKmsClient (type (aws-kms-client "aws-kms://register" "credentials_aws.cred"))))))

(deftest aws-kms-client-register-unbound-test
  (testing "A new instance of the AWS client with uri is created"
    (let [key-uri "aws-kms://register-unbound"
          client (aws-kms-client key-uri "")]
      (is (true? (client/does-support client key-uri))))))