package ca.ubc.cs.cpsc210.ui.buttons;


import static ca.ubc.cs.cpsc210.ui.Tetris.isPlayMusic;
import static ca.ubc.cs.cpsc210.ui.Tetris.setPlayMusic;
import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class MusicButton extends TetrisButton {

    public MusicButton() {
        super(mBX, mBY, mBW, mBH, mBN);
    }

    public void buttonAction() {
        if (isPlayMusic()) {
            tetrisMusic.stop();
            setPlayMusic(false);
            buttonName = "MUSIC OFF";
        } else {
            tetrisMusic.playTetrisTheme();
            setPlayMusic(true);
            buttonName = "MUSIC ON";
        }
    }
}
