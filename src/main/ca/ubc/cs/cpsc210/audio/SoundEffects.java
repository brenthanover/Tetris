package ca.ubc.cs.cpsc210.audio;

import ca.ubc.cs.cpsc210.model.Tetris;

import javax.sound.sampled.*;
import java.io.File;

public class SoundEffects {

    /**
     * Constants
     */
    public static final String SFX_PATH = "src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/";
    public static final  File fileOneCleared = new File(SFX_PATH + "onecleared.wav");
    public static final  File fileTwoCleared = new File(SFX_PATH + "twocleared.wav");
    public static final  File fileThreeCleared = new File(SFX_PATH + "threecleared.wav");
    public static final  File fileFourCleared = new File(SFX_PATH + "fourcleared.wav");
    public static final  File fileGameStart = new File(SFX_PATH + "gamestart.wav");
    public static final  File fileGameOver = new File(SFX_PATH + "gameover.wav");
    public static final  File fileButtonClick = new File(SFX_PATH + "buttonclick.wav");

    /**
     * Declarations
     */
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
        playSoundEffect(fileOneCleared);
    }

    public void playTwoCleared() {
        playSoundEffect(fileTwoCleared);
    }

    public void playThreeCleared() {
        playSoundEffect(fileThreeCleared);
    }

    public void playFourCleared() {
        playSoundEffect(fileFourCleared);
    }

    public void playGameStart() {
        playSoundEffect(fileGameStart);
    }

    public void playGameOver() {
        playSoundEffect(fileGameOver);
    }

    public void playButtonClick() {
        playSoundEffect(fileButtonClick);
    }

    private void playSoundEffect(File file) {
        if (tetris.isPlaySfx()) {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(file);
                AudioFormat format = stream.getFormat();
                DataLine.Info info = new DataLine.Info(Clip.class, format);
                Clip clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.start();
            } catch (Exception e) {
                System.out.println("Could not play sound effect");
            }
        }
    }
}
