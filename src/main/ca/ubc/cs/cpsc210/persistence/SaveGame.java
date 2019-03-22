package ca.ubc.cs.cpsc210.persistence;

import ca.ubc.cs.cpsc210.model.Tetris;

import static ca.ubc.cs.cpsc210.persistence.Jsonifier.tetrisToJson;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveGame {

    // MODIFIES: save file called fileName in String directory
    // EFFECTS:  saves tetris to a string in file fileName by changing to JSONObject with Jsonifier
    public static void saveGame(String fileName, Tetris tetris) throws IOException {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;

        // save useful saveGame data into a string
        String saveDataString = tetrisToJson(tetris).toString();

        File file = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(saveDataString);
        bw.close();
    }
}
