# noughts-and-crosses (minmax)

## Explanation

A RESTful service to generate the next move in a noughts and crosses game.

Submit a board and the service will:
    
  1.  Check the move being requested is legal - e.g. placing an X on a board with 3 X's and 2 O's is illegal. 
  2.  Look up the board in DynamoDb.
  3.  If board does not exist create and store it
  4.  Return the board. 
    
## Guide

Before starting the application make sure that Dynamodb is configured correctly and running (change Dynamodb config in `database.clj`).
 
To start the app run:

    lein ring server-headless

The service exposes two endpoints `http://localhost:3000/X/` and `http://localhost:3000/O/`. Send a board to the appropriate end point to get the next move. 

## Example

Request (OSX terminal): `curl -X POST -H "Content-Type: application/json" -d '{"board": [["-", "-", "-"],["-", "O", "-"],["-", "X", "X"]]}' http://localhost:3000/O/`

Response: `{:next-move [["-" "-" "-"] ["-" "O" "-"] ["O" "X" "X"]], :piece "O", :board [["-" "-" "-"] ["-" "O" "-"] ["-" "X" "X"]]}`