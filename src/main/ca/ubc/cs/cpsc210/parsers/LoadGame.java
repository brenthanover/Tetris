package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static ca.ubc.cs.cpsc210.ui.Tetris.tetris;

public class LoadGame {


    // EFFECTS: converts text file in the style 'int.' (repeated) to
    //          integer array list of integers separated by '.' in text file
    private static ArrayList<Integer> decodeSavedTextToArray(String saveData) {
        ArrayList<Integer> decoded = new ArrayList<>();
        String dataString = "";

        for (char c : saveData.toCharArray()) {
            if (c == '.') {
                decoded.add(Integer.parseInt(dataString));
                dataString = "";
            } else {
                dataString += c;
            }
        }

        return decoded;
    }

    // EFFECTS: decodes array of integers into Tetris saved game state
    private static Tetris decodeArrayToGameState(ArrayList<Integer> decodedData) {
        Tetris loadedTetris = new Tetris(0);

        // save each decoded value to loadedTetris

        return loadedTetris;
    }

    // EFFECTS: loads saved game data from text file and produce Tetris game state
    public static void loadGame(String fileName) {
        Tetris loadedTetris = new Tetris(tetris.getHighScore());

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String data = br.readLine();
            ArrayList<Integer> decodedData = decodeSavedTextToArray(data);
            loadedTetris = decodeArrayToGameState(decodedData);

            // decode the data here from string data
        } catch (Exception e) {
            e.printStackTrace();
        }

        tetris = loadedTetris;
    }
}
