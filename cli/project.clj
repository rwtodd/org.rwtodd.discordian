(defproject org-rwtodd.discordian/ddate "1.0"
  :description "A clojure version of the ddate utility"
  :url "https://github.com/rwtodd/org-rwtodd.discordian"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org-rwtodd.discordian/org-rwtodd.discordian "1.0"]]
  :main ^:skip-aot org-rwtodd.ddate
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
