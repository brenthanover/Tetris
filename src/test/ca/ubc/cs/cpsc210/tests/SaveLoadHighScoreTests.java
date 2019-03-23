package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.saveHighScore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SaveLoadHighScoreTests {

    /**
     *  Declarations
     */
    private Tetris tetris;
    private String testHighScoreZero;
    private String testHighScoreThousand;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        tetris = new Tetris(0);
        testHighScoreThousand = "testhighscore1000";
        testHighScoreZero = "testhighscore0";
    }

    @Test
    public void testSaveLoadHighScoreNoException() {
        try {
            saveHighScore(testHighScoreThousand, 1000);
        } catch (IOException e) {
            fail("should not have thrown an exception here");
        }
        try {
            assertEquals(1000, loadHighScore(testHighScoreThousand));
        } catch (MissingFileException e) {
            fail("should not throw MissingFileException");
        } catch (IOException e) {
            fail("should not throw IOException");
        }
    }

    @Test
    public void testSaveHighScoreIOException() {
        try {
            saveHighScore("/", 1000);
            fail("should throw IOException");
        } catch (IOException e) {
            //expected
        }
    }

    @Test
    public void testLoadHighScoreNoFile() {
        try {
            int highScore = loadHighScore("nonexistenthighscorefile");
            fail("MissingFileException should have been thrown");
        } catch (MissingFileException e) {
            // expected
        } catch (IOException e) {
            fail("MissingFileException should have been thrown");
        }
    }

    @Test
    public void testLoadHighScoreIOException() {
        try {
            int highScore = loadHighScore("/");
            fail("IOException should have been thrown");
        } catch (MissingFileException e) {
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }
}
