(ns noughts-and-crosses.core-test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.core :refer :all]))


(def legal-for-x [[:X :- :-]
                  [:O :- :-]
                  [:O :- :-]])

(def legal-for-o [[:O :- :O]
                  [:X :- :-]
                  [:X :X :-]])


(def map-legal-x {:board legal-for-x :piece :X})
(def map-illegal-x {:board legal-for-o :piece :X})

(def map-legal-o {:board legal-for-o :piece :O})
(def map-illegal-o {:board legal-for-x :piece :O})


(deftest test-process-map
  (is (= (:legal (process-map map-legal-x)) true))
  (is (= (:legal (process-map map-illegal-x)) false))
  (is (= (:legal (process-map map-legal-o)) true))
  (is (= (:legal (process-map map-illegal-o)) false))
  )

