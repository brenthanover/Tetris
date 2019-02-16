package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.parsers.exceptions.MissingScoreException;
import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class LoadHighScore {

    // EFFECTS: loads saved game data from text file and produce Tetris game state
    public static int loadHighScore(String fileName) {
        String data = "0";
        String path = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = path + fileName;

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            data = br.readLine();


            // decode the data here from string data
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Integer.parseInt(data);
    }


}
