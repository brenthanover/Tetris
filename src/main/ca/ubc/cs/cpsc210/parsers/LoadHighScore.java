package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;

import java.io.*;

import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.HIGH_SCORE_DIRECTORY;

public class LoadHighScore {
    /**
     *  Methods
     */
    // EFFECTS: loads saved game data from text file and produce Tetris game state
    public static int loadHighScore(String fileName) throws MissingFileException, IOException {
        String data = "0";
        String path = HIGH_SCORE_DIRECTORY;
        fileName = path + fileName;

        File file = new File(fileName);

        if (!file.exists()) {
            throw new MissingFileException("file does not exist");
        } else {
            // decode the data here from string data
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            data = br.readLine();
        }

        return Integer.parseInt(data);
    }


}
