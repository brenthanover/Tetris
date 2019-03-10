package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.persistence.SaveGame.saveGame;

public class SaveButton extends TetrisButton {

    /**
     * Declarations
     */
    Tetris tetris;

    public SaveButton(Tetris tetris) {
        super(sBX, sBY, sBW, sBH, sBN);
        this.tetris = tetris;
    }

    public void buttonAction() {
        try {
            saveGame("savegame", tetris);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
