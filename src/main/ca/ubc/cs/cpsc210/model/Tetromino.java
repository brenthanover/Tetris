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
    private Color c;
    private int[][] shape = {{}};
    private int x;
    private int y;
    private char label;
    private int rotationPosition = 1000;

    /**
     * Getters
     */
    public char getLabel() {
        return label;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int[][] getShape() {
        return shape;
    }

    /**
     * Constructor
     */
    public Tetromino(int[][] shape, Color c, char label) {
        this.shape = shape;
        this.c = c;
        this.label = label;

        previewTetromino();
    }

    public void initializeTetromino() {
        x = (BLOCKS_WIDE - shape[0].length) / 2 * BLOCK_SIZE;
        y = 0;
    }

    public void previewTetromino() {
        x = BOARD_WIDTH / 2 - shape[0].length * BLOCK_SIZE / 2;
        y = 14 * BLOCK_SIZE;
    }

    // render tetromino on screen
    public void draw(Graphics g) {
        int xPos = x;
        int yPos = y;

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == 1) {
                    block = new Block(xPos, yPos, c);
                    block.draw(g);
                }
                xPos += BLOCK_SIZE;
            }
            xPos = x;
            yPos += BLOCK_SIZE;
        }
    }

    public void fall() {
        y += BLOCK_SIZE;
    }

    public void moveLeft() {
        x -= BLOCK_SIZE;
    }

    public void moveRight() {
        x += BLOCK_SIZE;
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
            if (rotationPosition % 2 == 0) {
                x += 2 * BLOCK_SIZE;
                y -= 2 * BLOCK_SIZE;
            } else {
                x -= 2 * BLOCK_SIZE;
                y += 2 * BLOCK_SIZE;
            }
            //reposition other tetrominos
        } else if (shape.length != shape[0].length) {
            int n = rotationPosition % 4;
            switch (n) {
                case 0:
                    y -= BLOCK_SIZE;
                    break;
                case 2:
                    x += BLOCK_SIZE;
                    break;
                case 3:
                    x -= BLOCK_SIZE;
                    y += BLOCK_SIZE;
                    break;
                default:
                    break;
            }
        }

        transpose();
        flipHorizontal();
        rotationPosition++;

        keepTetrominoInBounds();
    }

    // rotates matrix counter clockwise
    public void rotateCCW() {
        // reposition I tetromino
        if (shape.length == 4 || shape[0].length == 4) {
            if (rotationPosition % 2 == 0) {
                x += 2 * BLOCK_SIZE;
                y -= 2 * BLOCK_SIZE;
            } else {
                x -= 2 * BLOCK_SIZE;
                y += 2 * BLOCK_SIZE;
            }
            //reposition other tetrominos
        } else if (shape.length != shape[0].length) {
            int n = rotationPosition % 4;
            switch (n) {
                case 1:
                    y += BLOCK_SIZE;
                    break;
                case 3:
                    x -= BLOCK_SIZE;
                    break;
                case 0:
                    x += BLOCK_SIZE;
                    y -= BLOCK_SIZE;
                    break;
                default:
                    break;
            }
        }


        transpose();
        flipVertical();
        rotationPosition--;

        keepTetrominoInBounds();
    }

    private void keepTetrominoInBounds() {
        // ensure block doesn't go off left side of board
        while (x < 0) {
            x += BLOCK_SIZE;
        }

        // ensure block doesn't go off right side of board
        while (x + (shape[0].length * BLOCK_SIZE) > BOARD_WIDTH) {
            x -= BLOCK_SIZE;
        }

        // ensure block doesn't go off top side of board
        while (y < 0) {
            y += BLOCK_SIZE;
        }

        // ensure block doesn't go off bottom side of board
        while (y + (shape.length * BLOCK_SIZE) > BOARD_HEIGHT) {
            y -= BLOCK_SIZE;
        }

    }
}