package ca.ubc.cs.cpsc210.parsers.exceptions;


public class MissingScoreException extends Exception {


    public MissingScoreException() {
        super();
    }

    public MissingScoreException(String fileName) {
        super("Cannot read high score from file: " + fileName + ". High score set to 0");
    }
}
