package ca.ubc.cs.cpsc210.ui;


import ca.ubc.cs.cpsc210.audio.SoundEffects;
import ca.ubc.cs.cpsc210.audio.Music;
import ca.ubc.cs.cpsc210.ui.buttons.*;
import ca.ubc.cs.cpsc210.model.Board;
import ca.ubc.cs.cpsc210.model.Tetromino;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.parsers.SaveHighScore.saveHighScore;


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
    private static final String highScoreFileName = "highscore";

    /**
     * Declarations
     */
    // Game classes
    private static Board board;
    public static Tetris tetris;
    private static Render render;
    public static GameBackground gameBackground;
    public static Music tetrisMusic;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private SoundEffects soundEffects;
    // Buttons
    private MusicButton musicButton;
    private SoundEffectsButton sfxButton;
    private PauseButton pauseButton;
    private SaveButton saveButton;
    private LoadButton loadButton;
    private MysteryButton mysteryButton;
    private TetrisButton[] buttonList;

    /**
     * Variables
     */
    private int ticks = 0;
    private int fallSpeed = 20;
    private static boolean gameStart = false;
    private static boolean paused = false;
    private static boolean playMusic = true;
    private static boolean playSfx = true;
    private static boolean gameOver = false;
    private static boolean highScoreSaved = false;
    private static int score = 0;
    private static int linesCleared = 0;
    private static int highScore = 0;

    /**
     * Getters
     */
    public static boolean isPaused() {
        return paused;
    }

    public static boolean isPlayMusic() {
        return playMusic;
    }

    public static boolean isPlaySfx() {
        return playSfx;
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

    public int getHighScore() {
        return highScore;
    }

    public int getScore() {
        return score;
    }

    public Board getGameBoard() {
        return board;
    }

    public char getCurrentTetrominoLabel() {
        return currentTetromino.getLabel();
    }

    public char getNextTetrominoLabel() {
        return nextTetromino.getLabel();
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

    public static void setPlaySfx(boolean p) {
        playSfx = p;
    }

    public void setGameBoard(char[][] newBoard) {
        board.setBoardGrid(newBoard);
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public void setCurrentTetromino(char c) {
        currentTetromino = getTetromino(c);
    }

    public void setNextTetromino(char c) {
        nextTetromino = getTetromino(c);
    }

    /**
     * Constructor
     */
    public Tetris(int highScore) {
        JFrame tetrisJFrame = new JFrame();
        Timer timer = new Timer(20, this);
        tetrisMusic = new Music();
        soundEffects = new SoundEffects();
        render = new Render();
        board = new Board();

        tetrisJFrame.add(render);
        tetrisJFrame.setResizable(false);
        tetrisJFrame.addKeyListener(this);
        tetrisJFrame.addMouseListener(this);
        tetrisJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tetrisJFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        tetrisJFrame.setTitle("Tetris");
        tetrisJFrame.setVisible(true);

        gameBackground = new GameBackground();
        buttonList = new TetrisButton[6];
        musicButton = new MusicButton();
        buttonList[0] = musicButton;
        sfxButton = new SoundEffectsButton();
        buttonList[1] = sfxButton;
        mysteryButton = new MysteryButton();
        buttonList[2] = mysteryButton;
        pauseButton = new PauseButton();
        buttonList[3] = pauseButton;
        saveButton = new SaveButton();
        buttonList[4] = saveButton;
        loadButton = new LoadButton();
        buttonList[5] = loadButton;

        this.highScore = highScore;

        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameStart && !paused && !gameOver) {

            if (ticks % fallSpeed == 0) {
                if (board.isTetrominoTouchingBottom(currentTetromino)
                        || board.isTetrominoAboveBlock(currentTetromino)) {
                    cycleTetromino();

                    // add score based on number of rows cleared
                    // then clear rows
                    int numFullRows = board.countFullRows();
                    clearRowSoundEffects(numFullRows);
                    addRowClearScore(numFullRows);

                } else {
                    currentTetromino.fall();
                }
            }
        }

        gameOverScoreRecord();


        render.repaint();
        ticks++;
    }

    private void gameOverScoreRecord() {
        if (gameOver && !highScoreSaved) {
            highScoreSaved = true;
            if (score > loadHighScore(highScoreFileName)) {
                saveHighScore(highScoreFileName, score);
            }
        }
    }

    public void draw(Graphics g) {
        // draw background
        gameBackground.draw(g);
        g.translate(BOARD_X_POS, BOARD_Y_POS);
        board.draw(g);

        // draw tetrominos
        if (gameStart) {
            currentTetromino.draw(g);
        }
        g.translate(BOARD_X_POS + BOARD_WIDTH, 0);
        if (gameStart) {
            nextTetromino.draw(g);
        }

        // draw buttons
        drawButtons(g);
        g.translate(-BOARD_X_POS - BOARD_WIDTH, 0);

        // game state messages
        drawGameStart(g);
        drawGameOver(g);
    }

    private void drawButtons(Graphics g) {
        musicButton.draw(g);
        sfxButton.draw(g);
        pauseButton.draw(g);
        saveButton.draw(g);
        loadButton.draw(g);
        mysteryButton.draw(g);
    }

    private void drawGameStart(Graphics g) {
        if (!gameStart) {
            g.setFont(new Font("Arial", 1, BLOCK_SIZE));
            g.setColor(Color.white);
            g.drawString("PRESS SPACE", 45, BLOCK_SIZE * 10 - 3);
            g.drawString("TO START", 75, BLOCK_SIZE * 11 - 3);
        }
    }

    private void drawGameOver(Graphics g) {
        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 80));
            g.drawString("GAME", BLOCK_SIZE, BLOCK_SIZE * 9);
            g.drawString("OVER", BLOCK_SIZE, BLOCK_SIZE * 9 + BLOCK_SIZE * 3);
        }
    }


    // saves current tetromino to board, summons next one to top, gets random new one
    // adds score, checks to see if game is over
    private void cycleTetromino() {
        board.freezeTetrominoToBoard(currentTetromino);
        currentTetromino = nextTetromino;
        currentTetromino.initializeTetromino();
        nextTetromino = getRandomTetromino();

        score += TETROMINO_SCORE;

        if (board.isGameOver(currentTetromino)) {
            gameOver = true;
            soundEffects.playGameOver();
        }
    }

    private void clearRowSoundEffects(int numFullRows) {

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
    }

    private void addRowClearScore(int numFullRows) {
        for (int i = 0; i < numFullRows; i++) {
            if (i == 0) {
                score -= TETROMINO_SCORE;
            }
            score += LINE_SCORE * (i + 1);
            linesCleared++;
            board.clearRow();
        }
    }

    private void startGame() {
        gameStart = true;
        currentTetromino = getRandomTetromino();
        currentTetromino.initializeTetromino();
        nextTetromino = getRandomTetromino();
        soundEffects.playGameStart();
    }

    // return random tetromino
    private Tetromino getRandomTetromino() {
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

    // return random tetromino
    private Tetromino getTetromino(char c) {
        switch (c) {
            case 'o':
                return new Tetromino(oTetrominoMatrix, O_COLOUR, 'o');
            case 'z':
                return new Tetromino(zTetrominoMatrix, Z_COLOUR, 'z');
            case 'i':
                return new Tetromino(iTetrominoMatrix, I_COLOUR, 'i');
            case 's':
                return new Tetromino(sTetrominoMatrix, S_COLOUR, 's');
            case 't':
                return new Tetromino(tTetrominoMatrix, T_COLOUR, 't');
            case 'l':
                return new Tetromino(lTetrominoMatrix, L_COLOUR, 'l');
            default:
                return new Tetromino(jTetrominoMatrix, J_COLOUR, 'j');
        }
    }

    // fill score with zeroes on left
    private static String fillZeroes(int numZeroes, int num) {
        StringBuilder outputString = new StringBuilder();

        if (num == 0) {
            for (int i = 0; i < numZeroes; i++) {
                outputString.append(0);
            }
        } else {
            outputString.append(num);
            int n = outputString.length();

            for (int i = 0; i < numZeroes - n; i++) {
                outputString.insert(0, 0);
            }
        }

        return outputString.toString();
    }

    // keystroke event methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gameStart && !paused) {
            boolean touchingBlock = board.isTetrominoAboveBlock(currentTetromino);
            boolean touchingBottom = board.isTetrominoTouchingBottom(currentTetromino);

            // move blocks down with down arrow
            if (keyCode == KeyEvent.VK_DOWN && !touchingBottom && !touchingBlock && !gameOver) {
                currentTetromino.fall();
            }

            // move blocks left/right with arrow keys
            if (keyCode == KeyEvent.VK_LEFT
                    && !board.isTetrominoMovementRestrictedOnLeft(currentTetromino) && !gameOver) {
                currentTetromino.moveLeft();
            }
            if (keyCode == KeyEvent.VK_RIGHT
                    && !board.isTetrominoMovementRestrictedOnRight(currentTetromino) && !gameOver) {
                currentTetromino.moveRight();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // start game on spacebar, initialize tetrominos
        // pause/unpause game
        if (keyCode == KeyEvent.VK_SPACE) {
            if (!gameStart) {
                startGame();
            } else if (!gameOver) {
                board.dropTetrominoToBottom(currentTetromino);
                ticks = 0;
            }
        }

        // rotate tetromino CW or CCW
        if (!gameOver) {

            if (keyCode == KeyEvent.VK_D && gameStart && board.canRotateCW(currentTetromino)) {
                currentTetromino.rotateCW();
            }
            if (keyCode == KeyEvent.VK_A && gameStart && board.canRotateCCw(currentTetromino)) {
                currentTetromino.rotateCCw();
            }
        }
    }

    // mouse event methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // show visual of button pressed when button is clicked
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {

            for (int i = 0; i < buttonList.length; i++) {
                if (buttonList[i].isMouseTouching(mouseX, mouseY)
                        && (i < 3
                        || gameStart)) {
                    buttonList[i].showButtonPressed();
                }
            }
        }

    }

    // execute button action when button released, play sound effect, show visual
    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {

            for (int i = 0; i < buttonList.length; i++) {
                if (buttonList[i].isMouseTouching(mouseX, mouseY)
                        && (i < 3
                        || gameStart)) {
                    buttonList[i].showButtonReleased();
                    buttonList[i].buttonAction();
                    soundEffects.playButtonClick();
                }
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    public static void main(String[] args) {
        tetris = new Tetris(loadHighScore(highScoreFileName));
        tetrisMusic.playTetrisTheme();
    }
}