package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class LoadGame {

    // EFFECTS: loads saved game data from text file and produce Tetris game state
    public static Tetris loadGame(String fileName) throws MissingFileException, IOException {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;
        String data = "";
        File file = new File(fileName);

        if (!file.exists()) {
            throw new MissingFileException("file does not exist");
        }

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        data = br.readLine();


        // decode the data here from string data
        String[] parsedData = data.split("@", 5);

        return loadTetris(parsedData);
    }

    public static Tetris loadTetris(String[] parsedData) throws MissingFileException, IOException {
        String boardString = parsedData[0];
        char currentColour = parsedData[1].charAt(0);
        char nextColour = parsedData[2].charAt(0);
        int score = Integer.parseInt(parsedData[3]);
        Tetris loadedTetris;

        loadedTetris = new Tetris(loadHighScore("highscore"));

        loadedTetris.setScore(score);
        loadedTetris.setGameBoard(loadedBoard(boardString));
        loadedTetris.setCurrentTetrominoByLabel(currentColour);
        loadedTetris.setNextTetrominoByLabel(nextColour);

        return loadedTetris;
    }

    public static char[][] loadedBoard(String data) {
        int count = 0;
        char[][] output = new char[BLOCKS_HIGH][BLOCKS_WIDE];

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                output[i][j] = data.charAt(count);
                count++;
            }
        }

        return output;
    }
}
