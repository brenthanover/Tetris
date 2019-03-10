package ca.ubc.cs.cpsc210.ui;

import ca.ubc.cs.cpsc210.model.Tetris;

import javax.swing.*;
import java.awt.*;

public class Render extends JPanel {

    /**
     * Declarations
     */
    private Game game;

    /**
     * Constructor
     */
    public Render(Game game) {
        this.game = game;
    }

    // EFFECTS: renders the draw file for Tetris
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.draw(g);
    }
}
