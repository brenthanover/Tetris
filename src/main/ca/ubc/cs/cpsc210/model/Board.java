package ca.ubc.cs.cpsc210.model;

import java.awt.*;

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

    /**
     * Variables
     */
    private int linesCleared = 0;


    /**
     * Constructor
     */
    public Board() {
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                boardGrid[i][j] = 'e';
            }
        }
    }

    /**
     * Methods
     */
    public void draw(Graphics g) {
        int xPos = 0;
        int yPos = 0;
        Block block;

        // skip if 'e', ie grid should be empty at that location
        // else draw block corresponding to colour
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                switch (boardGrid[i][j]) {
                    case 'i':
                        block = new Block(xPos, yPos, I_COLOUR);
                        block.draw(g);
                        break;
                    case 'j':
                        block = new Block(xPos, yPos, J_COLOUR);
                        block.draw(g);
                        break;
                    case 'l':
                        block = new Block(xPos, yPos, L_COLOUR);
                        block.draw(g);
                        break;
                    case 'o':
                        block = new Block(xPos, yPos, O_COLOUR);
                        block.draw(g);
                        break;
                    case 's':
                        block = new Block(xPos, yPos, S_COLOUR);
                        block.draw(g);
                        break;
                    case 't':
                        block = new Block(xPos, yPos, T_COLOUR);
                        block.draw(g);
                        break;
                    case 'z':
                        block = new Block(xPos, yPos, Z_COLOUR);
                        block.draw(g);
                        break;
                    default:
                        break;
                }

                xPos += BLOCK_SIZE;
            }
            yPos += BLOCK_SIZE;
            xPos = 0;
        }
    }

    public boolean isTetrominoTouchingBottom(Tetromino t) {
        boolean answer = false;
        int[][] tShape = t.getShape();
        int tYCoord = (t.getY()) / BLOCK_SIZE;

        if (tYCoord + tShape.length == BLOCKS_HIGH) {
            answer = true;
        }

        return answer;
    }

    public boolean isTetrominoAboveBlock(Tetromino t) {
        boolean answer = false;
        int[][] tShape = t.getShape();
        int tXCoord = (t.getX()) / BLOCK_SIZE;
        int tYCoord = (t.getY()) / BLOCK_SIZE;
        int xPos;
        int yPos;

        // if touching another tetromino
        for (int j = 0; j < tShape[0].length; j++) {
            for (int i = tShape.length - 1; i >= 0; i--) {

                // separate if statement to prevent out of array exception
                if (tYCoord + i + 1 < BLOCKS_HIGH) {
                    // true if tetromino has full block with a board block directly below
                    if (tShape[i][j] == 1
                            && boardGrid[tYCoord + i + 1][tXCoord + j] != 'e') {
                        answer = true;
                        break;
                    }
                }
            }
        }

        return answer;
    }


    public void freezeTetrominoToBoard(Tetromino t) {
        char tetrominoColour = t.getLabel();
        int[][] tShape = t.getShape();

        // x, y locations in board coordinates
        int xBlockPos = (t.getX()) / BLOCK_SIZE;
        int yBlockPos = (t.getY()) / BLOCK_SIZE;

        // save tetromino locations to board
        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                if (tShape[i][j] == 1) {
                    boardGrid[i + yBlockPos][j + xBlockPos] = tetrominoColour;
                }
            }
        }
    }

    public void clearRow() {
        for (int n = 0; n < boardGrid.length; n++) {
            if (isRowFull(boardGrid[n])) {
                for (int a = n; a > 0; a--) {
                    boardGrid[a] = boardGrid[a - 1];
                }
                for (int b = 0; b < boardGrid[0].length; b++) {
                    boardGrid[0][b] = 'e';
                }
                linesCleared++;
                break;
            }
        }
    }

    public int countFullRows() {
        int rowsToClear = 0;

        for (int n = 0; n < boardGrid.length; n++) {
            if (isRowFull(boardGrid[n])) {
                rowsToClear++;
            }
        }

        return rowsToClear;
    }

    private boolean isRowFull(char[] row) {
        for (char c : row) {
            if (c == 'e') {
                return false;
            }
        }
        return true;
    }

    // produce true if tetromino clashes with grid
    public boolean isGameOver(Tetromino t) {
        int numCols = t.getShape()[0].length;

        if (numCols == 4) {
            for (int j = 3; j < 7; j++) {
                if (boardGrid[0][j] != 'e') {
                    return true;
                }
            }
        } else if (numCols == 3) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (boardGrid[i][j + 3] != 'e'
                            && t.getShape()[i][j] == 1) {
                        return true;
                    }
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (int j = 4; j < 6; j++) {
                    if (boardGrid[i][j] != 'e') {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void dontOverlapBlocksWhenMoving(Tetromino t) {
        //TODO: make it so the blocks can't travel sideways over eachother
    }

    public void dontOverlapBlocksWhenRotating(Tetromino t) {
        //TODO: make it so the blocks don't overlap when rotating pieces
    }
}
