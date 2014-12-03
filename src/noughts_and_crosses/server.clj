(ns noughts-and-crosses.server
  (:require clojure.pprint
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]))


;; View functions
(defn view [x]
  (if (contains? #{"X" "O"} x)
    (str "<h1>You are playing as " x "</h1>" )
    (str "<h1>" x " is not recognised as a piece!</h1>")))

;; handler prints body and returns it
(defn handler [request]
  (let [body (get-in request [:body])]
    (prn body)
    body))

(def get-body-map
  (wrap-json-body handler {:keywords? true}))

(defn add-player [board-map piece]
  (assoc board-map :piece piece))

(defn create-response [body]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str body)})


; Is this how to thread the board through the processes?
(defn process-request [request]
  (-> request
      (get-body-map)
      (add-player "X")
      (create-response)))

(defroutes main-routes
           (POST "/X/" request process-request)
           (GET "/X/" _ (str "Hello form X!"))
           (GET "/O/" [body] (view body))
           (POST "/" request  )
           (route/not-found "Incorrect end point"))