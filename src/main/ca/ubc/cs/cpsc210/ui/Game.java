package ca.ubc.cs.cpsc210.ui;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.HIGH_SCORE_FILENAME;

public class Game implements ActionListener {
    /**
     * Constants
     */
    public static final int BLOCK_SIZE = 30;
    public static final int BOARD_X_POS = 30;
    public static final int BOARD_Y_POS = 30;
    public static final int BLOCKS_WIDE = 10;
    public static final int BLOCKS_HIGH = 20;
    public static final int BOARD_WIDTH = BLOCK_SIZE * BLOCKS_WIDE;
    public static final int BOARD_HEIGHT = BLOCK_SIZE * BLOCKS_HIGH;
    public static final int WINDOW_WIDTH = BOARD_X_POS * 3 + BOARD_WIDTH * 2 + 10;
    public static final int WINDOW_HEIGHT = BOARD_Y_POS * 2 + BOARD_HEIGHT + 30;

    /**
     *  Variables
     */
    private int fallSpeed = 20;

    /**
     * Declarations
     */
    private static Tetris tetris;
    private static Render render;
    private static JFrame gameJFrame;
    private static Timer timer;

    /**
     * Constructor
     */
    // EFFECTS: constructs Game object
    public Game() {
        try {
            tetris = new Tetris(loadHighScore(HIGH_SCORE_FILENAME));
        } catch (MissingFileException | IOException e) {
            e.printStackTrace();
        }

        gameJFrame = new JFrame();
        render = new Render(this);
    }

    /**
     * Variables
     */
    private static int ticks = 0;

    /**
     * Setters
     */
    public static void resetTicks() {
        ticks = 0;
    }

    /**
     * Methods
     */
    // MODIFIES: this
    // EFFECTS: runs Tetris game
    //          creates JFrame GUI with timer
    public void run() {
        timer = new Timer(20, this);

        gameJFrame.add(render);
        gameJFrame.setResizable(false);
        gameJFrame.addKeyListener(tetris);
        gameJFrame.addMouseListener(tetris);
        gameJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameJFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameJFrame.setTitle("Tetris");
        gameJFrame.setVisible(true);

        timer.start();
    }

    // REQUIRES: game is started, game is not paused, game is not over
    // MODIFIES: this
    // EFFECTS:  runs the game based on time
    //           causes current tetromino to drop at rate fallSpeed if it can fall
    //           when tetromino cannot fall any more, freeze to board and get new current and next tetrominos
    //           when cycling tetromino, clear any full lines
    @Override
    public void actionPerformed(ActionEvent e) {
        Board board = tetris.getBoard();

        if (tetris.isGameStart() && !tetris.isPaused() && !tetris.isGameOver()) {
            if (ticks % fallSpeed == 0) {
                if (board.isTetrominoTouchingBottom(tetris.getCurrentTetromino())
                        || board.isTetrominoAboveBlock(tetris.getCurrentTetromino())) {
                    tetris.cycleTetromino();

                    // add score based on number of rows cleared, then clear rows
                    if (board.countFullRows() != 0) {
                        tetris.clearRowSoundEffects(board.countFullRows());
                        tetris.clearRows(board.countFullRows());
                    }

                } else {
                    tetris.getCurrentTetromino().fall();
                }
            }
        }

        tetris.gameOverScoreRecord();

        render.repaint();
        ticks++;
    }

    // EFFECTS: draws game to JFrame
    public void draw(Graphics g) {
        tetris.draw(g);
    }

    // MODIFIES: this
    // EFFECTS:  main method for Tetris
    //           starts game, starts music
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
        tetris.getTetrisMusic().playTetrisTheme();
    }
}
