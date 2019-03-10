package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cs.cpsc210.model.Tetromino.J_COLOUR;
import static ca.ubc.cs.cpsc210.model.Tetromino.jTetrominoMatrix;
import static ca.ubc.cs.cpsc210.parsers.TetrisParser.*;
import static ca.ubc.cs.cpsc210.persistence.Jsonifier.*;
import static org.junit.jupiter.api.Assertions.*;

public class TetrisParserJsonifierTests {

    private Tetromino jTetromino;
    private Tetromino testTetromino;
    private Board board;
    private Board testBoard;
    private Tetris tetris;
    private Tetris testTetris;

    @BeforeEach
    public void setup() {
        jTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        testTetromino = new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        board = new Board();
        testBoard = new Board();
        tetris = new Tetris(0);
        testTetris = new Tetris(0);
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
        board.setBoardGridBlock(4, 7, 'i');
        assertNotEquals(board, testBoard);
    }

    @Test
    public void testTetrisSame() {
        tetris.setCurrentTetrominoByLabel('s');
        tetris.setNextTetrominoByLabel('i');
        tetris.getGameBoard().setBoardGridBlock(4, 5, 'j');
        tetris.setScore(1200);
        tetris.setLinesCleared(123);

        JSONObject tetrisJson = tetrisToJson(tetris);
        testTetris = parseTetris(tetrisJson);

        assertEquals(tetris, testTetris);
    }
}
