package ca.ubc.cs.cpsc210.model;

import ca.ubc.cs.cpsc210.ui.Tetris;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    /**
     *  Constructor
     */
    public Render() { }

    // EFFECTS: renders the draw file for FlappyBird
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Tetris.tetris.draw(g);
    }
}
