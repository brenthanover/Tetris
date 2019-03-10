package ca.ubc.cs.cpsc210.model;

import ca.ubc.cs.cpsc210.audio.SoundEffects;
import ca.ubc.cs.cpsc210.audio.Music;
import ca.ubc.cs.cpsc210.exceptions.MissingFileException;
import ca.ubc.cs.cpsc210.ui.Game;
import ca.ubc.cs.cpsc210.ui.GameBackground;
import ca.ubc.cs.cpsc210.ui.buttons.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

import static ca.ubc.cs.cpsc210.model.Tetromino.*;
import static ca.ubc.cs.cpsc210.parsers.LoadHighScore.loadHighScore;
import static ca.ubc.cs.cpsc210.persistence.SaveHighScore.saveHighScore;
import static ca.ubc.cs.cpsc210.ui.Game.*;

public class Tetris implements KeyListener, MouseListener {

    /**
     * Constants
     */
    private static final int LINE_SCORE = 100;
    private static final int TETROMINO_SCORE = 10;
    private static final int SCORE_ZEROES = 6;
    private static final int LINE_ZEROES = 3;

    /**
     * Declarations
     */
    private Board board;
    private GameBackground gameBackground;
    private Music tetrisMusic;
    private Tetromino currentTetromino;
    private Tetromino nextTetromino;
    private SoundEffects soundEffects;
    private TetrisButton[] buttonList;

    /**
     * Variables
     */
    private boolean gameStart = false;
    private boolean paused = false;
    private boolean playMusic = true;
    private boolean playSfx = true;
    private boolean gameOver = false;
    private boolean highScoreSaved = false;
    private int score = 0;
    private int linesCleared = 0;
    private int highScore = 0;

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

    public String getScoreString() {
        return fillZeroes(SCORE_ZEROES, score);
    }

    public String getHighScoreString() {
        return fillZeroes(SCORE_ZEROES, highScore);
    }

    public int getLinesCleared() {
        return linesCleared;
    }

    public String getLinesClearedString() {
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

    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    public Tetromino getNextTetromino() {
        return nextTetromino;
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

    public void setGameBoard(char[][] newBoard) {
        board.setBoardGrid(newBoard);
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public void setHighScore(int hs) {
        highScore = hs;
    }

    public void setLinesCleared(int lc) {
        linesCleared = lc;
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

    /**
     * Constructor
     */
    public Tetris(int highScore) {
        soundEffects = new SoundEffects(this);
        board = new Board();
        gameBackground = new GameBackground(this);
        tetrisMusic = new Music();

        buttonList = new TetrisButton[6];
        buttonList[0] = new MusicButton(this);
        buttonList[1] = new SoundEffectsButton(this);
        buttonList[2] = new MysteryButton(this);
        buttonList[3] = new PauseButton(this);
        buttonList[4] = new SaveButton(this);
        buttonList[5] = new LoadButton();

        this.highScore = highScore;

    }

    // REQUIRES: game is over, high score is not saved
    // MODIFIES: highscore save file
    // EFFECTS:  on game over, record score as new high score if score > old high score
    public void gameOverScoreRecord(String highScoreFileName) {
        if (gameOver && !highScoreSaved) {
            highScoreSaved = true;
            int loadedHighScore = 0;
            try {
                loadedHighScore = loadHighScore(highScoreFileName);
            } catch (MissingFileException | IOException e) {
                e.printStackTrace();
            }

            if (score > loadedHighScore) {
                try {
                    System.out.println("Your new high score is " + score + "!");
                    saveHighScore(highScoreFileName, score);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

        score += TETROMINO_SCORE;

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

    // REQUIRES: board with at least one full row, ie a row with no value marked 'e'
    // MODIFIES: this
    // EFFECTS:  adds score based on number of rows cleared at once: 100, 300, 600, 1000 for 1, 2, 3, 4 lines
    public void addRowClearScore(int numFullRows) {
        for (int i = 0; i < numFullRows; i++) {
            score += LINE_SCORE * (i + 1);
            linesCleared++;
            board.clearRow();
        }

        score -= TETROMINO_SCORE;
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

    // EFFECTS:  fill score with zeroes on left to maximum of numZeroes numbers total
    //           assuming nobody will get more than a 6 digit score, but will still function if they do
    public String fillZeroes(int numZeroes, int score) {
        StringBuilder outputString = new StringBuilder();

        if (score == 0) {
            for (int i = 0; i < numZeroes; i++) {
                outputString.append(0);
            }
        } else {
            outputString.append(score);
            int n = outputString.length();

            for (int i = 0; i < numZeroes - n; i++) {
                outputString.insert(0, 0);
            }
        }

        return outputString.toString();
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
        if (keyCode == KeyEvent.VK_SPACE) {
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
                        && (i < 3
                        || gameStart)) {
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tetris tetris = (Tetris) o;
        return getScore() == tetris.getScore()
                && getLinesCleared() == tetris.getLinesCleared()
                && getHighScore() == tetris.getHighScore()
                && Objects.equals(board, tetris.board)
                && Objects.equals(getCurrentTetromino(), tetris.getCurrentTetromino())
                && Objects.equals(getNextTetromino(), tetris.getNextTetromino());
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, getCurrentTetromino(), getNextTetromino(), getScore(), getLinesCleared(),
                getHighScore());
    }
}