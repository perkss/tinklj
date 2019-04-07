(ns tinklj.keysets.integration.aws-kms-client-test
  (:require [clojure.test :refer [deftest is testing]])
  (:require [tinklj.keysets.integration.kms-client :as client]
            [tinklj.keysets.integration.aws-kms-client :refer [aws-kms-client]])
  (:import (com.google.crypto.tink.integration.awskms AwsKmsClient)))

(deftest aws-kms-client-instance-test
  (testing "A new instance of the AWS client is created"
    (is (= AwsKmsClient (type (aws-kms-client))))))

(deftest aws-kms-client-instance-uri-test
  (testing "A new instance of the AWS client with uri is created"
    (is (= AwsKmsClient (type (aws-kms-client "aws-kms://"))))))

(deftest aws-kms-client-does-support-test
  (testing "A new instance of the AWS client with uri is created"
    (let [client (aws-kms-client)]
      (is (true? (client/does-support client "aws-kms://"))))))