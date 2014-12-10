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
  (is (= (get-availible-moves empty-board) empty-board-available-spaces))
  (is (= (get-availible-moves full-board) full-board-available-spaces))
  (is (= (get-availible-moves x-to-move-next) x-to-move-next-available-spaces))
  (is (= (get-availible-moves o-to-move-next) o-to-move-next-available-spaces))
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

(deftest minmax-emptyboard
  (do
    (println (generate-next-board empty-board "X")))
  )

(deftest time-taken
  (do
    (swap! minmax-called (fn [_] 0))
    (swap! empty-boards (fn [_] 0))

    (def start (System/currentTimeMillis))
    (generate-next-board empty-board "X")
    (def end (System/currentTimeMillis))
    (println (str "Time taken: " (- end start)))
    (println (str "Minmax called: " @minmax-called))
    (println (str "Wins: " @wins))
    (println (str "Losses: " @losses))
    (println (str "Draws: " @draws))
    (println (str "Empty boards: " @empty-boards))

    )
  )

(deftest time-draw-function
  (time (board-is-full empty-board))
  (time (board-is-full full-board))
  )