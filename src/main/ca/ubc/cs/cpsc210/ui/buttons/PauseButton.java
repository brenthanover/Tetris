package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import static ca.ubc.cs.cpsc210.ui.buttons.MysteryButton.SHREK_NAME;
import static ca.ubc.cs.cpsc210.ui.buttons.MysteryButton.TETRIS_NAME;

public class PauseButton extends TetrisButton {

    /**
     *  Declarations
     */
    private Tetris tetris;

    /**
     *  Constructor
     */
    public PauseButton(Tetris tetris) {
        super(pBX, pBY, pBW, pBH, pBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
    // REQUIRES: gameStart = true
    // EFFECTS:  pauses and unpauses game and music
    public void buttonAction() {
        if (!tetris.isPaused()) {
            tetris.setPaused(true);
            if (tetris.isPlayMusic()) {
                tetris.getTetrisMusic().stop();
            }
            buttonName = "UNPAUSE";
        } else {
            tetris.setPaused(false);
            if (tetris.getTetrisMusic() == null) {
                playMusicByColour(tetris.getButtonList()[2].getButtonName());
            } else {
                tetris.getTetrisMusic().unpause();
            }
            buttonName = "PAUSE";
        }
    }

    // REQUIRES: music is not playing, ie playMusic is false
    // MODIFIES: this
    // EFFECTS:  plays theme music based on background colour
    public void playMusicByColour(String c) {
        switch (c) {
            case TETRIS_NAME:
                tetris.getTetrisMusic().playShrekTheme();
                break;
            case SHREK_NAME:
                tetris.getTetrisMusic().playSaxTheme();
                break;
            default:
                tetris.getTetrisMusic().playTetrisTheme();
                break;
        }
    }
}
