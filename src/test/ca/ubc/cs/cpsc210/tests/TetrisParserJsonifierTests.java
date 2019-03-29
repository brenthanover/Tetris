package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.ui.Game;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static ca.ubc.cs.cpsc210.model.Tetromino.J_COLOUR;
import static ca.ubc.cs.cpsc210.model.Tetromino.jTetrominoMatrix;
import static ca.ubc.cs.cpsc210.parsers.GameParser.*;
import static ca.ubc.cs.cpsc210.persistence.Jsonifier.*;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisParserJsonifierTests {
    /**
     *  Declarations
     */
    private Tetromino jTetromino;
    private Tetromino testTetromino;
    private Board board;
    private Board testBoard;
    private Tetris tetris;
    private Tetris testTetris;
    private GameBackground gameBackground;
    private GameBackground testGameBackground;

    /**
     *  Tests
     */
    @BeforeEach
    public void setup() {
        jTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        board = new Board();
        testBoard = new Board();
        tetris = new Tetris(0);
        testTetris = new Tetris(0);
        gameBackground = new GameBackground(0);
        testGameBackground = new GameBackground(0);
    }

    @Test
    public void testTetrominoSame() {
        jTetromino.fall();
        jTetromino.rotateCW();

        JSONObject tetrominoJson = tetrominoToJson(jTetromino);
        testTetromino = parseTetromino(tetrominoJson);

        assertEquals(jTetromino, testTetromino);
    }

    @Test
    public void testTetrominoDifferent() {
        jTetromino.fall();
        jTetromino.rotateCW();

        JSONObject tetrominoJson = tetrominoToJson(jTetromino);
        testTetromino = parseTetromino(tetrominoJson);

        assertEquals(jTetromino, testTetromino);
        jTetromino.fall();
        assertNotEquals(jTetromino, testTetromino);
    }

    @Test
    public void testBoardSame() {
        board.setBoardGridBlock(4, 6, 'i');

        JSONObject boardJson = boardToJson(board);
        testBoard = parseBoard(boardJson);

        assertEquals(board, testBoard);
    }

    @Test
    public void testBoardDifferent() {
        board.setBoardGridBlock(4, 6, 'i');

        JSONObject boardJson = boardToJson(board);
        testBoard = parseBoard(boardJson);

        assertEquals(board, testBoard);
        board.setBoardGridBlock(4, 7, 'i');
        assertNotEquals(board, testBoard);
    }

    @Test
    public void testGameBackgroundSame() {
        gameBackground.setScore(20);
        gameBackground.setHighScore(2000);
        gameBackground.setLinesCleared(20);
        gameBackground.setBackgroundColour(Color.red);

        JSONObject gameBackgroundJson = gameBackgroundToJson(gameBackground);
        testGameBackground = parseGameBackground(gameBackgroundJson);

        assertEquals(gameBackground, testGameBackground);
    }

    @Test
    public void testGameBackgroundDifferent() {
        gameBackground.setScore(20);
        gameBackground.setHighScore(2000);
        gameBackground.setLinesCleared(20);
        gameBackground.setBackgroundColour(Color.red);

        JSONObject gameBackgroundJson = gameBackgroundToJson(gameBackground);
        testGameBackground = parseGameBackground(gameBackgroundJson);

        assertEquals(gameBackground, testGameBackground);
        gameBackground.setScore(0);
        assertNotEquals(gameBackground, testGameBackground);
    }

    @Test
    public void testTetrisSame() {
        tetris.setCurrentTetrominoByLabel('s');
        tetris.setNextTetrominoByLabel('i');
        tetris.getBoard().setBoardGridBlock(4, 5, 'j');
        tetris.getGameBackground().setScore(20);
        tetris.getGameBackground().setHighScore(2000);
        tetris.getGameBackground().setLinesCleared(20);
        tetris.getGameBackground().setBackgroundColour(Color.red);

        JSONObject tetrisJson = tetrisToJson(tetris);
        testTetris = parseTetris(tetrisJson);

        assertEquals(tetris, testTetris);
    }
}
