package ca.ubc.cs.cpsc210.model;


import ca.ubc.cs.cpsc210.ui.Tetris;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static ca.ubc.cs.cpsc210.ui.Tetris.*;

public class GameBackground {

    /**
     * Constants
     */
    private static final  Color OUTLINE_COLOUR = Color.gray;
    private static final Color BACKGROUND_COLOUR = Color.black;
    private static final Color TEXT_COLOUR = Color.white;
    private static final int FONT_STYLE = 1;
    private static final int FONT_SIZE = 60;
    private static final String FONT_TYPE = "Arial";
    private static final int SCORE_X = BLOCK_SIZE;
    private static final int SCORE_Y = BLOCK_SIZE * 4;
    private static final int HSCORE_X = BLOCK_SIZE;
    private static final int HSCORE_Y = BLOCK_SIZE * 8;
    private static final int LINES_X = BLOCK_SIZE;
    private static final int LINES_Y = BLOCK_SIZE * 12;

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
        g.setColor(BACKGROUND_COLOUR);
        g.fillRect(0, 0, Tetris.WINDOW_WIDTH, Tetris.WINDOW_HEIGHT);

        // draw blank board with grey squares
        g.translate(BOARD_X_POS, BOARD_Y_POS);
        g.setColor(OUTLINE_COLOUR);
        for (int i = 0; i < BOARD_WIDTH; i += BLOCK_SIZE) {
            for (int j = 0; j < BOARD_HEIGHT; j += BLOCK_SIZE) {
                g.drawRect(i, j, BLOCK_SIZE, BLOCK_SIZE);
            }
        }

        g.translate(-BOARD_X_POS, -BOARD_Y_POS);

        // draw score, preview screen
        g.translate(BOARD_X_POS * 2 + BOARD_WIDTH, BOARD_Y_POS);
        // menu window
        g.setColor(OUTLINE_COLOUR);
        g.drawRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        // score
        g.setColor(TEXT_COLOUR);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, FONT_SIZE));
        g.drawString("LINES", BLOCK_SIZE, BLOCK_SIZE * 2);
        g.drawString(getLinesClearedString(), SCORE_X, SCORE_Y);
        g.drawString("TOP", BLOCK_SIZE, BLOCK_SIZE * 6);
        g.drawString(getHighScoreString(), HSCORE_X, HSCORE_Y);
        g.drawString("SCORE", BLOCK_SIZE, BLOCK_SIZE * 10);
        g.drawString(getScoreString(), LINES_X, LINES_Y);

        // box around next block
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
        int xOffset = (nextWidth - (int) r.getWidth()) / 2;
        int yOffset = (nextHeight - (int) r.getHeight()) / 2 + fm.getAscent();

        g.drawString("NEXT", nextX + xOffset, nextY + yOffset - BLOCK_SIZE * 2);

        g.translate(-BOARD_X_POS * 2 - BOARD_WIDTH, -BOARD_Y_POS);
    }
}
