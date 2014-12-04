(ns noughts-and-crosses.board-check_test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.board-check :refer :all]))


; define some boards
(def empty-board [["-" "-" "-"]
                  ["-" "-" "-"]
                  ["-" "-" "-"]])

(def full-board [["O" "X" "O"]
                 ["X" "X" "O"]
                 ["O" "O" "X"]])

(def legal-for-x-to-move-next [["O" "X" "O"]
                               ["-" "-" "-"]
                               ["-" "-" "-"]])

(def legal-for-o-to-move-next [["X" "-" "-"]
                               ["O" "-" "-"]
                               ["X" "-" "-"]])

(deftest x-and-o-move-legal
  (is (= (legal-move-available? empty-board "X") true))
  (is (= (legal-move-available? empty-board "O") true)))

(deftest no-legal-moves
  (is (= (legal-move-available? full-board "X") false))
  (is (= (legal-move-available? full-board "O") false)))

(deftest x-can-go?
  (is (= (legal-move-available? legal-for-x-to-move-next "X") true))
  (is (= (legal-move-available? legal-for-x-to-move-next "O") false)))

(deftest o-can-go?
  (is (= (legal-move-available? legal-for-o-to-move-next "O") true))
  (is (= (legal-move-available? legal-for-o-to-move-next "X") false))
  )

; define some boards with illegal numbers of rows or columns.
(def wrong-cols [["X"]
                 ["-" "-" "-"]
                 ["O" "O" "-"]])

(def wrong-rows [[] [] [] [] []])

(deftest check-number-of-columns
  (is (= (board-has-n-cols? wrong-cols 3) false))
  (is (= (board-has-n-cols? full-board 3) true))
  )


(deftest check-number-of-rows
  (is (= (board-has-n-rows? wrong-rows 3) false))
  (is (= (board-has-n-rows? full-board 3) true))
  )


; define board with illegal differience between number of X and O's.
(def illegal-board [["X" "X" "-"]
                    ["-" "X" "-"]
                    ["-" "O" "-"]])

(deftest illegal-move-finder
  (is (= (board-legal? illegal-board) false))
  (is (= (board-legal? empty-board) true))
  (is (= (board-legal? legal-for-o-to-move-next) true))
  (is (= (board-legal? legal-for-x-to-move-next) true))
  )

(def nested-str-vec [["1" "2" "3"] ["4" "5" "6"]])
(def nested-key-vec [[:1 :2 :3] [:4 :5 :6]])

(deftest convert-vector
  (is (= nested-key-vec (map-nested-vector str->keyword nested-str-vec))))
