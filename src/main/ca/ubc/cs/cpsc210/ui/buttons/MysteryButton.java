package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.awt.*;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;


public class MysteryButton extends TetrisButton {

    private int count;

    public MysteryButton() {
        super(qBX, qBY, qBW, qBH, qBN);
        count = 0;
    }

    public void buttonAction() {
        Tetris.getTetrisMusic().stop();
        playNextSong();
        count++;
        setPlayMusic(true);
    }

    private void playNextSong() {
        switch (count % 3) {
            case 0:
                Tetris.getTetrisMusic().shrek();
                buttonName = "SAX";
                Tetris.getGameBackground().setBackgroundColour(Color.green.darker());
                break;
            case 1:
                Tetris.getTetrisMusic().sax();
                buttonName = "TETRIS";
                Tetris.getGameBackground().setBackgroundColour(Color.cyan.darker());
                break;
            default:
                Tetris.getTetrisMusic().playTetrisTheme();
                buttonName = "SHREK";
                Tetris.getGameBackground().setBackgroundColour(Color.black);
                break;

        }
    }
}
