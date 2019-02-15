package ca.ubc.cs.cpsc210.audio;

import javax.sound.sampled.*;
import java.io.File;

public class ThemeMusic {

    /**
     * Constants
     */
    private String themeSong = "src/main/ca/ubc/cs/cpsc210/resources/audio/tetrisTheme.wav";

    private File fileName = new File(themeSong);
    private AudioInputStream stream;
    private AudioFormat format;
    private DataLine.Info info;
    private Clip clip;

    /**
     * Constructor
     */
    public ThemeMusic() {

    }

    /**
     * Methods
     */
    public void play() {

        try {
            stream = AudioSystem.getAudioInputStream(fileName);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Could not play theme music");
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
