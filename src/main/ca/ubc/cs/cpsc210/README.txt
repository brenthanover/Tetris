Welcome to Tetris!

CPSC 210 term project by Brent Hanover

My version of Tetris stays true to the original: the tetrominos, colour scheme, rotation patterns,
and points system are all as accurate as possible


CONTROLS:
Spacebar - drops tetromino to bottom of game board and spawns the next tetromino
Left arrow - move tetromino left
Right arrow - move tetromino right
A - rotate counter-clockwise
D - rotate clockwise
Music button - toggle music on and off. Music restarts at beginning of track
Sound effects button - toggle the sound effects on and off
Mystery button - ??????
Pause button - pauses game and music. When unpaused, music begins where it left off
Save button - saves state when game is unpaused and in play
Load button - loads state when game is unpaused and in play


RULES:
Rotate tetrominos and place them at the bottom of the game board.
Place tetrominos in rows, full rows will be cleared and score will be added
Tetrominos cannot rotate if they would be overlapping a game piece after rotating
Tetrominos cannot move through blocks on the board - they stop directly above them
Clear as many as possible and try to get a high score!


WHAT'S NEW:
Sound effects - try all four sound effects for 1, 2, 3, and 4 simultaneous line clears
              - button click
              - game start/game over
S and Z blocks having four rotation states instead of two
If a tetromino will extend down into the board or past the bottom of the board during rotation,
the game will simply prevent you from doing so instead of rotating and moving the tetromino up


DESIGN PATTERNS:
GameBackground acts as the scoresheet and implements Observer. Tetris adds that observer and
notifies the observer when necessary
Board implements Iterable and its private class BoardIterator implements Iterator<Character>.
It goes over its boardGrid, which is a BLOCKS_WIDE x BLOCKS_HIGH matrix of characters. It lists
all of the characters sequentially, the first line, then the second, then the third, etc

