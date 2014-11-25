# noughts-and-crosses

## Explanation
A RESTful service to make the next move in a noughts and crosses game.

Submit a board to a transactional endpoint and the service will:
    
  1.  check the move being requested is legal - e.g. placing an X on a board with 3 X's and 2 O's is illegal. 
  2.  Look up the board in DynamoDb.
  3.  If board does not exist create and store it
  4.  Return the board. 
    
### TODO
things that need to get done: 

  -  write functions that check if board legal
  -  write minmax implementation to work out next board
  -  set up tables for X and O - (can the board be the key?)
  -  Set up REST end points
    