package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.ui.Tetris;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.loadGame;
import static ca.ubc.cs.cpsc210.ui.buttons.TetrisButton.*;

public class LoadButton extends TetrisButton {

    public LoadButton() {
        super(lBX, lBY, lBW, lBH, lBN);
    }

    public void buttonAction() {
        loadGame("savegame");
    }
}
