package ca.ubc.cs.cpsc210.model;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

import static ca.ubc.cs.cpsc210.ui.Game.*;


public class Tetromino {

    /**
     * Constants
     */
    public static final Color I_COLOUR = Color.cyan;
    public static final Color J_COLOUR = Color.blue;
    public static final Color L_COLOUR = Color.orange;
    public static final Color O_COLOUR = Color.yellow;
    public static final Color S_COLOUR = Color.green;
    public static final Color T_COLOUR = Color.magenta.darker();
    public static final Color Z_COLOUR = Color.red;

    /**
     * Tetromino Matrices
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
    private Block block;

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
     * Constructor
     */
    // EFFECTS: constructs Tetromino object
    public Tetromino(int[][] shape, Color c, char label) {
        this.shape = shape;
        this.tetrominoColour = c;
        this.label = label;

        previewTetromino();
    }

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

    public int getRotationPosition() {
        return rotationPosition;
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

    public void setRotationPosition(int rp) {
        rotationPosition = rp;
    }

    /**
     * Methods
     */
    // MODIFIES: this
    // EFFECTS:  places tetromino at top of board
    public void initializeTetromino() {
        tetrominoX = (BLOCKS_WIDE - shape[0].length) / 2 * BLOCK_SIZE;
        tetrominoY = 0;
    }

    // MODIFIES: this
    // EFFECTS:  places tetromino in centre of preview window on right side of the game screen
    public void previewTetromino() {
        tetrominoX = BOARD_WIDTH / 2 - shape[0].length * BLOCK_SIZE / 2;
        tetrominoY = 15 * BLOCK_SIZE - BLOCK_SIZE / 2 * shape.length;
    }

    // MODIFIES: this
    // EFFECTS:  moves tetromino down by one block
    public void fall() {
        tetrominoY += BLOCK_SIZE;
    }

    // MODIFIES: this
    // EFFECTS:  moves tetromino left by one block
    public void moveLeft() {
        tetrominoX -= BLOCK_SIZE;
    }

    // MODIFIES: this
    // EFFECTS:  moves tetromino right by one block
    public void moveRight() {
        tetrominoX += BLOCK_SIZE;
    }

    // MODIFIES: this
    // EFFECTS:  transposes shape field
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

    // MODIFIES: this
    // EFFECTS:  flips matrix horizontally, ie rows stay the same, column order is reversed
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

    // MODIFIES: this
    // EFFECTS:  flips matrix vertically, ie columns stay the same, row order is reversed
    public void flipVertical() {
        int numRows = shape.length;
        int numCols = shape[0].length;
        int[][] output = new int[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            output[numRows - i - 1] = shape[i];
        }

        shape = output;
    }

    // MODIFIES: this
    // EFFECTS:  rotates matrix clockwise
    public void rotateCW() {
        // reposition tetromino according to shape
        if (shape.length == 4 || shape[0].length == 4) {
            repositionITetromino();
        } else if (shape.length != shape[0].length) {
            repositionOtherTetrominosCW();
        }

        // rotate matrix
        transpose();
        flipHorizontal();
        rotationPosition++;

        keepTetrominoInBounds();
    }

    // MODIFIES: this
    // EFFECTS:  rotates matrix counter clockwise
    public void rotateCCw() {
        // reposition tetromino according to shape
        if (shape.length == 4 || shape[0].length == 4) {
            repositionITetromino();
        } else if (shape.length != shape[0].length) {
            repositionOtherTetrominosCCw();
        }

        // rotate matrix
        transpose();
        flipVertical();
        rotationPosition--;
        keepTetrominoInBounds();
    }

    // REQUIRES: current tetromino is an I (straight) tetromino
    // MODIFIES: this
    // EFFECTS:  repositions I tetromino to match classic tetris rotation pattern
    public void repositionITetromino() {
        if (rotationPosition % 2 == 0) {
            tetrominoX += 2 * BLOCK_SIZE;
            tetrominoY -= 2 * BLOCK_SIZE;
        } else {
            tetrominoX -= 2 * BLOCK_SIZE;
            tetrominoY += 2 * BLOCK_SIZE;
        }
    }

    // REQUIRES: current tetromino is any tetromino but an I (straight) or O (square) tetromino
    // MODIFIES: this
    // EFFECTS:  repositions 2x3 tetromino to match classic tetris CW rotation pattern
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

    // REQUIRES: current tetromino is any tetromino but an I (straight) or O (square) tetromino
    // MODIFIES: this
    // EFFECTS:  repositions 2x3 tetromino to match classic tetris CCW rotation pattern
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

    // MODIFIES: this
    // EFFECTS: moves tetromino to back within the game board if it's moved out of bounds from rotating
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

    // EFFECTS: draw method not included in tests
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tetromino tetromino = (Tetromino) o;
        if (shape.length != tetromino.getShape().length
                || shape[0].length != tetromino.getShape()[0].length) {
            return false;
        }
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != tetromino.getShape()[i][j]) {
                    return false;
                }
            }
        }

        return getTetrominoX() == tetromino.getTetrominoX() && getTetrominoY() == tetromino.getTetrominoY()
                && getLabel() == tetromino.getLabel() && rotationPosition == tetromino.rotationPosition
                && getTetrominoColour().equals(tetromino.getTetrominoColour());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getTetrominoColour(), getTetrominoX(), getTetrominoY(), getLabel(), rotationPosition);
        result = 31 * result + Arrays.hashCode(getShape());
        return result;
    }
}