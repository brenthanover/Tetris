package ca.ubc.cs.cpsc210.ui.buttons;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;


public class MysteryButton extends TetrisButton {

    int count;

    public MysteryButton() {
        super(qBX, qBY, qBW, qBH, qBN);
        count = 0;
    }

    public void buttonAction() {
        tetrisMusic.stop();
        playNextSong();
        count++;
        setPlayMusic(true);
    }

    public void playNextSong() {
        switch (count % 3) {
            case 0:
                tetrisMusic.shrek();
                buttonName = "SAX";
                break;
            case 1:
                tetrisMusic.sax();
                buttonName = "TETRIS";
                break;
            default:
                tetrisMusic.playTetrisTheme();
                buttonName = "SHREK";
                break;

        }
    }
}
