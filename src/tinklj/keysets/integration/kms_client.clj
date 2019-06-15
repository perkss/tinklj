(ns tinklj.keysets.integration.kms-client
  (:import (com.google.crypto.tink KmsClient KmsClients)))

(defn does-support
  [^KmsClient client uri]
  (.doesSupport client uri))

(defn with-credentials
  [^KmsClient client credential-path]
  (.withCredentials client credential-path))

(defn with-default-credentials
  [^KmsClient client]
  (.withDefaultCredentials client))

(defn get-aead
  [^KmsClient client key-uri]
  (.getAead client key-uri))

(defn kms-client-add
  [kms-client]
  (KmsClients/add kms-client))