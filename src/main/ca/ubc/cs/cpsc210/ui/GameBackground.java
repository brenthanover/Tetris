package ca.ubc.cs.cpsc210.ui;

import ca.ubc.cs.cpsc210.model.Tetris;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.ubc.cs.cpsc210.ui.Game.*;

public class GameBackground {

    /**
     * Constants
     */
    private static final Color OUTLINE_COLOUR = Color.gray;
    private static final Color TEXT_COLOUR = Color.white;
    private static final int FONT_STYLE = 1;
    private static final int FONT_SIZE = 30;
    private static final String FONT_TYPE = "Arial";
    private static final int SCORE_X = BLOCK_SIZE * 5 + 19;
    private static final int SCORE_Y = BLOCK_SIZE * 6;
    private static final int HIGH_SCORE_X = BLOCK_SIZE * 5 + 19;
    private static final int HIGH_SCORE_Y = BLOCK_SIZE * 4;
    private static final int LINES_X = BLOCK_SIZE * 7 + 9;
    private static final int LINES_Y = BLOCK_SIZE * 2;

    /**
     *  Declarations
     */
    Tetris tetris;

    /**
     * Variables
     */
    private Color backgroundColour = Color.black;


    /**
     * Setters
     */
    public void setBackgroundColour(Color c) {
        backgroundColour = c;
    }

    /**
     * Constructor
     */
    public GameBackground(Tetris tetris) {
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // EFFECTS:  renders game background on game screen
    public void draw(Graphics g) {
        // fill background black
        drawWindow(g);

        // draw blank board with grey squares
        g.translate(BOARD_X_POS, BOARD_Y_POS);
        drawBlankBoard(g);
        g.translate(-BOARD_X_POS, -BOARD_Y_POS);

        // draw score, preview screen
        // menu window
        g.translate(BOARD_X_POS * 2 + BOARD_WIDTH, BOARD_Y_POS);
        g.setColor(OUTLINE_COLOUR);
        g.drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        drawScore(g);
        drawNextBox(g);
        g.translate(-BOARD_X_POS * 2 - BOARD_WIDTH, -BOARD_Y_POS);
    }

    // EFFECTS: draws window of game background
    private void drawWindow(Graphics g) {
        g.setColor(backgroundColour);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // EFFECTS: draws blank game board
    private void drawBlankBoard(Graphics g) {
        g.setColor(OUTLINE_COLOUR);
        for (int i = 0; i < BOARD_WIDTH; i += BLOCK_SIZE) {
            for (int j = 0; j < BOARD_HEIGHT; j += BLOCK_SIZE) {
                g.drawRect(i, j, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

    // EFFECTS: draws box that will show upcoming tetrominos
    private void drawNextBox(Graphics g) {
        g.setColor(OUTLINE_COLOUR);
        int halfBlock = BLOCK_SIZE / 2;
        int quarterBlock = BLOCK_SIZE / 4;
        int nextX = BLOCK_SIZE * 3 - quarterBlock;
        int nextY = BLOCK_SIZE * 14 - quarterBlock;
        int nextWidth = BLOCK_SIZE * 4 + halfBlock;
        int nextHeight = BLOCK_SIZE * 2 + halfBlock;
        g.drawRect(nextX, nextY, nextWidth, nextHeight);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, 30));

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds("NEXT", g2d);
        int offsetX = (nextWidth - (int) r.getWidth()) / 2;
        int offsetY = (nextHeight - (int) r.getHeight()) / 2 + fm.getAscent();

        g.drawString("NEXT", nextX + offsetX, nextY + offsetY - BLOCK_SIZE * 2);
    }

    // EFFECTS: draws the high score, current score, and lines cleared to the game window
    private void drawScore(Graphics g) {
        g.setColor(TEXT_COLOUR);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, FONT_SIZE));
        g.drawString("LINES", BLOCK_SIZE, LINES_Y);
        g.drawString(tetris.getLinesClearedString(), LINES_X, LINES_Y);
        g.drawString("TOP", BLOCK_SIZE, HIGH_SCORE_Y);
        g.drawString(tetris.getHighScoreString(), HIGH_SCORE_X, HIGH_SCORE_Y);
        g.drawString("SCORE", BLOCK_SIZE, SCORE_Y);
        g.drawString(tetris.getScoreString(), SCORE_X, SCORE_Y);
    }
}
