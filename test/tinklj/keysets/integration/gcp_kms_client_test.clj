(ns tinklj.keysets.integration.gcp-kms-client-test
  (:require [clojure.test :refer [deftest testing is]])
  (:require [tinklj.keysets.integration.gcp-kms-client :refer [gcp-kms-client]]
            [tinklj.keysets.integration.kms-client :as client])
  (:import (com.google.crypto.tink.integration.gcpkms GcpKmsClient)))

(deftest gcp-kms-client-test-instance
  (testing "A new instance of the GCP client is created"
    (is (= GcpKmsClient (type (gcp-kms-client))))))

(deftest gcp-kms-client-test-instance-uri
  (testing "A new instance of the GCP client with uri is created"
    (is (= GcpKmsClient (type (gcp-kms-client "gcp-kms://"))))))

(deftest gcp-kms-client-does-support-test
  (testing "A new instance of the GCP client with uri is created"
    (let [client (gcp-kms-client)]
      (is (true? (client/does-support client "gcp-kms://"))))))