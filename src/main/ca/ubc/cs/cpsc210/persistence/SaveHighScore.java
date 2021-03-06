package ca.ubc.cs.cpsc210.persistence;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHighScore {
    /**
     *  Constants
     */
    public static final String HIGH_SCORE_FILENAME = "highscore";
    public static final String HIGH_SCORE_DIRECTORY = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";

    /**
     *  Methods
     */
    // MODIFIES: highScore save file
    // EFFECTS:  saves Tetris.highScore to file
    public static void saveHighScore(String fileName, int highScore) throws IOException {
        String directory = HIGH_SCORE_DIRECTORY;
        String saveData = Integer.toString(highScore);
        fileName = directory + fileName;


        File file = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(saveData);
        bw.close();
    }
}
