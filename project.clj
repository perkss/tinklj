(defproject tinklj "0.1.15-SNAPSHOT"
  :description "A Clojure API for Google Tink Crypto Library. Offering a range of cryptographic techniques that are simple and easy to use."
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.google.crypto.tink/tink "1.20.0"]
                 ;; https://mvnrepository.com/artifact/com.google.crypto.tink/tink-awskms
                 [com.google.crypto.tink/tink-awskms "1.10.1"]
                 ;; https://mvnrepository.com/artifact/com.google.crypto.tink/tink-gcpkms
                 [com.google.crypto.tink/tink-gcpkms "1.10.0"]
                 [com.google.http-client/google-http-client-jackson2 "1.45.0"]]
  :license {:name "Apache License 2.0"
            :url "http://www.apache.org/licenses/" }
  :repositories [["clojars" {:sign-releases false}]]
  :plugins [[lein-cljfmt "0.6.4"]]
  :profiles {:kaocha {:dependencies [[lambdaisland/kaocha "0.0-389"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]})
