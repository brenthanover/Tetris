package ca.ubc.cs.cpsc210.model;

import ca.ubc.cs.cpsc210.audio.SoundEffects;
import ca.ubc.cs.cpsc210.audio.Music;
import ca.ubc.cs.cpsc210.ui.Game;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import ca.ubc.cs.cpsc210.ui.buttons.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.ui.Game.*;

public class Tetris extends Observable implements KeyListener, MouseListener {

    /**
     * Observer Strings
     */
    public static final String EVENT_TETROMINO_SCORE_ADD = "ADD SCORE";
    public static final String EVENT_ONE_LINE_CLEARED = "ONE LINE CLEARED";
    public static final String EVENT_TWO_LINES_CLEARED = "TWO LINES CLEARE";
    public static final String EVENT_THREE_LINES_CLEARED = "THREE LINES CLEARED";
    public static final String EVENT_FOUR_LINES_CLEARED = "FOUR LINES CLEARED";
    public static final String EVENT_GAME_OVER = "GAME OVER";

    /**
     * Declarations
     */
    private Board board;
    private GameBackground gameBackground;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private TetrisButton[] buttonList;
    private Map<Integer, String> scoreLinesClearedMap;
    private Music tetrisMusic;
    private static SoundEffects soundEffects;


    /**
     * Variables
     */
    private boolean gameStart = false;
    private boolean paused = false;
    private boolean playMusic = true;
    private boolean playSfx = true;
    private boolean gameOver = false;
    private boolean highScoreSaved = false;
    private int highScore;

    /**
     * Getters
     */
    public Music getTetrisMusic() {
        return tetrisMusic;
    }

    public GameBackground getGameBackground() {
        return gameBackground;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }

    public boolean isPlaySfx() {
        return playSfx;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean isHighScoreSaved() {
        return highScoreSaved;
    }

    public int getHighScore() {
        return highScore;
    }

    public Board getBoard() {
        return board;
    }

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
    }

    public TetrisButton[] getButtonList() {
        return buttonList;
    }

    public Map<Integer, String> getScoreClearedLinesMap() {
        return scoreLinesClearedMap;
    }

    public TetrisButton getMusicButton() {
        return buttonList[0];
    }

    public TetrisButton getSoundEffectsButton() {
        return buttonList[1];
    }

    public TetrisButton getMysteryButton() {
        return buttonList[2];
    }

    public TetrisButton getPauseButton() {
        return buttonList[3];
    }

    /**
     * Setters
     */
    public void setPaused(boolean p) {
        paused = p;
    }

    public void setPlayMusic(boolean p) {
        playMusic = p;
    }

    public void setPlaySfx(boolean p) {
        playSfx = p;
    }

    public void setGameOver(boolean b) {
        gameOver = b;
    }

    public void setGameStart(boolean b) {
        gameStart = b;
    }

    public void setGameBoard(char[][] newBoard) {
        board.setBoardGrid(newBoard);
    }

    public void setHighScore(int hs) {
        highScore = hs;
    }

    public void setCurrentTetrominoByLabel(char c) {
        currentTetromino = getTetrominoByLabel(c);
    }

    public void setNextTetrominoByLabel(char c) {
        nextTetromino = getTetrominoByLabel(c);
    }

    public void setCurrentTetromino(Tetromino t) {
        currentTetromino = t;
    }

    public void setNextTetromino(Tetromino t) {
        nextTetromino = t;
    }

    public void setGameBackground(GameBackground gameBackground) {
        this.gameBackground.setScore(gameBackground.getScore());
        this.gameBackground.setHighScore(gameBackground.getHighScore());
        this.gameBackground.setLinesCleared(gameBackground.getLinesCleared());
        this.gameBackground.setBackgroundColour(gameBackground.getBackgroundColour());
    }

