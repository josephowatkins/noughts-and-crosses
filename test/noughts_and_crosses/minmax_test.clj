(ns noughts-and-crosses.minmax-test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.minmax :refer :all]))

;; Define boards, their associated empty spaces and next boards.

(def empty-board [["-" "-" "-"]
                  ["-" "-" "-"]
                  ["-" "-" "-"]])

(def empty-board-available-spaces (for [x (range 3) y (range 3)] [x y]))


(def full-board [["O" "X" "O"]
                 ["X" "X" "O"]
                 ["O" "O" "X"]])

(def full-board-available-spaces '())


(def x-to-move-next [["O" "X" "O"]
                     ["-" "-" "-"]
                     ["-" "-" "-"]])

(def x-to-move-next-available-spaces (for [x '(1 2) y '(0 1 2)] [x y]))

(def x-to-move-next-possible-boards '([["O" "X" "O"]
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


(def o-to-move-next [["X" "-" "-"]
                     ["O" "-" "-"]
                     ["X" "-" "-"]])

(def o-to-move-next-available-spaces (for [x '(0 1 2) y '(1 2)] [x y]))

(def o-to-move-next-possible-boards '([["X" "O" "-"]
                                       ["O" "-" "-"]
                                       ["X" "-" "-"]]

                                       [["X" "-" "O"]
                                        ["O" "-" "-"]
                                        ["X" "-" "-"]]

                                       [["X" "-" "-"]
                                        ["O" "O" "-"]
                                        ["X" "-" "-"]]

                                       [["X" "-" "-"]
                                        ["O" "-" "O"]
                                        ["X" "-" "-"]]

                                       [["X" "-" "-"]
                                        ["O" "-" "-"]
                                        ["X" "O" "-"]]

                                       [["X" "-" "-"]
                                        ["O" "-" "-"]
                                        ["X" "-" "O"]]))


(deftest check-get-availble-moves
  (is (= (get-available-moves empty-board) empty-board-available-spaces))
  (is (= (get-available-moves full-board) full-board-available-spaces))
  (is (= (get-available-moves x-to-move-next) x-to-move-next-available-spaces))
  (is (= (get-available-moves o-to-move-next) o-to-move-next-available-spaces))
  )

(deftest check-next-moves
  (is (= (get-next-boards x-to-move-next "X") x-to-move-next-possible-boards))
  (is (= (get-next-boards o-to-move-next "O") o-to-move-next-possible-boards))
  )


;; Difficult to test minmax but it must block a potential win
; Define boards and appropriate counters

(def o-win-in-one [["-" "X" "O"]
                   ["-" "O" "X"]
                   ["-" "-" "-"]])

(def counter-o-win-in-one [["-" "X" "O"]
                           ["-" "O" "X"]
                           ["X" "-" "-"]])

(def x-win-in-one [["O" "X" "O"]
                   ["-" "X" "-"]
                   ["-" "-" "-"]])

(def counter-x-win-in-one [["O" "X" "O"]
                           ["-" "X" "-"]
                           ["-" "O" "-"]])

(deftest minmax-must-block-opponent-win
  (is (= counter-o-win-in-one (generate-next-board o-win-in-one "X")))
  (is (= counter-x-win-in-one (generate-next-board x-win-in-one "O")))
  )