package ca.ubc.cs.cpsc210.ui;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.*;

import static ca.ubc.cs.cpsc210.model.Tetris.*;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.HIGH_SCORE_FILENAME;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.saveHighScore;
import static ca.ubc.cs.cpsc210.ui.Game.*;


// this class tracks score, lines cleared, and high score, and renders the game window,
// the next tetromino window, the blank board, and the aformentioned parameters
public class GameBackground implements Observer {
    /**
     * Constants
     */
    private static final Color OUTLINE_COLOUR = Color.gray;
    private static final Color TEXT_COLOUR = Color.white;
    private static final int FONT_STYLE = 1;
    private static final int FONT_SIZE = 30;
    private static final String FONT_TYPE = "Arial";
    private static final int SCORE_X_POS = BLOCK_SIZE * 5 + 19;
    private static final int SCORE_Y_POS = BLOCK_SIZE * 6;
    private static final int HIGH_SCORE_X_POS = BLOCK_SIZE * 5 + 19;
    private static final int HIGH_SCORE_Y_POS = BLOCK_SIZE * 4;
    private static final int LINES_X_POS = BLOCK_SIZE * 7 + 9;
    private static final int LINES_Y_POS = BLOCK_SIZE * 2;
    private static final int SCORE_ZEROES = 6;
    private static final int LINE_ZEROES = 3;
    public static final int LINE_SCORE = 100;
    public static final int TETROMINO_SCORE = 10;

    /**
     * Variables
     */
    private Color backgroundColour;
    private int score;
    private int linesCleared;
    private int highScore;
    private Map<String, Integer> linesClearedScoreMap;

    /**
     * Getters
     */
    public int getScore() {
        return score;
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public int getHighScore() {
        return highScore;
    }

    public Color getBackgroundColour() {
        return backgroundColour;
    }

    /**
     * Setters
     */
    public void setBackgroundColour(Color c) {
        backgroundColour = c;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }

    /**
     * Constructor
     */
    public GameBackground(int highScore) {
        this.highScore = highScore;
        linesCleared = 0;
        score = 0;
        backgroundColour = Color.black;

        linesClearedScoreMap = new HashMap<>();
        linesClearedScoreMap.put(EVENT_ONE_LINE_CLEARED, 1);
        linesClearedScoreMap.put(EVENT_TWO_LINES_CLEARED, 2);
        linesClearedScoreMap.put(EVENT_THREE_LINES_CLEARED, 3);
        linesClearedScoreMap.put(EVENT_FOUR_LINES_CLEARED, 4);
    }

    /**
     * Methods
     */
    // REQUIRES: score is a maximum of numZeroes digits
    // EFFECTS:  take score and turn it into a string with zeroes on left, max numZeroes total digits
    public String fillZeroes(int numZeroes, int score) {
        StringBuilder outputString = new StringBuilder();

        outputString.append(score);
        int n = outputString.length();

        if (numZeroes == 0) {
            return "";
        }

        for (int i = 0; i < numZeroes - n; i++) {
            outputString.insert(0, 0);
        }

        return outputString.toString();
    }

    // EFFECTS: returns a string with SCORE_ZEROES number of digits, with zeroes to left of score
    public String getScoreString() {
        return fillZeroes(SCORE_ZEROES, score);
    }

    // EFFECTS: returns a string with SCORE_ZEROES number of digits, with zeroes to left of highScore
    public String getHighScoreString() {
        return fillZeroes(SCORE_ZEROES, highScore);
    }

    // EFFECTS: returns a string with LINE_ZEROES number of digits, with zeroes to left of linesCleared
    public String getLinesClearedString() {
        return fillZeroes(LINE_ZEROES, linesCleared);
    }

    // MODIFIES: file saved under resources/savefiles/HIGH_SCORE_FILENAME
    // EFFECTS:  if score > highScore, saves new high score to HIGH_SCORE_FILENAME, prints new high score
    public void recordHighScore() {
        if (score > highScore) {
            try {
                System.out.println("Your new high score is " + score + "!");
                saveHighScore(HIGH_SCORE_FILENAME, score);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // REQUIRES: event is one of: EVENT_ONE_LINE_CLEARED
    //                            EVENT_TWO_LINES_CLEARED
    //                            EVENT_THREE_LINES_CLEARED
    //                            EVENT_FOUR_LINES_CLEARED
    // MODIFIES: this
    // EFFECTS:  adds line score based on number of lines cleared
    public void addLineScore(String event) {
        int linesToClear = linesClearedScoreMap.get(event);

        for (int i = 0; i < linesToClear; i++) {
            score += LINE_SCORE * (i + 1);
            linesCleared++;
        }

        score -= TETROMINO_SCORE;
    }

    // REQUIRES: event is one of: EVENT_TETROMINO_SCORE_ADD
    //                            EVENT_ONE_LINE_CLEARED
    //                            EVENT_TWO_LINES_CLEARED
    //                            EVENT_THREE_LINES_CLEARED
    //                            EVENT_FOUR_LINES_CLEARED
    // MODIFIES: this
    // EFFECTS:  updates score and lines cleared for appropriate events
    @Override
    public void update(Observable o, Object event) {
        switch ((String) event) {
            case EVENT_TETROMINO_SCORE_ADD:
                score += TETROMINO_SCORE;
                break;
            case EVENT_GAME_OVER:
                recordHighScore();
                break;
            default:
                addLineScore((String) event);
                break;
        }
    }

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
        int nextXPos = BLOCK_SIZE * 3 - quarterBlock;
        int nextYPos = BLOCK_SIZE * 14 - quarterBlock;
        int nextWidth = BLOCK_SIZE * 4 + halfBlock;
        int nextHeight = BLOCK_SIZE * 2 + halfBlock;
        g.drawRect(nextXPos, nextYPos, nextWidth, nextHeight);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, 30));

        Graphics2D g2d = (Graphics2D) g;
        FontMetrics fm = g2d.getFontMetrics();
        Rectangle2D r = fm.getStringBounds("NEXT", g2d);
        int offsetX = (nextWidth - (int) r.getWidth()) / 2;
        int offsetY = (nextHeight - (int) r.getHeight()) / 2 + fm.getAscent();

        g.drawString("NEXT", nextXPos + offsetX, nextYPos + offsetY - BLOCK_SIZE * 2);
    }

    // EFFECTS: draws the high score, current score, and lines cleared to the game window
    private void drawScore(Graphics g) {
        g.setColor(TEXT_COLOUR);
        g.setFont(new Font(FONT_TYPE, FONT_STYLE, FONT_SIZE));
        g.drawString("LINES", BLOCK_SIZE, LINES_Y_POS);
        g.drawString(getLinesClearedString(), LINES_X_POS, LINES_Y_POS);
        g.drawString("TOP", BLOCK_SIZE, HIGH_SCORE_Y_POS);
        g.drawString(getHighScoreString(), HIGH_SCORE_X_POS, HIGH_SCORE_Y_POS);
        g.drawString("SCORE", BLOCK_SIZE, SCORE_Y_POS);
        g.drawString(getScoreString(), SCORE_X_POS, SCORE_Y_POS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GameBackground gb = (GameBackground) o;
        return score == gb.getScore()
                && linesCleared == gb.getLinesCleared()
                && highScore == gb.getHighScore()
                && Objects.equals(backgroundColour, gb.getBackgroundColour());
    }

    @Override
    public int hashCode() {
        return Objects.hash(backgroundColour, score, linesCleared, highScore);
    }
}
