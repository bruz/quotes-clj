(defproject quotes-clj "0.1.0-SNAPSHOT"
            :description "A trivial database-backed web app"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [noir "1.2.1"]
                           [clj-record "1.1.3"]
                           [org.clojars.narma/postgresql "9.1-902.jdbc4"]
                           [clj-time "0.4.4"]]
            :main quotes-clj.server)

