package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.awt.*;

public class MysteryButton extends TetrisButton {
    /**
     * Constants
     */
    public static final Color TETRIS_COLOUR = Color.black;
    public static final Color SHREK_COLOUR = Color.green.darker();
    public static final Color SAX_COLOUR = Color.cyan.darker();

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    public MysteryButton(Tetris tetris) {
        super(MYSTERY_BUTTON_X_POS, MYSTERY_BUTTON_Y_POS,
                MYSTERY_BUTTON_WIDTH, MYSTERY_BUTTON_HEIGHT, MYSTERY_BUTTON_NAME_SHREK);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // EFFECTS: cycles between songs and background themes
    public void buttonAction() {
        tetris.getTetrisMusic().stop();
        tetris.setPlayMusic(true);
        tetris.getMusicButton().setButtonName(MUSIC_BUTTON_NAME_INITIAL);

        playNextSong();
    }

    // EFFECTS:  if playing tetris theme, play Shrek theme, set background to green
    //           if playing Shrek theme, play sax theme, set background to blue
    //           if playing sax theme, play tetris theme, set background to black
    private void playNextSong() {
        switch (buttonName) {
            case MYSTERY_BUTTON_NAME_SHREK:
                tetris.getTetrisMusic().playShrekTheme();
                buttonName = MYSTERY_BUTTON_NAME_KENNY;
                tetris.getGameBackground().setBackgroundColour(SHREK_COLOUR);
                break;
            case MYSTERY_BUTTON_NAME_KENNY:
                tetris.getTetrisMusic().playSaxTheme();
                buttonName = MYSTERY_BUTTON_NAME_TETRIS;
                tetris.getGameBackground().setBackgroundColour(SAX_COLOUR);
                break;
            default:
                tetris.getTetrisMusic().playTetrisTheme();
                buttonName = MYSTERY_BUTTON_NAME_SHREK;
                tetris.getGameBackground().setBackgroundColour(TETRIS_COLOUR);
                break;
        }
    }
}
