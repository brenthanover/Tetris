package ca.ubc.cs.cpsc210.ui.buttons;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public abstract class TetrisButton {

    /**
     *  Constants
     */
    private int buttonArcWidth = 10;
    private int buttonArcHeight = 10;
    private String fontType = "Arial";
    private int fontStyle = 1;
    private int fontSize = 16;

    /**
     *  MusicButton constants
     */
    protected static final int mBX = BLOCK_SIZE;
    protected static final int mBY = BLOCK_SIZE * 7;
    protected static final int mBW = BLOCK_SIZE * 3;
    protected static final int mBH = BLOCK_SIZE * 2;
    protected static final String mBN = "MUSIC ON";

    /**
     *  SoundEffectsButton constants
     */
    protected static final int sfxBX = BLOCK_SIZE * 6;
    protected static final int sfxBY = BLOCK_SIZE * 7;
    protected static final int sfxBW = BLOCK_SIZE * 3;
    protected static final int sfxBH = BLOCK_SIZE * 2;
    protected static final String sfxBN = "SFX ON";

    /**
     *  SaveButton constants
     */
    protected static final int sBX = BLOCK_SIZE;
    protected static final int sBY = BLOCK_SIZE * 10;
    protected static final int sBW = BLOCK_SIZE * 3;
    protected static final int sBH = BLOCK_SIZE * 2;
    protected static final String sBN = "SAVE";

    /**
     *  LoadButton constants
     */
    protected static final int lBX = BLOCK_SIZE * 6;
    protected static final int lBY = BLOCK_SIZE * 10;
    protected static final int lBW = BLOCK_SIZE * 3;
    protected static final int lBH = BLOCK_SIZE * 2;
    protected static final String lBN = "LOAD";

    /**
     *  MysteryButton constants
     */
    protected static final int qBX = BLOCK_SIZE * 6;
    protected static final int qBY = BLOCK_SIZE * 17;
    protected static final int qBW = BLOCK_SIZE * 3;
    protected static final int qBH = BLOCK_SIZE * 2;
    protected static final String qBN = "SHREK";

    /**
     *  PauseButton constants
     */
    protected static final int pBX = BLOCK_SIZE;
    protected static final int pBY = BLOCK_SIZE * 17;
    protected static final int pBW = BLOCK_SIZE * 3;
    protected static final int pBH = BLOCK_SIZE * 2;
    protected static final String pBN = "PAUSE";

    /**
     *  Variables
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
     *  Constructor
     */
    public TetrisButton(int buttonX, int buttonY, int buttonWidth, int buttonHeight, String buttonName) {
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.buttonName = buttonName;
    }

    /**
     *  Methods
     */
    public boolean isMouseTouching(int mouseX, int mouseY) {
        initializeMouseCoords(mouseX, mouseY);

        boolean onX = this.mouseX >= buttonX && this.mouseX <= buttonX + buttonWidth;
        boolean onY = this.mouseY >= buttonY && this.mouseY <= buttonY + buttonHeight;
        return onX && onY;
    }

    public abstract void buttonAction();

    // changes button colour to provide feedback
    public void showButtonPressed() {
        buttonColour = Color.gray;
    }

    // changes button colour to provide feedback
    public void showButtonReleased() {
        buttonColour = Color.white;
    }

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

    // for whatever reason, clicking on 0,0 in the window yields 3, 25
    public void initializeMouseCoords(int mouseX, int mouseY) {
        this.mouseX = mouseX - 3 - BOARD_X_POS - BLOCK_SIZE - BOARD_WIDTH;
        this.mouseY = mouseY - 25 - BLOCK_SIZE;
    }

    // Tutorial cited from StackOverflow:
    // centring text in button
    // https://stackoverflow.com/questions/14284754/java-center-text-in-rectangle
}
