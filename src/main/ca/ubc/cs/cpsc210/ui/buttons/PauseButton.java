package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.ui.Tetris;

import static ca.ubc.cs.cpsc210.ui.Tetris.isPlayMusic;
import static ca.ubc.cs.cpsc210.ui.Tetris.setPaused;

public class PauseButton extends TetrisButton {

    public PauseButton() {
        super(pBX, pBY, pBW, pBH, pBN);
    }

    public void buttonAction() {
        if (!Tetris.isPaused()) {
            setPaused(true);
            Tetris.getTetrisMusic().stop();
            buttonName = "UNPAUSE";
        } else {
            setPaused(false);
            if (isPlayMusic()) {
                Tetris.getTetrisMusic().unpause();
            }
            buttonName = "PAUSE";
        }
    }
}
