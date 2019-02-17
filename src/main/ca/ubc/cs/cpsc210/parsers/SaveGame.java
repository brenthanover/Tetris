package ca.ubc.cs.cpsc210.parsers;

import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class SaveGame {


    public static void saveGame(String fileName, Tetris tetris) {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;

        // save useful saveGame data into a string
        String saveData = "";

        // add each data value separated by a '.'
        saveData += boardToString() + "@";
        saveData += tetris.getCurrentTetrominoLabel() + "@";
        saveData += tetris.getNextTetrominoLabel() + "@";
        saveData += tetris.getScore();


        try {
            File file = new File(fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(saveData);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String boardToString() {
        char[][] board = tetris.getGameBoard().getBoardGrid();
        String output = "";

        for (int i = 0; i < BLOCKS_HIGH; i++) {
            for (int j = 0; j < BLOCKS_WIDE; j++) {
                output += board[i][j];
            }
        }

        return output;
    }
}
