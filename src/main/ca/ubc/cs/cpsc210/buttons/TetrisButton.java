package ca.ubc.cs.cpsc210.buttons;

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
    private int fontSize = 20;

    /**
     *  MusicButton constants
     */
    protected static final int mBX = BLOCK_SIZE;
    protected static final int mBY = BLOCK_SIZE * 17;
    protected static final int mBW = BLOCK_SIZE * 3;
    protected static final int mBH = BLOCK_SIZE * 2;
    protected static final String mBN = "MUSIC";

    /**
     *  PauseButton constants
     */
    protected static final int pBX = BLOCK_SIZE * 6;
    protected static final int pBY = BLOCK_SIZE * 17;
    protected static final int pBW = BLOCK_SIZE * 3;
    protected static final int pBH = BLOCK_SIZE * 2;
    protected static final String pBN = "PAUSE";


    /**
     *  Variables
     */
    private int mouseX;
    private int mouseY;
    private int bX;
    private int bY;
    private int bWidth;
    private int bHeight;
    private String bName;
    private Color buttonColour = Color.white;

    /**
     *  Constructor
     */
    public TetrisButton(int bX, int bY, int bWidth, int bHeight, String bName) {
        this.bX = bX;
        this.bY = bY;
        this.bWidth = bWidth;
        this.bHeight = bHeight;
        this.bName = bName;
    }

    /**
     *  Methods
     */
    public boolean isMouseTouching(int mouseX, int mouseY) {
        initializeMouseCoords(mouseX, mouseY);

        boolean onX = this.mouseX >= bX && this.mouseX <= bX + bWidth;
        boolean onY = this.mouseY >= bY && this.mouseY <= bY + bHeight;
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
        g.fillRoundRect(bX, bY, bWidth, bHeight, buttonArcWidth, buttonArcHeight);

        // text on button
        g.setColor(Color.black);
        g.setFont(new Font(fontType, fontStyle, fontSize));

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds(bName, g2d);
        int xOffset = (bWidth - (int) r.getWidth()) / 2;
        int yOffset = (bHeight - (int) r.getHeight()) / 2 + fm.getAscent();

        g.drawString(bName, bX + xOffset, bY + yOffset);
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