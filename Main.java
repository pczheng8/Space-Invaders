//By Peter Zheng


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Main extends JFrame implements WindowListener {

    public static final int alienShiftXValue = 10;
    public static final int alienShiftYValue = 5;
    public static final float alienShiftTimeStart = 600.0f;

    private static final int maxAliensHorizontal = 40;
    private static final int maxAliensVertical = 40;

    private int totalChancesToCreateAnAlienBullet = 650;

    private int winningScore = 50;  // you need a score of 50 to win at level 1

    private int gameOverMessageXValue = 20;
    private int gameOverMessageYValue = 300;

    private int level = 1;


    private int screenWidth = 500;
    private int indentWidth = 20;
    private int screenHeight = 600;

    private int startMouseDragX = -1;
    private int startMouseDragY = -1;

    private boolean gameOver = true;
    private boolean gamePaused = false;
    private boolean createAllObjects = true;

    private boolean doPositioning = true;

    // for buffering
    private BufferedImage back;

    // keys for movement of the ship and firing
    private boolean[] keys;

    // ***** declaration of JFrame variables *****

    // define a mainPanel for components
    JPanel mainPanel;

    // define JPanels for a BorderLayout
    JPanel northPanel;   // this is the message panel
    SouthPanel southPanel;
    JPanel westPanel;
    boolean showWestPanel = false;
    JPanel eastPanel;
    boolean showEastPanel = false;

    DrawPanel centerPanel;  // this will be the panel with all the drawing of MovableObjects

    // title in northPanel
    JLabel northText;

    // Buttons
    JButton runButton;
    JButton pauseButton;
    JButton stopButton;
    JButton exitButton;


    private MovableObject ship;

    private int shipDistanceFromBottomOfScreen = 95;
    private int barricadeDistanceFromBottomOfScreen = 150;

    private Image shipImage;

    private Image alienImage;

    private Image shipBulletImage;

    private Image alienBulletImage;

    private Image explosionImage;

    private Image barricadeImage;


    private MovableObject[][] aliens;

    private int numAliensHorizontal = 15;
    private int numAliensVertical = 6;

    private ArrayList<MovableObject> alienBullets;
    private ArrayList<MovableObject> shipBullets;
    private ArrayList<MovableObject> explosions;
    private ArrayList<MovableObject> barricades;

    private int score;
    private int wins = 0;
    private int losses = 0;

    private int pointsForHittingAnAlien = 5;

    private String showMessage = "Space Invaders by Peter Zheng";

    Thread runThread = null;
    int threadDelay = 20;


    public void turnOffAliens() {
        for (int r = 0; r < maxAliensVertical; r++) {
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setHidden(true);
            }
        }
    }


    public void turnOnVisibleAliens() {

        for (int r = 0; r < numAliensVertical; r++) {
            for (int c = 0; c < numAliensHorizontal; c++) {
                aliens[r][c].setY(aliens[r][0].getY());
                aliens[r][c].setHidden(false);
            }
        }
    }


    public void turnOnAliensShift() {
        for (int r = 0; r < maxAliensVertical; r++) {
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setShiftTimeStart(alienShiftTimeStart);
                aliens[r][c].setShiftXValue(alienShiftXValue);
                aliens[r][c].setShiftYValue(alienShiftYValue);
                aliens[r][c].setShiftTimeInterval(
                        threadDelay); // every 20 ms subtract from shift time
                aliens[r][c].setShiftTime(
                        alienShiftTimeStart); // amount of time left before the shift
            }
        }

    }

    public void resetAllAliens() {
        // we will only show some of them (what fits on the screen)
        // we will never get rid of them, but rather flip the hidden flag
        int x = 20;
        int y = 10;
        for (int r = 0; r < maxAliensVertical; r++) {
            x = 20;
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setX(x);
                aliens[r][c].setY(y);
                x = x + 55;
            }
            y = y + 55;
        }
    }


    public void resetAllAliensXValue() {
        int x = 20;
        for (int r = 0; r < maxAliensVertical; r++) {
            x = 20;
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setX(x);
                x = x + 55;
            }
        }
    }

    public void resetAllAliensYValue() {
        float y = aliens[0][0].getY();

        for (int r = 0; r < maxAliensVertical; r++) {
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setY(y);
            }
            y = y + 55;
        }
    }

    public void resetAllAliensXYValue() {
        int x = 20;
        float y = aliens[0][0].getY();
        for (int r = 0; r < maxAliensVertical; r++) {
            x = 20;
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c].setXY(x, y);
                x = x + 55;
            }
            y = y + 55;
        }
    }


    public void createAllAliens() {
        aliens = new Alien[maxAliensVertical][maxAliensHorizontal];

        int x = 20;
        int y = 10;
        for (int r = 0; r < maxAliensVertical; r++) {
            x = 20;
            for (int c = 0; c < maxAliensHorizontal; c++) {
                aliens[r][c] = new Alien(centerPanel, "alien.png", alienImage, x, y, 50, 50);
                x = x + 55;
            }
            y = y + 55;
        }
    }


    // this finds the number of aliens that are still left on
    //    the screen.
    // if this returns 0, then the aliens are all gone and
    //    we are a winner
    // So, this method will be called to determine that we are
    //    a winner
    public int numAliensLeft() {
        int count = 0;
        for (int r = 0; r < numAliensVertical; r++) {
            for (int c = 0; c < numAliensHorizontal; c++) {
                if (!aliens[r][c].getHidden()) {
                    count++;
                }
            }
        }

        return count;
    }


    public void resetAllAliensWithScreenResize() {

        numAliensHorizontal = (screenWidth - (2 * indentWidth)) / 55;
        numAliensVertical = (screenHeight - (int) (screenHeight * 3.0 / 5)) / 55;

        if (numAliensHorizontal > maxAliensHorizontal) {
            numAliensHorizontal = maxAliensHorizontal;
        }

        if (numAliensVertical > maxAliensVertical) {
            numAliensVertical = maxAliensVertical;
        }

        turnOffAliens();
        turnOnVisibleAliens();

        resetAllAliensXValue();
        resetAllAliensYValue();

        turnOnAliensShift();

    }


    private Image loadImage(String filename) {
        Image tempImage = null;

        if (filename != null && filename != "") {
            try {
                tempImage = Toolkit.getDefaultToolkit().getImage(filename);
                System.out.println("toolkit ok");
            } catch (Exception e) {
                System.out.println(
                        "error getImage with toolkit unable to load filename " + filename);
            }
        }

        return tempImage;
    }


    public void initMovableObjects() {

        numAliensHorizontal = (screenWidth - (2 * indentWidth)) / 55;
        numAliensVertical = (screenHeight - (int) (screenHeight * 3.0 / 5)) / 55;

        if (numAliensHorizontal > maxAliensHorizontal) {
            numAliensHorizontal = maxAliensHorizontal;
        }

        if (numAliensVertical > maxAliensVertical) {
            numAliensVertical = maxAliensVertical;
        }

        if (createAllObjects) {
            System.out.println("CREATING all objects");
            createAllObjects = false;

            shipImage = loadImage("ship.png");
            shipBulletImage = loadImage("bullet.png");
            alienImage = loadImage("alien.png");
            alienBulletImage = loadImage("bullet.png");
            explosionImage = loadImage("explosion.png");
            barricadeImage = loadImage("barricade.png");

            ship = new Ship(centerPanel, "ship.png", shipImage, screenWidth / 2 - 25,
                    screenHeight - shipDistanceFromBottomOfScreen, 50, 50);
            System.out.println("creating ship");

            createAllAliens();

            turnOnAliensShift();

            alienBullets = new ArrayList<MovableObject>();
            shipBullets = new ArrayList<MovableObject>();
            explosions = new ArrayList<MovableObject>();
            barricades = new ArrayList<MovableObject>();

            int xx = 100;
            int yy = (int) (ship.getY() - 100);
            for (int i = 0; i < 3; i++) {

                MovableObject barricade = new Barricade(centerPanel, "barricade.png",
                        barricadeImage, xx, yy, 50, 30);

                if (i == 0) {
                    xx = (int) (.2 * screenWidth);
                } else if (i == 1) {
                    xx = (int) (screenWidth / 2 - barricade.getWidth() / 2);
                } else {
                    xx = (int) (screenWidth - .2 * screenWidth - barricade.getWidth());
                }

                barricade.setX(xx);
                barricades.add(barricade);
                xx += 150;
            }
            System.out.println("barricades size=" + barricades.size());
        } else {
            resetAllAliens();

            alienBullets.clear();
            shipBullets.clear();
            explosions.clear();

            for (int i = 0; i < barricades.size(); i++) {
                barricades.get(i).setHidden(false);
                ((Barricade) barricades.get(i)).setNumHitsAllowed(3);
            }

            System.out.println("barricades size=" + barricades.size());


        }

        score = 0;

        barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;

        for (int i = 0; i < barricades.size(); i++) {
            barricades.get(i).setHidden(false);
        }

        turnOffAliens();

        turnOnVisibleAliens();

        ship.setHidden(false);

    }


    public void initialize() {
        back = null;
        gameOver = true;

        keys = new boolean[5];

        runButton = new JButton("Run");
        pauseButton = new JButton("Pause");
        stopButton = new JButton("Stop");
        exitButton = new JButton("Exit");

        mainPanel = new JPanel();

        northPanel = new JPanel();
        southPanel = new SouthPanel();
        southPanel.setListeners();

        if (showWestPanel) {
            westPanel = new JPanel();
        }
        if (showEastPanel) {
            eastPanel = new JPanel();
        }

        centerPanel = new DrawPanel();

        mainPanel.setLayout(new BorderLayout());
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        setCenterPanelColor(Color.black);
        northPanel.setBackground(new Color(115, 205, 255));
        southPanel.setBackground(new Color(115, 205, 255));
        if (showWestPanel) {
            westPanel.setBackground(new Color(115, 205, 255));
        }
        if (showEastPanel) {
            eastPanel.setBackground(new Color(115, 205, 255));
        }

        southPanel.add(runButton);
        southPanel.add(pauseButton);
        southPanel.add(stopButton);
        southPanel.add(exitButton);

        northText = new JLabel("Space Invaders by Peter Zheng");
        northPanel.add(northText);

        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        if (showEastPanel) {
            mainPanel.add(eastPanel, BorderLayout.EAST);
        }
        if (showWestPanel) {
            mainPanel.add(westPanel, BorderLayout.WEST);
        }
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        southPanel.setFocusable(true);
        southPanel.requestFocus();

        initMovableObjects();

        centerPanel.repaint();

        setContentPane(mainPanel);

        setVisible(true);

        southPanel.setFocusable(true);
        southPanel.requestFocus();
    }

    public Main() {
        setSize(screenWidth, screenHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Space Invaders Classic");
        addWindowListener(this);

        initialize();
    }

    public void setMessage(String message) {
        northText.setText(message);
    }

    public String getMessage() {
        return northText.getText();
    }

    public void setThreadDelay(int threadDelay) {
        this.threadDelay = threadDelay;
    }

    public void setNorthPanelColor(Color color) {
        northPanel.setBackground(color);
    }

    public void setSouthPanelColor(Color color) {
        southPanel.setBackground(color);
    }

    public void setCenterPanelColor(Color color) {
        centerPanel.setBackground(color);
    }

    public void setWestPanelColor(Color color) {
        if (showWestPanel) {
            westPanel.setBackground(color);
        }
    }

    public void setEastPanelColor(Color color) {
        if (showEastPanel) {
            eastPanel.setBackground(color);
        }
    }


    // Window event methods
    public void windowClosing(WindowEvent e) {
        System.out.println("The frame is closing.....");
        ((Window) e.getSource()).dispose();
    }

    public void windowClosed(WindowEvent e) {
        System.out.println("The frame has been closed!");
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) {
        System.out.println("The frame has been activated");
    }

    public void windowDeactivated(WindowEvent e) {
        System.out.println("The frame has been deactivated");
    }

    public void windowDeiconified(WindowEvent e) {
        System.out.println("The frame has been restored from a minimized state");
    }

    public void windowIconified(WindowEvent e) {
        System.out.println("The frame has been minimized");
    }

    public void windowOpened(WindowEvent e) {
        System.out.println("The frame is now visible");
    }

    // ***** main method *****
    public static void main(String[] arguments) {
        Main game = new Main();
        game.centerPanel.repaint();
    }


    class SouthPanel extends JPanel implements KeyListener, ActionListener, Runnable {

        public SouthPanel() {
            super();
        }

        public void setListeners() {
            runButton.addActionListener(this);
            pauseButton.addActionListener(this);
            stopButton.addActionListener(this);
            exitButton.addActionListener(this);
            addKeyListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            String command = e.getActionCommand();

            if (source == exitButton) {
                gameOver = true;
                if (runThread != null) {
                    runThread.stop();
                    runThread = null;
                }
                System.exit(0);
            } else if (source == runButton) {
                if (!gameOver) {
                    if (runThread != null) {
                        if (runThread.isAlive()) {
                            return;
                        }
                    }
                }

                if (runThread == null) {
                    runThread = new Thread(this);
                }

                initialize();

                ship.setXY(screenWidth / 2 - 25, screenHeight - shipDistanceFromBottomOfScreen);

                showMessage = "";
                centerPanel.setShowGameStartScreen(false);

                if (!runThread.isAlive()) {
                    runThread.start();
                }
                gameOver = false;
                gamePaused = false;
            } else if (source == stopButton) {
                gameOver = true;
                gamePaused = false;
                if (runThread != null) {
                    runThread.stop();
                    runThread = null;
                }

                initialize();

                showMessage = "";
                centerPanel.setShowGameStartScreen(true);
                ship.setXY(screenWidth / 2 - 25, screenHeight - shipDistanceFromBottomOfScreen);
                centerPanel.repaint();
            } else if (source == pauseButton) {
                System.out.println("paused button pressed command =" + command);
                showMessage = "";
                if (gamePaused) {
                    pauseButton.setText("Pause");
                    gamePaused = false;
                    showMessage = "";
                    centerPanel.setShowGameStartScreen(false);
                    centerPanel.repaint();
                    southPanel.setFocusable(true);
                    southPanel.requestFocus();
                } else {
                    pauseButton.setText("Resume");
                    gamePaused = true;
                    showMessage = "";
                    centerPanel.setShowGameStartScreen(true);
                    centerPanel.repaint();
                    southPanel.setFocusable(true);
                    southPanel.requestFocus();
                }

            }
        }


        public void run() {
            try {
                while (true) {
                    centerPanel.repaint();

                    setFocusable(true);
                    requestFocus();

                    Thread.currentThread().sleep(threadDelay);

                }
            } catch (Exception e) {
            }
        }

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            keys[2] = false;
            keys[3] = false;
            keys[4] = false;

            int key = e.getKeyCode();
            String keyString = e.getKeyText(key);
            keyString = keyString.toUpperCase();

            System.out.println("Key is pressed inside southPanel=" + keyString);

            if (gameOver) {
                return;
            }
            if (gamePaused) {
                return;
            }

            if (keyString.equals("UP")) {
                keys[0] = true;
            } else if (keyString.equals("DOWN")) {
                keys[1] = true;
            } else if (keyString.equals("LEFT")) {
                keys[2] = true;
            } else if (keyString.equals("RIGHT")) {
                keys[3] = true;
            } else if (keyString.equals("SPACE")) {
                System.out.println("Key is pressed inside southPanel=" + keyString);

                keys[4] = true;
            }

        }

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            String keyString = e.getKeyText(key);
            keyString = keyString.toUpperCase();

            if (keyString.equals("UP")) {
                keys[0] = false;
            } else if (keyString.equals("DOWN")) {
                keys[1] = false;
            } else if (keyString.equals("LEFT")) {
                keys[2] = false;
            } else if (keyString.equals("RIGHT")) {
                keys[3] = false;
            } else if (keyString.equals("SPACE")) {
                keys[4] = false;
            }
        }


    }


    class DrawPanel extends JPanel implements MouseListener, MouseMotionListener {

        String testXY = "X= Y=";
        boolean dragging = false;
        boolean showGameStartScreen = true;

        public DrawPanel() {
            super();
            addMouseListener(this);
            addMouseMotionListener(this);
        }

        public void update(Graphics window) {
            paintComponent(window);
        }

        public void setLevelSettings() {
            if (level <= 2) {
                winningScore = 5 * numAliensVertical * numAliensHorizontal;
                totalChancesToCreateAnAlienBullet = 650;
            } else if (level <= 4) {
                winningScore = 5 * numAliensVertical * numAliensHorizontal;
                totalChancesToCreateAnAlienBullet = 650;
            }
        }


        public void positionBarricades() {
            int xx = 100;
            int yy = (int) (ship.getY() - 100);
            for (int i = 0; i < barricades.size(); i++) {
                MovableObject barricade = barricades.get(i);

                if (i == 0) {
                    xx = (int) (.2 * screenWidth);
                } else if (i == 1) {
                    xx = (int) (screenWidth / 2 - barricade.getWidth() / 2);
                } else {
                    xx = (int) (screenWidth - .2 * screenWidth - barricade.getWidth());
                }

                barricade.setX(xx);
            }
        }


        public void createAShipBullet() {

            MovableObject bullet = new ShipBullet(centerPanel, "bullet.png", shipBulletImage,
                    Math.round(ship.getX() + ship.getWidth() / 2 - 25),
                    Math.round(ship.getY() - 50), 50, 30);

            shipBullets.add(bullet);

        }


        public void createAnAlienBullet(MovableObject alien) {

            // create an alien bullet below the given alien and center it
            // set its speedY to be greater than the alien's speedY
            // an alien should only be allowed to fire a bullet if there is
            // no alien in front of it (or it would shoot one of its own)

            MovableObject bullet = new AlienBullet(centerPanel, "bullet.png", alienBulletImage,
                    Math.round(alien.getX() + alien.getWidth() / 2 - 25),
                    Math.round(alien.getY() + alien.getHeight() + 3), 25, 40);

            alienBullets.add(bullet);

        }


        public void createRandomAlienBullets() {

            // create randomly alien bullets below the alien and center it
            // we will not create a bullet if there is another alien in front of it
            for (int r = 0; r < numAliensVertical; r++) {
                for (int c = 0; c < numAliensHorizontal; c++) {
                    MovableObject alien = aliens[r][c];

                    int chanceValue = (int) (Math.random() * totalChancesToCreateAnAlienBullet);

                    if (chanceValue == 2) {

                        boolean canFire = false;

                        if (r == numAliensVertical - 1) {
                            canFire = true;
                        } else if (aliens[r + 1][c].isHidden()) {
                            canFire = true;
                        }

                        if (alien != null && !alien.isHidden() && canFire) {
                            createAnAlienBullet(alien);
                        }
                    }
                }
            }

        }


        public void createAnExplosion(MovableObject object) {

            Explosion explosion = new Explosion(centerPanel, "explosion.png", explosionImage,
                    object.getXRounded(), object.getYRounded(), object.getWidth(),
                    object.getHeight());

            explosion.setTime(300);
            explosions.add(explosion);

        }


        public boolean isCollisionBetweenShipAndAliens() {
            for (int r = 0; r < numAliensVertical; r++) {
                for (int c = 0; c < numAliensHorizontal; c++) {
                    MovableObject alien = aliens[r][c];
                    if (!alien.isHidden() && alien.intersects(ship)) {
                        createAnExplosion(ship);

                        ship.setHidden(true);
                        alien.setHidden(true);

                        return true;
                    }
                }
            }

            return false;
        }


        public boolean isCollisionBetweenShipBulletsAndAliens() {
            for (int i = 0; i < shipBullets.size(); i++) {
                MovableObject shipBullet = shipBullets.get(i);

                boolean finished = false;

                for (int r = 0; !finished && r < maxAliensVertical; r++) {

                    for (int c = 0; !finished && c < maxAliensHorizontal; c++) {
                        if (!aliens[r][c].isHidden() &&
                                shipBullet.intersects(aliens[r][c])
                        ) {

                            createAnExplosion(shipBullet);

                            shipBullets.remove(i);
                            i--;
                            aliens[r][c].setHidden(true);
                            score += pointsForHittingAnAlien;
                            finished = true;
                        }

                    }

                }

            }

            return false;
        }


        public boolean isCollisionBetweenBarricadesAndAliens() {
            for (int i = 0; i < barricades.size(); i++) {

                MovableObject barricade = barricades.get(i);

                boolean finished = false;

                for (int r = 0; !finished && r < maxAliensVertical; r++) {

                    for (int c = 0; !finished && c < maxAliensHorizontal; c++) {
                        if (!aliens[r][c].isHidden() &&
                                barricade.intersects(aliens[r][c])
                        ) {
                            createAnExplosion(aliens[r][c]);
                            aliens[r][c].setHidden(true);
                            barricade.setHidden(true);
                            finished = true;
                        }

                    }

                }

            }

            return false;
        }


        public boolean isCollisionBetweenShipBulletsAndAlienBullets() {
            for (int i = 0; i < shipBullets.size(); i++) {
                MovableObject shipBullet = shipBullets.get(i);

                for (int k = 0; k < alienBullets.size(); k++) {
                    if (shipBullet.intersects(alienBullets.get(k))) {
                        createAnExplosion(shipBullet);

                        shipBullets.remove(i);
                        alienBullets.remove(k);
                        i--;
                        k--;
                        break;
                    }

                }

            }

            return false;
        }


        public boolean isCollisionBetweenBarricadesAndAlienBullets() {
            for (int i = 0; i < barricades.size(); i++) {
                MovableObject barricade = barricades.get(i);
                Barricade barricadeObject = (Barricade) barricades.get(i);

                for (int k = 0; k < alienBullets.size(); k++) {
                    if (barricade.intersects(alienBullets.get(k))) {
                        createAnExplosion(alienBullets.get(k));

                        alienBullets.remove(k);

                        barricadeObject.decrementNumHitsAllowed();

                        if (barricadeObject.getNumHitsAllowed() <= 0) {
                            barricade.setHidden(true);
                        }

                        return true;
                    }

                }

            }

            return false;
        }


        public float findFrontAlienPosition() {
            // we will start looking at the row that is
            // closest to the ship  
            for (int r = numAliensVertical - 1; r >= 0; r--) {
                for (int c = 0; c < numAliensHorizontal; c++) {
                    if (!aliens[r][c].isHidden()) {
                        return aliens[r][c].getY() + aliens[r][c].getHeight();
                    }
                }
            }

            return 0;
        }


        public boolean isCollisionBetweenShipAndAlienBullets() {

            for (int i = 0; i < alienBullets.size(); i++) {
                MovableObject alienBullet = alienBullets.get(i);

                if (alienBullet.intersects(ship)) {
                    return true;
                }

            }
            return false;
        }


        public void drawAliens(Graphics gMemory) {
            for (int r = 0; r < numAliensVertical; r++) {
                for (int c = 0; c < numAliensHorizontal; c++) {
                    MovableObject alien = aliens[r][c];
                    if (alien != null) {
                        alien.draw(gMemory);
                    }
                }
            }

        }

        public void drawAlienBullets(Graphics gMemory) {
            for (MovableObject bullet : alienBullets) {
                bullet.draw(gMemory);
            }


        }


        public void drawBarricades(Graphics gMemory) {
            for (int i = 0; i < barricades.size(); i++) {
                barricades.get(i).draw(gMemory);
            }


        }


        public void drawShip(Graphics gMemory) {
            if (ship != null) {
                ship.draw(gMemory);
            }

        }


        public void drawShipBullets(Graphics gMemory) {
            for (MovableObject bullet : shipBullets) {
                bullet.draw(gMemory);
            }


        }


        public void drawExplosions(Graphics gMemory) {
            for (MovableObject explosion : explosions) {
                explosion.draw(gMemory);
            }


        }


        public void moveAliens() {
            for (int r = 0; r < numAliensVertical; r++) {
                for (int c = 0; c < numAliensHorizontal; c++) {
                    MovableObject alien = aliens[r][c];
                    if (alien != null) {
                        alien.shift();
                    }
                }
            }

        }

        public void moveAlienBullets() {
            for (int i = 0; i < alienBullets.size(); i++) {
                alienBullets.get(i).move();

                if (alienBullets.get(i).getY() > getHeight()) {
                    alienBullets.remove(i);
                    i--;
                }
            }

        }


        public void moveShipBullets() {
            for (int i = 0; i < shipBullets.size(); i++) {
                shipBullets.get(i).move();

                // if they go off the screen, remove them from the ArrayList
                if (shipBullets.get(i).getY() + shipBullets.get(i).getHeight() < 0) {
                    shipBullets.remove(i);
                    i--;
                }

            }
        }


        public void moveExplosions() {
            for (MovableObject e : explosions) {
                e.move();
            }
        }


        public void updateTimeForExplosions() {

            for (int i = 0; i < explosions.size(); i++) {
                MovableObject explosion = explosions.get(i);

                explosion.setTime(explosion.getTime() - threadDelay);

                if (explosion.getTime() <= 0) {
                    explosions.remove(i);
                    i--;
                }
            }

        }


        public void showScreen(Graphics2D g2, boolean resetAllObjectsToStart) {

            back = (BufferedImage) (createImage(getWidth(), getHeight()));

            Graphics gMemory = back.createGraphics();

            gMemory.setColor(Color.BLACK);
            gMemory.fillRect(0, 0, getWidth(), getHeight());

            if (screenWidth != getWidth() || screenHeight != getHeight()) {
                screenWidth = getWidth();
                screenHeight = getHeight();
            }

            drawAliens(gMemory);
            drawAlienBullets(gMemory);
            drawShipBullets(gMemory);
            drawExplosions(gMemory);

            if (doPositioning) {
                ship.setX(screenWidth / 2 - 25);
                doPositioning = false;
            }

            ship.setY(screenHeight - shipDistanceFromBottomOfScreen);

            drawShip(gMemory);

            positionBarricades();

            barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;

            for (int i = 0; i < barricades.size(); i++) {
                barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
            }

            drawBarricades(gMemory);

            if (barricades != null && barricades.size() > 0) {
                System.out.println("x=" + barricades.get(0).getX());
                System.out.println("x=" + barricades.get(0).getY());
            } else if (barricades == null) {
                System.out.println("barricades does not exist!");
            } else if (barricades.size() == 0) {
                System.out.println("barricades is empty!");
            }

            Font font;

            gMemory.setColor(Color.RED);
            font = new Font("Courier New", Font.BOLD, 16);

            gMemory.drawString(
                    "Score: " + score + "  Wins: " + wins + "  Losses: " + losses + "  Level: "
                            + level, 10, 10);

            gMemory.setColor(Color.RED);
            font = new Font("Courier New", Font.BOLD, 32);

            gMemory.drawString(showMessage, gameOverMessageXValue, gameOverMessageYValue);

            g2.drawImage(back, null, 0, 0);

        }


        public void paintComponent(Graphics g) {
            super.paintComponent((Graphics2D) g);
            Graphics2D g2 = (Graphics2D) g;

            if (showGameStartScreen) {
                showScreen(g2, true);
                winningScore = 5 * numAliensVertical * numAliensHorizontal;
                return;
            }

            if (gameOver) {
                return;
            }

            if (gamePaused) {
                showScreen(g2, false);
                return;
            }

            back = (BufferedImage) (createImage(getWidth(), getHeight()));

            Graphics gMemory = back.createGraphics();

            gMemory.setColor(Color.BLACK);
            gMemory.fillRect(0, 0, getWidth(), getHeight());

            if (screenWidth != getWidth() || screenHeight != getHeight()) {
                screenWidth = getWidth();
                screenHeight = getHeight();

                resetAllAliensWithScreenResize();

                ship.setY(screenHeight - shipDistanceFromBottomOfScreen);
                barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;

                for (int i = 0; i < barricades.size(); i++) {
                    barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
                }
            } else {
                ship.setY(screenHeight - shipDistanceFromBottomOfScreen);
                barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;
                for (int i = 0; i < barricades.size(); i++) {
                    barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
                }

            }

            // move the ship
            if (keys[0] && ship != null) // UP
            {
                // ship.setDirection(0);
                // ship.moveUp(); 
            } else if (keys[1] && ship != null) // DOWN
            {
                // ship.setDirection(180);
                // ship.moveDown();     
            } else if (keys[2] && ship != null) // LEFT
            {
                ship.setDirection(270);
                ship.moveLeft();
            } else if (keys[3] && ship != null) // RIGHT
            {
                ship.setDirection(90);
                ship.moveRight();
            } else if (keys[4] && ship != null) // SPACE
            {

                keys[4] = false; // stops it from creating several bullets in the time frame

                createAShipBullet();

            }

            isCollisionBetweenShipBulletsAndAliens();

            isCollisionBetweenShipBulletsAndAlienBullets();

            isCollisionBetweenBarricadesAndAlienBullets();

            isCollisionBetweenBarricadesAndAliens();

            moveAliens();

            moveAlienBullets();

            moveShipBullets();

            moveExplosions();

            createRandomAlienBullets();

            updateTimeForExplosions();

            drawShipBullets(g2);
            drawAlienBullets(g2);
            drawExplosions(g2);
            drawAliens(g2);
            drawShip(g2);
            drawBarricades(g2);
            showScreen(g2, false);

            Font font;

            if (numAliensLeft() == 0 || score >= winningScore) {
                gMemory.setColor(Color.RED);
                font = new Font("Courier New", Font.BOLD, 32);
                gMemory.drawString("Y O U   W I N ! !", (int) ship.getX(), (int) ship.getY() + 50);
                gameOver = true;
                showGameStartScreen = true;
                gameOverMessageXValue = (int) getWidth() / 2 - 2 * "Y O U   W I N ! !".length();
                gameOverMessageYValue = (int) ship.getY() + ship.getHeight() + 30;
                showMessage = "Y O U   W I N ! !";
                wins++;
                if (wins % 2 == 0) {
                    level++;
                }
            } else if (findFrontAlienPosition() > ship.getY() ||
                    isCollisionBetweenShipAndAliens()
            ) {
                gMemory.setColor(Color.RED);
                font = new Font("Courier New", Font.BOLD, 32);
                gameOverMessageXValue = (int) getWidth() / 2 - 2 * "Y O U   W I N ! !".length();
                gameOverMessageYValue = (int) ship.getY() + ship.getHeight() + 30;
                gMemory.drawString("Y O U   L O S E ! !", gameOverMessageXValue,
                        gameOverMessageYValue);
                System.out.println("YOU LOSE 2");
                gameOver = true;
                showGameStartScreen = true;
                showMessage = "Y O U   L O S E ! !";
                losses++;
            } else if (isCollisionBetweenShipAndAlienBullets()) {
                createAnExplosion(ship);
                drawExplosions(gMemory);
                ship.setHidden(true);
                gMemory.setColor(Color.RED);
                font = new Font("Courier New", Font.BOLD, 32);
                gameOverMessageXValue = (int) getWidth() / 2 - 2 * "Y O U   W I N ! !".length();
                gameOverMessageYValue = (int) ship.getY() + ship.getHeight() + 30;
                gMemory.drawString("Y O U   L O S E ! !", gameOverMessageXValue,
                        gameOverMessageYValue);
                System.out.println("YOU LOSE 2");
                gameOver = true;
                showGameStartScreen = true;
                showMessage = "Y O U   L O S E ! !";
                losses++;
            }

            gMemory.setColor(Color.RED);
            font = new Font("Courier New", Font.BOLD, 16);

            String scoreAsStr = "" + score;
            if (scoreAsStr.length() < 2) {
                scoreAsStr = " " + scoreAsStr;
            }
            gMemory.drawString(
                    "Score: " + scoreAsStr + "  Wins: " + wins + "  Losses: " + losses + "  Level: "
                            + level, 10, 10);

            g2.drawImage(back, null, 0, 0);
        }


        public void setShowGameStartScreen(boolean value) {
            showGameStartScreen = value;
        }


        public void keyPressed(String keyString) {
            // nothing to do, as we will handle it elsewhere
            //System.out.println("Key is pressed inside centerPanel="+keyString);
        }

        // ***** MouseListener interface methods *****

        public void mouseClicked(MouseEvent e) {
            //int xPos = e.getX();
            //int yPos = e.getY();
        }


        public void mousePressed(MouseEvent e) {
            // nothing to do
        }


        // *** if the mouse is released, fire a bullet  ************************
        public void mouseReleased(MouseEvent e) {
            dragging = false;
            startMouseDragX = -1;
            startMouseDragY = -1;
        }


        public void mouseEntered(MouseEvent e) {
            // nothing to do
        }


        public void mouseExited(MouseEvent e) {

        }

        // ***** MouseMotionListener interface methods *****


        public void mouseMoved(MouseEvent e) {
            int xPos = e.getX();
            int yPos = e.getY();
            testXY = "X=" + xPos + "  Y=" + yPos;
        }


        public void mouseDragged(MouseEvent e) {
            // nothing to do
        }

    }


}