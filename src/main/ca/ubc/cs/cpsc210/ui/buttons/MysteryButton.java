package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.awt.*;

public class MysteryButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Variables
     */
    private int count;

    /**
     * Constructor
     */
    public MysteryButton(Tetris tetris) {
        super(qBX, qBY, qBW, qBH, qBN);
        count = 0;
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // EFFECTS: cycles between songs and background themes
    public void buttonAction() {
        tetris.getTetrisMusic().stop();
        playNextSong();
        count++;
        tetris.setPlayMusic(true);
    }

    // EFFECTS:  if playing tetris theme, play shrek theme, set backgorund to green
    //           if playing shrek theme, play sax theme, set background to blue
    //           if playing sax theme, play tetris theme, set background to black
    private void playNextSong() {
        switch (count % 3) {
            case 0:
                tetris.getTetrisMusic().shrek();
                buttonName = "SAX";
                tetris.getGameBackground().setBackgroundColour(Color.green.darker());
                break;
            case 1:
                tetris.getTetrisMusic().sax();
                buttonName = "TETRIS";
                tetris.getGameBackground().setBackgroundColour(Color.cyan.darker());
                break;
            default:
                tetris.getTetrisMusic().playTetrisTheme();
                buttonName = "SHREK";
                tetris.getGameBackground().setBackgroundColour(Color.black);
                break;

        }
    }
}
