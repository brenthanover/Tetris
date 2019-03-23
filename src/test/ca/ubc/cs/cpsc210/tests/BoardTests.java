package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Board;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Game.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.cpsc210.model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTests {
    /**
     *  Declarations
     */
    private Board board;
    private Board testBoard;
    private Tetromino squareTetromino;
    private Tetromino straightTetromino;
    private Tetromino sTetromino;
    private char[][] testBoardGrid;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        board = new Board();
        testBoardGrid = new char[BLOCKS_HIGH][BLOCKS_WIDE];
        testBoard = new Board();
        squareTetromino = new Tetromino(oTetrominoMatrix, O_COLOUR, 'o');
        straightTetromino = new Tetromino(iTetrominoMatrix, I_COLOUR, 'i');
        sTetromino = new Tetromino(sTetrominoMatrix, S_COLOUR, 's');
        squareTetromino.initializeTetromino();
        straightTetromino.initializeTetromino();
        sTetromino.initializeTetromino();

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                testBoardGrid[i][j] = 'e';
            }
        }
        testBoard.setBoardGrid(testBoardGrid);
    }

    @Test
    public void testConstructor() {
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                assertEquals('e', board.getBoardGrid()[i][j]);
            }
        }
    }


    @Test
    public void testIsStraightTetrominoAboveBlock() {
        board.setBoardGridBlock(2, 5, 'o');
        assertFalse(board.isTetrominoAboveBlock(straightTetromino));
        straightTetromino.fall();
        assertTrue(board.isTetrominoAboveBlock(straightTetromino));
    }

    @Test
    public void testIsSTetrominoAboveBlock() {
        board.setBoardGridBlock(3, 5, 'o');
        assertFalse(board.isTetrominoAboveBlock(sTetromino));
        sTetromino.fall();
        assertFalse(board.isTetrominoAboveBlock(sTetromino));
        sTetromino.fall();
        assertTrue(board.isTetrominoAboveBlock(sTetromino));
    }

    @Test
    public void testIsStraightTetrominoAboveBlockOutOfBounds() {
        straightTetromino.rotateCW();
        board.setBoardGridBlock(19, 5, 'i');
        assertFalse(board.isTetrominoAboveBlock(straightTetromino));
        straightTetromino.setTetrominoY(14 * BLOCK_SIZE);
        assertFalse(board.isTetrominoAboveBlock(straightTetromino));
        straightTetromino.fall();
        assertTrue(board.isTetrominoAboveBlock(straightTetromino));
        straightTetromino.setTetrominoY(18 * BLOCK_SIZE);
        assertFalse(board.isTetrominoAboveBlock(straightTetromino));
    }

    @Test
    public void testIsDirectlyTouchingBottom() {
        assertFalse(board.isTetrominoTouchingBottom(straightTetromino));
        for (int i = 0; i < 19; i++) {
            straightTetromino.fall();
        }
        assertTrue(board.isTetrominoTouchingBottom(straightTetromino));
    }

    @Test
    public void testIsGameOverBlocksSurroundingStraight() {
        board.setBoardGridBlock(0, 2, 'o');
        board.setBoardGridBlock(0, 7, 'o');
        for (int i = 2; i < 8; i++) {
            board.setBoardGridBlock(1, i, 'o');
        }

        assertFalse(board.isBoardObstructingNewIBlock());
        assertTrue(board.isBoardObstructingNewOBlock());
        assertTrue(board.isBoardObstructingNewOtherBlocks(sTetromino));

        assertFalse(board.isGameOver(straightTetromino));
        assertTrue(board.isGameOver(squareTetromino));
        assertTrue(board.isGameOver(sTetromino));
    }

    @Test
    public void testIsGameOverBlocksSurroundingSquare() {
        board.setBoardGridBlock(0, 3, 'o');
        board.setBoardGridBlock(1, 3, 'o');
        board.setBoardGridBlock(0, 6, 'o');
        board.setBoardGridBlock(1, 6, 'o');
        for (int i = 3; i < 7; i++) {
            board.setBoardGridBlock(2, i, 'o');
        }

        assertTrue(board.isBoardObstructingNewIBlock());
        assertFalse(board.isBoardObstructingNewOBlock());
        assertTrue(board.isBoardObstructingNewOtherBlocks(sTetromino));

        assertTrue(board.isGameOver(straightTetromino));
        assertFalse(board.isGameOver(squareTetromino));
        assertTrue(board.isGameOver(sTetromino));
    }

    @Test
    public void testIsGameOverBlocksSurroundingOther() {
        board.setBoardGridBlock(0, 2, 'o');
        board.setBoardGridBlock(0, 3, 'o');
        board.setBoardGridBlock(1, 2, 'o');
        board.setBoardGridBlock(0, 6, 'o');
        board.setBoardGridBlock(1, 5, 'o');
        board.setBoardGridBlock(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGridBlock(2, i, 'o');
        }

        assertTrue(board.isBoardObstructingNewIBlock());
        assertTrue(board.isBoardObstructingNewOBlock());
        assertFalse(board.isBoardObstructingNewOtherBlocks(sTetromino));

        assertTrue(board.isGameOver(straightTetromino));
        assertTrue(board.isGameOver(squareTetromino));
        assertFalse(board.isGameOver(sTetromino));
    }

    @Test
    public void testFreezeStraightTetrominoToBoard() {
        assertTrue(board.equals(testBoard));
        board.freezeTetrominoToBoard(straightTetromino);
        for (int i = 3; i < 7; i++) {
            testBoard.setBoardGridBlock(0, i, 'i');
        }
        assertTrue(board.equals(testBoard));
    }

    @Test
    public void testFreezeSTetrominoToBoard() {
        assertTrue(board.equals(testBoard));
        board.freezeTetrominoToBoard(sTetromino);
        testBoard.setBoardGridBlock(0, 4, 's');
        testBoard.setBoardGridBlock(0, 5, 's');
        testBoard.setBoardGridBlock(1, 3, 's');
        testBoard.setBoardGridBlock(1, 4, 's');

        assertTrue(board.equals(testBoard));
    }

    @Test
    public void testDropToGround() {
        assertEquals(0, straightTetromino.getTetrominoY());
        assertTrue(board.equals(testBoard));
        for (int i = 3; i < 7; i++) {
            testBoard.setBoardGridBlock(19, i, 'i');
        }
        board.dropTetrominoToBottom(straightTetromino);
        board.freezeTetrominoToBoard(straightTetromino);
        assertTrue(board.equals(testBoard));
    }

    @Test
    public void testDropToBlock() {
        assertEquals(0, straightTetromino.getTetrominoY());
        assertTrue(board.equals(testBoard));
        board.setBoardGridBlock(19, 5, 'o');
        testBoardGrid[19][5] = 'o';
        for (int i = 3; i < 7; i++) {
            testBoard.setBoardGridBlock(18, i, 'i');
        }
        board.dropTetrominoToBottom(straightTetromino);
        board.freezeTetrominoToBoard(straightTetromino);
        assertTrue(board.equals(testBoard));
    }

    @Test
    public void testIsRowFull() {
        for (int i = 0; i < 10; i++) {
            board.setBoardGridBlock(19, i, 'i');
        }
        assertTrue(board.isRowFull(board.getBoardGrid()[19]));
    }

    @Test
    public void testCountZeroFullRows() {
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testCountOneFullRow() {
        for (int j = 0; j < 10; j++) {
            board.setBoardGridBlock(0, j, 'i');
        }
        assertEquals(1, board.countFullRows());
    }

    @Test
    public void testCountTwoFullRows() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(2, board.countFullRows());
    }

    @Test
    public void testCountThreeFullRows() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(3, board.countFullRows());
    }

    @Test
    public void testCountFourFullRows() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(4, board.countFullRows());
    }

    @Test
    public void testClearZeroFullRows() {
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());

        board.clearRow();
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearOneFullRow() {
        for (int j = 0; j < 10; j++) {
            board.setBoardGridBlock(0, j, 'i');
        }
        assertEquals(1, board.countFullRows());
        board.clearRow();
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearTwoFullRows() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(2, board.countFullRows());
        board.clearRow();
        board.clearRow();
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearThreeFullRows() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(3, board.countFullRows());
        board.clearRow();
        board.clearRow();
        board.clearRow();
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearFourFullRows() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGridBlock(i, j, 'i');
            }
        }
        assertEquals(4, board.countFullRows());
        board.clearRow();
        board.clearRow();
        board.clearRow();
        board.clearRow();
        assertTrue(board.equals(testBoard));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearRowWithOtherBlocks() {
        assertTrue(board.equals(testBoard));
        for (int j = 0; j < 10; j++) {
            board.setBoardGridBlock(18, j, 'i');
        }
        board.setBoardGridBlock(19, 0, 'i');
        board.setBoardGridBlock(17, 1, 'i');
        board.clearRow();
        testBoard.setBoardGridBlock(19, 0, 'i');
        testBoard.setBoardGridBlock(18, 1, 'i');
        assertTrue(board.equals(testBoard));
    }

    @Test
    public void testIsTetrominoNotRestrictedLeft() {
        assertFalse(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
    }

    @Test
    public void testIsTetrominoNotRestrictedRight() {
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
    }

    @Test
    public void testIsTetrominoRestrictedWallLeft() {
        assertFalse(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
        sTetromino.setTetrominoX(0);
        assertTrue(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
    }

    @Test
    public void testIsTetrominoRestrictedWallRight() {
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
        sTetromino.setTetrominoX(BOARD_WIDTH - sTetromino.getShape()[0].length * BLOCK_SIZE);
        assertTrue(board.isTetrominoMovementRestrictedOnRight(sTetromino));
    }

    @Test
    public void testIsTetrominoRestrictedBlockLeft() {
        assertFalse(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
        board.setBoardGridBlock(0, 2, 'i');
        assertFalse(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
        board.setBoardGridBlock(1, 2, 'i');
        assertTrue(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
    }

    @Test
    public void testIsTetrominoRestrictedBlockRight() {
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
        board.setBoardGridBlock(1, 6, 'i');
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
        board.setBoardGridBlock(0, 6, 'i');
        assertTrue(board.isTetrominoMovementRestrictedOnRight(sTetromino));
    }

    @Test
    public void testTetrominoNotOverlappingBoard() {
        board.setBoardGridBlock(0, 2, 'o');
        board.setBoardGridBlock(0, 3, 'o');
        board.setBoardGridBlock(1, 2, 'o');
        board.setBoardGridBlock(0, 6, 'o');
        board.setBoardGridBlock(1, 5, 'o');
        board.setBoardGridBlock(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGridBlock(2, i, 'o');
        }

        assertFalse(board.isTetrominoOverlappingBoard(sTetromino));
    }

    @Test
    public void testTetrominoOverlappingBoard() {
        board.setBoardGridBlock(0, 2, 'o');
        board.setBoardGridBlock(0, 3, 'o');
        board.setBoardGridBlock(1, 2, 'o');
        board.setBoardGridBlock(0, 6, 'o');
        board.setBoardGridBlock(1, 5, 'o');
        board.setBoardGridBlock(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGridBlock(2, i, 'o');
        }

        assertFalse(board.isTetrominoOverlappingBoard(sTetromino));
        sTetromino.fall();
        assertTrue(board.isTetrominoOverlappingBoard(sTetromino));
    }

    @Test
    public void testTetrominoCanRotateCW() {
        assertTrue(board.canRotateCW(sTetromino));
    }

    @Test
    public void testTetrominoCanRotateCCw() {
        assertTrue(board.canRotateCCw(sTetromino));
    }

    @Test
    public void testTetrominoCannotRotateCW() {
        straightTetromino.fall();
        straightTetromino.fall();
        assertTrue(board.canRotateCW(straightTetromino));
        board.setBoardGridBlock(3, 5, 'o');
        assertFalse(board.canRotateCW(straightTetromino));
    }

    @Test
    public void testTetrominoCannotRotateCCw() {
        straightTetromino.fall();
        straightTetromino.fall();
        assertTrue(board.canRotateCCw(straightTetromino));
        board.setBoardGridBlock(3, 5, 'o');
        assertFalse(board.canRotateCCw(straightTetromino));
    }

    @Test
    public void testTetrominoSurroundedCanRotate() {
        straightTetromino.fall();
        straightTetromino.fall();
        assertTrue(board.canRotateCW(straightTetromino));
        assertTrue(board.canRotateCCw(straightTetromino));
        board.setBoardGridBlock(0, 4, 'o');
        board.setBoardGridBlock(0, 6, 'o');
        board.setBoardGridBlock(1, 4, 'o');
        board.setBoardGridBlock(1, 6, 'o');
        board.setBoardGridBlock(1, 3, 'o');
        board.setBoardGridBlock(3, 3, 'o');
        board.setBoardGridBlock(3, 4, 'o');
        board.setBoardGridBlock(3, 6, 'o');
        board.setBoardGridBlock(4, 5, 'o');
        assertTrue(board.canRotateCW(straightTetromino));
        assertTrue(board.canRotateCCw(straightTetromino));
    }

    @Test
    public void testBoardEquals() {
        assertEquals(board, testBoard);
    }

    @Test
    public void testBoardNotEquals() {
        assertNotEquals(board, new Object());

        board.setBoardGridBlock(0,0,'i');
        assertNotEquals(board, testBoard);
    }

    @Test
    public void testSameBoardEqual() {
        assertEquals(board, board);
    }

    @Test
    public void testBoardEqualsNull() {
        assertNotEquals(board, null);
    }

    @Test
    public void testDraw() {
        // not in scope for this project
    }
}
