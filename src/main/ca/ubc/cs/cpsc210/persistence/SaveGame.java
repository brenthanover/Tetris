package ca.ubc.cs.cpsc210.persistence;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_HIGH;
import static ca.ubc.cs.cpsc210.ui.Game.BLOCKS_WIDE;

public class SaveGame {


    public static void saveGame(String fileName, Tetris tetris) throws IOException {
        String directory = "src/main/ca/ubc/cs/cpsc210/resources/savefiles/";
        fileName = directory + fileName;

        // save useful saveGame data into a string
        String saveData = "";

        // add each data value separated by a '.'
        saveData += matrixToString(tetris) + "@";
        saveData += tetris.getCurrentTetrominoLabel() + "@";
        saveData += tetris.getNextTetrominoLabel() + "@";
        saveData += tetris.getScore();


        File file = new File(fileName);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(saveData);
        bw.close();

    }

    public static String matrixToString(Tetris tetris) {
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
