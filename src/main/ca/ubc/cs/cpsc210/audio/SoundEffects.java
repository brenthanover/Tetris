package ca.ubc.cs.cpsc210.audio;

import ca.ubc.cs.cpsc210.model.Tetris;

import javax.sound.sampled.*;
import java.io.File;

public class SoundEffects {

    /**
     * Constants
     */
    private final File fileOneCleared = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/onecleared.wav");
    private final File fileTwoCleared = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/twocleared.wav");
    private final File fileThreeCleared = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/threecleared.wav");
    private final File fileFourCleared = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/fourcleared.wav");
    private final File fileGameStart = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/gamestart.wav");
    private final File fileGameOver = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/gameover.wav");
    private final File fileButtonClick = new File("src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/buttonclick.wav");

    /**
     *  Declarations
     */
    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;
    private Tetris tetris;

    /**
     * Constructor
     */
    public SoundEffects(Tetris tetris) {
        this.tetris = tetris;
    }

    /**
     * Methods
     */

    // try to optimize, combine into one method, output an array or something?
    public void playOneCleared() {

        try {
            stream = AudioSystem.getAudioInputStream(fileOneCleared);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playTwoCleared() {

        try {
            stream = AudioSystem.getAudioInputStream(fileTwoCleared);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playThreeCleared() {

        try {
            stream = AudioSystem.getAudioInputStream(fileThreeCleared);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playFourCleared() {

        try {
            stream = AudioSystem.getAudioInputStream(fileFourCleared);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playGameStart() {

        try {
            stream = AudioSystem.getAudioInputStream(fileGameStart);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playGameOver() {

        try {
            stream = AudioSystem.getAudioInputStream(fileGameOver);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }

    public void playButtonClick() {

        try {
            stream = AudioSystem.getAudioInputStream(fileButtonClick);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            if (tetris.isPlaySfx()) {
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme sound effect");
        }
    }
}
