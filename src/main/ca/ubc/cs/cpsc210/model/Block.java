package ca.ubc.cs.cpsc210.model;

import java.awt.*;

import static ca.ubc.cs.cpsc210.ui.Tetris.BLOCK_SIZE;

public class Block {

    /**
     * Variables
     */
    private int blockXPos;
    private int blockYPos;
    private Color blockColour;

    /**
     * Getters
     */
    public int getBlockXPos() {
        return blockXPos;
    }

    public int getBlockYPos() {
        return blockYPos;
    }

    public Color getBlockColour() {
        return blockColour;
    }

    /**
     * Constructor
     */
    public Block(int x, int y, Color c) {
        this.blockXPos = x;
        this.blockYPos = y;
        this.blockColour = c;
    }

    /**
     *  Methods
     */
    // draw function not included in tests
    public void draw(Graphics g) {
        g.setColor(blockColour);
        g.fillRect(blockXPos, blockYPos, BLOCK_SIZE, BLOCK_SIZE);
        // draw border around
        g.setColor(Color.gray);
        g.drawRect(blockXPos, blockYPos, BLOCK_SIZE, BLOCK_SIZE);
    }
}
