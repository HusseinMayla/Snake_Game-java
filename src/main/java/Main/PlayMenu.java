package Main;

import Exceptions.GameWonException;
import Exceptions.GameLossException;
import Exceptions.EatAppleException;
import Components.SnakeTail;
import Components.SnakeHead;
import Components.Snake;
import Components.SnakeBody;
import Components.Apple;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.logging.*;
import javax.swing.*;

public class PlayMenu extends JFrame implements Serializable {

    private boolean test = false;
    public static ArrayList<Snake> Snakes = new ArrayList<>();
    public static ArrayList<SnakeBody> SnakeBodies = new ArrayList<>();
    public ArrayList<Snake> Snakess = new ArrayList<>();
    public ArrayList<SnakeBody> SnakeBodiess = new ArrayList<>();
    public static final Color ctop = new Color(74, 117, 44);
    public static final Color cc = new Color(87, 138, 52);
    public static final Color co = new Color(170, 215, 81);
    public static final Color ce = new Color(162, 209, 73);
    private JPanel pauseMenu;
    Apple a1;
    JPanel[] cells;
    JPanel main, c, top, pG;
    JPanel appleScore, bestScore, scorePanel;
    BorderLayout bL1;
    FlowLayout flowLeft;
    GridLayout grid;
    int score;
    int bestScoreInt;
    JLabel AscoreLabel, BscoreLabel;
    transient ImageIcon icApple, icTrophy, pauseIcon;
    transient Image scaledImg, scaledImg2, PauseImg;
    JLabel pauseLabel;
    JLayeredPane layeredPane;
    boolean startGame, disableControl, paused, lost = false;
    Timer moveSnakeLoop;
    int dirKey;
    JButton pauseBtn, resumeBtn, restartBtn, exitBtn;

    public PlayMenu(String title, int b) throws HeadlessException, GameLossException, EatAppleException {
        super(title);
        Snakes = new ArrayList<>();
        SnakeBodies = new ArrayList<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(450, 450));
        setSize(new Dimension(600, 600));
        setLocation(700, 300);

        main = new JPanel();
        setSizeFixed(main, 450, 450);
        bL1 = new BorderLayout(0, 0);
        add(main);
        main.setLayout(bL1);
        flowLeft = new FlowLayout(FlowLayout.LEFT);
        top = new JPanel();
        top.setBackground(ctop);

        scorePanel = new JPanel(new GridLayout(1, 2));
        scorePanel.setPreferredSize(new Dimension(200, 40));
        score = 0;
        bestScoreInt = b;
        appleScore = new JPanel(flowLeft);
        icApple = new ImageIcon("assets/images.png");
        scaledImg = icApple.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
        icApple = new ImageIcon(scaledImg);
        AscoreLabel = new JLabel("X " + score);
        AscoreLabel.setIcon(icApple);
        AscoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        appleScore.add(AscoreLabel);
        scorePanel.add(appleScore);
        appleScore.setPreferredSize(new Dimension(50, 40));
        appleScore.setBackground(ctop);

        bestScore = new JPanel(flowLeft);
        icTrophy = new ImageIcon("assets/trophy.png");
        scaledImg2 = icTrophy.getImage().getScaledInstance(30, 35, Image.SCALE_SMOOTH);
        icTrophy = new ImageIcon(scaledImg2);
        BscoreLabel = new JLabel(" X " + bestScoreInt);
        BscoreLabel.setIcon(icTrophy);
        BscoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        bestScore.add(BscoreLabel);
        scorePanel.add(bestScore);
        bestScore.setPreferredSize(new Dimension(50, 40));
        bestScore.setBackground(ctop);

        top.add(scorePanel);

        top.add(Box.createRigidArea(new Dimension(100, 0)));

        pauseBtn = new JButton();
        pauseBtn.setBackground(ctop);
        pauseBtn.setFocusPainted(false);
        pauseBtn.setBorder(null);
        pauseIcon = new ImageIcon("assets/pausea.png");
        PauseImg = pauseIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        pauseIcon = new ImageIcon(PauseImg);
        pauseBtn.setIcon(pauseIcon);
        pauseBtn.setFont(new Font("Arial", Font.BOLD, 20));
        pauseBtn.setPreferredSize(new Dimension(40, 40));

