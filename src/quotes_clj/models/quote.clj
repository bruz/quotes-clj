(ns quotes-clj.models.quote
  (:require clj-record.boot
            [clj-time.format :as tform]
            [clj-time.core :as ctime]
            [clj-time.coerce :as coerce]))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user (System/getenv "DATABASE_USER")
   :password (System/getenv "DATABASE_PASSWORD")
   :subname (System/getenv "DATABASE_SUBNAME")})

(clj-record.core/init-model
  :table-name "quotes")

(def date-format (tform/formatter "YYY/MM/dd" (ctime/default-time-zone)))

(defn all []
  (find-by-sql ["select * from quotes order by added_at DESC"]))

(defn add! [quote]
  (let [ts (ctime/now)
        {:keys [text quote_by]} quote]
    (create {:text text
             :added_at (coerce/to-timestamp ts)
             :quote_by quote_by})))