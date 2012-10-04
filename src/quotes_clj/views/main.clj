(ns quotes-clj.views.main
  (:require [quotes-clj.views.common :as common]
            [quotes-clj.models.quote :as quotes]
            [noir.response :as resp])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial quote-type [{:keys [text quote_by type]}]
  (let [css-class
        (if (= type :current ) "current_quote" "past_quote")]
    [:li {:class css-class}
     [:blockquote [:p text]]
     [:p {:class "byline"} (str "-- " quote_by)]
     [:img {:src "/img/separator.gif"}]])
)

(defn quote-item
  ([type item]
    (quote-type (assoc item :type type)))
  ([item]
    (quote-type (assoc item :type :current))))

(defpartial main-page [items]
  (let [first-item (first items)
        remaining-items (rest items)]
    (common/layout
      [:h1 "Quotes"]
      [:p
       [:a {:href "/rss.xml"}
        "Subscribe"]]
      [:ul (quote-item :current first-item)
       (map quote-item remaining-items)]
      [:div#add_quote [:h2 "Add A Quote"]
       [:form {:action "/add" :method "post"}
        [:p [:label {:for "text"} "Quote"]
         [:br ]
         [:input#quote {:name "text" :size "30" :type "text"}]]
        [:p [:label {:for "quote_by"} "By"]
         [:br ]
         [:input#added_by {:name "quote_by" :size "30" :type "text"}]]
        [:p [:label {:for "verify"} "What's 1 plus 2?"]
         [:br ]
         [:input#quote {:name "verify" :size "30" :type "text"}]]
        [:p [:input#submit {:name "commit" :type "submit" :value "Submit"}]]]])))

(defpartial rss-item [item]
  [:item
   [:title (:text item)]
   [:link "http://quotes-clj.herokuapp.com"]
   [:description ""]
   [:pubDate (:timestamp item)]
   [:guid (str "http://quotes-clj.herokuapp.com/" (:id item))]])

(defpartial rss-page [items]
    (html
      [:?xml {:version "1.0" :encoding "UTF-8"}]
      [:rss {:version "2.0"}
       [:title "Some Quotes"]
       [:description "Nifty quotes."]
       [:link "http://quotes-clj.herokuapp.com"]
       (map rss-item items)]))

(defn verify-correct [value]
  (= value "3"))

(defpage "/" []
  (main-page (quotes/all)))

(defpage [:post "/add"] {:as quote}
  (if (verify-correct (:verify quote))
    (do
      (quotes/add! quote)
      (resp/redirect "/"))
    (common/layout
      [:p "Oops, time to work on those math skills."
       [:a {:href "/"} "Go Back"]])))

(defpage "/rss.xml" []
  (rss-page (quotes/all)))