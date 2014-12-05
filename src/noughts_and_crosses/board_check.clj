(ns noughts-and-crosses.board-check
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json])
  (:gen-class))

; Provide functions to check board is in the right state / legal form before passing to move generator stage.

(defn x? [piece]
  (= "X" piece))

(defn o? [piece]
  (= "O" piece))

(defn count-pieces [f board]
  (->> board
       (flatten)
       (filter f)
       (count)))

(defn board-has-n-rows?
  [board rows]
  (= rows (count board)))

(defn board-has-n-cols?
  [board cols]
  (every?
    #(= cols %) (map count board)))

(defn board-size-legal?
  "Check board has the right size - 3 x 3"
  [board]
  (cond
     (not (board-has-n-rows? board 3)) false
     (not (board-has-n-cols? board 3)) false
     :else true
     ))


(defn board-legal?
  "Check the difference between X and O is within legal limit- i.e. ∆ <= 1.
  (Included to give more detailed feedback to user on error.)"
  [board]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (<= (Math/abs (- x o)) 1)))


(defn legal-move-available?
  "Check the board is not full & and the difference between X and O permits
  a move to be made."
  [board piece]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (cond
      (= 9 (+ x o)) false
      (and (= piece "X") (<= x o)) true
      (and (= piece "O") (<= o x)) true
      :else false
      )))