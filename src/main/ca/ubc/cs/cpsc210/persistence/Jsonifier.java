package ca.ubc.cs.cpsc210.persistence;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.json.JSONArray;
import org.json.JSONObject;

import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;

public class Jsonifier {

    // EFFECTS: returns JSON object representing a Tetromino object
    public static JSONObject tetrominoToJson(Tetromino t) {
        JSONObject tetrominoJson = new JSONObject();

        tetrominoJson.put("label", String.valueOf(t.getLabel()));
        tetrominoJson.put("tetrominoColour", Integer.toString(t.getTetrominoColour().getRGB()));
        tetrominoJson.put("tetrominoX", t.getTetrominoX());
        tetrominoJson.put("tetrominoY", t.getTetrominoY());
        tetrominoJson.put("rotationPosition", t.getRotationPosition());
        tetrominoJson.put("shapeRows", t.getShape().length);
        tetrominoJson.put("shapeCols", t.getShape()[0].length);

        JSONArray tetrominoShapeJsonArrayRow = new JSONArray();

        for (int i = 0; i < t.getShape().length; i++) {
            for (int j = 0; j < t.getShape()[0].length; j++) {
                tetrominoShapeJsonArrayRow.put(t.getShape()[i][j]);
            }
            tetrominoJson.put("shape" + i, tetrominoShapeJsonArrayRow);
            tetrominoShapeJsonArrayRow = new JSONArray();
        }

        return tetrominoJson;
    }

    // EFFECTS: returns JSON object representing a Board object
    public static JSONObject boardToJson(Board b) {
        JSONObject boardJson = new JSONObject();
        JSONArray boardJsonArrayRow = new JSONArray();

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                boardJsonArrayRow.put(String.valueOf(b.getBoardGrid()[i][j]));
            }
            boardJson.put("board" + i, boardJsonArrayRow);
            boardJsonArrayRow = new JSONArray();
        }

        return boardJson;
    }

    // EFFECTS: returns JSON object representing a Tetris object
    public static JSONObject tetrisToJson(Tetris t) {
        JSONObject tetrisJson = new JSONObject();

        tetrisJson.put("currentTetromino", tetrominoToJson(t.getCurrentTetromino()));
        tetrisJson.put("nextTetromino", tetrominoToJson(t.getNextTetromino()));
        tetrisJson.put("board", boardToJson(t.getGameBoard()));
        tetrisJson.put("score", t.getScore());
        tetrisJson.put("linesCleared", t.getLinesCleared());
        tetrisJson.put("highScore", t.getHighScore());

        return tetrisJson;
    }
}
