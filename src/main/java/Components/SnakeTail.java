package Components;

import java.awt.*;
import javax.swing.*;

public class SnakeTail extends Snake {

    public transient ImageIcon R, L, U, D;

    public SnakeTail() {
        super();
        R = new ImageIcon(Snake.TRight);
        scaledImg = R.getImage().getScaledInstance(33, 26, Image.SCALE_SMOOTH);
        R = new ImageIcon(scaledImg);
        L = new ImageIcon(Snake.TLeft);
        scaledImg = L.getImage().getScaledInstance(30, 28, Image.SCALE_SMOOTH);
        L = new ImageIcon(scaledImg);
        U = new ImageIcon(Snake.TUp);
        scaledImg = U.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        U = new ImageIcon(scaledImg);
        D = new ImageIcon(Snake.TDown);
        scaledImg = D.getImage().getScaledInstance(29, 31, Image.SCALE_SMOOTH);
        D = new ImageIcon(scaledImg);
        super.coor = 45;
        setIcon(R);
    }
}
