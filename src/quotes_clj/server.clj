(ns quotes-clj.server
  (:require [noir.server :as server]))

(server/load-views "src/quotes_clj/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8090"))]
    (server/start port {:mode mode
                        :ns 'quotes-clj})))

