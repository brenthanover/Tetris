package ca.ubc.cs.cpsc210.audio;

import javax.sound.sampled.*;
import java.io.File;

public class Music {

    /**
     * Constants
     */
    private String themeSong = "src/main/ca/ubc/cs/cpsc210/resources/audio/songs/tetrisTheme.wav";
    private String shrekSong = "src/main/ca/ubc/cs/cpsc210/resources/audio/songs/shrek.wav";
    private String saxSong = "src/main/ca/ubc/cs/cpsc210/resources/audio/songs/sax.wav";

    private File themeFileName = new File(themeSong);
    private File shrekFileName = new File(shrekSong);
    private File saxFileName = new File(saxSong);
    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;

    /**
     * Constructor
     */
    public Music() {

    }

    /**
     * Methods
     */
    public void playTetrisTheme() {

        try {
            stream = AudioSystem.getAudioInputStream(themeFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme theme tetrisMusic");
        }
    }

    public void shrek() {

        try {
            stream = AudioSystem.getAudioInputStream(shrekFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme theme tetrisMusic");
        }
    }

    public void sax() {

        try {
            stream = AudioSystem.getAudioInputStream(saxFileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not playTetrisTheme theme tetrisMusic");
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
