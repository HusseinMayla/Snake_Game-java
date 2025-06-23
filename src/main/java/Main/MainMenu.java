package Main;

import Exceptions.GameLossException;
import Exceptions.EatAppleException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;

public class MainMenu extends JFrame {

    JButton playBtn;
    JLabel Ltitle;
    JPanel p;

    public MainMenu(String title) throws HeadlessException {
        super(title);
        Image ii = Toolkit.getDefaultToolkit().getImage("assets/images.jpeg");
        setLayout(new GridLayout());

        JPanel bgPanel = new JPanel(new GridLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(ii, 0, 0, getWidth(), getHeight(), this);
            }
        };

        setContentPane(bgPanel);
        p = new JPanel(new GridLayout(2, 1));
        p.setOpaque(false);
        bgPanel.add(p);
        playBtn = new JButton("Play");
        Ltitle = new JLabel("SNAKE io");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        Ltitle.setFont(new Font("Arial", Font.BOLD, 24));
        Ltitle.setPreferredSize(new Dimension(150, 30));
        playBtn.setFont(new Font("Arial", Font.PLAIN, 20));
        playBtn.setPreferredSize(new Dimension(115, 40));

        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.add(Ltitle);
        labelPanel.setOpaque(false);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(playBtn);
        buttonPanel.setOpaque(false);
        p.add(labelPanel);
        p.add(buttonPanel);
        setLocation(700, 300);
        ActionListener listener = new OKListener();
        playBtn.addActionListener(listener);
        setVisible(true);

    }

    class OKListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                SnakeIo.startGame();
            } catch (HeadlessException | GameLossException | EatAppleException ex) {
                Logger.getLogger(MainMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
