package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SaveGame {


    public static void saveGame(String fileName, Tetris tetris) {

        // save useful saveGame data into a string
        String saveData = "";
        int highScorePlaceholder = 10;

        // add each data value separated by a '.'
        saveData += highScorePlaceholder + '.';

        try {
            File file = new File(fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(saveData);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
