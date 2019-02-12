package ca.ubc.cs.cpsc210.ui;


import ca.ubc.cs.cpsc210.audio.SoundEffects;
import ca.ubc.cs.cpsc210.audio.ThemeMusic;
import ca.ubc.cs.cpsc210.buttons.MusicButton;
import ca.ubc.cs.cpsc210.buttons.PauseButton;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.GameBackground;
import ca.ubc.cs.cpsc210.model.Render;
import ca.ubc.cs.cpsc210.model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;


public class Tetris implements ActionListener, KeyListener, MouseListener {

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
    private static final int LINE_SCORE = 100;
    private static final int TETROMINO_SCORE = 10;
    private static final int SCORE_ZEROES = 6;
    private static final int LINE_ZEROES = 3;

    /**
     * Declarations
     */
    // Game classes
    public static Tetris tetris;
    private Render render;
    private static GameBackground gameBackground;
    public static ThemeMusic themeMusic;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private SoundEffects soundEffects;
    // Buttons
    private Board board;
    private MusicButton musicButton;
    private PauseButton pauseButton;

    /**
     * Variables
     */
    private int ticks = 0;
    private int fallSpeed = 50;
    private static boolean gameStart = false;
    private static boolean paused = false;
    private static boolean playMusic = true;
    private static boolean gameOver = false;
    private static int score = 0;
    private static int linesCleared = 0;
    private static int highScore = 0;
    private boolean touchingBlock;
    private boolean touchingBottom;

    /**
     * Getters
     */
    public static boolean isPaused() {
        return paused;
    }

    public static boolean isPlayMusic() {
        return playMusic;
    }

    public static String getScoreString() {
        return fillZeroes(SCORE_ZEROES, score);
    }

    public static String getHighScoreString() {
        return fillZeroes(SCORE_ZEROES, highScore);
    }

    public static String getLinesClearedString() {
        return fillZeroes(LINE_ZEROES, linesCleared);
    }

    /**
     * Setters
     */
    public static void setPaused(boolean p) {
        paused = p;
    }

    public static void setPlayMusic(boolean p) {
        playMusic = p;
    }

    /**
     * Constructor
     */
    public Tetris(int highScore) {
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);
        themeMusic = new ThemeMusic();
        soundEffects = new SoundEffects();
        render = new Render();
        board = new Board();

        jFrame.add(render);
        jFrame.setResizable(false);
        jFrame.addKeyListener(this);
        jFrame.addMouseListener(this);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        jFrame.setTitle("Tetris");
        jFrame.setVisible(true);

        gameBackground = new GameBackground();
        musicButton = new MusicButton();
        pauseButton = new PauseButton();

        this.highScore = highScore;

        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;

        if (gameStart && !paused && !gameOver) {

            if (ticks % fallSpeed == 0) {
                if (board.isTetrominoTouchingBottom(currentTetromino)
                        || board.isTetrominoAboveBlock(currentTetromino)) {
                    board.freezeTetrominoToBoard(currentTetromino);
                    currentTetromino = nextTetromino;
                    currentTetromino.initializeTetromino();
                    nextTetromino = getRandomTetromino();
                    score += TETROMINO_SCORE;
                    if (board.isGameOver(currentTetromino)) {
                        gameOver = true;
                        soundEffects.playGameOver();
                    }


                    // add score based on number of rows cleared
                    // then clear rows
                    int numFullRows = board.countFullRows();
                    switch (numFullRows) {
                        case 1:
                            soundEffects.playOneCleared();
                            break;
                        case 2:
                            soundEffects.playTwoCleared();
                            break;
                        case 3:
                            soundEffects.playThreeCleared();
                            break;
                        case 4:
                            soundEffects.playFourCleared();
                            break;
                        default:
                            break;
                    }
                    for (int i = 0; i < numFullRows; i++) {
                        if (i == 0) {
                            score -= TETROMINO_SCORE;
                        }
                        score += LINE_SCORE * (i + 1);
                        linesCleared++;
                        board.clearRow();
                    }

                } else {
                    currentTetromino.fall();
                }
            }
        }

