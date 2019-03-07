package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.loadGame;

public class LoadButton extends TetrisButton {

    public LoadButton() {
        super(lBX, lBY, lBW, lBH, lBN);
    }

    public void buttonAction() {
        try {
            loadGame("savegame");
        } catch (MissingFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
