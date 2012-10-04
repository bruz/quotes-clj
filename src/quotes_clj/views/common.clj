(ns quotes-clj.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial layout [& content]
            (html5
              [:head
               [:title "Quotes"]
               (include-css "/css/styles.css")]
              [:body
               [:div#center
                content]]))
