package ca.ubc.cs.cpsc210.ui.buttons;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

import static ca.ubc.cs.cpsc210.ui.Game.*;

public abstract class TetrisButton {
    /**
     * Constants
     */
    private int buttonArcWidth = 10;
    private int buttonArcHeight = 10;
    private String fontType = "Arial";
    private int fontStyle = 1;
    private int fontSize = 16;
    /**
     * MusicButton constants
     */
    public static final int MUSIC_BUTTON_X_POS = BLOCK_SIZE;
    public static final int MUSIC_BUTTON_Y_POS = BLOCK_SIZE * 7;
    public static final int MUSIC_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int MUSIC_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String MUSIC_BUTTON_NAME_INITIAL = "MUSIC ON";
    public static final String MUSIC_BUTTON_NAME_CLICKED = "MUSIC OFF";

    /**
     * SoundEffectsButton constants
     */
    public static final int SOUND_EFECTS_BUTTON_X_POS = BLOCK_SIZE * 6;
    public static final int SOUND_EFECTS_BUTTON_Y_POS = BLOCK_SIZE * 7;
    public static final int SOUND_EFECTS_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int SOUND_EFECTS_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String SOUND_EFECTS_BUTTON_NAME_INITIAL = "SFX ON";
    public static final String SOUND_EFECTS_BUTTON_NAME_CLICKED = "SFX OFF";

    /**
     * SaveButton constants
     */
    public static final int SAVE_BUTTON_X_POS = BLOCK_SIZE;
    public static final int SAVE_BUTTON_Y_POS = BLOCK_SIZE * 10;
    public static final int SAVE_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int SAVE_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String SAVE_BUTTON_NAME = "SAVE";

    /**
     * LoadButton constants
     */
    public static final int LOAD_BUTTON_X_POS = BLOCK_SIZE * 6;
    public static final int LOAD_BUTTON_Y_POS = BLOCK_SIZE * 10;
    public static final int LOAD_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int LOAD_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String LOAD_BUTTON_NAME = "LOAD";

    /**
     * MysteryButton constants
     */
    public static final int MYSTERY_BUTTON_X_POS = BLOCK_SIZE * 6;
    public static final int MYSTERY_BUTTON_Y_POS = BLOCK_SIZE * 17;
    public static final int MYSTERY_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int MYSTERY_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String MYSTERY_BUTTON_NAME_SHREK = "SHREK";
    public static final String MYSTERY_BUTTON_NAME_KENNY = "KENNY G";
    public static final String MYSTERY_BUTTON_NAME_TETRIS = "TETRIS";

    /**
     * PauseButton constants
     */
    public static final int PAUSE_BUTTON_X_POS = BLOCK_SIZE;
    public static final int PAUSE_BUTTON_Y_POS = BLOCK_SIZE * 17;
    public static final int PAUSE_BUTTON_WIDTH = BLOCK_SIZE * 3;
    public static final int PAUSE_BUTTON_HEIGHT = BLOCK_SIZE * 2;
    public static final String PAUSE_BUTTON_NAME_INITIAL = "PAUSE";
    public static final String PAUSE_BUTTON_NAME_CLICKED = "UNPAUSE";

    /**
     * Variables
     */
    private int mouseX;
    private int mouseY;
    private int buttonX;
    private int buttonY;
    private int buttonWidth;
    private int buttonHeight;
    public String buttonName;
    private Color buttonColour = Color.white;

    /**
     * Constructor
     */
    // EFFECTS: constructs a TetrisButton object
    public TetrisButton(int buttonX, int buttonY, int buttonWidth, int buttonHeight, String buttonName) {
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.buttonName = buttonName;
    }

    /**
     * Getters
     */
    public String getButtonName() {
        return buttonName;
    }

    /**
     * Setters
     */
    public void setButtonName(String name) {
        buttonName = name;
    }

    /**
     * Methods
     */
    // EFFECTS: produces true if mouse is inside the button
    public boolean isMouseTouching(int mouseX, int mouseY) {
        initializeMouseCoords(mouseX, mouseY);

        boolean onX = this.mouseX >= buttonX && this.mouseX <= buttonX + buttonWidth;
        boolean onY = this.mouseY >= buttonY && this.mouseY <= buttonY + buttonHeight;
        return onX && onY;
    }

    // EFFECTS: abstract method for button action
    public abstract void buttonAction();

    // EFFECTS: changes button colour to provide feedback
    public void showButtonPressed() {
        buttonColour = Color.gray;
    }

    // EFFECTS: changes button colour to provide feedback
    public void showButtonReleased() {
        buttonColour = Color.white;
    }

    // EFFECTS: draws tetris button
    public void draw(Graphics g) {
        // set up button on screen
        g.setColor(buttonColour);
        g.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, buttonArcWidth, buttonArcHeight);

        // text on button
        g.setColor(Color.black);
        g.setFont(new Font(fontType, fontStyle, fontSize));

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(buttonName, g2d);
        int offsetX = (buttonWidth - (int) r.getWidth()) / 2;
        int offsetY = (buttonHeight - (int) r.getHeight()) / 2 + fm.getAscent();

        g.drawString(buttonName, buttonX + offsetX, buttonY + offsetY);
    }

    // REQUIRES: mouse listener
    // EFFECTS:  for whatever reason, clicking on 0,0 in the window yields 3, 25
    //           something to do with the margins of the window maybe?
    //           offsetting by 3 and 25 fixes the issue
    public void initializeMouseCoords(int mouseX, int mouseY) {
        this.mouseX = mouseX - 3 - BOARD_X_POS - BLOCK_SIZE - BOARD_WIDTH;
        this.mouseY = mouseY - 25 - BLOCK_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TetrisButton that = (TetrisButton) o;
        return buttonX == that.buttonX
                && buttonY == that.buttonY
                && Objects.equals(buttonName, that.buttonName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buttonX, buttonY, buttonName);
    }

    // Tutorial cited from StackOverflow:
    // centring text in button
    // https://stackoverflow.com/questions/14284754/java-center-text-in-rectangle
}
