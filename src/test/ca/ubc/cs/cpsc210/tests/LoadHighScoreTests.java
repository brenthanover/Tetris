package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.parsers.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.ui.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class LoadHighScoreTests {

    /**
     *  Declarations
     */
    Tetris tetris;

    @BeforeEach
    public void setup() {
        tetris = new Tetris(0);
    }

    @Test
    public void testLoadHighScore() {
        try {
            tetris = new Tetris(loadHighScore("testhighscore"));
            assertEquals(1000, tetris.getHighScore());
            // expected
        } catch (MissingFileException e) {
            e.printStackTrace();
            fail("no exception should be thrown, testhighscore exists");
        } catch (IOException e) {
            fail("no exception should be thrown, testhighscore exists");
        }
    }

    @Test
    public void testNoFile() {
        try {
            int highScore = loadHighScore("nonexistenthighscore");
            fail("MissingFileException should have been thrown");
        } catch (MissingFileException e) {
            // expected
        } catch (IOException e) {
            fail("MissingFileException should have been thrown");
        }
    }

    @Test
    public void testIOException() {
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
