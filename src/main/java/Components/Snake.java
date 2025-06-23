package Components;

import javax.swing.*;
import java.awt.*;

public class Snake extends JLabel {

    public static final String HUp = "assets/headUp.png", HLeft = "assets/headLeft.png", HDown = "assets/headDown.png", HRight = "assets/headRight.png", HOR = "assets/H.png", VER = "assets/V.png", UPL = "assets/UL.png", UPR = "assets/UR.png", DOWNL = "assets/DL.png", DOWNR = "assets/DR.png", TRight = "assets/TR.png", TLeft = "assets/TL.png", TUp = "assets/TU.png", TDown = "assets/TD.png";
    public static final int UP = 1, Right = 2, DOWN = 3, Left = 4;
    public static int length = 0;
    public int coor;
    public int index;
    public static int dir;   // up(1) right(2) down(3) left(4)
    public int path;
    transient Image scaledImg;

    public Snake() {
        index = length++;
        dir = Right;
    }

    @Override
    public String toString() {
        return "Snake{" + "coor=" + coor + ", index=" + index + ", path=" + path + ", scaledImg=" + scaledImg + '}';
    }

}
