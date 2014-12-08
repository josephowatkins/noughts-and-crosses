(defproject noughts-and-crosses "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.json "0.2.5"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [ring/ring-json "0.3.1"]
                 [compojure "1.2.2"]
                 [com.taoensso/faraday "1.5.0" :exclusions [org.clojure/clojure]]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler noughts-and-crosses.core/main-routes
         :init noughts-and-crosses.database/setup-tables}
  :main ^:skip-aot noughts-and-crosses.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
