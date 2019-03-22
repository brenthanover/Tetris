package ca.ubc.cs.cpsc210.model;

import java.awt.*;
import java.util.Objects;

import static ca.ubc.cs.cpsc210.ui.Game.BLOCK_SIZE;

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
    // EFFECTS: constructs Block object
    public Block(int x, int y, Color c) {
        this.blockXPos = x;
        this.blockYPos = y;
        this.blockColour = c;
    }

    /**
     * Methods
     */
    // EFFECTS: draws blockColour coloured square with grey border at blockXPos, blockYPos, of size BLOCK_SIZE
    public void draw(Graphics g) {
        g.setColor(blockColour);
        g.fillRect(blockXPos, blockYPos, BLOCK_SIZE, BLOCK_SIZE);
        // draw border around
        g.setColor(Color.gray);
        g.drawRect(blockXPos, blockYPos, BLOCK_SIZE, BLOCK_SIZE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Block block = (Block) o;
        return getBlockXPos() == block.getBlockXPos()
                && getBlockYPos() == block.getBlockYPos()
                && Objects.equals(getBlockColour(), block.getBlockColour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBlockXPos(), getBlockYPos(), getBlockColour());
    }
}
