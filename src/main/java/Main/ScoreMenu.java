package Main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class ScoreMenu extends JFrame {

    PrintWriter out;
    Scanner in;
    ImageIcon icApple, icTrophy;
    JButton continueBtn;
    JPanel scorePanel, appleScore, bestScore;
    JLabel scoreL, bestL;
    int cscore, bScore;

    public ScoreMenu(String title) throws HeadlessException {
        super(title);
        continueBtn = new JButton("Continue");
        continueBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        continueBtn.setPreferredSize(new Dimension(100, 25));
        setLayout(new GridLayout(2, 1));
        scorePanel = new JPanel(new GridLayout(1, 2));
        scorePanel.setPreferredSize(new Dimension(200, 40));
        cscore = 0;
        bScore = 0;
        appleScore = new JPanel(new FlowLayout(FlowLayout.LEFT));
        icApple = new ImageIcon("assets/images.png");
        Image scaledImg = icApple.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
        icApple = new ImageIcon(scaledImg);
        scoreL = new JLabel("X " + cscore);
        scoreL.setIcon(icApple);
        scoreL.setFont(new Font("Arial", Font.BOLD, 20));
        appleScore.add(scoreL);
        scorePanel.add(appleScore);
        appleScore.setPreferredSize(new Dimension(50, 40));

        bestScore = new JPanel(new FlowLayout(FlowLayout.LEFT));
        icTrophy = new ImageIcon("assets/trophy.png");
        Image scaledImg2 = icTrophy.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
        icTrophy = new ImageIcon(scaledImg2);
        bestL = new JLabel(" X " + bScore);
        bestL.setIcon(icTrophy);
        bestL.setFont(new Font("Arial", Font.BOLD, 20));
        bestScore.add(bestL);
        scorePanel.add(bestScore);
        bestScore.setPreferredSize(new Dimension(50, 40));
        add(scorePanel);
        JPanel temp = new JPanel();
        temp.add(continueBtn);
        add(temp);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setSize(new Dimension(600, 600));
        setLocation(700, 300);

    }

    void updateScores(int x) {
        File file = new File("score.txt");
        try {
            cscore = x;
            if (file.exists() && file.isFile()) {
                if (file.length() != 0) {
                    in = new Scanner(file);
                    bScore = in.nextInt();
                    in.close();
                }
                if (cscore > bScore) {
                    bScore = cscore;
                }
                out = new PrintWriter(file);
                out.println(bScore);
                out.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("file not found.");
        }

        scoreL.setText(" X " + cscore);
        bestL.setText(" X " + bScore);

        ActionListener listener = new OKListener();
        continueBtn.addActionListener(listener);
    }

    class OKListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            SnakeIo.resetGame(bScore);
        }

    }
}