        top.add(pauseBtn);

        main.add(top, BorderLayout.NORTH);

        c = new JPanel();
        main.add(c, BorderLayout.CENTER);
        c.setSize(new Dimension(450, 300));
        c.setBackground(cc);
        c.setLayout(new GridBagLayout());
        grid = new GridLayout(9, 11);
        pG = new JPanel(grid);
        pG.setPreferredSize(new Dimension(396, 324));
        pG.setBackground(Color.DARK_GRAY);
        pG.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        c.add(pG);
        main.setBackground(Color.BLACK);

        cells = new JPanel[(grid.getColumns() * grid.getRows())];
        for (int i = 0; i < (grid.getColumns() * grid.getRows()); i++) {
            JPanel cell = new JPanel(new GridLayout(1, 1));
            Color color = (i % 2 == 0) ? ce : co;
            cell.setBackground(color);
            cells[i] = cell;
            pG.add(cell);
        }
        for (int i = 0; i < 95; i++) {
            SnakeBody sb = new SnakeBody();
            SnakeBodies.add(sb);
        }
        SnakeHead sh1 = new SnakeHead();
        Snakes.add(sh1);
        cells[48].add(sh1);
        SnakeBody sb1 = new SnakeBody(47);
        sb1.path = sb1.Right;
        sb1.index = 1;
        Snakes.addLast(sb1);
        cells[47].add(sb1);
        SnakeBody sb2 = new SnakeBody(46);
        sb2.path = sb2.Right;
        sb2.index = 2;
        Snakes.addLast(sb2);
        cells[46].add(sb2);
        SnakeTail st1 = new SnakeTail();
        st1.path = st1.Right;
        st1.index = 3;
        Snakes.addLast(st1);
        cells[45].add(st1);

        a1 = new Apple();
        cells[52].add(a1);
        layeredPane = getLayeredPane();
        pauseLabel = new JLabel("<html>Use arrow keys to start<br>and navigate the snake.</html>");
        pauseLabel.setFont(new Font("Arial", Font.BOLD, 25));
        pauseLabel.setForeground(Color.WHITE);
        pauseLabel.setBounds(70, 50, 400, 100);
        layeredPane.add(pauseLabel, JLayeredPane.POPUP_LAYER);
        startGame = false;

        addKeyListener(new KeyPress());

        // Pause menu buttons
        resumeBtn = new JButton("Resume");
        restartBtn = new JButton("Restart");
        exitBtn = new JButton("Exit");

        pauseMenu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(new Color(0, 0, 0, 180));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        pauseMenu.setOpaque(false);
        pauseMenu.setLayout(new GridBagLayout());

        Box box = Box.createVerticalBox();
        box.add(resumeBtn);
        box.add(Box.createVerticalStrut(10));
        box.add(restartBtn);
        box.add(Box.createVerticalStrut(10));
        box.add(exitBtn);
        pauseMenu.add(box);

        layeredPane.setLayout(new BorderLayout());
        layeredPane.add(pauseMenu, BorderLayout.CENTER, JLayeredPane.DRAG_LAYER);
        pauseMenu.setVisible(false);

