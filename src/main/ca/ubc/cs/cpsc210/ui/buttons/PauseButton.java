package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

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
    //           note music is just paused, replays at same spot it was paused at
    public void buttonAction() {
        if (!tetris.isPaused()) {
            tetris.setPaused(true);
            tetris.getTetrisMusic().stop();
            buttonName = "UNPAUSE";
        } else {
            tetris.setPaused(false);
            if (tetris.isPlayMusic()) {
                tetris.getTetrisMusic().unpause();
            }
            buttonName = "PAUSE";
        }
    }
}
