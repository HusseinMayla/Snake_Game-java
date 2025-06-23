package Components;

import java.awt.*;
import javax.swing.*;

public class SnakeBody extends Snake {

    public transient ImageIcon H, V, UR, UL, DR, DL;

    public SnakeBody() {
        super();
        H = new ImageIcon(Snake.HOR);
        scaledImg = H.getImage().getScaledInstance(32, 30, Image.SCALE_SMOOTH);
        H = new ImageIcon(scaledImg);

        V = new ImageIcon(Snake.VER);
        scaledImg = V.getImage().getScaledInstance(30, 33, Image.SCALE_SMOOTH);
        V = new ImageIcon(scaledImg);

        UR = new ImageIcon(Snake.UPR);
        scaledImg = UR.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        UR = new ImageIcon(scaledImg);

        UL = new ImageIcon(Snake.UPL);
        scaledImg = UL.getImage().getScaledInstance(31, 31, Image.SCALE_SMOOTH);
        UL = new ImageIcon(scaledImg);

        DR = new ImageIcon(Snake.DOWNR);
        scaledImg = DR.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        DR = new ImageIcon(scaledImg);

        DL = new ImageIcon(Snake.DOWNL);
        scaledImg = DL.getImage().getScaledInstance(28, 30, Image.SCALE_SMOOTH);
        DL = new ImageIcon(scaledImg);

        setIcon(H);
    }

    public SnakeBody(int c) {
        this();
        super.coor = c;
    }
}
