package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.ui.Tetris;

import static ca.ubc.cs.cpsc210.parsers.SaveGame.saveGame;

public class SaveButton extends TetrisButton {

    public SaveButton() {
        super(sBX, sBY, sBW, sBH, sBN);
    }

    public void buttonAction() {
        saveGame("savegame", Tetris.tetris);
    }
}
