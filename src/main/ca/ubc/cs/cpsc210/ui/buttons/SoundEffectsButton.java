package ca.ubc.cs.cpsc210.ui.buttons;


import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class SoundEffectsButton extends TetrisButton {

    public SoundEffectsButton() {
        super(sfxBX, sfxBY, sfxBW, sfxBH, sfxBN);
    }

    public void buttonAction() {
        if (isPlaySfx()) {
            setPlaySfx(false);
            buttonName = "SFX OFF";
        } else {
            setPlaySfx(true);
            buttonName = "SFX ON";
        }
    }
}
