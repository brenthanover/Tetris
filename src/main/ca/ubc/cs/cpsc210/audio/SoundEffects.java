package ca.ubc.cs.cpsc210.audio;

import ca.ubc.cs.cpsc210.model.Tetris;

import javax.sound.sampled.*;
import java.io.File;

public class SoundEffects {

    /**
     * Constants
     */
    public static final String SFX_PATH = "src/main/ca/ubc/cs/cpsc210/resources/audio/sfx/";
    public static final File fileOneCleared = new File(SFX_PATH + "onecleared.wav");
    public static final File fileTwoCleared = new File(SFX_PATH + "twocleared.wav");
    public static final File fileThreeCleared = new File(SFX_PATH + "threecleared.wav");
    public static final File fileFourCleared = new File(SFX_PATH + "fourcleared.wav");
    public static final File fileGameStart = new File(SFX_PATH + "gamestart.wav");
    public static final File fileGameOver = new File(SFX_PATH + "gameover.wav");
    public static final File fileButtonClick = new File(SFX_PATH + "buttonclick.wav");
    public static final File fileLevelUp = new File(SFX_PATH + "levelup.wav");

    /**
     * Declarations
     */
    private Tetris tetris;

    /**
     * Constructor
     */
    // EFFECTS: constructor for SoundEffects object
    public SoundEffects(Tetris tetris) {
        this.tetris = tetris;
    }

    /**
     * Methods
     */
    // EFFECTS: plays sound effect for clearing one line
    public void playOneCleared() {
        playSoundEffect(fileOneCleared);
    }

    // EFFECTS: plays sound effect for clearing two lines
    public void playTwoCleared() {
        playSoundEffect(fileTwoCleared);
    }

    // EFFECTS: plays sound effect for clearing three lines
    public void playThreeCleared() {
        playSoundEffect(fileThreeCleared);
    }

    // EFFECTS: plays sound effect for clearing four lines
    public void playFourCleared() {
        playSoundEffect(fileFourCleared);
    }

    // EFFECTS: plays sound effect for starting the game/level
    public void playGameStart() {
        playSoundEffect(fileGameStart);
    }

    // EFFECTS: plays sound effect for losing the game
    public void playGameOver() {
        playSoundEffect(fileGameOver);
    }

    // EFFECTS: plays sound effect for clearing one line
    public void playButtonClick() {
        playSoundEffect(fileButtonClick);
    }

    // EFFECTS: plays sound effect for clearing one line
    public void playLevelUp() {
        playSoundEffect(fileLevelUp);
    }

    // EFFECTS: plays a sound effect found at file location file
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
