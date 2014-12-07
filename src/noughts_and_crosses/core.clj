(ns noughts-and-crosses.core
  (:require [noughts-and-crosses.checks :as checks]
            [noughts-and-crosses.minmax :as minmax]
            [noughts-and-crosses.database :as db]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]))

;;; Pipeline for processing board-map
;; First check board is OK.

(defn check-board
  "Takes a map and checks if :board entry exists and is in a legal state.
  Returns unchanged map."
  [board-map]
  (let [{board :board piece :piece} board-map]
    (-> board
      (checks/board-exists?)
      (checks/board-size-legal?)
      (checks/board-state-legal?)
      (checks/board-has-space?)
      (checks/legal-move-available? piece))
    board-map))


;; Check if next board is present in DB - if not create it using minmax

(defn save-and-return-next-board
  "Create new board, save it to db and return it."
  [board-map]
  (println board-map)
  (let [{board :board piece :piece} board-map
        next-board (minmax/generate-next-board board piece)]
    (do
      (db/save-board board next-board piece)
      (assoc board-map :next-board next-board))
    ))

(defn get-next-move
  "Look up board in db - if it exists return it. Otherwise create,
  save and return it."
  [board-map]
  ;look up board in db
  (if-let [next-board (db/check-if-board-exists (:board board-map) (:piece board-map))]
    ; if board exists assoc into map and return new map.
    (assoc board-map :next-move next-board)
    ; else create next-board save and assoc.
    (save-and-return-next-board board-map))
  )


;;; Server -

;; handler prints body and returns it

(defn handler [request]
  (let [body (get-in request [:body])]
    body))

(def get-body-map
  (wrap-json-body handler {:keywords? true}))

(defn add-player [board-map piece]
  (assoc board-map :piece piece))


;; Responses

(defn response-ok [body]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (str body)})

(defn response-error [body]
  {:status 500
   :headers {"Content-Type" "text/plain"}
   :body (str "Exception caught: " body)
   })


;; Process request -

(defn process-request [request piece]
  "Processes request."
  (try (-> request
           (get-body-map)
           (add-player piece)
           (check-board)
           (get-next-move)
           (response-ok))
       (catch Exception e
         (response-error (.getMessage e)))))


;; Define routes -

(defroutes main-routes
           (POST "/X/" request (process-request request "X"))
           (POST "/O/" request (process-request request "O"))
           (route/not-found "Incorrect end point"))