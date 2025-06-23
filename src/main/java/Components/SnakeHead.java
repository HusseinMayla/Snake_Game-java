package Components;

import java.awt.*;
import javax.swing.*;

public class SnakeHead extends Snake {

    public transient ImageIcon R, L, U, D;

    public SnakeHead() {
        super();
        index = 0;
        coor = 48;
        R = new ImageIcon(Snake.HRight);
        scaledImg = R.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        R = new ImageIcon(scaledImg);
        L = new ImageIcon(Snake.HLeft);
        scaledImg = L.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        L = new ImageIcon(scaledImg);
        U = new ImageIcon(Snake.HUp);
        scaledImg = U.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        U = new ImageIcon(scaledImg);
        D = new ImageIcon(Snake.HDown);
        scaledImg = D.getImage().getScaledInstance(32, 31, Image.SCALE_SMOOTH);
        D = new ImageIcon(scaledImg);
        setIcon(R);
        path = Right;
    }

}
