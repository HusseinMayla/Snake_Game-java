package Components;

import java.awt.*;
import javax.swing.*;

public class Apple extends JLabel {

    transient ImageIcon icon;
    transient Image scaledImg;
    public int coor;

    public Apple() {
        super();
        icon = new ImageIcon("assets/images.png");
        scaledImg = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImg);
        setIcon(icon);
    }
}
