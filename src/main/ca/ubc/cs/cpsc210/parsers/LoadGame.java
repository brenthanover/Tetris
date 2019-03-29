package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.GameParser.parseTetris;
import static ca.ubc.cs.cpsc210.ui.buttons.TetrisButton.*;

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
        tetris.setGameBoard(parsedTetris.getBoard().getBoardGrid());
        tetris.setGameBackground(parsedTetris.getGameBackground());
        tetris.setHighScore(parsedTetris.getHighScore());
        tetris.setPlayMusic(parsedTetris.isPlayMusic());
        tetris.getMusicButton().setButtonName(MUSIC_BUTTON_NAME_CLICKED);
        tetris.getMysteryButton().setButtonName(parsedTetris.getMysteryButton().getButtonName());
        tetris.getPauseButton().setButtonName(PAUSE_BUTTON_NAME_CLICKED);
        tetris.setPaused(true);
        tetris.setLinesToClear(parsedTetris.getLinesToClear());
        tetris.setLevel(parsedTetris.getLevel());
        tetris.setFallSpeed(parsedTetris.getFallSpeed());
        tetris.setGameLoaded(true);
        setMusic(tetris);
        tetris.getGameBackground().setLevel(parsedTetris.getLevel());
        tetris.getGameBackground().setLinesToClear(parsedTetris.getLinesToClear());
    }

    // EFFECTS: plays music according to mystery button name if loaded tetris has music on
    private static void setMusic(Tetris tetris) {
        if (tetris.isPlayMusic()) {
            tetris.getMusicButton().setButtonName(MUSIC_BUTTON_NAME_INITIAL);
            switch (tetris.getMysteryButton().getButtonName()) {
                case MYSTERY_BUTTON_NAME_SHREK:
                    tetris.getTetrisMusic().playTetrisTheme();
                    break;
                case MYSTERY_BUTTON_NAME_KENNY:
                    tetris.getTetrisMusic().playShrekTheme();
                    break;
                default:
                    tetris.getTetrisMusic().playSaxTheme();
                    break;
            }
        } else {
            tetris.getMusicButton().setButtonName(MUSIC_BUTTON_NAME_CLICKED);
        }
    }
}