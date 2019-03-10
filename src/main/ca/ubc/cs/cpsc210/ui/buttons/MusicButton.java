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
    public MusicButton(Tetris tetris) {
        super(mBX, mBY, mBW, mBH, mBN);
        this.tetris = tetris;
    }

    /**
     *  Methods
     */
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
