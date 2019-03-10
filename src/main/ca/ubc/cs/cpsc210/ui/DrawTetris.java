package ca.ubc.cs.cpsc210.ui;

import ca.ubc.cs.cpsc210.audio.Music;
import ca.ubc.cs.cpsc210.model.Tetris;
import ca.ubc.cs.cpsc210.ui.buttons.TetrisButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ca.ubc.cs.cpsc210.model.Tetris.*;

public class DrawTetris {

    /**
     *  Declarations
     */
    private static Tetris tetris;
    private static Music tetrisMusic;
    private static final String highScoreFileName = "highscore";
    private static DrawTetris drawTetris;
    private static Render render;

    /**
     *  Variables
     */
    private int ticks = 0;

    public DrawTetris() {


    }


}
