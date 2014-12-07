(ns noughts-and-crosses.database
  (:require [taoensso.faraday :as far]
            [taoensso.nippy.tools :as nippy]))

;;; Database - serialised boards are used as index.
;; Define client options.

(def client-options
  {:access-key "<AWS_DYNAMODB_ACCESS_KEY>"  ; For DynamoDB Local, just put some random string
   :secret-key "<AWS_DYNAMODB_SECRET_KEY>"  ; For production, put your IAM keys here

   :endpoint "http://localhost:8000" }      ; This line is the only line that is added for DynamoDB Local.
  )


;; (setup-tables) called by ring :init to check tables exists.

(defn setup-tables []
  (do
    (far/ensure-table client-options :x-boards
                      [:current-board :b]
                      {:throughput {:read 1 :write 1}
                       :block? true })
    (far/ensure-table client-options :o-boards
                      [:current-board :b]
                      {:throughput {:read 1 :write 1}
                       :block? true })
    ))


;; Map strings to table names.

(def piece-table {"X" :x-boards "O" :o-boards})


;; Functions to check and save boards.

(defn check-if-board-exists [board piece]
  (let [result (far/query client-options (piece-table piece)
                          {:current-board [:eq (nippy/freeze board)]})]
    (if (not-empty result)
      ; TODO - not ideal!! but should only be one result...
      (nippy/thaw ((first result) :next-board))
      )))

(defn save-board [board next-board piece]
  (far/put-item client-options
                (piece-table piece)
                {:current-board (nippy/freeze board)
                 :next-board (nippy/freeze next-board)}))