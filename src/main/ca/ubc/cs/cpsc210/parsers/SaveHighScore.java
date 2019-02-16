package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class SaveHighScore {


    public static void saveHighScore(String fileName, int highScore) {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        String saveData = Integer.toString(highScore);
        fileName = directory + fileName;

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
