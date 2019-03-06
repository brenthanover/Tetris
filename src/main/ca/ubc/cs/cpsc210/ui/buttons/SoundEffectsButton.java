package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.ui.Tetris;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class SoundEffectsButton extends TetrisButton {

    public SoundEffectsButton() {
        super(sfxBX, sfxBY, sfxBW, sfxBH, sfxBN);
    }

    public void buttonAction() {
        if (Tetris.isPlaySfx()) {
            Tetris.setPlaySfx(false);
            buttonName = "SFX OFF";
        } else {
            Tetris.setPlaySfx(true);
            buttonName = "SFX ON";
        }
    }
}
