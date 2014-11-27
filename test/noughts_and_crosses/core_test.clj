(ns noughts-and-crosses.core-test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.core :refer :all]))

(def empty-board [[:- :- :-]
                  [:- :- :-]
                  [:- :- :-]])

(def full-board [[:O :X :O]
                 [:X :X :O]
                 [:O :O :X]])

(def legal-for-x [[:O :X :O]
                  [:- :- :-]
                  [:- :- :-]])

(def legal-for-o [[:X :- :-]
                  [:O :- :-]
                  [:X :- :-]])

(deftest x-and-o-move-legal
  (is (= (move-legal? :X empty-board) true))
  (is (= (move-legal? :O empty-board) true)))

(deftest no-legal-moves
  (is (= (move-legal? :X full-board) false))
  (is (= (move-legal? :O full-board) false)))

(deftest x-can-go?
  (is (= (move-legal? :X legal-for-x) true))
  (is (= (move-legal? :O legal-for-x) false)))

(deftest o-can-go?
  (is (= (move-legal? :O legal-for-o) true))
  (is (= (move-legal? :X legal-for-o) false))
  )

(def illegal-board [[:X :X :-]
                    [:- :X :-]
                    [:- :O :-]])

(deftest illegal-move-finder
  (is (= (legal-board? illegal-board) false))
  (is (= (legal-board? empty-board) true))
  (is (= (legal-board? legal-for-o) true))
  (is (= (legal-board? legal-for-x) true))
  )

(def nested-str-vec [["1" "2" "3"] ["4" "5" "6"]])
(def nested-key-vec [[:1 :2 :3] [:4 :5 :6]])

(deftest convert-vector
  (is (= nested-key-vec (map-nested-vector str->keyword nested-str-vec))))