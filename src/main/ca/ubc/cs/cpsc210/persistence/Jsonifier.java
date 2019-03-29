package ca.ubc.cs.cpsc210.persistence;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import org.json.JSONArray;
import org.json.JSONObject;

import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;

public class Jsonifier {
    /**
     * Constants
     */
    public static final String KEY_TETROMINO_LABEL = "label";
    public static final String KEY_TETROMINO_COLOUR = "tetrominoColour";
    public static final String KEY_TETROMINO_X = "tetrominoX";
    public static final String KEY_TETROMINO_Y = "tetrominoY";
    public static final String KEY_TETROMINO_ROTATION_POSITION = "rotationPosition";
    public static final String KEY_TETROMINO_SHAPE_ROWS = "shapeRows";
    public static final String KEY_TETROMINO_SHAPE_COLS = "shapeCols";
    public static final String KEY_TETROMINO_SHAPE = "shape";

    public static final String KEY_BOARD = "board";

    public static final String KEY_GAME_BACKGROUND_SCORE = "score";
    public static final String KEY_GAME_BACKGROUND_HIGH_SCORE = "highScore";
    public static final String KEY_GAME_BACKGROUND_LINES_CLEARED = "linesCleared";
    public static final String KEY_GAME_BACKGROUND_BACKGROUND_COLOUR = "backgroundColour";

    public static final String KEY_TETRIS_CURRENT_TETROMINO = "currentTetromino";
    public static final String KEY_TETRIS_NEXT_TETROMINO = "nextTetromino";
    public static final String KEY_TETRIS_BOARD = "board";
    public static final String KEY_TETRIS_GAME_BACKGROUND = "gameBackground";
    public static final String KEY_TETRIS_HIGH_SCORE = "highScore";
    public static final String KEY_TETRIS_MYSTERY_BUTTON_NAME = "mysteryButtonName";
    public static final String KEY_TETRIS_IS_PLAY_MUSIC = "isPlayMusic";

    public static final String KEY_GAME_LEVEL = "level";
    public static final String KEY_GAME_FALL_SPEED = "fallSpeed";
    public static final String KEY_GAME_LINES_TO_CLEAR = "linesToClear";

    /**
     * Methods
     */
    // EFFECTS: returns JSONObject representing a Tetromino object
    public static JSONObject tetrominoToJson(Tetromino t) {
        JSONObject tetrominoJson = new JSONObject();

        tetrominoJson.put(KEY_TETROMINO_LABEL, String.valueOf(t.getLabel()));
        tetrominoJson.put(KEY_TETROMINO_COLOUR, Integer.toString(t.getTetrominoColour().getRGB()));
        tetrominoJson.put(KEY_TETROMINO_X, t.getTetrominoX());
        tetrominoJson.put(KEY_TETROMINO_Y, t.getTetrominoY());
        tetrominoJson.put(KEY_TETROMINO_ROTATION_POSITION, t.getRotationPosition());
        tetrominoJson.put(KEY_TETROMINO_SHAPE_ROWS, t.getShape().length);
        tetrominoJson.put(KEY_TETROMINO_SHAPE_COLS, t.getShape()[0].length);

        JSONArray tetrominoShapeJsonArrayRow = new JSONArray();

        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                tetrominoShapeJsonArrayRow.put(t.getShape()[i][j]);
            }
            tetrominoJson.put(KEY_TETROMINO_SHAPE + i, tetrominoShapeJsonArrayRow);
            tetrominoShapeJsonArrayRow = new JSONArray();
        }

        return tetrominoJson;
    }

    // EFFECTS: returns JSONObject representing a Board object
    public static JSONObject boardToJson(Board b) {
        JSONObject boardJson = new JSONObject();
        JSONArray boardJsonArrayRow = new JSONArray();

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                boardJsonArrayRow.put(String.valueOf(b.getBoardGrid()[i][j]));
            }
            boardJson.put(KEY_BOARD + i, boardJsonArrayRow);
            boardJsonArrayRow = new JSONArray();
        }

        return boardJson;
    }

    // EFFECTS: returns JSONObject representing a GameBackground object
    public static JSONObject gameBackgroundToJson(GameBackground gb) {
        JSONObject gbJson = new JSONObject();

        gbJson.put(KEY_GAME_BACKGROUND_SCORE, gb.getScore());
        gbJson.put(KEY_GAME_BACKGROUND_HIGH_SCORE, gb.getHighScore());
        gbJson.put(KEY_GAME_BACKGROUND_LINES_CLEARED, gb.getLinesCleared());
        gbJson.put(KEY_GAME_BACKGROUND_BACKGROUND_COLOUR, Integer.toString(gb.getBackgroundColour().getRGB()));

        return gbJson;
    }

    // EFFECTS: returns JSONObject representing a Tetris object
    public static JSONObject tetrisToJson(Tetris t) {
        JSONObject tetrisJson = new JSONObject();

        tetrisJson.put(KEY_TETRIS_CURRENT_TETROMINO, tetrominoToJson(t.getCurrentTetromino()));
        tetrisJson.put(KEY_TETRIS_NEXT_TETROMINO, tetrominoToJson(t.getNextTetromino()));
        tetrisJson.put(KEY_TETRIS_BOARD, boardToJson(t.getBoard()));
        tetrisJson.put(KEY_TETRIS_GAME_BACKGROUND, gameBackgroundToJson(t.getGameBackground()));
        tetrisJson.put(KEY_TETRIS_HIGH_SCORE, t.getHighScore());
        tetrisJson.put(KEY_TETRIS_MYSTERY_BUTTON_NAME, t.getMysteryButton().getButtonName());
        tetrisJson.put(KEY_GAME_FALL_SPEED, t.getFallSpeed());
        tetrisJson.put(KEY_GAME_LEVEL, t.getLevel());
        tetrisJson.put(KEY_GAME_LINES_TO_CLEAR, t.getLinesToClear());
        tetrisJson.put(KEY_TETRIS_IS_PLAY_MUSIC, t.isPlayMusic());

        return tetrisJson;
    }
}