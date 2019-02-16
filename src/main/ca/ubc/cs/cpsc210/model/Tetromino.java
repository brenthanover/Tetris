package ca.ubc.cs.cpsc210.model;

import java.awt.*;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;


public class Tetromino {

    /**
     * Constants
     */
    // Colors
    public static final Color I_COLOUR = Color.cyan;
    public static final Color J_COLOUR = Color.blue;
    public static final Color L_COLOUR = Color.orange;
    public static final Color O_COLOUR = Color.yellow;
    public static final Color S_COLOUR = Color.green;
    public static final Color T_COLOUR = Color.magenta.darker();
    public static final Color Z_COLOUR = Color.red;

    /**
     * Tetrominos
     */
    // I block: 5 wide, 1 high, I shape
    //          [X] [X] [X] [X] [X]
    public static final int[][] iTetrominoMatrix = {{1, 1, 1, 1}};

    // J block: 3 wide, 2 high, J shape
    //          [X] [X] [X]
    //          [ ] [ ] [X]
    public static final int[][] jTetrominoMatrix = {{1, 1, 1,}, {0, 0, 1}};

    // L block: 3 wide, 2 high, L shape
    //          [X] [X] [X]
    //          [X] [ ] [ ]
    public static final int[][] lTetrominoMatrix = {{1, 1, 1}, {1, 0, 0}};

    // O block: 2 wide, 2 high, square shape
    //          [X] [X]
    //          [X] [X]
    public static final int[][] oTetrominoMatrix = {{1, 1}, {1, 1}};

    // S block: 3 wide, 2 high, S shape
    //          [ ] [X] [X]
    //          [X] [X] [ ]
    public static final int[][] sTetrominoMatrix = {{0, 1, 1}, {1, 1, 0}};

    // T block: 3 wide, 2 high, T shape
    //          [X] [X] [X]
    //          [ ] [X] [ ]
    public static final int[][] tTetrominoMatrix = {{1, 1, 1}, {0, 1, 0}};

    // Z block: 3 wide, 2 high, S shape
    //          [X] [X] [ ]
    //          [ ] [X] [X]
    public static final int[][] zTetrominoMatrix = {{1, 1, 0}, {0, 1, 1}};

    /**
     * Declarations
     */
    Block block;

    /**
     * Variables
     */
    private Color tetrominoColour;
    private int[][] shape;
    private int tetrominoX;
    private int tetrominoY;
    private char label;
    private int rotationPosition = 1000;

    /**
     * Getters
     */
    public char getLabel() {
        return label;
    }

    public int getTetrominoX() {
        return tetrominoX;
    }

    public int getTetrominoY() {
        return tetrominoY;
    }

    public int[][] getShape() {
        return shape;
    }

    public Color getTetrominoColour() {
        return tetrominoColour;
    }

    /**
     * Setters
     */
    public void setTetrominoX(int x) {
        tetrominoX = x;
    }

    public void setTetrominoY(int y) {
        tetrominoY = y;
    }

    public void incrementRotationPosition() {
        rotationPosition++;
    }

    public void decrementRotationPosition() {
        rotationPosition--;
    }

    /**
     * Constructor
     */
    public Tetromino(int[][] shape, Color c, char label) {
        this.shape = shape;
        this.tetrominoColour = c;
        this.label = label;

        previewTetromino();
    }

    /**
     * Methods
     */
    public void initializeTetromino() {
        tetrominoX = (BLOCKS_WIDE - shape[0].length) / 2 * BLOCK_SIZE;
        tetrominoY = 0;
    }

    public void previewTetromino() {
        tetrominoX = BOARD_WIDTH / 2 - shape[0].length * BLOCK_SIZE / 2;
        tetrominoY = 14 * BLOCK_SIZE;
    }

    // moves tetromino down by one block
    public void fall() {
        tetrominoY += BLOCK_SIZE;
    }

    // moves tetromino left by one block
    public void moveLeft() {
        tetrominoX -= BLOCK_SIZE;
    }

    // moves tetromino right by one block
    public void moveRight() {
        tetrominoX += BLOCK_SIZE;
    }

