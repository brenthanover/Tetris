Welcome to Tetris!

Java project by Brent Hanover

My version of Tetris stays true to the original: the tetrominos, their colour scheme, rotation patterns,
and other aspects of the gameplay are as accurate as possible.


CONTROLS:
Spacebar - starts the game. In game, it drops tetromino to bottom of game board and spawns the next tetromino
Left arrow - move tetromino left
Right arrow - move tetromino right
A - rotate counter-clockwise
D - rotate clockwise
Music button - toggle music on and off. Music restarts at beginning of track when turned back on
Sound effects button - toggle the sound effects on and off
Mystery button - ??????
Pause button - pauses and unpauses game
Save button - saves state when game is unpaused and in play
Saves state for all aspects of the game, including tetrominos, the game board, score, lines cleared,
lines to clear, level, fall speed, button status, music etc
Load button - loads state when game is unpaused and in play
Load button loads all aspects of the game, see above. Returns the game with music playing if the music
was playing when the game was saved, and pauses the game.


RULES:
Rotate tetrominos and place them at the bottom of the game board.
Place tetrominos in rows, full rows will be cleared and score will be added
Tetrominos cannot rotate if they would be overlapping a game piece after rotating
Tetrominos cannot move through blocks on the board - they stop directly above them
Clear as many as possible and try to get a high score!
If you clear a certain number of lines, you will level up. The game board will reset to
blank and the lines to clear will reset to the original number of lines to clear multiplied
by the current level. Your score is also multiplied by your current level. However, the
fall speed of the tetrominos increase when you level up as well!


WHAT'S NEW:
Sound effects - try all four sound effects for 1, 2, 3, and 4 simultaneous line clears
              - button click
              - game start/game over
              - level up
S and Z blocks having four rotation states instead of two
If a tetromino will extend down into the board or past the bottom of the board during rotation,
the game will simply prevent you from doing so instead of rotating and moving the tetromino up


DESIGN PATTERNS:
GameBackground acts as the scoresheet and implements Observer.
Tetris adds GameBackground and MessagePrinter as observers, and acts as an observer for Game.
Game adds Tetris, Tetris.GameBackground, and MessagePrinter as observers

Board implements Iterable and its private class BoardIterator implements Iterator<Character>.
It goes over its boardGrid, which is a BLOCKS_WIDE x BLOCKS_HIGH matrix of characters. It lists
all of the characters sequentially, all elements in the first line, then second, then third, etc