        // Button listeners
        pauseBtn.addActionListener(e -> openPause());
        restartBtn.addActionListener(e -> restart());
        resumeBtn.addActionListener(e -> closePause());
        exitBtn.addActionListener(e -> exit());
        setFocusable(true);
        requestFocusInWindow();
    }

    void openPause() {
        pauseMenu.setVisible(true);
        pauseMenu.setBounds(0, 0, getWidth() - 100, getHeight() - 100);
        if (startGame) {
            moveSnakeLoop.stop();
        }
        pauseMenu.setFocusable(true);
        disableControl = true;
        paused = true;
//        for (int i = 0; i < Snakes.size(); i++) {
//            System.out.println(Snakes.get(i).toString());  // for debugging
//        }
    }

    void closePause() {
        pauseMenu.setVisible(false);
        if (startGame) {
            moveSnakeLoop.start();
        }
        disableControl = false;
        pauseMenu.setFocusable(false);
        paused = false;

    }

    void restart() {
        if (!lost) {
            SnakeIo.gameExit();
            SnakeIo.GameEnd(score);
            if (startGame) {
                moveSnakeLoop.stop();
            }
            SnakeIo.restart(score);
        }
    }

    void exit() {
        if (!lost) {
            SnakeIo.exit = true;
            SnakeIo.gameExit();
            if (startGame) {
                moveSnakeLoop.stop();
            }
            startGame = false;
            pauseLabel.setText("press arrow keys to continue");
        }
    }

    public static void setSpacing(JComponent comp, int top, int left, int bottom, int right) {
        comp.setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));
    }

    public static void setSizeFixed(JComponent comp, int width, int height) {
        comp.setPreferredSize(new Dimension(width, height));
        comp.setMinimumSize(new Dimension(width, height));
        comp.setMaximumSize(new Dimension(width, height));
    }

    public void setSizeFixed(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));
    }

    public void eatApple() {
        Snake h = (SnakeHead) Snakes.get(0);
        cells[h.coor].remove(h);
        SnakeBody s = SnakeBodies.get(0);
        for (int i = 1; i < Snakes.size(); i++) {
            Snakes.get(i).index++;
        }
        s.index = 1;
        s.coor = h.coor;
        s.length++;
        s.path = h.path;
        Snakes.add(1, s);
        cells[s.coor].add(s);
        SnakeBodies.remove(0);
    }

    void regenerateApple(SnakeHead h) throws GameWonException {
        cells[h.coor].remove(0); // remove apple
        cells[h.coor].add(h);  // add head
        if (Snakes.size() >= 99) {
            throw new GameWonException("you cleared the game congrates!");
        } else {
            int r;
            boolean con;
            do {
                r = (int) (random() * 99);
                con = false;
                for (int i = 0; i < Snakes.size(); i++) {
                    if (r == Snakes.get(i).coor) {
                        con = true;
                        break;
                    }
                }
            } while (con);
            a1.coor = r;
            cells[r].add(a1);
        }
        score++;
        AscoreLabel.setText("X " + score);
    }

    public static void moveSnake() {
        Snakes.get(Snakes.size() - 1).path = Snakes.get(Snakes.size() - 3).path;
        for (int i = Snakes.size() - 2; i > 1; i--) {
            Snakes.get(i).path = Snakes.get(i - 1).path;
        }
        for (int i = Snakes.size() - 1; i > 0; i--) {
            Snakes.get(i).coor = Snakes.get(i - 1).coor;
        }
        Snakes.get(1).path = Snakes.get(0).path;
    }

    public void removeSnake() {
        for (int i = 0; i < Snakes.size(); i++) {
            cells[Snakes.get(i).coor].remove(Snakes.get(i));
        }
    }

    public void moveSnakeFinal() {
        for (int i = 0; i < Snakes.size(); i++) {
            cells[Snakes.get(i).coor].add(Snakes.get(i));
        }
    }

    public void CheckNextCell(int x) throws GameLossException, EatAppleException {

        JPanel comp = this.cells[Snakes.get(0).coor + x];
        if (comp.getComponentCount() > 0) {
            if (comp.getComponent(0) instanceof SnakeBody) {
                throw new GameLossException("Snake collided with a component, you lost.");
            } else if (comp.getComponent(0) instanceof Apple) {
                throw new EatAppleException("You ate an apple!!");
            }
        }
    }

    public void moveRight() throws GameLossException, GameWonException {
        if (!(Snakes.get(0).dir == Snake.Left)) {
            if ((Snakes.get(0).coor + 1) % 11 == 0) {
                throw new GameLossException("Snake collided with a component, you lost.");
            }
            try {
                CheckNextCell(1);
                removeSnake();
                moveSnake();
                Snakes.get(0).coor++;
            } catch (EatAppleException ex) {
                eatApple();
                Snakes.get(0).coor++;
                regenerateApple((SnakeHead) Snakes.get(0));
            }
            Snakes.get(1).path = Snakes.get(0).path;
            Snakes.get(0).path = Snakes.get(0).Right;
            Snakes.get(0).dir = Snakes.get(0).Right;
            checkPath();
            moveSnakeFinal();
            revalidate();
            repaint();
        } else {
            moveLeft();
        }
    }

    public void moveLeft() throws GameLossException, GameWonException {
        if (!(Snakes.get(0).dir == Snake.Right)) {
            if ((Snakes.get(0).coor) % 11 == 0) {
                throw new GameLossException("Snake collided with a component, you lost.");
            }
            try {
                CheckNextCell(-1);
                removeSnake();
                moveSnake();
                Snakes.get(0).coor--;
            } catch (EatAppleException ex) {
                eatApple();
                Snakes.get(0).coor--;
                regenerateApple((SnakeHead) Snakes.get(0));
            }

            Snakes.get(0).path = Snakes.get(0).Left;
            Snakes.get(0).dir = Snakes.get(0).Left;
            checkPath();
            moveSnakeFinal();
            revalidate();
            repaint();
        } else {
            moveRight();
        }
    }

    public void moveUp() throws GameLossException, GameWonException {
        if (!(Snakes.get(0).dir == Snake.DOWN)) {
            if ((Snakes.get(0).coor) < 11) {
                throw new GameLossException("Snake collided with a component, you lost.");
            }
            try {
                CheckNextCell(-11);
                removeSnake();
                moveSnake();
                Snakes.get(0).coor -= 11;
            } catch (EatAppleException ex) {
                eatApple();
                Snakes.get(0).coor -= 11;
                regenerateApple((SnakeHead) Snakes.get(0));
            }
            Snakes.get(0).path = Snakes.get(0).UP;
            Snakes.get(0).dir = Snakes.get(0).UP;
            checkPath();
            moveSnakeFinal();
            revalidate();
            repaint();
        } else {
            moveDown();
        }
    }

    public void moveDown() throws GameLossException, GameWonException {
        if (!(Snakes.get(0).dir == Snake.UP)) {
            if ((Snakes.get(0).coor + 11) >= 99) {
                throw new GameLossException("Snake collided with a component, you lost.");
            }
            try {
                CheckNextCell(11);
                removeSnake();
                moveSnake();
                Snakes.get(0).coor += 11;
            } catch (EatAppleException ex) {
                eatApple();
                Snakes.get(0).coor += 11;
                regenerateApple((SnakeHead) Snakes.get(0));
            }
            Snakes.get(0).path = Snakes.get(0).DOWN;
            Snakes.get(0).dir = Snakes.get(0).DOWN;
            checkPath();
            moveSnakeFinal();
            revalidate();
            repaint();
        } else {
            moveUp();
        }
    }

    public static void checkPath() {
        SnakeHead h = (SnakeHead) Snakes.get(0);
        if (h.dir == h.Right) {
            h.setIcon(h.R);
        } else if (h.dir == h.Left) {
            h.setIcon(h.L);
        } else if (h.dir == h.UP) {
            h.setIcon(h.U);
        } else if (h.dir == h.DOWN) {
            h.setIcon(h.D);
        }
        for (int i = Snakes.size() - 2; i > 1; i--) {
            SnakeBody a = (SnakeBody) Snakes.get(i);
            SnakeBody b = (SnakeBody) Snakes.get(i - 1);
            if (a.path == a.Right && b.path == b.DOWN || a.path == a.UP && b.path == b.Left) {
                a.setIcon(a.UL);
            } else if (a.path == a.Right && b.path == b.UP || a.path == a.DOWN && b.path == b.Left) {
                a.setIcon(a.DL);
            } else if (a.path == a.DOWN && b.path == b.Right || a.path == a.Left && b.path == b.UP) {
                a.setIcon(a.DR);
            } else if (a.path == a.UP && b.path == b.Right || a.path == a.Left && b.path == b.DOWN) {
                a.setIcon(a.UR);
            } else if (a.path == b.path) {
                if (a.path == a.Right || a.path == a.Left) {
                    a.setIcon(a.H);
                } else if (a.path == a.UP || a.path == a.DOWN) {
                    a.setIcon(a.V);
                }
            }
        }
        SnakeBody a = (SnakeBody) Snakes.get(1);
        SnakeHead b = (SnakeHead) Snakes.get(0);
        if (a.path == a.Right && b.path == b.DOWN || a.path == a.UP && b.path == b.Left) {
            a.setIcon(a.UL);
        } else if (a.path == a.Right && b.path == b.UP || a.path == a.DOWN && b.path == b.Left) {
            a.setIcon(a.DL);
        } else if (a.path == a.DOWN && b.path == b.Right || a.path == a.Left && b.path == b.UP) {
            a.setIcon(a.DR);
        } else if (a.path == a.UP && b.path == b.Right || a.path == a.Left && b.path == b.DOWN) {
            a.setIcon(a.UR);
        } else if (a.path == b.path) {
            if (a.path == a.Right || a.path == a.Left) {
                a.setIcon(a.H);
            } else if (a.path == a.UP || a.path == a.DOWN) {
                a.setIcon(a.V);
            }
        }
        SnakeTail l = (SnakeTail) Snakes.get(Snakes.size() - 1);
        if (l.path == l.Right) {
            l.setIcon(l.R);
        } else if (l.path == l.Left) {
            l.setIcon(l.L);
        } else if (l.path == l.UP) {
            l.setIcon(l.U);
        } else if (l.path == l.DOWN) {
            l.setIcon(l.D);
        }
    }
