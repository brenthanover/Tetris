package ca.ubc.cs.cpsc210.ui.buttons;


import ca.ubc.cs.cpsc210.model.Tetris;

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
            tetris.getTetrisMusic().playTetrisTheme();
            tetris.setPlayMusic(true);
            buttonName = "MUSIC ON";
        }
    }
}
