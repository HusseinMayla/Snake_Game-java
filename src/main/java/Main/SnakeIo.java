package Main;

import Exceptions.GameLossException;
import Exceptions.EatAppleException;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;

public class SnakeIo {

    public boolean newGame;
    static MainMenu menu;
    static PlayMenu Pmenu;
    static ScoreMenu Smenu;
    static boolean exit = false;

    public static void main(String[] args) {
        menu = new MainMenu("SNAKE io");
        Smenu = new ScoreMenu("SNAKE io");
    }

    public static void startGame() throws HeadlessException, GameLossException, EatAppleException {

        File file = new File("score.txt");
        int b = 0;
        if (file.exists() && file.isFile()) {
            if (file.length() != 0) {
                Scanner in;
                try {
                    in = new Scanner(file);
                    b = in.nextInt();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(SnakeIo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (!exit) {
            Pmenu = new PlayMenu("Snake io", b);
        } else {
            Pmenu.closePause();
        }
        menu.setVisible(false);
        Pmenu.setVisible(true);
    }

    public static void gameExit() {
        Pmenu.setVisible(false);
        menu.setVisible(true);
    }

    public static void GameEnd(int x) {
        exit = false;
        Smenu.updateScores(x);
        Pmenu.setVisible(false);
        Smenu.setVisible(true);
    }

    public static void restart(int x) {
        try {
            exit = false;
            Smenu.updateScores(x);
            resetGame(Smenu.bScore);
            startGame();
        } catch (HeadlessException | GameLossException | EatAppleException ex) {
            Logger.getLogger(SnakeIo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void resetGame(int x) {
        Pmenu.BscoreLabel.setText(" X " + x);
        Smenu.setVisible(false);
        menu.setVisible(true);
    }

}