    // transposes matrix
    public void transpose() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        int[][] output = new int[numCols][numRows];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                output[j][i] = shape[i][j];
            }
        }

        shape = output;
    }

    // flips matrix horizontally
    public void flipHorizontal() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        int[][] output = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                output[i][numCols - j - 1] = shape[i][j];
            }
        }

        shape = output;
    }

    // flips matrix vertically
    public void flipVertical() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        int[][] output = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            output[numRows - i - 1] = shape[i];
        }

        shape = output;
    }

    // rotates matrix clockwise
    public void rotateCW() {
        // reposition I tetromino
        if (shape.length == 4 || shape[0].length == 4) {
            repositionITetromino();
            //reposition other tetrominos except square
        } else if (shape.length != shape[0].length) {
            repositionOtherTetrominosCW();
        }

        transpose();
        flipHorizontal();
        rotationPosition++;

        keepTetrominoInBounds();
    }

    // repositions I tetromino to match classic tetris rotation pattern
    public void repositionITetromino() {
        if (rotationPosition % 2 == 0) {
            tetrominoX += 2 * BLOCK_SIZE;
            tetrominoY -= 2 * BLOCK_SIZE;
        } else {
            tetrominoX -= 2 * BLOCK_SIZE;
            tetrominoY += 2 * BLOCK_SIZE;
        }
    }

    // repositions 2x3 tetromino to match classic tetris CW rotation pattern
    public void repositionOtherTetrominosCW() {
        int n = rotationPosition % 4;
        switch (n) {
            case 0:
                tetrominoY -= BLOCK_SIZE;
                break;
            case 2:
                tetrominoX += BLOCK_SIZE;
                break;
            case 3:
                tetrominoX -= BLOCK_SIZE;
                tetrominoY += BLOCK_SIZE;
                break;
            default:
                break;
        }
    }

    // rotates matrix counter clockwise
    public void rotateCCw() {
        // reposition I tetromino
        if (shape.length == 4 || shape[0].length == 4) {
            repositionITetromino();
            //reposition other tetrominos except square
        } else if (shape.length != shape[0].length) {
            repositionOtherTetrominosCCw();
        }


        transpose();
        flipVertical();
        rotationPosition--;

        keepTetrominoInBounds();
    }

    // repositions 2x3 tetromino to match classic tetris CCW rotation pattern
    public void repositionOtherTetrominosCCw() {
        int n = rotationPosition % 4;
        switch (n) {
            case 1:
                tetrominoY += BLOCK_SIZE;
                break;
            case 3:
                tetrominoX -= BLOCK_SIZE;
                break;
            case 0:
                tetrominoX += BLOCK_SIZE;
                tetrominoY -= BLOCK_SIZE;
                break;
            default:
                break;
        }
    }

    // moves tetromino to back within the game board
    public void keepTetrominoInBounds() {
        // ensure block doesn't go off left side of board
        while (tetrominoX < 0) {
            tetrominoX += BLOCK_SIZE;
        }

        // ensure block doesn't go off right side of board
        while (tetrominoX + (shape[0].length * BLOCK_SIZE) > BOARD_WIDTH) {
            tetrominoX -= BLOCK_SIZE;
        }

        // ensure block doesn't go off top side of board
        while (tetrominoY < 0) {
            tetrominoY += BLOCK_SIZE;
        }

        // ensure block doesn't go off bottom side of board
        while (tetrominoY + (shape.length * BLOCK_SIZE) > BOARD_HEIGHT) {
            tetrominoY -= BLOCK_SIZE;
        }
    }

    // draw method not included in tests
    public void draw(Graphics g) {
        int toDrawX = tetrominoX;
        int toDrawY = tetrominoY;

        for (int[] row : shape) {
            for (int col : row) {
                if (col == 1) {
                    block = new Block(toDrawX, toDrawY, tetrominoColour);
                    block.draw(g);
                }
                toDrawX += BLOCK_SIZE;
            }
            toDrawX = tetrominoX;
            toDrawY += BLOCK_SIZE;
        }
    }
}