(ns noughts-and-crosses.minmax)

(def piece-set #{"X" "O"})

(defn swap-piece [current-peice]
  (if (= current-peice "X")
    "O"
    "X"))

(defn empty-cell?
  [current-cell]
  (not (contains? piece-set current-cell)))

(defn has-player-won?
  "Returns true if player has won"
  [board piece]
  (let [[[a b c]
         [d e f]
         [g h i]] board]
    (cond
      (every? #(= piece %) [a b c]) true
      (every? #(= piece %) [d e f]) true
      (every? #(= piece %) [g h i]) true
      (every? #(= piece %) [a d g]) true
      (every? #(= piece %) [b e h]) true
      (every? #(= piece %) [c f i]) true
      (every? #(= piece %) [a e i]) true
      (every? #(= piece %) [c e g]) true
      )))

(defn board-is-full
  "Returns true if the board is full - as run after
  checking for winners true => draw"
  [board]
  (= 9 (count
        (filter #(contains? piece-set %)
          (flatten board)))))

(defn get-availible-moves [board]
  (for
    [[x row] (map-indexed vector board)
     [y value] (map-indexed vector row)
     :when (empty-cell? value)]
    [x y]))

(defn get-next-boards
  "Get a list of all available boards"
  [board piece]
  (loop [moves (get-availible-moves board) acc []]
    (if (empty? moves)
      acc
      (recur
        (rest moves)
        (conj acc (assoc-in board (first moves) piece))))))

(defn game-score [board piece]
  (cond
    (has-player-won? board piece) 1
    (has-player-won? board (swap-piece piece)) -1
    (board-is-full board) 0
    ))


;; Minmax solution
; 1. check if there is a winner - need to know if min-ing or max-ing... (-1 or 1)
; 2. check if board is full -> draw (0)
; 3. get all available moves
; 4. make all available moves.
; 5. repeat for each new board.
(defn minmax
  "Recursive minmax solution - danger stack overflow defo possible!!
  For use with 3x3 boards only"
  [board piece]
  ;; if game over return score
  (let [score (game-score board piece)]
    (if (not (nil? score))
      ; If game over return appropriate score.
      score
      ; else loop through new possible boards calling minimax
      (loop [best-score -1 boards (get-next-boards board (swap-piece piece))]
        (if (empty? boards)
          ; Can we get rid of this (magic) number?
          (* -1 best-score)
          (let [score (minmax (first boards) (swap-piece piece))]
            (recur (max score best-score) (rest boards))))))))

(defn return-next-board [board piece]
  "Returns a list of all the available next moves and their scores"
  (let [all-next-boards (get-next-boards board piece)
        board-map (zipmap all-next-boards (map #(minmax % piece) all-next-boards))]
    (key (first (sort-by val > board-map)))))