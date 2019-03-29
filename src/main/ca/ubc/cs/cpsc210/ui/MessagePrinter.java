package ca.ubc.cs.cpsc210.ui;

import java.util.Observable;
import java.util.Observer;

import static ca.ubc.cs.cpsc210.model.Tetris.*;

public class MessagePrinter implements Observer {

    /**
     *  Constants
     */
    public static final String LEVEL_UP_MSG = "Level up!";
    public static final String GAME_OVER_MSG = "Thanks for playing!";

    /**
     *  Methods
     */
    // EFFECTS:  prints a message to the command window
    private void print(String msg) {
        System.out.println(msg);
    }

    // EFFECTS: calls print(event) if we want to print a message when a game event happens
    @Override
    public void update(Observable o, Object event) {
        switch ((String) event) {
            case EVENT_GAME_OVER:
                print(GAME_OVER_MSG);
                break;
            case EVENT_LEVEL_UP:
                print(LEVEL_UP_MSG);
                break;
            default:
                break;
        }
    }
}
