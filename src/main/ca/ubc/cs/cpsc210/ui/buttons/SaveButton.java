package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.SaveGame.saveGame;

public class SaveButton extends TetrisButton {

    public SaveButton() {
        super(sBX, sBY, sBW, sBH, sBN);
    }

    public void buttonAction() {
        try {
            saveGame("savegame", Tetris.getTetris());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