    /**
     * Constructor
     */
    // EFFECTS: constructs Tetris object
    public Tetris(int highScore) {
        this.highScore = highScore;
        board = new Board();
        gameBackground = new GameBackground(highScore);

        soundEffects = new SoundEffects(this);
        tetrisMusic = new Music();

        buttonList = new TetrisButton[6];
        buttonList[0] = new MusicButton(this);
        buttonList[1] = new SoundEffectsButton(this);
        buttonList[2] = new MysteryButton(this);
        buttonList[3] = new PauseButton(this);
        buttonList[4] = new SaveButton(this);
        buttonList[5] = new LoadButton(this);

        scoreLinesClearedMap = new HashMap<>();
        scoreLinesClearedMap.put(1, EVENT_ONE_LINE_CLEARED);
        scoreLinesClearedMap.put(2, EVENT_TWO_LINES_CLEARED);
        scoreLinesClearedMap.put(3, EVENT_THREE_LINES_CLEARED);
        scoreLinesClearedMap.put(4, EVENT_FOUR_LINES_CLEARED);

        addObserver(gameBackground);
    }

    // REQUIRES: game is over, high score is not saved
    // MODIFIES: highscore save file
    // EFFECTS:  on game over, notify GameBackground observer to update high score if necessary
    public void gameOverScoreRecord() {
        if (gameOver && !highScoreSaved) {
            highScoreSaved = true;

            setChanged();
            notifyObservers(EVENT_GAME_OVER);
        }
    }

    // REQUIRES: current tetromino is directly above block on board or at bottom of game board
    // MODIFIES: this
    // EFFECTS:  saves currentTetromino to board,
    //           changes nextTetromino to currentTetromino
    //           sets new random tetromino to nextTetromino
    //           adds score
    //           checks to see if game is over, makes game over if true
    public void cycleTetromino() {
        board.freezeTetrominoToBoard(currentTetromino);
        currentTetromino = nextTetromino;
        currentTetromino.initializeTetromino();
        nextTetromino = getRandomTetromino();

        setChanged();
        notifyObservers(EVENT_TETROMINO_SCORE_ADD);

        if (board.isGameOver(currentTetromino)) {
            gameOver = true;
            soundEffects.playGameOver();
        }
    }

