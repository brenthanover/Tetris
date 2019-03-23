package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.*;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.Jsonifier.tetrisToJson;
import static ca.ubc.cs.cpsc210.persistence.SaveGame.saveGame;
import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadGameTests {
    /**
     *  Declarations
     */
    private Tetris testTetris;
    private Tetris loadedTetris;
    private String testSaveFullFilename;
    private String testFilename;
    private String saveJsonString;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        testTetris = new Tetris(0);
        try {
            testTetris = new Tetris(loadHighScore("testhighscore0"));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }
        testTetris.initializeTetris();
        testTetris.setCurrentTetrominoByLabel('i');
        testTetris.setNextTetrominoByLabel('o');

        loadedTetris = new Tetris(0);

        testSaveFullFilename = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/testsavegame";
        testFilename = "testsavegame";

        saveJsonString = tetrisToJson(testTetris).toString();
    }

    @Test
    public void testSaveGameNoException() {
        String savedString = "";

        try {
            saveGame(testFilename, testTetris);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(testSaveFullFilename));
            savedString = br.readLine();
        } catch (Exception e) {
            // will not occur, not testing built in functions
        }

        String saveDataString = tetrisToJson(testTetris).toString();

        assertEquals(savedString, saveDataString);
    }

    @Test
    public void testSaveGameIOException() {
        testTetris.setCurrentTetrominoByLabel('l');
        testTetris.setNextTetrominoByLabel('i');

        try {
            saveGame("/", testTetris);
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testLoadGameNoException() {

        try {
            loadGame(testFilename, loadedTetris);
        } catch (MissingFileException | IOException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(testTetris, loadedTetris);

    }

    @Test
    public void testLoadGameMissingFileException() {

        try {
            loadGame("nonexistentfilename", testTetris);
        } catch (MissingFileException e) {
            // expected
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    public void testLoadGameIOException() {
        try {
            loadGame("/", testTetris);
        } catch (MissingFileException e) {
            fail("Should not have thrown exception");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testLoadStringNoException() {
        String loadedString = "";

        try {
            loadedString = loadString(testFilename);
        } catch (IOException | MissingFileException e) {
            fail("should not have thrown exception");
        }

        assertEquals(saveJsonString, loadedString);
    }

    @Test
    public void testLoadStringIOException() {
        String loadedString = "";

        try {
            loadedString = loadString("/");
        } catch (IOException e) {
            // expected
        } catch (MissingFileException e) {
            fail("should not have thrown MissingFileException");
        }
    }

    @Test
    public void testLoadStringMissingFileException() {
        String loadedString = "";

        try {
            loadedString = loadString("filethatdoesntexist");
        } catch (IOException e) {
            fail("should not have thrown IOException");
        } catch (MissingFileException e) {
            // expected
        }
    }

    @Test
    public void testSetTetris() {
        assertNotEquals(testTetris, loadedTetris);

        setTetris(loadedTetris, testTetris);

        assertEquals(testTetris, loadedTetris);
    }

    @Test
    public void testSetTetrisOtherWay() {
        assertNotEquals(testTetris, loadedTetris);

        setTetris(testTetris, loadedTetris);

        assertEquals(testTetris, loadedTetris);
    }
}
