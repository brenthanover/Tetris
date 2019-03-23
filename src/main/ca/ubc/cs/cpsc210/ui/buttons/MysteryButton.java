package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.awt.*;

public class MysteryButton extends TetrisButton {
    /**
     *  Constants
     */
    public static final Color TETRIS_COLOUR = Color.black;
    public static final Color SHREK_COLOUR = Color.green.darker();
    public static final Color SAX_COLOUR = Color.cyan.darker();
    public static final String TETRIS_NAME = "TETRIS";
    public static final String SHREK_NAME = "SHREK";
    public static final String SAX_NAME = "SAX";

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
        if (tetris.isPlayMusic()) {
            tetris.getTetrisMusic().stop();
        }
        playNextSong();
        count++;
        tetris.setPlayMusic(true);
    }

    // EFFECTS:  if playing tetris theme, play Shrek theme, set background to green
    //           if playing Shrek theme, play sax theme, set background to blue
    //           if playing sax theme, play tetris theme, set background to black
    private void playNextSong() {
        switch (count % 3) {
            case 0:
                tetris.getTetrisMusic().playShrekTheme();
                buttonName = SAX_NAME;
                tetris.getGameBackground().setBackgroundColour(SHREK_COLOUR);
                break;
            case 1:
                tetris.getTetrisMusic().playSaxTheme();
                buttonName = TETRIS_NAME;
                tetris.getGameBackground().setBackgroundColour(SAX_COLOUR);
                break;
            default:
                tetris.getTetrisMusic().playTetrisTheme();
                buttonName = SHREK_NAME;
                tetris.getGameBackground().setBackgroundColour(TETRIS_COLOUR);
                break;

        }
    }
}
