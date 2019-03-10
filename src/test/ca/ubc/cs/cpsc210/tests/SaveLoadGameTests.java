package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.persistence.SaveGame;
import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.*;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveGame.saveGame;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;
import static org.junit.jupiter.api.Assertions.*;

public class SaveLoadGameTests {

    private char[][] testBoardGrid = new char[BLOCKS_HIGH][BLOCKS_WIDE];
    private String blankBoardString = "";
    private Tetris tetris;
    private String saveString = "";

    @BeforeEach
    public void setup() {
        tetris = new Tetris(0);

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                testBoardGrid[i][j] = 'e';
            }
        }

        for (int i = 0; i < BLOCKS_WIDE * BLOCKS_HIGH; i++) {
            blankBoardString += "e";
        }
    }

    @Test
    public void testMatrixToString() {
        assertEquals(blankBoardString, SaveGame.matrixToString(tetris));
    }

    @Test
    public void testSaveGameNoException() {
        String testSaveFilename = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/testsavegame";
        try {
            tetris = new Tetris(loadHighScore("testhighscore"));
        } catch (MissingFileException | IOException e) {
            fail("should not throw exception");
        }

        tetris.setCurrentTetrominoByLabel('l');
        tetris.setNextTetrominoByLabel('i');
        testBoardGrid[19][9] = 'i';
        testBoardGrid[19][8] = 'i';
        testBoardGrid[19][7] = 'i';
        testBoardGrid[19][6] = 'i';
        tetris.setGameBoard(testBoardGrid);
        tetris.setScore(10);
        for (int i = 0; i < BLOCKS_HIGH * BLOCKS_WIDE - 4; i++) {
            saveString += "e";
        }
        for (int i = 0; i < 4; i ++) {
            saveString += "i";
        }
        saveString += "@l@i@10";
        String savedString = "";

        try {
            saveGame("testsavegame", tetris);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(testSaveFilename));
            savedString = br.readLine();
        } catch (Exception e) {
            // will not occur, not testing built in functions
        }

        assertEquals(savedString, savedString);
    }

    @Test
    public void testSaveGameIOException() {
        tetris.setCurrentTetrominoByLabel('l');
        tetris.setNextTetrominoByLabel('i');

        try {
            saveGame("/", tetris);
            fail("IOException should have been thrown");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testLoadGameNoException() {
        try {
            tetris = new Tetris(loadHighScore("testhighscore"));
        } catch (MissingFileException e) {
            fail("should not throw MissingFileException");
        } catch (IOException e) {
            fail("should not produce IOException");
        }
        tetris.setCurrentTetrominoByLabel('l');
        tetris.setNextTetrominoByLabel('i');
        testBoardGrid[19][9] = 'i';
        testBoardGrid[19][8] = 'i';
        testBoardGrid[19][7] = 'i';
        testBoardGrid[19][6] = 'i';
        tetris.setGameBoard(testBoardGrid);
        tetris.setScore(10);

        try {
            this.tetris = loadGame("testsavegame");
        } catch (MissingFileException | IOException e) {
            fail("Should not have thrown exception");
        }
        assertEquals(this.tetris.getCurrentTetrominoLabel(), tetris.getCurrentTetrominoLabel());
        assertEquals(this.tetris.getNextTetrominoLabel(), tetris.getNextTetrominoLabel());
        assertEquals(this.tetris.getGameBoard(), tetris.getGameBoard());
        assertEquals(this.tetris.getScore(), tetris.getScore());
        assertEquals(this.tetris.getHighScore(), tetris.getHighScore());
    }

    @Test
    public void testLoadGameMissingFileException() {
        try {
            tetris = loadGame("nonexistentfilename");
        } catch (MissingFileException e) {
            // expected
        } catch (IOException e) {
            fail("Should not have thrown exception");
        }
    }

    @Test
    public void testLoadGameIOException() {
        try {
            tetris = loadGame("/");
        } catch (MissingFileException e) {
            fail("Should not have thrown exception");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    public void testLoadTetris() {
        tetris.setCurrentTetrominoByLabel('l');
        tetris.setNextTetrominoByLabel('i');
        testBoardGrid[19][9] = 'i';
        testBoardGrid[19][8] = 'i';
        testBoardGrid[19][7] = 'i';
        testBoardGrid[19][6] = 'i';
        tetris.setGameBoard(testBoardGrid);
        tetris.setScore(10);
        for (int i = 0; i < BLOCKS_HIGH * BLOCKS_WIDE - 4; i++) {
            saveString += "e";
        }
        for (int i = 0; i < 4; i ++) {
            saveString += "i";
        }
        saveString += "@l@i@10";

        try {
            tetris = loadTetris(saveString.split("@"));
        } catch (IOException | MissingFileException e) {
            fail("should not produce exception");
            // testing for these exceptions in LoadHighScoreTests
        }

        assertEquals(tetris.getCurrentTetrominoLabel(), 'l');
        assertEquals(tetris.getNextTetrominoLabel(), 'i');
        assertEquals(tetris.getScore(), 10);

        char[][] loadedBoardTestGrid = tetris.getGameBoard().getBoardGrid();
        for (int row = 0; row < BLOCKS_HIGH; row++) {
            for (int col = 0; col < BLOCKS_WIDE; col++) {
                assertEquals(loadedBoardTestGrid[row][col], testBoardGrid[row][col]);
            }
        }
    }

    @Test
    public void testLoadedBoard() {
        char[][] loadedBoardTestGrid = loadedBoard(blankBoardString);

        for (int row = 0; row < BLOCKS_HIGH; row++) {
            for (int col = 0; col < BLOCKS_WIDE; col++) {
                assertEquals(loadedBoardTestGrid[row][col], testBoardGrid[row][col]);
            }
        }
    }
}
