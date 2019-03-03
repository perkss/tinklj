(defproject tinklj "0.1.0-SNAPSHOT"
  :description "A Clojure API for Google Tink Crypto Library"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.google.crypto.tink/tink "1.2.2"]]
  :profiles {:kaocha {:dependencies [[lambdaisland/kaocha "0.0-389"]]}}
  :aliases {"kaocha" ["with-profile" "+kaocha" "run" "-m" "kaocha.runner"]})
