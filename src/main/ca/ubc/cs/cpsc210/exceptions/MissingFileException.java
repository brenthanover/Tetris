package ca.ubc.cs.cpsc210.exceptions;

public class MissingFileException extends Exception {

    public MissingFileException() {
    }

    public MissingFileException(String msg) {
        super(msg);
    }

}
