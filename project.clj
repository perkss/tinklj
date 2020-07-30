(defproject tinklj "0.1.2-SNAPSHOT"
  :description "A Clojure API for Google Tink Crypto Library. Offering a range of cryptographic techniques that are simple and easy to use."
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [com.google.crypto.tink/tink "1.4.0"]
                 ;; https://mvnrepository.com/artifact/com.google.crypto.tink/tink-awskms
                 [com.google.crypto.tink/tink-awskms "1.4.0"]
                 ;; https://mvnrepository.com/artifact/com.google.crypto.tink/tink-gcpkms
                 [com.google.crypto.tink/tink-gcpkms "1.4.0"]]
  :plugins [[lein-cljfmt "0.6.4"]]
  :profiles {:kaocha {:dependencies [[lambdaisland/kaocha "0.0-389"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]})
