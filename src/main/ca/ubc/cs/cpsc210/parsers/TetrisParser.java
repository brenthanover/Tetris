package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.ui.Tetris;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.ui.Tetris.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Tetris.BLOCKS_WIDE;

public class TetrisParser {


    // EFFECTS: parses JSONObject representing a Tetris object, returns Tetris object
    public static Tetris parseTetris(JSONObject tetrisJson) {
        Tetris parsedTetris = new Tetris(0);

        try {
            parsedTetris = new Tetris(loadHighScore("highscore"));
        } catch (MissingFileException | IOException e) {
            //
        }

        Tetromino currentTetromino = parseTetromino(tetrisJson.getJSONObject("currentTetromino"));
        Tetromino nextTetromino = parseTetromino(tetrisJson.getJSONObject("nextTetromino"));
        Board board = parseBoard(tetrisJson.getJSONObject("board"));
        int score = tetrisJson.getInt("score");
        int linesCleared = tetrisJson.getInt("linesCleared");
        int highScore = tetrisJson.getInt("highScore");

        parsedTetris.setCurrentTetromino(currentTetromino);
        parsedTetris.setNextTetromino(nextTetromino);
        parsedTetris.setGameBoard(board.getBoardGrid());
        parsedTetris.setScore(score);
        parsedTetris.setLinesCleared(linesCleared);
        parsedTetris.setHighScore(highScore);

        return parsedTetris;
    }

    // EFFECTS: parses JSONObject representing a Tetromino object, returns Tetromino object
    public static Tetromino parseTetromino(JSONObject tetrominoJson) {
        char label = tetrominoJson.getString("label").charAt(0);
        Color tetrominoColour = new Color(Integer.parseInt(tetrominoJson.getString("tetrominoColour")));
        int tetrominoX = tetrominoJson.getInt("tetrominoX");
        int tetrominoY = tetrominoJson.getInt("tetrominoY");
        int rotationPosition = tetrominoJson.getInt("rotationPosition");
        int[][] shape = getShapeFromJson(tetrominoJson);

        Tetromino parsedTetromino = new Tetromino(shape, tetrominoColour, label);
        parsedTetromino.setTetrominoX(tetrominoX);
        parsedTetromino.setTetrominoY(tetrominoY);
        parsedTetromino.setRotationPosition(rotationPosition);

        return parsedTetromino;
    }

    // EFFECTS: parses JSONObject representing a Tetromino object, returns tetromino shape
    private static int[][] getShapeFromJson(JSONObject tetrominoJson) {
        int shapeRows = tetrominoJson.getInt("shapeRows");
        int shapeCols = tetrominoJson.getInt("shapeCols");
        int[][] shape = new int[shapeRows][shapeCols];
        JSONArray shapeRow;
        for (int i = 0; i < shapeRows; i++) {
            shapeRow = (tetrominoJson.getJSONArray("shape" + i));
            for (int j = 0; j < shapeCols; j++) {
                shape[i][j] = shapeRow.getInt(j);
            }
        }

        return shape;
    }

    // EFFECTS: parses JSONObject representing a Board object, returns Board object
    public static Board parseBoard(JSONObject boardJson) {
        Board parsedBoard = new Board();
        JSONArray boardRow;

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            boardRow = boardJson.getJSONArray("board" + i);
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                parsedBoard.setBoardGridBlock(i, j, boardRow.getString(j).charAt(0));
            }
        }

        return parsedBoard;
    }
}
