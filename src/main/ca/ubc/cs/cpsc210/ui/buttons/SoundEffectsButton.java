package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.model.Tetris;

public class SoundEffectsButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    // EFFECTS: constructs a SoundEffectsButton object
    public SoundEffectsButton(Tetris tetris) {
        super(SOUND_EFFECTS_BUTTON_X_POS, SOUND_EFFECTS_BUTTON_Y_POS,
                SOUND_EFFECTS_BUTTON_WIDTH, SOUND_EFFECTS_BUTTON_HEIGHT, SOUND_EFFECTS_BUTTON_NAME_INITIAL);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // MODIFIES: tetris
    // EFFECTS: switches the sound effects on and off
    //          switches between SFX ON and SFX OFF
    public void buttonAction() {
        if (tetris.isPlaySfx()) {
            buttonName = SOUND_EFFECTS_BUTTON_NAME_CLICKED;
            tetris.setPlaySfx(false);
        } else {
            buttonName = SOUND_EFFECTS_BUTTON_NAME_INITIAL;
            tetris.setPlaySfx(true);
        }
    }
}
