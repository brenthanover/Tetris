package ca.ubc.cs.cpsc210.ui;


import ca.ubc.cs.cpsc210.model.Block;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class GameBackground {

    /**
     * Constants
     */
    private static final Color OUTLINE_COLOUR = Color.gray;
    private static final Color BACKGROUND_COLOUR = Color.black;
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
     * Constructor
     */
    public GameBackground() {
    }

    /**
     * Methods
     */
    public void draw(Graphics g) {
        // fill background black
        drawBackground(g);

        // draw blank board with grey squares
        g.translate(BOARD_X_POS, BOARD_Y_POS);
        drawBlankBoard(g);

        g.translate(-BOARD_X_POS, -BOARD_Y_POS);

        // draw score, preview screen
        g.translate(BOARD_X_POS * 2 + BOARD_WIDTH, BOARD_Y_POS);
        // menu window
        g.setColor(OUTLINE_COLOUR);
        g.drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        // score
        drawScore(g);

        // box around next block
        drawNextBox(g);

        // translate back to original coordinates
        g.translate(-BOARD_X_POS * 2 - BOARD_WIDTH, - BOARD_Y_POS);
    }

    private void drawBackground(Graphics g) {
        g.setColor(BACKGROUND_COLOUR);
        g.fillRect(0, 0, Tetris.WINDOW_WIDTH, Tetris.WINDOW_HEIGHT);
    }

    private void drawBlankBoard(Graphics g) {
        g.setColor(OUTLINE_COLOUR);
        for (int i = 0; i < BOARD_WIDTH; i += BLOCK_SIZE) {
            for (int j = 0; j < BOARD_HEIGHT; j += BLOCK_SIZE) {
                g.drawRect(i, j, BLOCK_SIZE, BLOCK_SIZE);
            }
        }
    }

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

    private void drawScore(Graphics g) {
        g.setColor(TEXT_COLOUR);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, FONT_SIZE));
        g.drawString("LINES", BLOCK_SIZE, LINES_Y);
        g.drawString(getLinesClearedString(), LINES_X, LINES_Y);
        g.drawString("TOP", BLOCK_SIZE, HIGH_SCORE_Y);
        g.drawString(getHighScoreString(), HIGH_SCORE_X, HIGH_SCORE_Y);
        g.drawString("SCORE", BLOCK_SIZE, SCORE_Y);
        g.drawString(getScoreString(), SCORE_X, SCORE_Y);
    }
}
