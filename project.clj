(defproject hoplon-modern-business "0.1.0-SNAPSHOT"
  :description "The StartBootstrap 'Modern Business' template ported to Hoplon"
  :url "http://github.com/oubiwann/hoplon-modern-business"
  :license {:name "Apache V2 License"
            :url "http://www.apache.org/licenses/LICENSE-2.0.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.16"]
                 [ring "1.2.1"]
                 [compojure "1.1.6"]]
  :source-paths ["src"]
  :jvm-opts ^:replace ["-Xmx512m" "-server"]
  :aot [devserver]
  :main devserver/run)
