(ns noughts-and-crosses.minmax-test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.minmax :refer :all]))

(def empty-board [["-" "-" "-"]
                  ["-" "-" "-"]
                  ["-" "-" "-"]])
(def empty-board-moves (for [x (range 3) y (range 3)] [x y]))


(def full-board [["O" "X" "O"]
                 ["X" "X" "O"]
                 ["O" "O" "X"]])
(def full-board-moves '())

(def legal-for-x-to-move-next [["O" "X" "O"]
                               ["-" "-" "-"]
                               ["-" "-" "-"]])
(def moves-for-x (for [x '(1 2) y '(0 1 2)] [x y]))
(def next-moves-for-x '([["O" "X" "O"]
                         ["X" "-" "-"]
                         ["-" "-" "-"]]

                        [["O" "X" "O"]
                         ["-" "X" "-"]
                         ["-" "-" "-"]]

                        [["O" "X" "O"]
                         ["-" "-" "X"]
                         ["-" "-" "-"]]

                        [["O" "X" "O"]
                         ["-" "-" "-"]
                         ["X" "-" "-"]]

                        [["O" "X" "O"]
                         ["-" "-" "-"]
                         ["-" "X" "-"]]

                        [["O" "X" "O"]
                         ["-" "-" "-"]
                         ["-" "-" "X"]]))

(def legal-for-o-to-move-next [["X" "-" "-"]
                               ["O" "-" "-"]
                               ["X" "-" "-"]])
(def moves-for-o (for [x '(0 1 2) y '(1 2)] [x y]))


(deftest check-get-availble-moves
  (is (= (get-availible-moves empty-board) empty-board-moves))
  (is (= (get-availible-moves full-board) full-board-moves))
  (is (= (get-availible-moves legal-for-x-to-move-next) moves-for-x))
  (is (= (get-availible-moves legal-for-o-to-move-next) moves-for-o))
  )

(deftest check-next-moves
  (is (= (get-next-boards legal-for-x-to-move-next "X") next-moves-for-x))
  )

(def nearly-full-board [["-" "X" "O"]
                        ["-" "O" "X"]
                        ["-" "-" "-"]])

(print (return-next-board nearly-full-board "X"))