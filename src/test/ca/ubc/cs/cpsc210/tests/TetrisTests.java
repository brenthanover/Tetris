package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.model.Tetromino;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.saveHighScore;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisTests {
    private Tetris testTetris;
    private Board board;
    private int testHighScore = 1000;
    private String testHighScoreFileName = "testHighScore";
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private Tetromino oTetromino;
    private Tetromino zTetromino;
    private Tetromino iTetromino;
    private Tetromino sTetromino;
    private Tetromino tTetromino;
    private Tetromino lTetromino;
    private Tetromino jTetromino;

    @BeforeEach
    public void setup() {
        try {
            saveHighScore(testHighScoreFileName, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        assertEquals(board, testTetris.getGameBoard());
        assertEquals(0, testTetris.getScore());
    }

    @Test
    public void testGameOverScoreRecordWrongBoolean() {
        testTetris.setGameOver(false);
        testTetris.setHighScore(testHighScore);
        try {
            assertEquals(0, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
    }

    @Test
    public void testGameOverScoreRecordRightBooleansNewHighScore() {
        testTetris.setGameOver(true);
        try {
            assertEquals(0, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
        testTetris.setScore(testHighScore);
        assertTrue(testTetris.isGameOver());
        assertFalse(testTetris.isHighScoreSaved());
        testTetris.gameOverScoreRecord(testHighScoreFileName);
        try {
            assertEquals(testHighScore, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
    }

    @Test
    public void testGameOverScoreRecordRightBooleansNoHighScore() {
        testTetris = new Tetris(testHighScore * 2);

        try {
            saveHighScore(testHighScoreFileName, testHighScore * 2);
        } catch (IOException e) {
            fail("should not throw exception");
        }
        try {
            assertEquals(testHighScore * 2, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
        testTetris.setGameOver(true);
        testTetris.setScore(testHighScore);
        testTetris.gameOverScoreRecord(testHighScoreFileName);
        try {
            assertEquals(testHighScore * 2, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
    }

    @Test
    public void testGameOverScoreRecordTwice() {
        testTetris.setGameOver(true);
        testTetris.setScore(testHighScore);
        testTetris.gameOverScoreRecord(testHighScoreFileName);
        try {
            assertEquals(testHighScore, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
        testTetris.setScore(testHighScore * 2);
        testTetris.gameOverScoreRecord(testHighScoreFileName);
        try {
            assertEquals(testHighScore, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
    }

    @Test
    public void testGameOverScoreRecordNoCurrentHighScore() {
        testTetris.setGameOver(true);
        try {
            assertEquals(0, loadHighScore("wrongFileName"));
        } catch (MissingFileException e) {
            // expected
        } catch (IOException e) {
            fail("should not have thrown IOException");
        }
        testTetris.setScore(testHighScore);
        testTetris.gameOverScoreRecord(testHighScoreFileName);
        try {
            assertEquals(testHighScore, loadHighScore(testHighScoreFileName));
        } catch (MissingFileException | IOException e) {
            fail("should not have thrown exception");
        }
    }

    @Test
    public void testCycleTetrominoNoGameOver() {
        testTetris.initializeTetris();
        currentTetromino = testTetris.getCurrentTetromino();
        nextTetromino = testTetris.getNextTetromino();
        assertEquals(0, testTetris.getScore());
        assertFalse(testTetris.isGameOver());
        testTetris.getCurrentTetromino().fall();
        testTetris.getCurrentTetromino().fall();

        testTetris.cycleTetromino();
        assertEquals(10, testTetris.getScore());
        assertEquals(nextTetromino, testTetris.getCurrentTetromino());
        assertNotEquals(nextTetromino, testTetris.getNextTetromino());
        assertFalse(testTetris.isGameOver());

    }

    @Test
    public void testCycleTetrominoGameOver() {
        testTetris.initializeTetris();
        currentTetromino = testTetris.getCurrentTetromino();
        nextTetromino = testTetris.getNextTetromino();
        assertEquals(0, testTetris.getScore());
        testTetris.getGameBoard().setBoardGridBlock(0, 4, 'i');
        testTetris.setCurrentTetrominoByLabel('i');

        assertFalse(testTetris.isGameOver());
        testTetris.cycleTetromino();
        assertTrue(testTetris.isGameOver());
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
    public void testAddRowClearScore() {
        testTetris.initializeTetris();
        testTetris.cycleTetromino();
        testTetris.addRowClearScore(0);
        assertEquals(0, testTetris.getScore());

        testTetris.cycleTetromino();
        testTetris.addRowClearScore(1);
        assertEquals(100, testTetris.getScore());

        testTetris.cycleTetromino();
        testTetris.addRowClearScore(2);
        assertEquals(400, testTetris.getScore());

        testTetris.cycleTetromino();
        testTetris.addRowClearScore(3);
        assertEquals(1000, testTetris.getScore());

        testTetris.cycleTetromino();
        testTetris.addRowClearScore(4);
        assertEquals(2000, testTetris.getScore());
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
    public void testFillZeroesInputZero() {
        String testString = testTetris.fillZeroes(6, 0);
        assertEquals("000000", testString);

        testString = testTetris.fillZeroes(2, 0);
        assertEquals("00", testString);

        testString = testTetris.fillZeroes(0, 0);
        assertEquals("", testString);
    }

    @Test
    public void testFillZeroesInputNonZero() {
        String testString = testTetris.fillZeroes(6, 123);
        assertEquals("000123", testString);

        testString = testTetris.fillZeroes(3, 123);
        assertEquals("123", testString);

        testString = testTetris.fillZeroes(1, 123);
        assertEquals("123", testString);
    }

    @Test
    public void testKeyPressLeft() {
        testTetris.initializeTetris();
        assertFalse(testTetris.isPaused());
        assertFalse(testTetris.isGameOver());
        assertEquals(0, testTetris.getCurrentTetromino().getTetrominoY());
        //TODO: figure out how tf key pressing will work
    }

    @Test
    public void testKeyPressedWhenWrongBooleans() {
        testTetris.initializeTetris();
        assertEquals(0, testTetris.getCurrentTetromino().getTetrominoY());
        //TODO: figure out how tf key pressing will work
    }

    @Test
    public void testMouseEvent() {
        //TODO: okay how tf are mouse tests supposed to work
    }

    @Test
    public void testEquals() {
        Tetris t = new Tetris(0);
        assertEquals(t, testTetris);
        assertEquals(t, t);
    }

    @Test
    public void testNotEquals() {
        Tetris t = new Tetris(0);
        assertEquals(t, testTetris);
        t.addRowClearScore(1);
        assertNotEquals(t, testTetris);

        t = new Tetris(testHighScore);
        assertNotEquals(t, testTetris);

        t = new Tetris(0);
        assertEquals(t, testTetris);
        t.getGameBoard().setBoardGridBlock(0,0,'i');
        assertNotEquals(t, testTetris);


        t = new Tetris(0);
        assertEquals(t, testTetris);
        t.setLinesCleared(1);
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
