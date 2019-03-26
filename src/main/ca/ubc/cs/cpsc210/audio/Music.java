package ca.ubc.cs.cpsc210.audio;

import javax.sound.sampled.*;
import java.io.File;

public class Music {
    /**
     * Constants
     */
    public static final String musicDirectory = "src/main/ca/ubc/cs/cpsc210/resources/audio/songs/";
    public static final String themeFilename = "tetrisTheme.wav";
    public static final String shrekFilename = "shrek.wav";
    public static final String saxFilename = "sax.wav";
    public static final File tetrisFileName = new File(musicDirectory + themeFilename);
    public static final File shrekFileName = new File(musicDirectory + shrekFilename);
    public static final File saxFileName = new File(musicDirectory + saxFilename);


    /**
     *  Declarations
     */
    private Clip clip;

    /**
     * Constructor
     */
    public Music() {
    }

    /**
     * Methods
     */
    private void playSong(File tetrisFileName, String s) {
        try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(tetrisFileName);
            AudioFormat format = stream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println(s);
        }
    }

    public void playTetrisTheme() {
        playSong(tetrisFileName, "Could not play Tetris theme");
    }

    public void playShrekTheme() {
        playSong(shrekFileName, "Could not play Shrek theme");
    }

    public void playSaxTheme() {
        playSong(saxFileName, "Could not play Sax theme");
    }

    public void stop() {
        clip.stop();
    }

    public void unpause() {
        clip.start();
    }


    // Tutorial citation:
    // how to play .wav files in java
    // https://stackoverflow.com/questions/2416935/how-to-play-wav-files-with-java

}
