(ns noughts-and-crosses.board-check
  (:require [clojure.java.io :as io]
            [clojure.data.json :as json])
  (:gen-class))


; Provide functions to check board is in the right state / legal form before passing to move generator stage.

; helper functions
(defn x? [piece]
  (= :X piece))

(defn o? [piece]
  (= :O piece))

(defn count-pieces [f board]
  (->> board
       (flatten)
       (filter f)
       (count)))

; functions to check board is the right size - 3 by 3.
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


(defn board-legal? [board]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (<= (Math/abs (- x o)) 1)))


; fucntion to check if legal move is available
(defn legal-move-available?
  "Check that the board is not full & and the difference between X and O
  permits a move to be made."
  [board piece]
  (let [x (count-pieces x? board)
        o (count-pieces o? board)]
    (cond
      (= 9 (+ x o)) false
      (and (= piece :X) (<= x o)) true
      (and (= piece :O) (<= o x)) true
      :else false
      )))


(defn str->keyword [s]
  (keyword s))

(defn map-nested-vector [f coll]
  (loop [vx coll acc []]
    (if (not-empty vx)
      (recur
        (rest vx)
        (conj acc (into [] (map f (first vx)))))
      acc)))


(with-open [reader (io/reader (io/resource "test.json"))]
  (json/read reader))