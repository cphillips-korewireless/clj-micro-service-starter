(defproject micro-service "0.1.0-SNAPSHOT"
  :author "Cody Phillips"
  :description "A simple http service starter"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [http-kit "2.2.0"]
                 [clj-time "0.14.0"]
                 [compojure "1.6.0"]]
  :main ^:skip-aot micro-service.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
