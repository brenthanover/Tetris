package ca.ubc.cs.cpsc210.model;

import ca.ubc.cs.cpsc210.ui.Tetris;

import java.awt.*;

public class Block {

    /**
     *  Constants
     */
    private final int BLOCK_SIZE = Tetris.BLOCK_SIZE;

    /**
     *  Variables
     */
    private int x;
    private int y;
    private Color c;

    /**
     *  Getters
     */


    public Block(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void draw(Graphics g) {
        g.setColor(c);
        g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
        // draw border around
        g.setColor(Color.gray);
        g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
    }


}
