package ca.ubc.cs.cpsc210.ui.buttons;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.io.IOException;

import static ca.ubc.cs.cpsc210.persistence.SaveGame.saveGame;

public class SaveButton extends TetrisButton {
    /**
     *  Constants
     */
    public static final String SAVE_FILE_NAME = "savegame";

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    // EFFECTS: constructs a SaveButton object
    public SaveButton(Tetris tetris) {
        super(SAVE_BUTTON_X_POS, SAVE_BUTTON_Y_POS,
                SAVE_BUTTON_WIDTH, SAVE_BUTTON_HEIGHT, SAVE_BUTTON_NAME);
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // REQUIRES: gameStart = true
    // MODIFIES: save file on disk
    // EFFECTS:  saves current game state to file
    public void buttonAction() {
        try {
            saveGame(SAVE_FILE_NAME, tetris);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
