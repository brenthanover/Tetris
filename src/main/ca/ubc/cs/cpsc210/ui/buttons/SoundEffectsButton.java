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
    public SoundEffectsButton(Tetris tetris) {
        super(sfxBX, sfxBY, sfxBW, sfxBH, sfxBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
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
