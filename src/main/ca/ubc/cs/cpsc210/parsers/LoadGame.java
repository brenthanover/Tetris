package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedReader;
import java.io.FileReader;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class LoadGame {

    // EFFECTS: loads saved game data from text file and produce Tetris game state
    public static void loadGame(String fileName) {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;
        String data = "";

        try { 
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            data = br.readLine();

            // decode the data here from string data
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(data);

        String[] parsedData = data.split("@", 5);

        for (String s : parsedData) {
            System.out.println(s);
        }

        tetris = loadTetris(parsedData);
    }

    private static Tetris loadTetris(String[] parsedData) {
        for (String s : parsedData) {
            System.out.println(s);
        }
        String boardString = parsedData[0];
        char currentColour = parsedData[1].charAt(0);
        char nextColour = parsedData[2].charAt(0);
        int score = Integer.parseInt(parsedData[3]);

        Tetris loadedTetris = new Tetris(tetris.getHighScore());

        loadedTetris.setScore(score);
        loadedTetris.setGameBoard(loadedBoard(boardString));
        loadedTetris.setCurrentTetromino(currentColour);
        loadedTetris.setNextTetromino(nextColour);

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
