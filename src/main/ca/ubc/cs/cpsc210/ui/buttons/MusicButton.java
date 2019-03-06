package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.ui.Tetris;

import static ca.ubc.cs.cpsc210.ui.Tetris.isPlayMusic;
import static ca.ubc.cs.cpsc210.ui.Tetris.setPlayMusic;

public class MusicButton extends TetrisButton {

    public MusicButton() {
        super(mBX, mBY, mBW, mBH, mBN);
    }

    public void buttonAction() {
        if (Tetris.isPlayMusic()) {
            Tetris.getTetrisMusic().stop();
            Tetris.setPlayMusic(false);
            buttonName = "MUSIC OFF";
        } else {
            Tetris.getTetrisMusic().playTetrisTheme();
            setPlayMusic(true);
            buttonName = "MUSIC ON";
        }
    }
}
