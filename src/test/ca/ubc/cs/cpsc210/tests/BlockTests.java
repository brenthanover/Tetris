package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTests {

    private Block block;

    @BeforeEach
    public void setup() { block = new Block(0, 0, Color.white); }

    @Test
    public void testConstructor() {
        assertEquals(0, block.getBlockXPos());
        assertEquals(0, block.getBlockYPos());
        assertEquals(Color.white, block.getBlockColour());
    }

    @Test
    public void testDraw() {
        // how do you test to see if things are drawn the same?
    }

}
