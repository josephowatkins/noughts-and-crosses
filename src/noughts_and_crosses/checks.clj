(ns noughts-and-crosses.checks
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json])
  (:gen-class))

;;; Provide functions to check board is in the right state / legal form before
;;; passing to move generator stage.

;; Helper functions

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

(defn board-exists?
  "Returns true if board exists. Throws IllegalArgumentException if no board
  is found."
  [board]
  (if (nil? board)
    (throw (IllegalArgumentException. "No board found."))
    board))

(defn board-size-legal?
  "Checks the board is the right size - 3 x 3. Throws IllegalArgumentExeption
  if not."
  [board]
  (if (and (board-has-n-rows? board 3) (board-has-n-cols? board 3))
    board
    (throw (IllegalArgumentException. "Board is not the right size."))))

(defn board-state-legal?
  "Check the difference between X and O is within legal limit- i.e. ∆ <= 1.
  Throws IllegalArgumentException if not."
  [board]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (if (<= (Math/abs (- x o)) 1)
      board
      (throw (IllegalArgumentException. "Board is not in a legal state.")))))

(defn board-has-space?
  "Check if there are available spaces on the board. Throws IllegalArgumentException
  if it not."
  [board]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (if (< (+ x o) 9)
      board
      (throw (IllegalArgumentException. "Board is full.")))))

(defn legal-move-available?
  "Check the board is not full & and the difference between X and O permits
  a move to be made."
  [board piece]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (cond
      (and (= piece "X") (<= x o)) board
      (and (= piece "O") (<= o x)) board
      :else (throw (IllegalArgumentException. "No legal moves are available.")))))

; TODO - temporal coupling between (board-has-space?) and (legal-move-available?)...