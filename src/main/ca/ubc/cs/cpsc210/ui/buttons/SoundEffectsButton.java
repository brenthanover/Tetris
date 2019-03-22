package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.model.Tetris;

public class SoundEffectsButton extends TetrisButton {

    /**
     *  Declarations
     */
    private Tetris tetris;

    /**
     *  Constructor
     */
    // EFFECTS: constructs a SoundEffectsButton object
    public SoundEffectsButton(Tetris tetris) {
        super(sfxBX, sfxBY, sfxBW, sfxBH, sfxBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
    // MODIFIES: tetris
    // EFFECTS: toggles the sound effects on and off
    public void buttonAction() {
        if (tetris.isPlaySfx()) {
            tetris.setPlaySfx(false);
            buttonName = "SFX OFF";
        } else {
            tetris.setPlaySfx(true);
            buttonName = "SFX ON";
        }
    }
}
