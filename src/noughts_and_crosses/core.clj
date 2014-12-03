(ns noughts-and-crosses.core
  (:require [noughts-and-crosses.board-check :as check]
            [ring.adapter.jetty :as jetty]
            [noughts-and-crosses.server :as server]))

; function takes in map of the board and
; current piece turn.

(defn illegal-map [map]
  (assoc map :legal false))
(defn legal-map [map]
  (assoc map :legal true))

(defn process-map [board-map]
  (let [{board :board piece :piece} board-map]
    (cond
      (not (check/board-size-legal? board)) (illegal-map board-map)
      (not (check/board-legal? board)) (illegal-map board-map)
      (not (check/legal-move-available? board piece)) (illegal-map board-map)
      :else (legal-map board-map)
      )))

; process look up board in database

; send response

; create response

(defn -main []
  (jetty/run-jetty server/main-routes {:port 3000}))