package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.loadGame;

public class LoadButton extends TetrisButton {

    /**
     *  Declarations
     */
    private Tetris tetris;
    private String saveFile;

    /**
     *  Constructor
     */
    // EFFECTS: constructs LoadButton object
    public LoadButton(Tetris tetris) {
        super(lBX, lBY, lBW, lBH, lBN);
        this.tetris = tetris;
        saveFile = "savegame";
    }

    /**
     *  Methods
     */
    // REQUIRES: gameStart = true
    // MODIFIES: Game
    // EFFECTS:  loads game from save file and pauses the game
    public void buttonAction() {
        try {
            loadGame(saveFile, tetris);
        } catch (MissingFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
