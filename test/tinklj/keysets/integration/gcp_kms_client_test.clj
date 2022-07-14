(ns tinklj.keysets.integration.gcp-kms-client-test
  (:require [clojure.test :refer [deftest testing is]]
            [tinklj.keysets.integration.gcp-kms-client :refer [gcp-kms-client]]
            [tinklj.keysets.integration.kms-client :as client])
  (:import (com.google.crypto.tink.integration.gcpkms GcpKmsClient)
           (java.io File)))

(def credential-file-path (.getAbsolutePath (File. "test/tinklj/keysets/integration/credential.json")))

(deftest gcp-kms-client-register-test
  (testing "A new instance of the GCP client is created"
    (is (= GcpKmsClient (type (gcp-kms-client "gcp-kms://register" credential-file-path))))))

(deftest gcp-kms-client-does-support-test
  (testing "A new instance of the GCP client with uri is created"
    (let [key-uri "gcp-kms://register-unbound"
          client (gcp-kms-client  key-uri credential-file-path)]
      (is (true? (client/does-support client key-uri))))))