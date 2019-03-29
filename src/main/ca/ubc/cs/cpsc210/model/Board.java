package ca.ubc.cs.cpsc210.model;

import java.awt.*;
import java.util.*;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Game.*;

public class Board implements Iterable<Character> {

    // Board is represented by matrix by blocks wide by blocks high
    // once a tetromino falls and becomes static it gets added to the board
    // e - empty
    // i - I tetromino - cyan
    // j - J tetromino - blue
    // l - L tetromino - orange
    // o - O tetromino - yellow
    // s - S tetromino - green
    // t - T tetromino - purple (magenta.darker())
    // z - Z tetromino - red

    /**
     * Constants
     */
    private Map<Character, Color> colourHashMap = new HashMap<>();

    /**
     * Declarations
     */
    private char[][] boardGrid;

    /**
     * Constructor
     */
    // EFFECTS: constructs Board object
    public Board() {
        boardGrid = new char[BLOCKS_HIGH][BLOCKS_WIDE];

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                boardGrid[i][j] = 'e';
            }
        }

        colourHashMap.put('i', I_COLOUR);
        colourHashMap.put('j', J_COLOUR);
        colourHashMap.put('l', L_COLOUR);
        colourHashMap.put('o', O_COLOUR);
        colourHashMap.put('s', S_COLOUR);
        colourHashMap.put('t', T_COLOUR);
        colourHashMap.put('z', Z_COLOUR);
    }

    /**
     * Getters
     */
    public char[][] getBoardGrid() {
        return boardGrid;
    }

    /**
     * Setters
     */
    public void setBoardGridBlock(int row, int col, char val) {
        boardGrid[row][col] = val;
    }

    public void setBoardGrid(char[][] newBoard) {
        boardGrid = newBoard;
    }

    /**
     * Methods
     */
    // EFFECTS: return true if tetromino is at bottom of window
    public boolean isTetrominoTouchingBottom(Tetromino t) {
        boolean answer = false;
        int[][] tetrominoShape = t.getShape();
        int tetrominoYCoord = (t.getTetrominoY()) / BLOCK_SIZE;

        if (tetrominoYCoord + tetrominoShape.length == BLOCKS_HIGH) {
            answer = true;
        }

        return answer;
    }

    // EFFECTS: return true if tetromino is directly above a non-empty block in the grid
    public boolean isTetrominoAboveBlock(Tetromino t) {
        boolean answer = false;
        int[][] tetrominoShape = t.getShape();
        int tetrominoXCoord = (t.getTetrominoX()) / BLOCK_SIZE;
        int tetrominoYCoord = (t.getTetrominoY()) / BLOCK_SIZE;

        // if touching another tetromino
        for (int j = 0; j < tetrominoShape[0].length; j++) {
            for (int i = tetrominoShape.length - 1; i >= 0; i--) {

                // separate if statement to prevent out of array exception
                if (tetrominoYCoord + tetrominoShape.length < BLOCKS_HIGH) {
                    // true if tetromino has full block with a board block directly below
                    if (tetrominoShape[i][j] == 1
                            && boardGrid[tetrominoYCoord + i + 1][tetrominoXCoord + j] != 'e') {
                        answer = true;
                        break;
                    }
                }
            }
        }

        return answer;
    }

    // MODIFIES: this
    // EFFECTS: takes a tetromino and freezes it in place to the board
    public void freezeTetrominoToBoard(Tetromino t) {
        char tetrominoColour = t.getLabel();
        int[][] tetrominoShape = t.getShape();

        // x, y locations in board coordinates
        int blockXPos = (t.getTetrominoX()) / BLOCK_SIZE;
        int blockYPos = (t.getTetrominoY()) / BLOCK_SIZE;

        // save tetromino locations to board
        for (int i = 0; i < tetrominoShape.length; i++) {
            for (int j = 0; j < tetrominoShape[0].length; j++) {
                if (tetrominoShape[i][j] == 1) {
                    boardGrid[i + blockYPos][j + blockXPos] = tetrominoColour;
                }
            }
        }
    }

    // MODIFIES: Tetromino t
    // EFFECTS: immediately drops the tetromino to the bottom of the board
    public void dropTetrominoToBottom(Tetromino t) {
        while (!isTetrominoTouchingBottom(t) && !isTetrominoAboveBlock(t)) {
            t.fall();
        }
    }

    // REQUIRES: has at least one full row on the board, ie isRowFull is true for at least one line
    // MODIFIES: this
    // EFFECTS: removes the first full row, starting with the closest full row to the top
    public void clearRow() {
        char[][] output = new char[BLOCKS_HIGH][BLOCKS_WIDE];


        for (int n = 0; n < boardGrid.length; n++) {
            if (isRowFull(boardGrid[n])) {
                for (int a = 0; a < n; a++) {
                    output[a + 1] = boardGrid[a];
                }
                for (int b = n + 1; b < BLOCKS_HIGH; b++) {
                    output[b] = boardGrid[b];
                }
                for (int b = 0; b < boardGrid[0].length; b++) {
                    output[0][b] = 'e';
                }
                boardGrid = output;

                break;
            }
        }
    }

    // EFFECTS: returns the number of full rows in the board
    public int countFullRows() {
        int rowsToClear = 0;

        for (char[] row : boardGrid) {
            if (isRowFull(row)) {
                rowsToClear++;
            }
        }

        return rowsToClear;
    }

    // EFFECTS: produces true if the given char[] does not contain 'e'
    public boolean isRowFull(char[] row) {
        for (char c : row) {
            if (c == 'e') {
                return false;
            }
        }

        return true;
    }

    // EFFECTS: produce true if new tetromino overlaps grid
    public boolean isGameOver(Tetromino t) {
        int numCols = t.getShape()[0].length;

        if (numCols == 4) {
            return isBoardObstructingNewIBlock();
        } else if (numCols == 2) {
            return isBoardObstructingNewOBlock();
        } else {
            return isBoardObstructingNewOtherBlocks(t);
        }
    }

    // EFFECTS: produce true if board has block in position where new I block would appear
    public boolean isBoardObstructingNewIBlock() {
        for (int j = 3; j < 7; j++) {
            if (boardGrid[0][j] != 'e') {
                return true;
            }
        }

        return false;
    }

    // EFFECTS: produce true if board has block in position where new O block would appear
    public boolean isBoardObstructingNewOBlock() {
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j < 6; j++) {
                if (boardGrid[i][j] != 'e') {
                    return true;
                }
            }
        }

        return false;
    }

    // EFFECTS: produce true if board has block in position where new all other blocks would appear
    public boolean isBoardObstructingNewOtherBlocks(Tetromino t) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardGrid[i][j + 3] != 'e'
                        && t.getShape()[i][j] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    // EFFECTS: Returns true if tetromino cannot move left without hitting the window edge or a block
    public boolean isTetrominoMovementRestrictedOnLeft(Tetromino t) {
        int tetrominoPosX = t.getTetrominoX() / BLOCK_SIZE;
        int tetrominoPosY = t.getTetrominoY() / BLOCK_SIZE;

        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                if (t.getShape()[i][j] == 1
                        && (tetrominoPosX == 0
                        || boardGrid[tetrominoPosY + i][tetrominoPosX + j - 1] != 'e')) {
                    return true;
                }
            }
        }

        return false;
    }

    // EFFECTS: returns true if tetromino cannot move right without hitting the window edge or a block
    public boolean isTetrominoMovementRestrictedOnRight(Tetromino t) {
        int tetrominoPosX = t.getTetrominoX() / BLOCK_SIZE;
        int tetrominoPosY = t.getTetrominoY() / BLOCK_SIZE;

        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                if (t.getShape()[i][j] == 1
                        && (tetrominoPosX + t.getShape()[0].length == BLOCKS_WIDE
                        || boardGrid[tetrominoPosY + i][tetrominoPosX + j + 1] != 'e')) {
                    return true;
                }
            }
        }

        return false;
    }

    // EFFECTS: return true if the tetromino can rotate CW without overlapping board
    public boolean canRotateCW(Tetromino t) {
        t.rotateCW();

        if (isTetrominoOverlappingBoard(t)) {
            t.rotateCCw();
            return false;
        }

        t.rotateCCw();
        return true;
    }

    // EFFECTS: return true if the tetromino can rotate CCW without overlapping board
    public boolean canRotateCCw(Tetromino t) {
        t.rotateCCw();

        if (isTetrominoOverlappingBoard(t)) {
            t.rotateCW();
            return false;
        }

        t.rotateCW();
        return true;
    }

    // EFFECTS: produces true if tetromino is overlapping non-empty block in grid
    public boolean isTetrominoOverlappingBoard(Tetromino t) {
        int tetrominoPosX = t.getTetrominoX() / BLOCK_SIZE;
        int tetrominoPosY = t.getTetrominoY() / BLOCK_SIZE;

        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                if (boardGrid[tetrominoPosY + i][tetrominoPosX + j] != 'e'
                        && t.getShape()[i][j] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    // MODIFIES: this
    // EFFECTS:  resets all blocks on board to empty
    public void resetBoard() {
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                boardGrid[i][j] = 'e';
            }
        }
    }

    // EFFECTS: draw method not included in tests
    public void draw(Graphics g) {
        int blockXPos = 0;
        int blockYPos = 0;
        Block block;
        Color blockColour;

        // skip if 'e', ie grid should be empty at that location
        // else draw block corresponding to colour
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                if (boardGrid[i][j] != 'e') {
                    blockColour = colourHashMap.get(boardGrid[i][j]);
                    block = new Block(blockXPos, blockYPos, blockColour);
                    block.draw(g);
                }

                blockXPos += BLOCK_SIZE;
            }
            blockYPos += BLOCK_SIZE;
            blockXPos = 0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Board board = (Board) o;

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                if (boardGrid[i][j] != board.getBoardGrid()[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(boardGrid);
    }

    // EFFECTS:  returns an iterator of type BoardIterator
    @Override
    public Iterator<Character> iterator() {
        return new BoardIterator();
    }

    private class BoardIterator implements Iterator<Character> {
        private int colIndex;
        private int rowIndex;

        // EFFECTS:  constructor for BoardIterator
        private BoardIterator() {
            colIndex = 0;
            rowIndex = 0;
        }

        // EFFECTS:  return false if colIndex and rowIndex both no longer refer to elements in matrix, else true
        @Override
        public boolean hasNext() {
            return (colIndex * rowIndex < BLOCKS_WIDE * BLOCKS_HIGH - BLOCKS_WIDE - 1);
        }

        // EFFECTS:  returns each column in row, then moves to next row, until no more rows and columns
        @Override
        public Character next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (colIndex >= BLOCKS_WIDE) {
                rowIndex++;
                colIndex = 0;
            }

            return boardGrid[rowIndex][colIndex++];
        }
    }
}
