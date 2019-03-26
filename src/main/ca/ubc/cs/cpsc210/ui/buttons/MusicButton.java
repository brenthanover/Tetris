package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.model.Tetris;

public class MusicButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    // EFFECTS: constructs MusicButton object
    public MusicButton(Tetris tetris) {
        super(MUSIC_BUTTON_X_POS, MUSIC_BUTTON_Y_POS,
                MUSIC_BUTTON_WIDTH, MUSIC_BUTTON_HEIGHT, MUSIC_BUTTON_NAME_INITIAL);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // MODIFIES: tetris
    // EFFECTS:  toggles music on and off
    //           song depends on current theme, decided by mystery button
    //           music starts at beginning when it is turned on
    public void buttonAction() {
        tetris.getTetrisMusic().stop();
        if (buttonName.equals(MUSIC_BUTTON_NAME_CLICKED)) {
            playMusicByTheme(tetris.getMysteryButton().getButtonName());
        }

        buttonName = tetris.isPlayMusic() ? MUSIC_BUTTON_NAME_CLICKED : MUSIC_BUTTON_NAME_INITIAL;
        tetris.setPlayMusic(!tetris.isPlayMusic());
    }

    // REQUIRES: music is not playing, ie playMusic is false
    // MODIFIES: this
    // EFFECTS:  plays theme music based on background colour
    public void playMusicByTheme(String c) {
        switch (c) {
            case MYSTERY_BUTTON_NAME_KENNY:
                tetris.getTetrisMusic().playShrekTheme();
                break;
            case MYSTERY_BUTTON_NAME_TETRIS:
                tetris.getTetrisMusic().playSaxTheme();
                break;
            default:
                tetris.getTetrisMusic().playTetrisTheme();
                break;
        }
    }
}
