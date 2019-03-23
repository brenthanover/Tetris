package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import ca.ubc.cs.cpsc210.ui.buttons.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cs.cpsc210.model.Tetris.*;
import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;
import static ca.ubc.cs.cpsc210.ui.GameBackground.TETROMINO_SCORE;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisTests {
    /**
     *  Declarations
     */
    private Tetris testTetris;
    private Board board;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private Tetromino oTetromino;
    private Tetromino zTetromino;
    private Tetromino iTetromino;
    private Tetromino sTetromino;
    private Tetromino tTetromino;
    private Tetromino lTetromino;
    private Tetromino jTetromino;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        testTetris = new Tetris(0);
        board = new Board();
        oTetromino = new Tetromino(oTetrominoMatrix, O_COLOUR, 'o');
        zTetromino = new Tetromino(zTetrominoMatrix, Z_COLOUR, 'z');
        iTetromino = new Tetromino(iTetrominoMatrix, I_COLOUR, 'i');
        sTetromino = new Tetromino(sTetrominoMatrix, S_COLOUR, 's');
        tTetromino = new Tetromino(tTetrominoMatrix, T_COLOUR, 't');
        lTetromino = new Tetromino(lTetrominoMatrix, L_COLOUR, 'l');
        jTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testTetris.getHighScore());
        assertEquals(board, testTetris.getBoard());
        assertEquals(new GameBackground(testTetris.getHighScore()), testTetris.getGameBackground());

        assertEquals(new MusicButton(testTetris), testTetris.getButtonList()[0]);
        assertEquals(new SoundEffectsButton(testTetris), testTetris.getButtonList()[1]);
        assertEquals(new MysteryButton(testTetris), testTetris.getButtonList()[2]);
        assertEquals(new PauseButton(testTetris), testTetris.getButtonList()[3]);
        assertEquals(new SaveButton(testTetris), testTetris.getButtonList()[4]);
        assertEquals(new LoadButton(testTetris), testTetris.getButtonList()[5]);

        assertEquals(EVENT_ONE_LINE_CLEARED, testTetris.getScoreClearedLinesMap().get(1));
        assertEquals(EVENT_TWO_LINES_CLEARED, testTetris.getScoreClearedLinesMap().get(2));
        assertEquals(EVENT_THREE_LINES_CLEARED, testTetris.getScoreClearedLinesMap().get(3));
        assertEquals(EVENT_FOUR_LINES_CLEARED, testTetris.getScoreClearedLinesMap().get(4));
    }

    @Test
    public void testGameOverScoreRecordWrongBoolean() {
        assertFalse(testTetris.isHighScoreSaved());
        testTetris.setGameOver(false);

        testTetris.gameOverScoreRecord();

        assertFalse(testTetris.isHighScoreSaved());
    }

    @Test
    public void testGameOverScoreRecordRightBooleans() {
        assertFalse(testTetris.isHighScoreSaved());
        testTetris.setGameOver(true);

        testTetris.gameOverScoreRecord();

        assertTrue(testTetris.isHighScoreSaved());
    }

    @Test
    public void testCycleTetrominoNoGameOver() {
        testTetris.initializeTetris();
        testTetris.setCurrentTetrominoByLabel('o');
        testTetris.getCurrentTetromino().initializeTetromino();
        testTetris.getCurrentTetromino().fall();
        testTetris.getCurrentTetromino().fall();
        nextTetromino = testTetris.getNextTetromino();
        assertFalse(testTetris.isGameOver());
        assertEquals(0, testTetris.getGameBackground().getScore());
        assertEquals(board, testTetris.getBoard());

        testTetris.cycleTetromino();
        assertEquals(nextTetromino, testTetris.getCurrentTetromino());
        assertNotEquals(nextTetromino, testTetris.getNextTetromino());
        assertFalse(testTetris.isGameOver());
        assertEquals(TETROMINO_SCORE, testTetris.getGameBackground().getScore());

        board.setBoardGridBlock(2,4,'o');
        board.setBoardGridBlock(3,4,'o');
        board.setBoardGridBlock(2,5,'o');
        board.setBoardGridBlock(3,5,'o');
        assertEquals(board, testTetris.getBoard());
    }

    @Test
    public void testCycleTetrominoGameOver() {
        testTetris.initializeTetris();
        testTetris.setCurrentTetrominoByLabel('o');
        testTetris.getCurrentTetromino().initializeTetromino();
        assertFalse(testTetris.isGameOver());
        assertEquals(board, testTetris.getBoard());

        testTetris.cycleTetromino();
        assertTrue(testTetris.isGameOver());

        board.setBoardGridBlock(0,4,'o');
        board.setBoardGridBlock(1,4,'o');
        board.setBoardGridBlock(0,5,'o');
        board.setBoardGridBlock(1,5,'o');
        assertEquals(board, testTetris.getBoard());
    }

    @Test
    public void testClearRowSoundEffects() {
        testTetris.clearRowSoundEffects(0);
        testTetris.clearRowSoundEffects(1);
        testTetris.clearRowSoundEffects(2);
        testTetris.clearRowSoundEffects(3);
        testTetris.clearRowSoundEffects(4);
    }

    @Test
    public void testInitializeTetris() {
        assertFalse(testTetris.isGameStart());
        assertNull(testTetris.getCurrentTetromino());
        assertNull(testTetris.getNextTetromino());

        testTetris.initializeTetris();
        assertTrue(testTetris.isGameStart());
        assertNotNull(testTetris.getCurrentTetromino());
        assertNotNull(testTetris.getNextTetromino());
    }

    @Test
    public void testGetRandomTetromino() {
        Tetromino t = testTetris.getRandomTetromino();
        assertTrue(t.equals(oTetromino)
                || t.equals(zTetromino)
                || t.equals(iTetromino)
                || t.equals(sTetromino)
                || t.equals(tTetromino)
                || t.equals(lTetromino)
                || t.equals(jTetromino));
    }

    @Test
    public void testGetTetrominoByLabel() {
        Tetromino ot = testTetris.getTetrominoByLabel('o');
        Tetromino zt = testTetris.getTetrominoByLabel('z');
        Tetromino it = testTetris.getTetrominoByLabel('i');
        Tetromino st = testTetris.getTetrominoByLabel('s');
        Tetromino tt = testTetris.getTetrominoByLabel('t');
        Tetromino lt = testTetris.getTetrominoByLabel('l');
        Tetromino jt = testTetris.getTetrominoByLabel('j');

        assertEquals(ot, oTetromino);
        assertEquals(zt, zTetromino);
        assertEquals(it, iTetromino);
        assertEquals(st, sTetromino);
        assertEquals(tt, tTetromino);
        assertEquals(lt, lTetromino);
        assertEquals(jt, jTetromino);
    }

    @Test
    public void testClearOneRow() {
        assertEquals(testTetris.getBoard(), board);
        assertEquals(0, testTetris.getGameBackground().getScore());

        for (int i = 0; i < BLOCKS_WIDE; i++) {
            testTetris.getBoard().setBoardGridBlock(0, i, 'i');
        }
        assertNotEquals(testTetris.getBoard(), board);

        testTetris.clearRows(1);
        assertEquals(testTetris.getBoard(), board);
        assertEquals(90, testTetris.getGameBackground().getScore());
    }

    @Test
    public void testClearTwoRows() {
        assertEquals(testTetris.getBoard(), board);
        assertEquals(0, testTetris.getGameBackground().getScore());

        for (int i = 0; i < BLOCKS_WIDE; i++) {
            testTetris.getBoard().setBoardGridBlock(0, i, 'i');
            testTetris.getBoard().setBoardGridBlock(1, i, 'i');
        }
        assertNotEquals(testTetris.getBoard(), board);

        testTetris.clearRows(2);
        assertEquals(testTetris.getBoard(), board);
        assertEquals(290, testTetris.getGameBackground().getScore());
    }

    @Test
    public void testClearThreeRows() {
        assertEquals(testTetris.getBoard(), board);
        assertEquals(0, testTetris.getGameBackground().getScore());

        for (int i = 0; i < BLOCKS_WIDE; i++) {
            testTetris.getBoard().setBoardGridBlock(0, i, 'i');
            testTetris.getBoard().setBoardGridBlock(1, i, 'i');
            testTetris.getBoard().setBoardGridBlock(2, i, 'i');
        }
        assertNotEquals(testTetris.getBoard(), board);

        testTetris.clearRows(3);
        assertEquals(testTetris.getBoard(), board);
        assertEquals(590, testTetris.getGameBackground().getScore());
    }

    @Test
    public void testClearFourRows() {
        assertEquals(testTetris.getBoard(), board);
        assertEquals(0, testTetris.getGameBackground().getScore());

        for (int i = 0; i < BLOCKS_WIDE; i++) {
            testTetris.getBoard().setBoardGridBlock(0, i, 'i');
            testTetris.getBoard().setBoardGridBlock(1, i, 'i');
            testTetris.getBoard().setBoardGridBlock(2, i, 'i');
            testTetris.getBoard().setBoardGridBlock(3, i, 'i');
        }
        assertNotEquals(testTetris.getBoard(), board);

        testTetris.clearRows(4);
        assertEquals(testTetris.getBoard(), board);
        assertEquals(990, testTetris.getGameBackground().getScore());
    }

    @Test
    public void testDraw() {
        // not in scope for this project
    }

    @Test
    public void testDrawButtons() {
        // not in scope for this project
    }

    @Test
    public void testDrawGameStart() {
        // not in scope for this project
    }

    @Test
    public void testDrawGameOver() {
        // not in scope for this project
    }

    @Test
    public void testKeyTyped() {
        // not in scope for this project
    }

    @Test
    public void testKeyPressed() {
        // not in scope for this project
    }

    @Test
    public void testKeyReleased() {
        // not in scope for this project
    }

    @Test
    public void testMouseClicked() {
        // not in scope for this project
    }

    @Test
    public void testMousePressed() {
        // not in scope for this project
    }

    @Test
    public void testMouseReleased() {
        // not in scope for this project
    }

    @Test
    public void testMouseEntered() {
        // not in scope for this project
    }

    @Test
    public void testMouseExited() {
        // not in scope for this project
    }

    @Test
    public void testEquals() {
        Tetris t = new Tetris(0);
        assertEquals(t, testTetris);
        assertEquals(t, t);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(testTetris, new Object());

        Tetris t = new Tetris(0);
        assertEquals(t, testTetris);
        t.clearRows(1);
        assertNotEquals(t, testTetris);

        t = new Tetris(0);
        assertEquals(t, testTetris);
        t.getBoard().setBoardGridBlock(0, 0, 'i');
        assertNotEquals(t, testTetris);

        t = new Tetris(0);
        assertEquals(t, testTetris);
        t.getGameBackground().setScore(1);
        assertNotEquals(t, testTetris);

        t = new Tetris(0);
        t.initializeTetris();
        testTetris.initializeTetris();
        t.setCurrentTetrominoByLabel('i');
        t.setNextTetrominoByLabel('j');
        testTetris.setCurrentTetrominoByLabel('i');
        testTetris.setNextTetrominoByLabel('j');
        assertEquals(t, testTetris);
        t.setCurrentTetrominoByLabel('j');
        assertNotEquals(t, testTetris);
        t.setCurrentTetrominoByLabel('i');
        assertEquals(t, testTetris);
        t.setNextTetrominoByLabel('i');
        assertNotEquals(t, testTetris);
    }
}
