package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.parsers.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.ui.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.parsers.SaveHighScore.saveHighScore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class SaveHighScoreTests {

    /**
     *  Declarations
     */
    Tetris tetris;

    @BeforeEach
    public void setup() {
        tetris = new Tetris(0);
    }

    @Test
    public void testSaveHighScoreNoException() {
        try {
            saveHighScore("testhighscore", 1000);
        } catch (IOException e) {
            fail("should not have thrown an exception here");
        }
        try {
            assertEquals(1000, loadHighScore("testhighscore"));
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
}
