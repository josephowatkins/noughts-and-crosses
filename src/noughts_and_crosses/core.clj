(ns noughts-and-crosses.core
  (require [clojure.java.io :as io]
           [clojure.data.json :as json])
  (:gen-class))

(defn map-peice [input peice]
  (if (= peice input)
    1
    0))

(def x? (partial map-peice :X))
(def o? (partial map-peice :O))

(defn count-peice [f board]
  (->> board
       (flatten)
       (map f)
       (reduce +)))

(defn move-legal? [piece board]
  (let [x (count-peice x? board)
        o (count-peice o? board)]
    (cond
      (= 9 (+ x o)) false
      (and (= piece :X) (<= x o)) true
      (and (= piece :O) (<= o x)) true
      :else false
      )))

(defn legal-board? [board]
  (let [x (count-peice x? board)
        o (count-peice o? board)]
    (<= (Math/abs (- x o)) 1)))


(with-open [reader (io/reader (io/resource "test.json"))]
  (json/read reader))

(defn str->keyword [s]
  (keyword s))

(defn map-nested-vector [f coll]
  (loop [vx coll acc []]
    (if (not-empty vx)
      (recur
        (rest vx)
        (conj acc (into [] (map f (first vx)))))
      acc)))

