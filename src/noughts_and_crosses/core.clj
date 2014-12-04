(ns noughts-and-crosses.core
  (:require [noughts-and-crosses.board-check :as check]
            [noughts-and-crosses.minmax :as minmax]))

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

(defn create-next-move [board-map]
  (let [{board :board piece :piece} board-map]
    (assoc board-map :next-move (minmax/return-next-board board piece))))

; send response

; create response