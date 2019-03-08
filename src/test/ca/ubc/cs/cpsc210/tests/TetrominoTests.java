package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Tetris.*;
import static org.junit.jupiter.api.Assertions.*;

public class TetrominoTests {

    private Tetromino iTetromino;
    private Tetromino jTetromino;
    private Tetromino oTetromino;
    private int[][] jMatrixTransposed = {{1, 0}, {1, 0}, {1, 1}};
    private int[][] jMatrixHorizontalFlipped = {{1, 1, 1}, {1, 0, 0}};
    private int[][] jMatrixVerticalFlipped = {{0, 0, 1}, {1, 1, 1}};
    private int[][] jMatrixRotatedCW = {{0, 1}, {0, 1}, {1, 1}};
    private int[][] jMatrixTwiceRotated = {{1, 0, 0}, {1, 1, 1}};
    private int[][] jMatrixRotatedCCW = {{1, 1}, {1, 0}, {1, 0}};
    private int[][] iMatrixRotated = {{1}, {1}, {1}, {1}};

    @BeforeEach 
    public void setup() {
        iTetromino = new Tetromino(iTetrominoMatrix, I_COLOUR, 'i');
        jTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        oTetromino = new Tetromino(oTetrominoMatrix, O_COLOUR, 'o');

    }

