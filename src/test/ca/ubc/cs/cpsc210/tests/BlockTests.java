package ca.ubc.cs.cpsc210.tests;

import ca.ubc.cs.cpsc210.model.Block;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class BlockTests {

    private Block block;
    private Block testBlock;

    @BeforeEach
    public void setup() {
        block = new Block(0, 0, Color.white);
        testBlock = new Block(0, 0, Color.white);
    }

    @Test
    public void testConstructor() {
        assertEquals(0, block.getBlockXPos());
        assertEquals(0, block.getBlockYPos());
        assertEquals(Color.white, block.getBlockColour());
    }

    @Test
    public void testEquals() {
        assertEquals(block, testBlock);
    }

    @Test
    public void testNotEquals() {
        testBlock = new Block(0, 1, Color.white);
        assertNotEquals(block, testBlock);
        testBlock = new Block(1, 0, Color.white);
        assertNotEquals(block, testBlock);
        testBlock = new Block(0, 0, Color.black);
        assertNotEquals(block, testBlock);
    }

    // no tests for draw() function
}