        render.repaint();
    }


    public void draw(Graphics g) {
        // draw background
        gameBackground.draw(g);


        // translate coordinate system to top left of board
        g.translate(BOARD_X_POS, BOARD_Y_POS);

        // draw board
        board.draw(g);

        // draw current tetromino
        if (gameStart) {
            currentTetromino.draw(g);
        }
        // draw next tetromino
        g.translate(BOARD_X_POS + BOARD_WIDTH, 0);
        if (gameStart) {
            nextTetromino.draw(g);
        }

        // draw buttons
        musicButton.draw(g);
        pauseButton.draw(g);
        g.translate(-BOARD_X_POS - BOARD_WIDTH, 0);
        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 80));
            g.drawString("GAME", BLOCK_SIZE, BLOCK_SIZE * 9);
            g.drawString("OVER", BLOCK_SIZE, BLOCK_SIZE * 9 + BLOCK_SIZE * 3);
        }

        // game start message
        if (!gameStart) {
            g.setFont(new Font("Arial", 1, BLOCK_SIZE));
            g.setColor(Color.white);
            g.drawString("PRESS SPACE", 45, BLOCK_SIZE * 10 - 3);
            g.drawString("TO START", 75, BLOCK_SIZE * 11 - 3);
        }
    }

    // keystroke event methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int KeyCode = e.getKeyCode();

        if (gameStart && !paused) {
            touchingBlock = board.isTetrominoAboveBlock(currentTetromino);
            touchingBottom = board.isTetrominoTouchingBottom(currentTetromino);

            // move blocks down with down arrow
            if (KeyCode == KeyEvent.VK_DOWN && !touchingBottom && !touchingBlock && !gameOver) {
                currentTetromino.fall();
            }

            // move blocks left/right with arrow keys
            if (KeyCode == KeyEvent.VK_LEFT && !touchingLeft() && !gameOver) {
                currentTetromino.moveLeft();
            }
            if (KeyCode == KeyEvent.VK_RIGHT && !touchingright() && !gameOver) {
                currentTetromino.moveRight();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int KeyCode = e.getKeyCode();

        // start game on spacebar, initialize tetrominos
        // pause/unpause game
        if (KeyCode == KeyEvent.VK_SPACE) {
            if (!gameStart) {
                gameStart = true;
                currentTetromino = getRandomTetromino();
                currentTetromino.initializeTetromino();
                nextTetromino = getRandomTetromino();
                soundEffects.playGameStart();
            }
        }

        // rotate tetromino clockwise
        if (KeyCode == KeyEvent.VK_D && gameStart && !gameOver) {
            currentTetromino.rotateCW();
        }
        // rotate tetromino counter clockwise
        if (KeyCode == KeyEvent.VK_A && gameStart && !gameOver) {
            currentTetromino.rotateCCW();
        }
    }

    // mouse event methods
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {

            if (musicButton.isMouseTouching(mouseX, mouseY)) {
                musicButton.showButtonPressed();
            }
            if (pauseButton.isMouseTouching(mouseX, mouseY) && gameStart) {
                pauseButton.showButtonPressed();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {
            // toggle music
            if (musicButton.isMouseTouching(mouseX, mouseY)) {
                musicButton.showButtonReleased();
                musicButton.buttonAction();
                soundEffects.playButtonClick();
            }
            // pause/unpause
            if (pauseButton.isMouseTouching(mouseX, mouseY) && gameStart) {
                pauseButton.showButtonReleased();
                pauseButton.buttonAction();
                soundEffects.playButtonClick();
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    // return random tetromino
    public Tetromino getRandomTetromino() {
        Random rand = new Random();
        int n = rand.nextInt(7);
        switch (n) {
            case 1:
                return new Tetromino(oTetrominoMatrix, O_COLOUR, 'o');
            case 2:
                return new Tetromino(zTetrominoMatrix, Z_COLOUR, 'z');
            case 3:
                return new Tetromino(iTetrominoMatrix, I_COLOUR, 'i');
            case 4:
                return new Tetromino(sTetrominoMatrix, S_COLOUR, 's');
            case 5:
                return new Tetromino(tTetrominoMatrix, T_COLOUR, 't');
            case 6:
                return new Tetromino(lTetrominoMatrix, L_COLOUR, 'l');
            default:
                return new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        }
    }

    private boolean touchingLeft() {
        return currentTetromino.getX() == 0;
    }

    // true if current tetromino is aganst right wall
    private boolean touchingright() {
        int offset = currentTetromino.getShape()[0].length * BLOCK_SIZE;

        return currentTetromino.getX() + offset == BOARD_WIDTH;
    }

    // fill score with zeroes on left
    private static String fillZeroes(int numZeroes, int num) {
        String outputString = "";

        if (num == 0) {
            for (int i = 0; i < numZeroes; i++) {
                outputString += "0";
            }
        } else {
            outputString = Integer.toString(num);
            int n = outputString.length();

            for (int i = 0; i < numZeroes - n; i++) {
                outputString = "0" + outputString;
            }
        }

        return outputString;
    }

    public static void main(String[] args) {
        tetris = new Tetris(highScore);
        themeMusic.play();

    }
}