    @Test
    public void testConstructor() {
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(I_COLOUR, iTetromino.getTetrominoColour());
        assertEquals('i', iTetromino.getLabel());
        assertEquals(BOARD_WIDTH / 2 - iTetromino.getShape()[0].length * BLOCK_SIZE / 2, iTetromino.getTetrominoX());
        assertEquals(14 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testInitializeTetromino() {
        assertEquals(BOARD_WIDTH / 2 - iTetromino.getShape()[0].length * BLOCK_SIZE / 2, iTetromino.getTetrominoX());
        assertEquals(14 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.initializeTetromino();
        assertEquals((BLOCKS_WIDE - iTetromino.getShape()[0].length) / 2 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
    }

    @Test
    public void testPreviewTetromino() {
        assertEquals(BOARD_WIDTH / 2 - iTetromino.getShape()[0].length * BLOCK_SIZE / 2, iTetromino.getTetrominoX());
        assertEquals(14 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.initializeTetromino();
        assertEquals((BLOCKS_WIDE - iTetromino.getShape()[0].length) / 2 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.previewTetromino();
        assertEquals(BOARD_WIDTH / 2 - iTetromino.getShape()[0].length * BLOCK_SIZE / 2, iTetromino.getTetrominoX());
        assertEquals(14 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testFall() {
        iTetromino.initializeTetromino();
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.fall();
        assertEquals(BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.fall();
        assertEquals(BLOCK_SIZE * 2, iTetromino.getTetrominoY());
    }

    @Test
    public void testMoveLeft() {
        iTetromino.setTetrominoX(BLOCK_SIZE);
        assertEquals(BLOCK_SIZE, iTetromino.getTetrominoX());
        iTetromino.moveLeft();
        assertEquals(0, iTetromino.getTetrominoX());
    }

    @Test
    public void testMoveRight() {
        iTetromino.setTetrominoX(BLOCK_SIZE);
        assertEquals(BLOCK_SIZE, iTetromino.getTetrominoX());
        iTetromino.moveRight();
        assertEquals(BLOCK_SIZE * 2, iTetromino.getTetrominoX());
    }

    @Test
    public void testTransposeMatrix() {
        assertEquals(jTetrominoMatrix, jTetromino.getShape());
        jTetromino.transpose();
        assertArrayEquals(jMatrixTransposed, jTetromino.getShape());
    }

    @Test
    public void testHorizontalFlipMatrix() {
        assertEquals(jTetrominoMatrix, jTetromino.getShape());
        jTetromino.flipHorizontal();
        assertArrayEquals(jMatrixHorizontalFlipped, jTetromino.getShape());

    }

    @Test
    public void testVerticalFlipMatrix() {
        assertEquals(jTetrominoMatrix, jTetromino.getShape());
        jTetromino.flipVertical();
        assertArrayEquals(jMatrixVerticalFlipped, jTetromino.getShape());

    }

    @Test
    public void testRepositionITetromino() {
        iTetromino.initializeTetromino();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.fall();
        iTetromino.fall();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.repositionITetromino();
        iTetromino.incrementRotationPosition();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.repositionITetromino();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRepositionOtherTetrominoCW() {
        jTetromino.initializeTetromino();
        assertEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.fall();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCW();
        jTetromino.incrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCW();
        jTetromino.incrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCW();
        jTetromino.incrementRotationPosition();
        assertEquals(4 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCW();
        jTetromino.incrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
    }

    @Test
    public void testRepositionOtherTetrominoCCW() {
        jTetromino.initializeTetromino();
        assertEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.fall();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCCw();
        jTetromino.decrementRotationPosition();
        assertEquals(4 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCCw();
        jTetromino.decrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCCw();
        jTetromino.decrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.repositionOtherTetrominosCCw();
        jTetromino.decrementRotationPosition();
        assertEquals(3 * BLOCK_SIZE, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
    }

    @Test
    public void testRotateSquareTetromino() {
        oTetromino.initializeTetromino();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
        oTetromino.rotateCW();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
        oTetromino.rotateCW();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
        oTetromino.rotateCW();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
        oTetromino.rotateCW();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
        oTetromino.rotateCCw();
        assertArrayEquals(oTetrominoMatrix, oTetromino.getShape());
        assertEquals(4 * BLOCK_SIZE, oTetromino.getTetrominoX());
        assertEquals(0, oTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCWNoRepositioning() {
        iTetromino.initializeTetromino();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.fall();
        iTetromino.fall();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iMatrixRotated, iTetromino.getShape());
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCCWNoRepositioning() {
        iTetromino.initializeTetromino();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.fall();
        iTetromino.fall();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertArrayEquals(iMatrixRotated, iTetromino.getShape());
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCWTopRepositioning() {
        iTetromino.initializeTetromino();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iMatrixRotated, iTetromino.getShape());
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iMatrixRotated, iTetromino.getShape());
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertArrayEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCCWTopRepositioning() {
        iTetromino.initializeTetromino();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCWBottomRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.setTetrominoY(19 * BLOCK_SIZE);
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(19 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(16 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(18 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(16 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(18 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCCWBottomRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.setTetrominoY(19 * BLOCK_SIZE);
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(19 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(16 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(18 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(16 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(18 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCWLeftRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.fall();
        iTetromino.fall();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.setTetrominoX(0);
        iTetromino.rotateCW();
        assertEquals(0, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(0, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCCWLeftRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.fall();
        iTetromino.fall();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.setTetrominoX(0);
        iTetromino.rotateCCw();
        assertEquals(0, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(0, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCWRightRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.fall();
        iTetromino.fall();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.setTetrominoX(9 * BLOCK_SIZE);
        assertEquals(9 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(6 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(8 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCW();
        assertEquals(6 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateITetrominoCCWRightRepositioning() {
        iTetromino.initializeTetromino();
        iTetromino.fall();
        iTetromino.fall();
        assertEquals(iTetrominoMatrix, iTetromino.getShape());
        assertEquals(3 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(5 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.setTetrominoX(9 * BLOCK_SIZE);
        assertEquals(9 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(6 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(8 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(0, iTetromino.getTetrominoY());
        iTetromino.rotateCCw();
        assertEquals(6 * BLOCK_SIZE, iTetromino.getTetrominoX());
        assertEquals(2 * BLOCK_SIZE, iTetromino.getTetrominoY());
    }

    @Test
    public void testRotateOtherTetrominoCWNoRepositioning() {
        jTetromino.initializeTetromino();
        jTetromino.fall();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixRotatedCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixTwiceRotated, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixRotatedCCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 4, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
    }

    @Test
    public void testRotateOtherTetrominoCCWNoRepositioning() {
        jTetromino.initializeTetromino();
        jTetromino.fall();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixRotatedCCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 4, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixTwiceRotated, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixRotatedCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
    }

    @Test
    public void testRotateOtherTetrominoCWTopRepositioning() {
        jTetromino.initializeTetromino();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixRotatedCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixTwiceRotated, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jMatrixRotatedCCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 4, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCW();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());

    }

    @Test
    public void testRotateOtherTetrominoCCWTopRepositioning() {
        jTetromino.initializeTetromino();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixRotatedCCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 4, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixTwiceRotated, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jMatrixRotatedCW, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(0, jTetromino.getTetrominoY());
        jTetromino.rotateCCw();
        assertArrayEquals(jTetrominoMatrix, jTetromino.getShape());
        assertEquals(BLOCK_SIZE * 3, jTetromino.getTetrominoX());
        assertEquals(BLOCK_SIZE, jTetromino.getTetrominoY());
    }


    @Test
    public void testKeepTetrominoInBoundsLeft() {
        iTetromino.setTetrominoX(-BLOCK_SIZE * 2);
        assertEquals(-BLOCK_SIZE * 2, iTetromino.getTetrominoX());
        iTetromino.keepTetrominoInBounds();
        assertEquals(0, iTetromino.getTetrominoX());
    }

    @Test
    public void testKeepTetrominoInBoundsRight() {
        oTetromino.setTetrominoX(10 * BLOCK_SIZE);
        assertEquals(10 * BLOCK_SIZE, oTetromino.getTetrominoX());
        oTetromino.keepTetrominoInBounds();
        assertEquals(8 * BLOCK_SIZE, oTetromino.getTetrominoX());
    }

    @Test
    public void testKeepTetrominoInBoundsTop() {
        jTetromino.setTetrominoY(-BLOCK_SIZE * 3);
        assertEquals(-3 * BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.keepTetrominoInBounds();
        assertEquals(0, jTetromino.getTetrominoY());
    }


    @Test
    public void testKeepTetrominoInBoundsBottom() {
        jTetromino.setTetrominoY(BLOCK_SIZE * 20);
        assertEquals(20 * BLOCK_SIZE, jTetromino.getTetrominoY());
        jTetromino.keepTetrominoInBounds();
        assertEquals(18 * BLOCK_SIZE, jTetromino.getTetrominoY());
    }

    @Test
    public void testEquals() {
        Tetromino testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        assertEquals(testTetromino, jTetromino);

        testTetromino.fall();
        jTetromino.fall();
        assertEquals(testTetromino, jTetromino);
        testTetromino.rotateCW();
        jTetromino.rotateCW();
        assertEquals(testTetromino, jTetromino);
    }

    @Test
    public void testNotEquals() {
        // check label
        Tetromino testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'i');
        assertNotEquals(testTetromino, jTetromino);
        // check colour
        testTetromino = new Tetromino(jTetrominoMatrix, I_COLOUR, 'j');
        assertNotEquals(testTetromino, jTetromino);
        // check shape
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        assertEquals(testTetromino, jTetromino);
        testTetromino.rotateCW();
        assertNotEquals(testTetromino, jTetromino);
        // check x position
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        assertEquals(testTetromino, jTetromino);
        testTetromino.moveLeft();
        assertNotEquals(testTetromino, jTetromino);
        // check y position
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        assertEquals(testTetromino, jTetromino);
        testTetromino.fall();
        assertNotEquals(testTetromino, jTetromino);
        // test rotation position
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        assertEquals(testTetromino, jTetromino);
        testTetromino.decrementRotationPosition();
        assertNotEquals(testTetromino, jTetromino);
    }

    @Test
    public void testSameTetrominoEqual() {
        assertEquals(jTetromino, jTetromino);
    }

    @Test
    public void testTetrominoEqualsNull() {
        assertNotEquals(jTetromino, null);
    }

    // no tests for draw() function
}
