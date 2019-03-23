package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.model.Tetris;

import java.awt.*;

import static ca.ubc.cs.cpsc210.ui.buttons.MysteryButton.*;

public class MusicButton extends TetrisButton {

    /**
     *  Declarations
     */
    private Tetris tetris;

    /**
     *  Constructor
     */
    // EFFECTS: constructs MusicButton object
    public MusicButton(Tetris tetris) {
        super(mBX, mBY, mBW, mBH, mBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
    // MODIFIES: tetris
    // EFFECTS:  toggles music on and off
    //           music starts at beginning when it is turned on
    public void buttonAction() {
        if (tetris.isPlayMusic()) {
            tetris.getTetrisMusic().stop();
            tetris.setPlayMusic(false);
            buttonName = "MUSIC OFF";
        } else {
            playMusicByColour(tetris.getButtonList()[2].getButtonName());
            tetris.getTetrisMusic().playTetrisTheme();
            tetris.setPlayMusic(true);
            buttonName = "MUSIC ON";
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
