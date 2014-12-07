(ns noughts-and-crosses.core-test
  (:require [clojure.test :refer :all]
            [noughts-and-crosses.core :refer :all]))


(def legal-for-x [["X" "-" "-"]
                  ["O" "-" "-"]
                  ["O" "-" "-"]])

(def legal-for-o [["O" "-" "O"]
                  ["X" "-" "-"]
                  ["X" "X" "-"]])


(def map-legal-x {:board legal-for-x :piece "X"})
(def map-illegal-x {:board legal-for-o :piece "X"})

(def map-legal-o {:board legal-for-o :piece "O"})
(def map-illegal-o {:board legal-for-x :piece "O"})


(deftest test-process-map-should-throw-exception
  (is (thrown? Exception ((check-board map-illegal-x))))
  (is (thrown? Exception ((check-board map-illegal-o))))
  )

(deftest test-process-map
  (is (= (check-board map-legal-x) map-legal-x))
  (is (= (check-board map-legal-o) map-legal-o))
  )