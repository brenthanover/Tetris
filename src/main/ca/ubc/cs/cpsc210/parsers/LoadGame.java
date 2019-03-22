package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.TetrisParser.parseTetris;

public class LoadGame {

    // MODIFIES: Game
    // EFFECTS:  loads saved game data from String fileName
    //           parses string into JSONObject
    //           parses JSONObject into Tetris object
    //           sets current game state to parsed Tetris object
    public static void loadGame(String fileName, Tetris tetris) throws MissingFileException, IOException {
        String data = loadString(fileName);

        JSONObject obj = new JSONObject(data);
        Tetris parsedTetris = parseTetris(obj);

        setTetris(tetris, parsedTetris);
    }

    // EFFECTS: returns data string from file in savefiles directory
    public static String loadString(String fileName) throws MissingFileException, IOException {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;
        File file = new File(fileName);

        if (!file.exists()) {
            throw new MissingFileException("file does not exist");
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));

        return br.readLine();
    }

    // EFFECTS: sets current game state tetris to parameters of parsedTetris
    public static void setTetris(Tetris tetris, Tetris parsedTetris) {
        tetris.setCurrentTetromino(parsedTetris.getCurrentTetromino());
        tetris.setNextTetromino(parsedTetris.getNextTetromino());
        tetris.setGameBoard(parsedTetris.getGameBoard().getBoardGrid());
        tetris.setScore(parsedTetris.getScore());
        tetris.setLinesCleared(parsedTetris.getLinesCleared());
        tetris.setHighScore(parsedTetris.getHighScore());
        tetris.setPaused(true);
    }
}