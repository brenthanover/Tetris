package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.Jsonifier.*;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.HIGH_SCORE_FILENAME;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;

public class GameParser {
    /**
     *  Methods
     */
    // EFFECTS: parses JSONObject representing a Tetromino object, returns Tetromino object
    public static Tetromino parseTetromino(JSONObject tetrominoJson) {
        char label = tetrominoJson.getString(KEY_TETROMINO_LABEL).charAt(0);
        Color tetrominoColour = new Color(Integer.parseInt(tetrominoJson.getString(KEY_TETROMINO_COLOUR)));
        int tetrominoX = tetrominoJson.getInt(KEY_TETROMINO_X);
        int tetrominoY = tetrominoJson.getInt(KEY_TETROMINO_Y);
        int rotationPosition = tetrominoJson.getInt(KEY_TETROMINO_ROTATION_POSITION);
        int[][] shape = getShapeFromJson(tetrominoJson);

        Tetromino parsedTetromino = new Tetromino(shape, tetrominoColour, label);
        parsedTetromino.setTetrominoX(tetrominoX);
        parsedTetromino.setTetrominoY(tetrominoY);
        parsedTetromino.setRotationPosition(rotationPosition);

        return parsedTetromino;
    }

    // EFFECTS: parses JSONObject representing a Tetromino object, returns tetromino shape
    private static int[][] getShapeFromJson(JSONObject tetrominoJson) {
        int shapeRows = tetrominoJson.getInt(KEY_TETROMINO_SHAPE_ROWS);
        int shapeCols = tetrominoJson.getInt(KEY_TETROMINO_SHAPE_COLS);
        int[][] shape = new int[shapeRows][shapeCols];
        JSONArray shapeRow;
        for (int i = 0; i < shapeRows; i++) {
            shapeRow = (tetrominoJson.getJSONArray(KEY_TETROMINO_SHAPE + i));
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
            boardRow = boardJson.getJSONArray(KEY_BOARD + i);
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                parsedBoard.setBoardGridBlock(i, j, boardRow.getString(j).charAt(0));
            }
        }

        return parsedBoard;
    }

    // EFFECTS: parses JSONObject representing a GameBackgroundObject, returns GameBackground object
    public static GameBackground parseGameBackground(JSONObject gameBackgroundJson) {
        GameBackground parsedGameBackground = new GameBackground(0);

        int score = gameBackgroundJson.getInt(KEY_GAME_BACKGROUND_SCORE);
        int highScore = gameBackgroundJson.getInt(KEY_GAME_BACKGROUND_HIGH_SCORE);
        int linesCleared = gameBackgroundJson.getInt(KEY_GAME_BACKGROUND_LINES_CLEARED);
        String colorString = gameBackgroundJson.getString(KEY_GAME_BACKGROUND_BACKGROUND_COLOUR);
        Color backgroundColour = new Color(Integer.parseInt(colorString));

        parsedGameBackground.setScore(score);
        parsedGameBackground.setHighScore(highScore);
        parsedGameBackground.setLinesCleared(linesCleared);
        parsedGameBackground.setBackgroundColour(backgroundColour);

        return parsedGameBackground;
    }

    // EFFECTS: parses JSONObject representing a Tetris object, returns Tetris object
    public static Tetris parseTetris(JSONObject tetrisJson) {
        Tetris parsedTetris = new Tetris(0);

        try {
            parsedTetris = new Tetris(loadHighScore(HIGH_SCORE_FILENAME));
        } catch (MissingFileException | IOException e) {
            e.printStackTrace();
        }

        return getTetrisParameters(tetrisJson, parsedTetris);
    }

    // MODIFIES: parsedTetris
    // EFFECTS:  gets tetris parameters from JSONObject, saves to parsedTetris
    private static Tetris getTetrisParameters(JSONObject tetrisJson, Tetris parsedTetris) {
        Tetromino currentTetromino = parseTetromino(tetrisJson.getJSONObject(KEY_TETRIS_CURRENT_TETROMINO));
        Tetromino nextTetromino = parseTetromino(tetrisJson.getJSONObject(KEY_TETRIS_NEXT_TETROMINO));
        GameBackground gameBackground = parseGameBackground(tetrisJson.getJSONObject(KEY_TETRIS_GAME_BACKGROUND));

        parsedTetris.setCurrentTetromino(currentTetromino);
        parsedTetris.setNextTetromino(nextTetromino);
        parsedTetris.setGameBoard(parseBoard(tetrisJson.getJSONObject(KEY_TETRIS_BOARD)).getBoardGrid());
        parsedTetris.setGameBackground(gameBackground);
        parsedTetris.setHighScore(tetrisJson.getInt(KEY_TETRIS_HIGH_SCORE));
        parsedTetris.getMysteryButton().setButtonName(tetrisJson.getString(KEY_TETRIS_MYSTERY_BUTTON_NAME));
        parsedTetris.setLevel(tetrisJson.getInt(KEY_GAME_LEVEL));
        parsedTetris.setLinesToClear(tetrisJson.getInt(KEY_GAME_LINES_TO_CLEAR));
        parsedTetris.setFallSpeed(tetrisJson.getInt(KEY_GAME_FALL_SPEED));
        parsedTetris.setPlayMusic(tetrisJson.getBoolean(KEY_TETRIS_IS_PLAY_MUSIC));

        return parsedTetris;
    }
}
