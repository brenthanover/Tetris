package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadGame.loadGame;
import static ca.ubc.cs.cpsc210.ui.buttons.SaveButton.SAVE_FILE_NAME;

public class LoadButton extends TetrisButton {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    // EFFECTS: constructs LoadButton object
    public LoadButton(Tetris tetris) {
        super(LOAD_BUTTON_X_POS, LOAD_BUTTON_Y_POS,
                LOAD_BUTTON_WIDTH, LOAD_BUTTON_HEIGHT, LOAD_BUTTON_NAME);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // REQUIRES: gameStart = true
    // MODIFIES: Game
    // EFFECTS:  loads game from save file and pauses the game
    public void buttonAction() {
        try {
            loadGame(SAVE_FILE_NAME, tetris);
            tetris.getTetrisMusic().stop();
        } catch (MissingFileException | IOException e) {
            e.printStackTrace();
        }
    }
}
