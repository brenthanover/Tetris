package ca.ubc.cs.cpsc210.ui;

import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

import static ca.ubc.cs.cpsc210.model.Tetris.EVENT_LEVEL_UP;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.HIGH_SCORE_FILENAME;

public class Game extends Observable implements ActionListener {
    /**
     * Constants
     */
    public static final int BLOCK_SIZE = 30;
    public static final int BOARD_X_POS = BLOCK_SIZE;
    public static final int BOARD_Y_POS = BLOCK_SIZE;
    public static final int BLOCKS_WIDE = 10;
    public static final int BLOCKS_HIGH = 22;
    public static final int BOARD_WIDTH = BLOCK_SIZE * BLOCKS_WIDE;
    public static final int BOARD_HEIGHT = BLOCK_SIZE * BLOCKS_HIGH;
    public static final int WINDOW_WIDTH = BOARD_X_POS * 3 + BOARD_WIDTH * 2 + 10;
    public static final int WINDOW_HEIGHT = BOARD_Y_POS * 2 + BOARD_HEIGHT + 30;
    public static final int INITIAL_FALL_SPEED = 21;
    public static final int STARTING_LINES_TO_CLEAR = 5;
    public static final int FALL_SPEED_CHANGE_PER_LEVEL = 2;

    /**
     * Variables
     */
    private int fallSpeed;
    private int level;
    private int linesToClear;
    private static int ticks;

    /**
     * Declarations
     */
    private static Tetris tetris;
    private static Render render;
    private static JFrame gameJFrame;
    private Board board;

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

        board = tetris.getBoard();
        gameJFrame = new JFrame();
        addObserver(new MessagePrinter());
        addObserver(tetris);
        addObserver(tetris.getGameBackground());

        ticks = 0;
        linesToClear = STARTING_LINES_TO_CLEAR;
        fallSpeed = INITIAL_FALL_SPEED;
        level = 1;
    }

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
        Timer timer = new Timer(20, this);
        render = new Render(this);

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

    // REQUIRES: level cleared
    // MODIFIES: this, tetris
    // EFFECTS:  clears board, increments level by one
    //           decreases fallSpeed, which increases tetromino falling speed
    //           increases score multiplier by notifying observers to increase level by 1 in tetris.gameBackground
    public void levelUp() {
        tetris.levelUp();
        level++;
        fallSpeed -= FALL_SPEED_CHANGE_PER_LEVEL;
        linesToClear = level * STARTING_LINES_TO_CLEAR;

        setChanged();
        notifyObservers(EVENT_LEVEL_UP);
    }

    // REQUIRES: game is started, game is not paused, game is not over
    // MODIFIES: this
    // EFFECTS:  runs the game based on time
    //           causes current tetromino to drop at rate fallSpeed if it can fall
    //           when tetromino cannot fall any more, freeze to board and get new current and next tetrominos
    //           when cycling tetromino, clear any full lines
    @Override
    public void actionPerformed(ActionEvent e) {
        if (tetris.isGameStart() && !tetris.isPaused() && !tetris.isGameOver()
                && ticks % fallSpeed == 0) {
            if (board.isTetrominoTouchingBottom(tetris.getCurrentTetromino())
                    || board.isTetrominoAboveBlock(tetris.getCurrentTetromino())) {
                tetris.cycleTetromino();
                clearRows();
            } else {
                tetris.getCurrentTetromino().fall();
            }

        }

        if (tetris.isGameLoaded()) {
            updateWhenLoaded();
        }

        tetris.gameOverScoreRecord();

        render.repaint();
        ticks++;
    }

    // MODIFIES: this, game.tetris
    // EFFECTS:  if there are rows to clear on the board, tells tetris to clear them
    //           if the user clears all the lines for the level, they level up
    private void clearRows() {
        if (board.countFullRows() != 0) {
            linesToClear -= board.countFullRows();
            tetris.clearRows(board.countFullRows());

            if (linesToClear <= 0) {
                levelUp();
            }
        }

    }

    // MODIFIES: this
    // EFFECTS:  updates level, fallSpeed, and linesToClear to that of the values in tetris
    private void updateWhenLoaded() {
        tetris.setGameLoaded(false);
        level = tetris.getLevel();
        fallSpeed = tetris.getFallSpeed();
        linesToClear = tetris.getLinesToClear();
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
