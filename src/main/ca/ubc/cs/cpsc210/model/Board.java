package ca.ubc.cs.cpsc210.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class Board {


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
    private char[][] boardGrid = new char[BLOCKS_HIGH][BLOCKS_WIDE];
    private Map<Character, Color> colourHashMap = new HashMap<>();

    /**
     * Getters
     */
    public char[][] getBoardGrid() {
        return boardGrid;
    }

    /**
     * Setters
     */
    public void setBoardGrid(int row, int col, char val) {
        boardGrid[row][col] = val;
    }

    /**
     * Constructor
     */
    public Board() {
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
     *  Methods
     */
    //return true if tetromino is at bottom of window
    public boolean isTetrominoTouchingBottom(Tetromino t) {
        boolean answer = false;
        int[][] tetrominoShape = t.getShape();
        int tetrominoYCoord = (t.getTetrominoY()) / BLOCK_SIZE;

        if (tetrominoYCoord + tetrominoShape.length == BLOCKS_HIGH) {
            answer = true;
        }

        return answer;
    }

    //return true if tetromino is directly above a non-empty block in the grid
    public boolean isTetrominoAboveBlock(Tetromino t) {
        boolean answer = false;
        int[][] tetrominoShape = t.getShape();
        int tetrominoXCoord = (t.getTetrominoX()) / BLOCK_SIZE;
        int tetrominoYCoord = (t.getTetrominoY()) / BLOCK_SIZE;

        // if touching another tetromino
        for (int j = 0; j < tetrominoShape[0].length; j++) {
            for (int i = tetrominoShape.length - 1; i >= 0; i--) {

                // separate if statement to prevent out of array exception
                if (tetrominoYCoord + i + 1 < BLOCKS_HIGH) {
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

    // takes a tetromino and freezes it in place to the board
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

    // immediately drops the tetromino to the bottom of the board
    public void dropTetrominoToBottom(Tetromino t) {
        while (!isTetrominoTouchingBottom(t) && !isTetrominoAboveBlock(t)) {
            t.fall();
        }
    }

    // removes the first full row, starting from the top
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

    // returns the number of full rows in the board
    public int countFullRows() {
        int rowsToClear = 0;

        for (char[] row : boardGrid) {
            if (isRowFull(row)) {
                rowsToClear++;
            }
        }

        return rowsToClear;
    }

    // produces true if the given char[] does not contain 'e'
    public boolean isRowFull(char[] row) {
        for (char c : row) {
            if (c == 'e') {
                return false;
            }
        }
        return true;
    }

    // produce true if new tetromino overlaps grid
    public boolean isGameOver(Tetromino t) {
        int numCols = t.getShape()[0].length;

        if (numCols == 4) {
            return isObstructingIBlock();
        } else if (numCols == 2) {
            return isObstructingOBlock();
        } else {
            return isObstructingOtherBlocks(t);
        }
    }

    // prevents I block from appearing at top of game board
    public boolean isObstructingIBlock() {
        for (int j = 3; j < 7; j++) {
            if (boardGrid[0][j] != 'e') {
                return true;
            }
        }

        return false;
    }

    // prevents O block from appearing at top of game board
    public boolean isObstructingOBlock() {
        for (int i = 0; i < 2; i++) {
            for (int j = 4; j < 6; j++) {
                if (boardGrid[i][j] != 'e') {
                    return true;
                }
            }
        }

        return false;
    }

    // prevents other blocks from appearing at top of game board
    public boolean isObstructingOtherBlocks(Tetromino t) {
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

    // returns true if tetromino cannot move left without hitting a wall or a block
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

    // returns true if tetromino cannot move right without hitting a wall or a block
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

    // return true if the tetromino can rotate CW without overlapping board
    public boolean canRotateCW(Tetromino t) {
        t.rotateCW();

        if (isTetrominoOverlappingBoard(t)) {
            t.rotateCCw();
            return false;
        }

        t.rotateCCw();
        return true;
    }

    // return true if the tetromino can rotate CCW without overlapping board
    public boolean canRotateCCw(Tetromino t) {
        t.rotateCCw();

        if (isTetrominoOverlappingBoard(t)) {
            t.rotateCW();
            return false;
        }

        t.rotateCW();
        return true;
    }

    // produces true if tetromino is overlapping non-empty block in grid
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

    // draw method not included in tests
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
}
