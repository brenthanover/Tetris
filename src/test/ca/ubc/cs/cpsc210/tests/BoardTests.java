package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Board;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Tetris.*;
import static org.junit.jupiter.api.Assertions.*;

import ca.ubc.cs.cpsc210.model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTests {

    private Board board;
    private Tetromino squareTetromino;
    private Tetromino straightTetromino;
    private Tetromino sTetromino;
    private char[][] testBoardGrid;

    @BeforeEach
    public void setup() {
        board = new Board();
        testBoardGrid = new char[BLOCKS_HIGH][BLOCKS_WIDE];
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
        board.setBoardGrid(2, 5, 'o');
        assertFalse(board.isTetrominoAboveBlock(straightTetromino));
        straightTetromino.fall();
        assertTrue(board.isTetrominoAboveBlock(straightTetromino));
    }

    @Test
    public void testIsSTetrominoAboveBlock() {
        board.setBoardGrid(3, 5, 'o');
        assertFalse(board.isTetrominoAboveBlock(sTetromino));
        sTetromino.fall();
        assertFalse(board.isTetrominoAboveBlock(sTetromino));
        sTetromino.fall();
        assertTrue(board.isTetrominoAboveBlock(sTetromino));
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
        board.setBoardGrid(0, 2, 'o');
        board.setBoardGrid(0, 7, 'o');
        for (int i = 2; i < 8; i++) {
            board.setBoardGrid(1, i, 'o');
        }

        assertFalse(board.isObstructingIBlock());
        assertTrue(board.isObstructingOBlock());
        assertTrue(board.isObstructingOtherBlocks(sTetromino));

        assertFalse(board.isGameOver(straightTetromino));
        assertTrue(board.isGameOver(squareTetromino));
        assertTrue(board.isGameOver(sTetromino));
    }

    @Test
    public void testIsGameOverBlocksSurroundingSquare() {
        board.setBoardGrid(0, 3, 'o');
        board.setBoardGrid(1, 3, 'o');
        board.setBoardGrid(0, 6, 'o');
        board.setBoardGrid(1, 6, 'o');
        for (int i = 3; i < 7; i++) {
            board.setBoardGrid(2, i, 'o');
        }

        assertTrue(board.isObstructingIBlock());
        assertFalse(board.isObstructingOBlock());
        assertTrue(board.isObstructingOtherBlocks(sTetromino));

        assertTrue(board.isGameOver(straightTetromino));
        assertFalse(board.isGameOver(squareTetromino));
        assertTrue(board.isGameOver(sTetromino));
    }

    @Test
    public void testIsGameOverBlocksSurroundingOther() {
        board.setBoardGrid(0, 2, 'o');
        board.setBoardGrid(0, 3, 'o');
        board.setBoardGrid(1, 2, 'o');
        board.setBoardGrid(0, 6, 'o');
        board.setBoardGrid(1, 5, 'o');
        board.setBoardGrid(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGrid(2, i, 'o');
        }

        assertTrue(board.isObstructingIBlock());
        assertTrue(board.isObstructingOBlock());
        assertFalse(board.isObstructingOtherBlocks(sTetromino));

        assertTrue(board.isGameOver(straightTetromino));
        assertTrue(board.isGameOver(squareTetromino));
        assertFalse(board.isGameOver(sTetromino));
    }

    @Test
    public void testFreezeStraightTetrominoToBoard() {
        assertTrue(isBoardMatching(testBoardGrid));
        board.freezeTetrominoToBoard(straightTetromino);
        for (int i = 3; i < 7; i++) {
            testBoardGrid[0][i] = 'i';
        }
        assertTrue(isBoardMatching(testBoardGrid));
    }

    @Test
    public void testFreezeSTetrominoToBoard() {
        assertTrue(isBoardMatching(testBoardGrid));
        board.freezeTetrominoToBoard(sTetromino);
        testBoardGrid[0][4] = 's';
        testBoardGrid[0][5] = 's';
        testBoardGrid[1][3] = 's';
        testBoardGrid[1][4] = 's';

        assertTrue(isBoardMatching(testBoardGrid));
    }

    @Test
    public void testDropToGround() {
        assertEquals(0, straightTetromino.getTetrominoY());
        assertTrue(isBoardMatching(testBoardGrid));
        for (int i = 3; i < 7; i++) {
            testBoardGrid[19][i] = 'i';
        }
        board.dropTetrominoToBottom(straightTetromino);
        board.freezeTetrominoToBoard(straightTetromino);
        assertTrue(isBoardMatching(testBoardGrid));
    }

    @Test
    public void testDropToBlock() {
        assertEquals(0, straightTetromino.getTetrominoY());
        assertTrue(isBoardMatching(testBoardGrid));
        board.setBoardGrid(19, 5, 'o');
        testBoardGrid[19][5] = 'o';
        for (int i = 3; i < 7; i++) {
            testBoardGrid[18][i] = 'i';
        }
        board.dropTetrominoToBottom(straightTetromino);
        board.freezeTetrominoToBoard(straightTetromino);
        assertTrue(isBoardMatching(testBoardGrid));
    }

    @Test
    public void testIsRowFull() {
        for (int i = 0; i < 10; i++) {
            board.setBoardGrid(19, i, 'i');
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
            board.setBoardGrid(0, j, 'i');
        }
        assertEquals(1, board.countFullRows());
    }

    @Test
    public void testCountTwoFullRows() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(2, board.countFullRows());
    }

    @Test
    public void testCountThreeFullRows() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(3, board.countFullRows());
    }

    @Test
    public void testCountFourFullRows() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(4, board.countFullRows());
    }

    @Test
    public void testClearZeroFullRows() {
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());

        board.clearRow();
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());

    }

    @Test
    public void testClearOneFullRow() {
        for (int j = 0; j < 10; j++) {
            board.setBoardGrid(0, j, 'i');
        }
        assertEquals(1, board.countFullRows());
        board.clearRow();
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearTwoFullRows() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(2, board.countFullRows());
        board.clearRow();
        board.clearRow();
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearThreeFullRows() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(3, board.countFullRows());
        board.clearRow();
        board.clearRow();
        board.clearRow();
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearFourFullRows() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                board.setBoardGrid(i, j, 'i');
            }
        }
        assertEquals(4, board.countFullRows());
        board.clearRow();
        board.clearRow();
        board.clearRow();
        board.clearRow();
        assertTrue(isBoardMatching(testBoardGrid));
        assertEquals(0, board.countFullRows());
    }

    @Test
    public void testClearRowWithOtherBlocks() {
        assertTrue(isBoardMatching(testBoardGrid));
        for (int j = 0; j < 10; j++) {
            board.setBoardGrid(18, j, 'i');
        }
        board.setBoardGrid(19, 0, 'i');
        board.setBoardGrid(17, 1, 'i');
        board.clearRow();
        testBoardGrid[19][0] = 'i';
        testBoardGrid[18][1] = 'i';
        assertTrue(isBoardMatching(testBoardGrid));
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
        board.setBoardGrid(0, 2, 'i');
        assertFalse(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
        board.setBoardGrid(1, 2, 'i');
        assertTrue(board.isTetrominoMovementRestrictedOnLeft(sTetromino));
    }

    @Test
    public void testIsTetrominoRestrictedBlockRight() {
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
        board.setBoardGrid(1, 6, 'i');
        assertFalse(board.isTetrominoMovementRestrictedOnRight(sTetromino));
        board.setBoardGrid(0, 6, 'i');
        assertTrue(board.isTetrominoMovementRestrictedOnRight(sTetromino));
    }

    @Test
    public void testTetrominoNotOverlappingBoard() {
        board.setBoardGrid(0, 2, 'o');
        board.setBoardGrid(0, 3, 'o');
        board.setBoardGrid(1, 2, 'o');
        board.setBoardGrid(0, 6, 'o');
        board.setBoardGrid(1, 5, 'o');
        board.setBoardGrid(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGrid(2, i, 'o');
        }

        assertFalse(board.isTetrominoOverlappingBoard(sTetromino));
    }

    @Test
    public void testTetrominoOverlappingBoard(){
        board.setBoardGrid(0, 2, 'o');
        board.setBoardGrid(0, 3, 'o');
        board.setBoardGrid(1, 2, 'o');
        board.setBoardGrid(0, 6, 'o');
        board.setBoardGrid(1, 5, 'o');
        board.setBoardGrid(1, 6, 'o');
        for (int i = 2; i < 7; i++) {
            board.setBoardGrid(2, i, 'o');
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
        board.setBoardGrid(3, 5, 'o');
        assertFalse(board.canRotateCW(straightTetromino));
    }

    @Test
    public void testTetrominoCannotRotateCCw() {
        straightTetromino.fall();
        straightTetromino.fall();
        assertTrue(board.canRotateCCw(straightTetromino));
        board.setBoardGrid(3, 5, 'o');
        assertFalse(board.canRotateCCw(straightTetromino));
    }

    @Test
    public void testTetrominoSurroundedCanRotate() {
        straightTetromino.fall();
        straightTetromino.fall();
        assertTrue(board.canRotateCW(straightTetromino));
        assertTrue(board.canRotateCCw(straightTetromino));
        board.setBoardGrid(0, 4, 'o');
        board.setBoardGrid(0, 6, 'o');
        board.setBoardGrid(1, 4, 'o');
        board.setBoardGrid(1, 6, 'o');
        board.setBoardGrid(1, 3, 'o');
        board.setBoardGrid(3, 3, 'o');
        board.setBoardGrid(3, 4, 'o');
        board.setBoardGrid(3, 6, 'o');
        board.setBoardGrid(4, 5, 'o');
        assertTrue(board.canRotateCW(straightTetromino));
        assertTrue(board.canRotateCCw(straightTetromino));
    }


    // helper function for checking if matrices are equivalent
    public boolean isBoardMatching(char[][] b) {
        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                if (b[i][j] != board.getBoardGrid()[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }
}
