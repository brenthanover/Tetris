package ca.ubc.cs.cpsc210.ui;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    public Render(Tetris tetris) {
        this.tetris = tetris;
    }

    // EFFECTS: renders the draw file for Tetris
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        tetris.draw(g);
    }
}
