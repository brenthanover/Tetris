package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.ui.GameBackground;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.model.Tetris.*;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.saveHighScore;
import static org.junit.jupiter.api.Assertions.*;

public class GameBackgroundTests {
    /**
     *  Declarations
     */
    private GameBackground gb;
    private GameBackground testGB;
    private String testHighScoreFileName;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        gb = new GameBackground(0);
        testGB = new GameBackground(0);
        testHighScoreFileName = "testHighScore";

        try {
            saveHighScore(testHighScoreFileName, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddRowClearScore() {
        assertEquals(0, gb.getScore());

        gb.addLineScore(EVENT_ONE_LINE_CLEARED);
        assertEquals(90, gb.getScore());

        gb.setScore(0);
        gb.addLineScore(EVENT_TWO_LINES_CLEARED);
        assertEquals(290, gb.getScore());

        gb.setScore(0);
        gb.addLineScore(EVENT_THREE_LINES_CLEARED);
        assertEquals(590, gb.getScore());

        gb.setScore(0);
        gb.addLineScore(EVENT_FOUR_LINES_CLEARED);
        assertEquals(990, gb.getScore());
    }

    @Test
    public void testFillZeroesInputZero() {
        String testString = gb.fillZeroes(6, 0);
        assertEquals("000000", testString);

        testString = gb.fillZeroes(2, 0);
        assertEquals("00", testString);

        testString = gb.fillZeroes(0, 0);
        System.out.println(testString);
        assertEquals("", testString);
    }

    @Test
    public void testFillZeroesInputNonZero() {
        String testString = gb.fillZeroes(6, 123);
        assertEquals("000123", testString);

        testString = gb.fillZeroes(3, 123);
        assertEquals("123", testString);

        testString = gb.fillZeroes(1, 123);
        assertEquals("123", testString);
    }

    @Test
    public void testGetScoreString() {
        assertEquals("000000", gb.getScoreString());

        gb.setScore(1234);
        assertEquals("001234", gb.getScoreString());
    }

    @Test
    public void testGetHighScoreString() {
        assertEquals("000000", gb.getHighScoreString());

        gb.setHighScore(1234);
        assertEquals("001234", gb.getHighScoreString());
    }

    @Test
    public void testGetLinesClearedString() {
        assertEquals("000", gb.getLinesClearedString());

        gb.setLinesCleared(12);
        assertEquals("012", gb.getLinesClearedString());
    }

    @Test
    public void testEquals() {
        assertEquals(gb, gb);
        assertEquals(gb, testGB);
    }

    @Test
    public void testNotEquals() {
        assertNotEquals(gb, new Object());

        assertEquals(gb, testGB);
        testGB = new GameBackground(100);
        assertNotEquals(gb, testGB);

        testGB = new GameBackground(0);
        assertEquals(gb, testGB);
        testGB.setScore(100);
        assertNotEquals(gb, testGB);

        testGB = new GameBackground(0);
        assertEquals(gb, testGB);
        testGB.setLinesCleared(10);
        assertNotEquals(gb, testGB);

        testGB = new GameBackground(0);
        assertEquals(gb, testGB);
        testGB.setBackgroundColour(Color.blue);
        assertNotEquals(gb, testGB);
    }

    @Test
    public void testDraw() {
        // not in scope for this project
    }

    @Test
    public void testDrawWindow() {
        // not in scope for this project
    }

    @Test
    public void testDrawBlankBoard() {
        // not in scope for this project
    }

    @Test
    public void testDrawNextBox() {
        // not in scope for this project
    }

    @Test
    public void testDrawScore() {
        // not in scope for this project
    }
}
