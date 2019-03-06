package ca.ubc.cs.cpsc210.parsers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveHighScore {


    public static void saveHighScore(String fileName, int highScore) throws IOException {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        String saveData = Integer.toString(highScore);
        fileName = directory + fileName;


        File file = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(saveData);
        bw.close();
    }
}
