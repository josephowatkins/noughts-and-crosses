(ns noughts-and-crosses.checks_test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.checks :refer :all]))


;; Define some boards:

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

(def wrong-columns [["X" "-" "-" "-"]
                    ["O" "-" "-" "-"]
                    ["X" "-" "-" "-"]])

(def wrong-rows [["-" "-" "-"]
                 ["-" "-" "-"]
                 ["-" "-" "-"]
                 ["-" "-" "-"]])

(def illegal-board [["X" "X" "-"]
                    ["-" "X" "-"]
                    ["-" "O" "-"]])

;; Test helper functions (count-pieces), (board-has-n-rows?), (board-has-n-columns?) -

(deftest check-count
  (is (= (count-pieces o? full-board) 5))
  (is (= (count-pieces x? full-board) 4))
  )

(deftest check-number-of-columns
  (is (= (board-has-n-cols? wrong-columns 3) false))
  (is (= (board-has-n-cols? full-board 3) true))
  )

(deftest check-number-of-rows
  (is (= (board-has-n-rows? wrong-rows 3) false))
  (is (= (board-has-n-rows? full-board 3) true))
  )


;; Test (board-exists?) -

(deftest throws-exception-when-board-is-nil
  (is (thrown-with-msg? IllegalArgumentException #"No board found."
                        (board-exists? nil)))
  )


;; Test (board-size-legal?) -

(deftest throws-exception-if-board-size-wrong
  (is (thrown-with-msg? IllegalArgumentException #"Board is not the right size."
                        (board-size-legal? wrong-columns)))
  (is (thrown-with-msg? IllegalArgumentException #"Board is not the right size."
                        (board-size-legal? wrong-rows)))
  )


;; Test (board-state-legal?) -

(deftest throws-exception-if-board-state-not-legal
  (is (thrown-with-msg? IllegalArgumentException #"Board is not in a legal state."
                        (board-state-legal? illegal-board)))
  )

(deftest return-board-if-state-legal
  (is (= (board-state-legal? empty-board) empty-board))
  (is (= (board-state-legal? legal-for-o-to-move-next) legal-for-o-to-move-next))
  (is (= (board-state-legal? legal-for-x-to-move-next) legal-for-x-to-move-next))
  )


;; Test (board-has-space?) -

(deftest throws-exception-if-board-full
  (is (thrown-with-msg? IllegalArgumentException #"Board is full."
                        (board-has-space? full-board)))
  )


;; Test (legal-move-available?) -

(deftest x-and-o-move-legal
  (is (= (legal-move-available? empty-board "X") empty-board))
  (is (= (legal-move-available? empty-board "O") empty-board))
  )


(deftest x-can-go?
  (is (= (legal-move-available? legal-for-x-to-move-next "X") legal-for-x-to-move-next))
  (is (thrown-with-msg? IllegalArgumentException #"No legal moves are available."
                        (legal-move-available? legal-for-x-to-move-next "O")))
  )

(deftest o-can-go?
  (is (= (legal-move-available? legal-for-o-to-move-next "O") legal-for-o-to-move-next))
  (is (thrown-with-msg? IllegalArgumentException #"No legal moves are available."
                        (legal-move-available? legal-for-o-to-move-next "X")))
  )