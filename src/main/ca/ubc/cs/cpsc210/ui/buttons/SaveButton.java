package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.persistence.SaveGame.saveGame;

public class SaveButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     *  Constructor
     */
    // EFFECTS: constructs a SaveButton object
    public SaveButton(Tetris tetris) {
        super(sBX, sBY, sBW, sBH, sBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
    // REQUIRES: gameStart = true
    // MODIFIES: save file on disk
    // EFFECTS:  saves current game state to file
    public void buttonAction() {
        try {
            saveGame("savegame", tetris);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
