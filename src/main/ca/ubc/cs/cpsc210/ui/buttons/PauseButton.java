package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

public class PauseButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    public PauseButton(Tetris tetris) {
        super(PAUSE_BUTTON_X_POS, PAUSE_BUTTON_Y_POS,
                PAUSE_BUTTON_WIDTH, PAUSE_BUTTON_HEIGHT, PAUSE_BUTTON_NAME_INITIAL);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // REQUIRES: gameStart = true
    // EFFECTS:  pauses and unpauses game and music, switches between PAUSE and UNPAUSE
    public void buttonAction() {
        buttonName = tetris.isPaused() ? PAUSE_BUTTON_NAME_INITIAL : PAUSE_BUTTON_NAME_CLICKED;
        tetris.setPaused(!tetris.isPaused());
    }
}