boolean ttt=true;

    void cheat() {
        if (ttt) {
            moveUp(4);
        moveLeft(4);
        ttt=false;
        }
        
//        while (Snakes.size() < 98) {
        moveDown(1);
        for (int i = 0; i < 4; i++) {
            moveDown(7);
            moveRight(1);
            moveUp(7);
            moveRight(1);
        }
        moveDown(7);
        moveRight(2);
        for (int i = 0; i < 3; i++) {
            moveUp(1);
            moveLeft(1);
            moveUp(1);
            moveRight(1);
        }
        moveUp(1);
        moveLeft(1);
        moveUp(1);
        moveLeft(9);
//        }
//        moveDown(1);
//        for (int i = 0; i < 5; i++) {
//            moveDown(7);
//            moveRight(1);
//            moveUp(7);
//            moveRight(1);
//        }
//        moveDown(7);
    }
    void cheat2() {
        if (ttt) {
            moveUp(4);
        moveLeft(4);
        ttt=false;
        }
        
//        while (Snakes.size() < 98) {
        moveDown(1);
        for (int i = 0; i < 4; i++) {
            moveDown(7);
            moveRight(1);
            moveUp(7);
            moveRight(1);
        }
        moveDown(7);
        moveRight(2);
        for (int i = 0; i < 3; i++) {
            moveUp(1);
            moveLeft(1);
            moveUp(1);
            moveRight(1);
        }
        moveUp(1);
        moveUp(1);
                moveLeft(1);
        moveLeft(9);
//        }
//        moveDown(1);
//        for (int i = 0; i < 5; i++) {
//            moveDown(7);
//            moveRight(1);
//            moveUp(7);
//            moveRight(1);
//        }
//        moveDown(7);
    }

    void moveUp(int ii) {
        for (int i = 0; i < ii; i++) {

            try {
                moveUp();  ///////////////////////////////////////////////////////////////////////////
            } catch (GameLossException ex) {

                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Lost");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
                moveSnakeLoop.stop();

            } catch (GameWonException ex) {

                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Wonn Congrates for clearing the Game!!");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
            }

        }
    }

    void moveDown(int ii) {
        for (int i = 0; i < ii; i++) {

            try {
                moveDown();  ///////////////////////////////////////////////////////////////////////////
            } catch (GameLossException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Lost");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
                moveSnakeLoop.stop();

            } catch (GameWonException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Wonn Congrates for clearing the Game!!");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
            }

        }
    }

    void moveRight(int ii) {
        for (int i = 0; i < ii; i++) {

            try {
                moveRight();  ///////////////////////////////////////////////////////////////////////////
            } catch (GameLossException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Lost");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
                moveSnakeLoop.stop();

            } catch (GameWonException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Wonn Congrates for clearing the Game!!");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
            }

        }
    }

    void moveLeft(int ii) {
        for (int i = 0; i < ii; i++) {

            try {
                moveLeft();  ///////////////////////////////////////////////////////////////////////////
            } catch (GameLossException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Lost");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
                moveSnakeLoop.stop();

            } catch (GameWonException ex) {
                lost = true;
                disableControl = true;
                startGame = false;
                pauseLabel.setText("You Wonn Congrates for clearing the Game!!");
                Timer t = new Timer(3000, null);
                t.addActionListener(eee -> {
                    pauseLabel.setText("");
                    SnakeIo.GameEnd(score);
                    t.stop();
                });
                t.setRepeats(false);
                t.start();
            }

        }
    }

    void startSGame() {
        lost = false;
        pauseLabel.setText("");
        moveSnakeLoop = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (dirKey == Snakes.get(0).Right) {
                        moveRight();
                    } else if (dirKey == Snakes.get(0).Left) {
                        moveLeft();
                    } else if (dirKey == Snakes.get(0).UP) {
                        moveUp();
                    } else if (dirKey == Snakes.get(0).DOWN) {
                        moveDown();
                    }
                } catch (GameLossException ex) {
                    lost = true;
                    disableControl = true;
                    startGame = false;
                    pauseLabel.setText("You Lost");
                    Timer t = new Timer(3000, null);
                    t.addActionListener(ee -> {
                        pauseLabel.setText("");
                        SnakeIo.GameEnd(score);
                        t.stop();
                    });
                    t.setRepeats(false);
                    t.start();
                    moveSnakeLoop.stop();

                } catch (GameWonException ex) {
                    lost = true;
                    disableControl = true;
                    startGame = false;
                    pauseLabel.setText("You Wonn Congrates for clearing the Game!!");
                    Timer t = new Timer(3000, null);
                    t.addActionListener(ee -> {
                        pauseLabel.setText("");
                        SnakeIo.GameEnd(score);
                        t.stop();
                    });
                    t.setRepeats(false);
                    t.start();
                }
            }
        });
        moveSnakeLoop.start();
    }

    public class KeyPress implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            int c = e.getKeyCode();
            System.out.println(e + ", " + c);
            if (c == 27) {
                if (paused) {
                    closePause();
                } else {
                    openPause();
                }

            }
            if (disableControl == false) {
                if (c == 37) {  //Left key
                    if (test == true) {
                        try {
                            moveLeft();
                        } catch (GameLossException ex) {

                        } catch (GameWonException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (startGame) {
                        dirKey = Snakes.get(0).Left;
                    }
                }
                if (c == 89) {//  y
//                    disableControl = false;
                    cheat();
                }
                if (c == 84) {//  t
//                    disableControl = false;
                    cheat2();
                }
                if (c == 39) {
                    if (test) {
                        try {  //Right key
                            moveRight();
                        } catch (GameLossException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GameWonException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (startGame) {
                        dirKey = Snakes.get(0).Right;
                    } else {
                        dirKey = Snakes.get(0).Right;
                        startGame = true;
                        startSGame();
                    }
                }
                if (c == 40) {  //down key
                    if (test == true) {
                        try {
                            moveDown();
                        } catch (GameLossException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GameWonException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (startGame) {
                        dirKey = Snakes.get(0).DOWN;
                    } else {
                        dirKey = Snakes.get(0).DOWN;
                        startGame = true;
                        startSGame();
                    }

                }
                if (c == 38) {  //Up key
                    if (test == true) {
                        try {
                            moveUp();
                        } catch (GameLossException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (GameWonException ex) {
                            Logger.getLogger(PlayMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (startGame) {
                        dirKey = Snakes.get(0).UP;
                    } else {
                        dirKey = Snakes.get(0).UP;
                        startGame = true;
                        startSGame();
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
