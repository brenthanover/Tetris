package ca.ubc.cs.cpsc210.audio;

import javax.sound.sampled.*;
import java.io.File;

public class Music {
    /**
     * Constants
     */
    private String musicDirectory = "src/main/ca/ubc/cs/cpsc210/resources/audio/songs/";
    private String themeFilename = "tetrisTheme.wav";
    private String shrekFilename = "shrek.wav";
    private String saxFilename = "sax.wav";

    /**
     *  Declarations
     */
    private File tetrisFileName;
    private File shrekFileName;
    private File saxFileName;
    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;

    /**
     * Constructor
     */
    public Music() {
        tetrisFileName = new File(musicDirectory + themeFilename);
        shrekFileName = new File(musicDirectory + shrekFilename);
        saxFileName = new File(musicDirectory + saxFilename);
    }

    /**
     * Methods
     */
    public void playTetrisTheme() {
        try {
            stream = AudioSystem.getAudioInputStream(tetrisFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not play Tetris theme");
        }
    }

    public void playShrekTheme() {
        try {
            stream = AudioSystem.getAudioInputStream(shrekFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not play Shrek theme");
        }
    }

    public void playSaxTheme() {
        try {
            stream = AudioSystem.getAudioInputStream(saxFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not play Sax theme");
        }
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