    // REQUIRES: board has at least one full row, ie a row with no value marked 'e'
    // EFFECTS:  plays different sound effects based on number of rows cleared simultaneously
    public void clearRowSoundEffects(int numFullRows) {
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

    // REQUIRES: board with number of full rows in board == numFullRows
    // MODIFIES: this
    // EFFECTS:  clears number of full rows equal to input numFullRows
    public void clearRows(int numFullRows) {
        setChanged();
        notifyObservers(scoreLinesClearedMap.get(numFullRows));

        for (int i = 0; i < numFullRows; i++) {
            board.clearRow();
        }
    }

    // MODIFIES: this
    // EFFECTS:  intitialize game: set gameStart to true, assign new random tetrominos to current, next tetromino
    public void initializeTetris() {
        gameStart = true;
        currentTetromino = getRandomTetromino();
        currentTetromino.initializeTetromino();
        nextTetromino = getRandomTetromino();
        soundEffects.playGameStart();
    }

    // EFFECTS:  return random tetromino from available seven tetrominos as discussed in Tetromino class
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

    // EFFECTS:  return tetromino according to character label as described in Tetromino class
    public Tetromino getTetrominoByLabel(char c) {
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

    // EFFECTS: draws all aspects of the Tetris game
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

    // EFFECTS: draws buttons on game screen
    private void drawButtons(Graphics g) {
        for (TetrisButton b : buttonList) {
            b.draw(g);
        }
    }

    // REQUIRES: game is not started
    // EFFECTS:  draw start game message on game window
    private void drawGameStart(Graphics g) {
        if (!gameStart) {
            g.setFont(new Font("Arial", 1, BLOCK_SIZE));
            g.setColor(Color.white);
            g.drawString("PRESS SPACE", 45, BLOCK_SIZE * 10 - 3);
            g.drawString("TO START", 75, BLOCK_SIZE * 11 - 3);
        }
    }

    // REQUIRES: game over
    // EFFECTS:  draw game over message on game window
    private void drawGameOver(Graphics g) {
        if (gameOver) {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 80));
            g.drawString("GAME", BLOCK_SIZE, BLOCK_SIZE * 9);
            g.drawString("OVER", BLOCK_SIZE, BLOCK_SIZE * 9 + BLOCK_SIZE * 3);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // REQUIRES: key pressed, game is started, game is not paused
    // MODIFIES: this
    // EFFECTS:  down arrow:  move tetromino down on button press if move downwards is not obstructed
    //           right arrow: move tetromino right on button press if move right is not obstructed
    //           left arrow:  move tetromino left on button press if move right is not obstructed
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (gameStart && !paused && !gameOver) {
            boolean touchingBlock = board.isTetrominoAboveBlock(currentTetromino);
            boolean touchingBottom = board.isTetrominoTouchingBottom(currentTetromino);

            // move blocks down with down arrow
            if (keyCode == KeyEvent.VK_DOWN && !touchingBottom && !touchingBlock) {
                currentTetromino.fall();
            }

            // move blocks left/right with arrow keys
            if (keyCode == KeyEvent.VK_LEFT
                    && !board.isTetrominoMovementRestrictedOnLeft(currentTetromino)) {
                currentTetromino.moveLeft();
            }
            if (keyCode == KeyEvent.VK_RIGHT
                    && !board.isTetrominoMovementRestrictedOnRight(currentTetromino)) {
                currentTetromino.moveRight();
            }
        }
    }

    // REQUIRES: key released, game is not started or game is not over
    // MODIFIES: this
    // EFFECTS:  start game on spacebar press
    //           drop currentTetromino to bottom of board on spacebar press
    //           rotate currentTetromino clockwise on D button press
    //           rotate currentTetromino counter-clockwise on A button press
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // start game on spacebar, initialize tetrominos, or drop tetromino to bottom of board if game is started
        if (keyCode == KeyEvent.VK_SPACE && !paused) {
            if (!gameStart) {
                initializeTetris();
            } else if (!gameOver) {
                board.dropTetrominoToBottom(currentTetromino);
                Game.resetTicks();
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    // REQUIRES: MouseEvent: button pressed
    // MODIFIES: TetrisButton
    // EFFECTS:  show visual of button pressed when button is clicked by changing colour
    @Override
    public void mousePressed(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {

            for (int i = 0; i < buttonList.length; i++) {
                if (buttonList[i].isMouseTouching(mouseX, mouseY)
                        && (i < 3 || gameStart)
                        && (i < 4 || !paused)) {
                    buttonList[i].showButtonPressed();
                }
            }
        }

    }

    // REQUIRES: MouseEvent: mouse released
    // MODIFIES: TetrisButton, this
    // EFFECTS:  execute button action when button released, play sound effect, show visual
    //           iterate through button array, executing button if mouse is touching it
    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseCode = e.getButton();
        int mouseX = e.getX();
        int mouseY = e.getY();

        if (mouseCode == MouseEvent.BUTTON1) {

            for (int i = 0; i < buttonList.length; i++) {
                if (buttonList[i].isMouseTouching(mouseX, mouseY)
                        && (i < 3 || gameStart)
                        && (i < 4 || !paused)) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tetris tetris = (Tetris) o;
        return getHighScore() == tetris.getHighScore()
                && Objects.equals(board, tetris.board)
                && Objects.equals(getCurrentTetromino(), tetris.getCurrentTetromino())
                && Objects.equals(getNextTetromino(), tetris.getNextTetromino())
                && Objects.equals((gameBackground), tetris.getGameBackground());
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, getCurrentTetromino(), getNextTetromino(),
                getHighScore());
    }
}