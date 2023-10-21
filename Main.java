//By Peter Zheng



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Main extends JFrame implements WindowListener 
{
  // these constants should reall be in an interface
  public static final int alienShiftXValue = 10;
  public static final int alienShiftYValue = 5;
  public static final float alienShiftTimeStart = 600.0f;
  
  private static final int maxAliensHorizontal = 40;
  private static final int maxAliensVertical   = 40;
  
  private int totalChancesToCreateAnAlienBullet = 650;
    
  private int winningScore = 50;  // you need a score of 50 to win at level 1
  
  private int gameOverMessageXValue = 20;
  private int gameOverMessageYValue = 300;
  
  private int level = 1;
  
  
  // screen variables (these can change)
  
  private int screenWidth  = 500;
  private int indentWidth  = 20;
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
  JPanel     northPanel;   // this is the message panel
  SouthPanel southPanel;   // put your buttons on this panel
  JPanel     westPanel;    // this panel will be empty for now
  boolean    showWestPanel = false;
  JPanel     eastPanel;    // this panel will be empty for now
  boolean    showEastPanel = false;

  DrawPanel  centerPanel;  // this will be the panel with all the drawing of MovableObjects

  // title in northPanel
  JLabel northText;

  // Buttons
  JButton runButton;
  JButton pauseButton;
  JButton stopButton;
  JButton exitButton;

  
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *** declare your arrays and beginning MovableObjects ********************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  
  private MovableObject ship;  // this will be controlled by the user (moves left and right)

  private int shipDistanceFromBottomOfScreen = 95;
  private int barricadeDistanceFromBottomOfScreen = 150;
  
  private Image shipImage;  // we need one image to load

  private Image alienImage;  // we need one image to load and all aliens can use this 1 image

  private Image shipBulletImage;  // we need one image to load and all ship bullets can use this 1 image

  private Image alienBulletImage;  // we need one image to load and all alien bullets can use this 1 image

  private Image explosionImage;  // we need one image to load and all explosions can use this 1 image

  private Image barricadeImage;
  
    
  // we need to use a 2 dimensional array

  private MovableObject [][] aliens;

  private int numAliensHorizontal = 15;
  private int numAliensVertical = 6;
  
  // An ArrayList of alien bullets
  private ArrayList<MovableObject> alienBullets;
  
  // An ArrayList of ship bullets
  private ArrayList<MovableObject> shipBullets;

  // An ArrayList of explosions
  private ArrayList<MovableObject> explosions;

  // An ArrayList of barricade objects
  private ArrayList<MovableObject> barricades;

  // wins, losses, and score  
  private int score;
  private int wins = 0;
  private int losses = 0;

  private int pointsForHittingAnAlien = 5;

  // FINISH ME
  // PUT YOUR NAME ON THIS!!!!!!!!!
  private String showMessage = "Space Invaders the Classic Version by Peter Zheng";
  
  // thread for the runButton
  Thread runThread = null;
  int threadDelay = 20;  // the paintComponent method will be called every 25 milliseconds



  // 
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *** alien methods *******************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************

  // turn off all aliens
  public void turnOffAliens()
  {
    // FINISH ME
    // use nested loops to loop through all of the aliens
    // USE maxAliensVertical for the r (row)
    // USE maxAliensHorizontal for the c (col)
    for (int r=0; r<maxAliensVertical; r++ )
    {
      for (int c=0; c<maxAliensHorizontal; c++ )
      {
        // FINISH ME
        // CHANGE the hidden attribute to true
        // aliens[r][c].?????????;
        aliens[r][c].setHidden(true);
      }
    }
  }
  
  
  // turn on the aliens that will show on the screen
  public void turnOnVisibleAliens()
  {
    // FINISH ME
    // use nested loops to loop through all of the aliens shown on the screen
    // USE numAliensVertical for the r (row)
    // USE numAliensHorizontal for the c (col)
    // and set the Y value of all aliens on each row to the same y value
    // and set the hidden property to false
    
    for (int r=0; r<numAliensVertical;  r++ )
    {
      for (int c=0; c<numAliensHorizontal;  c++ )
      {
        aliens[r][c].setY(aliens[r][0].getY());
                
        // FINISH ME
        // CHANGE the hidden attribute to false
        aliens[r][c].setHidden(false);
      }
    }   
  }
  
  
  // make sure all possible aliens are set in case the screen is enlarged 
  public void turnOnAliensShift()
  {
    // FINISH ME
    // use nested loops to loop through all of the aliens
    // USE maxAliensVertical for the r (row)
    // USE maxAliensHorizontal for the c (col)
    
    for (int r=0; r<maxAliensVertical;  r++ )
    {
      for (int c=0; c<maxAliensHorizontal;  c++ )
      {
        aliens[r][c].setShiftTimeStart(alienShiftTimeStart);
        aliens[r][c].setShiftXValue(alienShiftXValue);  // shift left or right in pixels
        aliens[r][c].setShiftYValue(alienShiftYValue);  // shift down in pixels
        aliens[r][c].setShiftTimeInterval(threadDelay); // every 20 ms subtract from shift time
        aliens[r][c].setShiftTime(alienShiftTimeStart); // amount of time left before the shift
      }
    }   
    
  }
  
  public void resetAllAliens()
  {
    // we will only show some of them (what fits on the screen)
    // we will never get rid of them, but rather flip the hidden flag
    int x = 20;
    int y = 10;
    for (int r=0; r < maxAliensVertical; r++)
    {
      x = 20;
      for (int c=0; c < maxAliensHorizontal; c++)
      {
        // FINISH ME
        // change the x and y values of the alien to
        // the new values stored in x and y
        // call a method
        // aliens[r][c].???????;
        // aliens[r][c].???????;
        aliens[r][c].setX(x);
        aliens[r][c].setY(y);
        x = x + 55;                 
      }
      y = y + 55;
    }   
  }


  public void resetAllAliensXValue()
  {
    // we will only show some of them (what fits on the screen)
    // we will never get rid of them, but rather flip the hidden flag
    int x = 20;
    for (int r=0; r < maxAliensVertical; r++)
    {
      x = 20;
      for (int c=0; c < maxAliensHorizontal; c++)
      {
        // FINISH ME
        // set the x value of the alien to x
        // call a method
        // aliens[r][c].???????;
        aliens[r][c].setX(x);
        x = x + 55;                 
      }
    }   
  }

  public void resetAllAliensYValue()
  {
    // we will only show some of them (what fits on the screen)
    // we will never get rid of them, but rather flip the hidden flag
    float y = aliens[0][0].getY();
    
    for (int r=0; r < maxAliensVertical; r++)
    {
      for (int c=0; c < maxAliensHorizontal; c++)
      {
        // FINISH ME
        // set the y value to y
        // aliens[r][c].??????;
        aliens[r][c].setY(y);
      }
      y = y + 55;       
    }   
  }
  
  public void resetAllAliensXYValue()
  {
    // we will only show some of them (what fits on the screen)
    // we will never get rid of them, but rather flip the hidden flag
    int x = 20;
    float y = aliens[0][0].getY();
    for (int r=0; r < maxAliensVertical; r++)
    {
      x = 20;
      for (int c=0; c < maxAliensHorizontal; c++)
      {
        // FINISH ME
        // set the x and y value of the alien to x,y
        // aliens[r][c].????????;
        aliens[r][c].setXY(x, y);
        x = x + 55;                 
      }
      y = y + 55;   
    }   
  }

  
  public void createAllAliens()
  {
    
    // create all of the aliens
    // create the 2D array to hold all the possible aliens
    // actually we have pointers to MovableObject objects that are set to null
    // FINISH ME
    // create a 2d array that can refer to Alien objects 
    aliens = new Alien[maxAliensVertical][maxAliensHorizontal];

    // we will only show some of them (what fits on the screen)
    // we will never get rid of them, but rather flip the hidden flag
    int x = 20;
    int y = 10;
    for (int r=0; r < maxAliensVertical; r++)
    {
      x = 20;
      for (int c=0; c < maxAliensHorizontal; c++)
      {
        aliens[r][c] = new Alien(centerPanel, "alien.png", alienImage, x,y , 50,50);
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
  public int numAliensLeft()
  {
    int count = 0;
    // FINISH ME
    // use nested loops to loop through all of the aliens shown on the screen
    // USE numAliensVertical for the r (row)
    // USE numAliensHorizontal for the c (col)
    for (int r =0; r<numAliensVertical;  r++ )
    {
      for (int c=0; c<numAliensHorizontal;  c++ )
      {
        // FINISH ME
        // if this alien is NOT hidden, add 1 to your count
        // ?????????????????
        if(!aliens[r][c].getHidden())
          count++;
      }
    }
    
    // FINISH ME
    return count;
  }


  public void resetAllAliensWithScreenResize()
  {

      numAliensHorizontal = (screenWidth-(2*indentWidth))/55;
      numAliensVertical = (screenHeight - (int)(screenHeight*3.0/5))/55;


      if (numAliensHorizontal > maxAliensHorizontal)
        numAliensHorizontal = maxAliensHorizontal;
      
      if (numAliensVertical > maxAliensVertical)
        numAliensVertical = maxAliensVertical;

  
      turnOffAliens();
      turnOnVisibleAliens();
  
      resetAllAliensXValue();
      resetAllAliensYValue();
  
      turnOnAliensShift();
    
  }
  
  
  private Image loadImage(String filename)
  {
      Image tempImage = null;
      
    if (filename != null && filename != "")
    {
      try
      {
        tempImage = Toolkit.getDefaultToolkit().getImage(filename);
        System.out.println("toolkit ok");
      }
      catch (Exception e)
      {
        System.out.println("error getImage with toolkit unable to load filename "+filename);
      }
    }
    
    return tempImage;
  }





  // 
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *** create your arrays and beginning MovableObjects (new) ***************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  
    
  public void initMovableObjects()
  {

    numAliensHorizontal = (screenWidth-(2*indentWidth))/55;
    numAliensVertical = (screenHeight - (int)(screenHeight*3.0/5))/55;
    
    if (numAliensHorizontal > maxAliensHorizontal)
      numAliensHorizontal = maxAliensHorizontal;
      
    if (numAliensVertical > maxAliensVertical)
      numAliensVertical = maxAliensVertical;
    
    
    if (createAllObjects)
    {
      System.out.println("CREATING all objects");
      createAllObjects = false;

      // FINISH ME
      // FIND SUITABLE PICTURES FOR YOUR ship, aliens, bullets, and explosions 
      // your image files can be of type  gif, jpg, or png  
      // replace Actor.gif and Bug.gif with your image
      

      // FINISH ME
      // get an image for the ship
      // call loadImage and pass it "ship.png"
      shipImage = loadImage("ship.png");
    
      // FINISH ME
      // get an image for the ship bullet
      shipBulletImage = loadImage("bullet.png");


      // FINISH ME
      // get an image for the alien
      alienImage = loadImage("alien.png");

      // FINISH ME
      // get an image for the alien bullet
      alienBulletImage = loadImage("bullet.png");

      // FINISH ME
      // get an image for the explosion
      explosionImage = loadImage("explosion.png");

      // FINISH ME
      barricadeImage = loadImage("barricade.png");


    // FINISH ME
    // create the ship
    ship = new Ship(centerPanel, "ship.png", shipImage, screenWidth/2-25,screenHeight-shipDistanceFromBottomOfScreen, 50,50);     
    System.out.println("creating ship");
    

    createAllAliens();
    
    turnOnAliensShift();


    // FINISH ME
    // you will need to create all of your ArrayList objects
    // THEY NEED TO BE OF TYPE MovableObject
    
    // An ArrayList of alien bullets  USE <MovableObject>
    // FINISH ME
    alienBullets = new ArrayList<MovableObject>();
  
  
    // FINISH ME
    // An ArrayList of ship bullets  <MovableObject>
    shipBullets  = new ArrayList<MovableObject>();


    // FINISH ME
    // An ArrayList of explosions  <MovableObject>
    explosions = new ArrayList<MovableObject>();

    // FINISH ME
    // An ArrayList of barricade objects
    barricades = new ArrayList<MovableObject>();
    
    int xx = 100;
    int yy = (int) (ship.getY()-100);
    for (int i=0; i<3; i++)
    {
      
      MovableObject barricade = new Barricade(centerPanel, "barricade.png", barricadeImage, xx, yy, 50,30);
      
      if (i==0)
      {
        xx = (int) (.2*screenWidth);
      }
      else if (i==1)
      {
        xx = (int) (screenWidth/2 - barricade.getWidth()/2);
      }
      else
      {
        xx = (int) (screenWidth - .2*screenWidth - barricade.getWidth());         
      }
      
      barricade.setX(xx);
      barricades.add(barricade);
      xx += 150;
    }
    System.out.println("barricades size="+barricades.size());
    }
    else
    {
      resetAllAliens();
      
      // FINISH ME
      // clear all of your lists
      alienBullets.clear();
      shipBullets.clear();
      explosions.clear();
      
      // show the barricades
      for (int i=0; i<barricades.size(); i++)
      {
        barricades.get(i).setHidden(false);
        ((Barricade)barricades.get(i)).setNumHitsAllowed(3);
      }
      
      System.out.println("barricades size="+barricades.size());
      
      
    }

    score = 0;

  
  barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;     


  // show the barricades
  for (int i=0; i<barricades.size(); i++)
  {
    barricades.get(i).setHidden(false);
  }
      


    turnOffAliens();
    
    turnOnVisibleAliens();

    // FINISH ME
    // Set the ship's hidden attribute to false
    // ship.???????;  
    ship.setHidden(false);
      
  }


  
  // ***** public void initialize *****  
  public void initialize( )
  {
    back = null;
    gameOver = true;
        
    keys = new boolean[5];
    
    // create the JButton objects
    runButton   = new JButton("Run");
    pauseButton = new JButton("Pause");
    stopButton  = new JButton("Stop");
    exitButton  = new JButton("Exit");

    // create a mainPanel for components
    mainPanel = new JPanel();

    // ***** create JPanels for a BorderLayout *****
    northPanel   = new JPanel();
    southPanel   = new SouthPanel();
    southPanel.setListeners(); // this will add listeners for the JButton objects
  
  
    if (showWestPanel)
    {
      westPanel    = new JPanel();
    }
    if (showEastPanel)
    {
      eastPanel    = new JPanel();
    }
  
    centerPanel  = new DrawPanel();

    mainPanel.setLayout(new BorderLayout());
    southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    setCenterPanelColor(Color.black);
    northPanel.setBackground(new Color(115,205,255));
    southPanel.setBackground(new Color(115,205,255));
    if (showWestPanel)
    {
        westPanel.setBackground(new Color(115,205,255));
    }
    if (showEastPanel)
    {
        eastPanel.setBackground(new Color(115,205,255));
    }


    // add buttons to southPanel
    southPanel.add(runButton);
    southPanel.add(pauseButton);
    southPanel.add(stopButton);
    southPanel.add(exitButton);

    // FINISH ME
    // PUT YOUR NAME ON THIS
    northText = new JLabel("Space Invaders Classic by Peter Zheng");
    northPanel.add(northText);

    // ***** add the panels to the mainPanel 5 areas *****
    mainPanel.add(northPanel,BorderLayout.NORTH);
    mainPanel.add(southPanel,BorderLayout.SOUTH);
    if (showEastPanel)
    {
      mainPanel.add(eastPanel,BorderLayout.EAST);
    }
    if (showWestPanel)
    {
      mainPanel.add(westPanel,BorderLayout.WEST);
    }
    mainPanel.add(centerPanel,BorderLayout.CENTER);

    southPanel.setFocusable(true);
    southPanel.requestFocus();

    initMovableObjects();
  
    centerPanel.repaint();

    // make the mainPanel be the main content area and show it
    setContentPane(mainPanel);
    
    setVisible(true);  // always show the screen last


    // focus the southPanel so that we can receive key strokes
    southPanel.setFocusable(true);
    southPanel.requestFocus();
  }   // end of public void initialize

  // ***** default constructor *****
  public Main( )
  {
    setSize(screenWidth,screenHeight);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle("Space Invaders Classic");
    addWindowListener(this);
  
    // initialize variables
    initialize( );
  }

  public void setMessage(String message)
  {
    northText.setText(message);
  }

  public String getMessage()
  {
    return northText.getText();
  }

  public void setThreadDelay(int threadDelay)
  {
    this.threadDelay = threadDelay;
  }

  public void setNorthPanelColor(Color color)
  {
    northPanel.setBackground(color);
  }

  public void setSouthPanelColor(Color color)
  {
    southPanel.setBackground(color);
  }

  public void setCenterPanelColor(Color color)
  {
    centerPanel.setBackground(color);
  }

  public void setWestPanelColor(Color color)
  {
    if (showWestPanel)
    {
      westPanel.setBackground(color);
    }
  }

  public void setEastPanelColor(Color color)
  {
    if (showEastPanel)
    {
      eastPanel.setBackground(color);
    }
  }


   // Window event methods
   public void windowClosing(WindowEvent e)
   {
     System.out.println("The frame is closing.....");
     //The following line of code 
     //specifies that the frame should be closed
     ((Window)e.getSource()).dispose();
   }

   public void windowClosed(WindowEvent e)
   {
     System.out.println("The frame has been closed!");
     System.exit(0);
   }

   public void windowActivated(WindowEvent e){
     System.out.println("The frame has been activated");        
   }
   
   public void windowDeactivated(WindowEvent e){
     System.out.println("The frame has been deactivated");        
   }

   public void windowDeiconified(WindowEvent e){
     System.out.println("The frame has been restored from a minimized state");
   }

   public void windowIconified(WindowEvent e){
     System.out.println("The frame has been minimized");
   }

   public void windowOpened(WindowEvent e){
     System.out.println("The frame is now visible");
   }

  // ***** main method *****
  public static void main(String[] arguments)
  {
    Main game = new Main( );
    game.centerPanel.repaint();
  }


class SouthPanel extends JPanel implements KeyListener,ActionListener, Runnable
{
  // start of actionPerformed (ActionListener interface)
  // handle button clicks here
  public SouthPanel()
  {
    // allow buttons to listen for clicks
    super();
  }
  
  public void setListeners()
  {
    runButton.addActionListener(this);
    pauseButton.addActionListener(this);
    stopButton.addActionListener(this);
    exitButton.addActionListener(this);
    addKeyListener(this);
  }
  
  public void actionPerformed(ActionEvent e)
  {
    Object source = e.getSource(); // points to the object that got clicked
    String command = e.getActionCommand();
    
    if (source==exitButton)
    {
      gameOver = true;
      if (runThread!=null)
      {
        runThread.stop();
        runThread = null;
      }
      System.exit(0);
    }
    else if (source==runButton)
    {    
      if (!gameOver)  
        if (runThread != null)
          if (runThread.isAlive())
            return;  // if the game is active, ignore the click
      
      if (runThread==null)
      {
        runThread = new Thread(this); // the game is inactive, so let's play
      }
    
      // init the screen

      initialize( );
    
      ship.setXY(screenWidth/2-25,screenHeight-shipDistanceFromBottomOfScreen);

      showMessage = "";
      centerPanel.setShowGameStartScreen(false);
    
      // start the game
      if (!runThread.isAlive())
        runThread.start();
        gameOver = false;
          gamePaused = false;
    }
    else if (source==stopButton)  // they want to stop the game
    {
        gameOver = true;
        gamePaused = false;
      if (runThread!=null)
    {
      runThread.stop();
      runThread = null;
    }
    // init the screen
    
    initialize( );
    
    showMessage = "";
    centerPanel.setShowGameStartScreen(true);
    ship.setXY(screenWidth/2-25,screenHeight-shipDistanceFromBottomOfScreen);
    centerPanel.repaint();
  }
  else if (source==pauseButton)  // they want to pause the game
    {
      System.out.println("paused button pressed command ="+command);
      showMessage = "";
      if (gamePaused)
      {
        pauseButton.setText("Pause");
        gamePaused = false;       
        showMessage = "";
        centerPanel.setShowGameStartScreen(false);
        centerPanel.repaint();
        // focus the southPanel so that we can receive key strokes
        southPanel.setFocusable(true);
        southPanel.requestFocus();
      }
      else
      {
        pauseButton.setText("Resume");
        gamePaused = true;
        showMessage = "";
        centerPanel.setShowGameStartScreen(true);
        centerPanel.repaint();
        // focus the southPanel so that we can receive key strokes
        southPanel.setFocusable(true);
        southPanel.requestFocus();
      }

    }
  }  // end of actionPerformed


  // thread to delay for the runButton
  // do it all here so we have control of the buttons
  public void run()
  {
    try
    {
      while(true)
      {          
         // this will redraw everything on the centerPanel
         centerPanel.repaint();

         setFocusable(true);
         requestFocus();
         
         
         Thread.currentThread().sleep(threadDelay);
                 
        }
      } catch(Exception e)
      {
      }
  }

  // start of keyTyped (KeyListener interface)
  public void keyTyped(KeyEvent e)
  {
  }  // end of keyTyped(KeyEvent e)

  // start of keyPressed (KeyListener interface)
  public void keyPressed(KeyEvent e)
  {
    keys[2] = false;
    keys[3] = false;
    keys[4] = false;

    int key = e.getKeyCode();
    String keyString = e.getKeyText(key);
    keyString = keyString.toUpperCase();
      
     System.out.println("Key is pressed inside southPanel="+keyString);
    
    if (gameOver)
      return;
    if (gamePaused)
      return;
    

    if (keyString.equals("UP"))
    {
       keys[0]=true;
    }
    else if (keyString.equals("DOWN"))
    {
       keys[1]=true;
    }
    else if (keyString.equals("LEFT"))
    {
      keys[2]=true;
    }
    else if (keyString.equals("RIGHT"))
    {
      keys[3]=true;
    }
    else if (keyString.equals("SPACE"))
    {
         System.out.println("Key is pressed inside southPanel="+keyString);

      keys[4]=true;
    }
  
  }  // end of keyPressed(KeyEvent e)

  // start of keyReleased (KeyListener interface)
  public void keyReleased(KeyEvent e)
  {
    int key = e.getKeyCode();
    String keyString = e.getKeyText(key);
    keyString = keyString.toUpperCase();

    if (keyString.equals("UP"))
    {
      keys[0]=false;
    }
    else if (keyString.equals("DOWN"))
    {
      keys[1]=false;
    }
    else if (keyString.equals("LEFT"))
    {
      keys[2]=false;
    }
    else if (keyString.equals("RIGHT"))
    {
      keys[3]=false;
    }
    else if (keyString.equals("SPACE"))
    {
      keys[4]=false;
    }
  }  // end of keyReleased(KeyEvent e)

  
  
} // end of centerPanel class



// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// this is the panel for the game  (this is an inner class)
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
class DrawPanel extends JPanel implements  MouseListener, MouseMotionListener
{
  String testXY="X= Y=";
  boolean dragging = false;
  boolean showGameStartScreen = true;
  
  public DrawPanel()
  {
    super();
    addMouseListener(this);
    addMouseMotionListener(this);
  }

  public void update(Graphics window)
  {
    paintComponent(window);
  }

  public void setLevelSettings()
  {
    if (level <= 2)
    {
      winningScore = 5*numAliensVertical*numAliensHorizontal;     
      totalChancesToCreateAnAlienBullet = 650;
    }
    else if (level <= 4)
    {
      winningScore = 5*numAliensVertical*numAliensHorizontal;     
      totalChancesToCreateAnAlienBullet = 650;      
    } 
  }
  
  
  public void positionBarricades()
  {
    int xx = 100;
    int yy = (int) (ship.getY()-100);
    for (int i=0; i<barricades.size(); i++)
    { 
      MovableObject barricade = barricades.get(i);
            
      if (i==0)
      {
        xx = (int) (.2*screenWidth);
      }
      else if (i==1)
      {
        xx = (int) (screenWidth/2 - barricade.getWidth()/2);
      }
      else
      {
        xx = (int) (screenWidth - .2*screenWidth - barricade.getWidth());         
      }
      
      barricade.setX(xx);
    }
  }


  public void createAShipBullet()
  {
    
      MovableObject bullet = new ShipBullet(centerPanel, "bullet.png", shipBulletImage, Math.round(ship.getX()+ship.getWidth()/2 - 25), Math.round(ship.getY() - 50), 50,30);
      
      // FINISH ME
      // add the bullet to the shipBullets ArrayList
      shipBullets.add(bullet);
      
  }


  public void createAnAlienBullet(MovableObject alien)
  {
    
      // FINISH ME
      // create an alien bullet below the given alien and center it
      // set its speedY to be greater than the alien's speedY
      // NOTE: an alien should only be allowed to fire a bullet if there is
      //       NO alien in front of it (or it would shoot one of its own)
      
      MovableObject bullet = new AlienBullet(centerPanel, "bullet.png", alienBulletImage, Math.round(alien.getX()+alien.getWidth()/2 - 25), Math.round(alien.getY() + alien.getHeight() + 3), 25,40);
      
      // FINISH ME
      // add the bullet to the alienBullets ArrayList
      // ???????????
    alienBullets.add(bullet);
      
  }


  public void createRandomAlienBullets()
  {
    
      // FINISH ME
      // create randomly alien bullets below the alien and center it
      // we will not create a bullet if there is another alien in front of it
      for (int r=0; r < numAliensVertical; r++)
      {
        for (int c=0; c < numAliensHorizontal; c++)
        {
            MovableObject alien = aliens[r][c];
            
            
            // FINISH ME
            // create a random value between 0 and totalChancesToCreateAnAlienBullet-1 inclusive
            int chanceValue = (int)(Math.random() * totalChancesToCreateAnAlienBullet);  // creates a random number between 0 and ?? inclusive
            
            if (chanceValue == 2)  // if the number happens to be the lucky number 2
            {
            
              boolean canFire = false;

              if (r==numAliensVertical-1) // its on the bottom row
              {
                canFire = true;
              }
              else if (aliens[r+1][c].isHidden()) // nothing in front
              {
                canFire = true;
              }
            
              if (alien != null && !alien.isHidden() && canFire)
              { 
                // FINISH ME
                // call the method createAnAlienBullet and pass it the alien        
                createAnAlienBullet(alien);
              }
            }
        }
      }      
      
  }


  public void createAnExplosion(MovableObject object)
  {
    
      // FINISH ME
      // create an explosion on the given object using the object's x,y,width, and height values
      
      Explosion explosion = new Explosion(centerPanel, "explosion.png", explosionImage, object.getXRounded(), object.getYRounded(), object.getWidth(),object.getHeight());


      // FINISH ME
      // DO THIS LATER
      // set the time for the explosion to be on the screen
      explosion.setTime(300);
      
      
      // FINISH ME
      // DO THIS LATER
      // add the explosion to the explosions ArrayList
      explosions.add(explosion);
      
  }



  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *** Methods to check for a COLLISION of your objects on the screen ******************
  // *************************************************************************************
  // *** You will need to call these methods from the main event loop ********************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************


  public boolean isCollisionBetweenShipAndAliens()
  {
    for (int r=0; r < numAliensVertical; r++)
    {  
        for (int c=0; c < numAliensHorizontal; c++)
        {
            MovableObject alien = aliens[r][c];
            if (!alien.isHidden() && alien.intersects(ship))
            {
              // FINISH ME
              // Create an Explosion (call a method) over the ship
              createAnExplosion(ship);
              
              // FINISH ME
              // set the hidden flag to true
              ship.setHidden(true);
              
              // FINISH ME
              // set the hidden flag to true
              alien.setHidden(true);
              
              return true;              
            }
        }
      }
    
    return false;
  }   
    

  public boolean isCollisionBetweenShipBulletsAndAliens()
  {
    for (int i=0; i<shipBullets.size(); i++)
    {
      MovableObject shipBullet = shipBullets.get(i);
      
      boolean finished = false;
      
      for (int r=0; !finished && r < maxAliensVertical; r++)
        {

          for (int c=0; !finished && c < maxAliensHorizontal; c++)
          {
            // FINISH ME
            // see if the shipBullet intersects aliens[r][c]
            if (!aliens[r][c].isHidden() && 
              shipBullet.intersects(aliens[r][c])
               )
            {
              
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
    

  public  boolean isCollisionBetweenBarricadesAndAliens()
  {
    for (int i=0; i<barricades.size(); i++)
    {

      MovableObject barricade = barricades.get(i);
      
      boolean finished = false;
      
      for (int r=0; !finished && r < maxAliensVertical; r++)
        {

          for (int c=0; !finished && c < maxAliensHorizontal; c++)
          {
            if (!aliens[r][c].isHidden() && 
              barricade.intersects(aliens[r][c])
               )
            {
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
    

  public  boolean isCollisionBetweenShipBulletsAndAlienBullets()
  {
    for (int i=0; i<shipBullets.size(); i++)
    {
      MovableObject shipBullet = shipBullets.get(i);

      for (int k=0; k<alienBullets.size(); k++)
      {
        if (shipBullet.intersects(alienBullets.get(k)))
        {
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


  public  boolean isCollisionBetweenBarricadesAndAlienBullets()
  {
    for (int i=0; i<barricades.size(); i++)
    {
      MovableObject barricade = barricades.get(i);
      Barricade barricadeObject = (Barricade) barricades.get(i);
      
      for (int k=0; k<alienBullets.size(); k++)
      {
        if (barricade.intersects(alienBullets.get(k)))
        {
          createAnExplosion(alienBullets.get(k));

          alienBullets.remove(k);
          
          barricadeObject.decrementNumHitsAllowed();
          
          if (barricadeObject.getNumHitsAllowed()<= 0)
          {
            barricade.setHidden(true);
          }
          
          return true;              
        }
        
      }
      
    }


    
    return false;
  }   


  public float findFrontAlienPosition()
  {
    // we will start looking at the row that is
    // closest to the ship  
    for (int r=numAliensVertical-1; r>=0; r-- )
      {
        for (int c=0; c < numAliensHorizontal; c++ )
        {
        if (!aliens[r][c].isHidden())
          return aliens[r][c].getY()+aliens[r][c].getHeight();
        }
      }   
    
    return 0;
  }   



  public  boolean isCollisionBetweenShipAndAlienBullets()
  {

    for (int i=0; i<alienBullets.size(); i++)
    {
      MovableObject alienBullet = alienBullets.get(i);

      if (alienBullet.intersects(ship))
      {
        return true;
      }
      
    }
    return false;
  }   






  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *** Methods to draw all of your objects on the screen *******************************
  // *************************************************************************************
  // *** You will need to call these methods *********************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************


  public void drawAliens(Graphics gMemory)
  {
    for (int r=0; r < numAliensVertical; r++)
      {
        for (int c=0; c < numAliensHorizontal; c++)
        {
            MovableObject alien = aliens[r][c];
            if (alien != null)
            {           
              // draw the alien
              alien.draw(gMemory);
            }
        }
      }
    
  }   
    
  public void drawAlienBullets(Graphics gMemory)
  {
    for (MovableObject bullet : alienBullets)
    {
      bullet.draw(gMemory);
    }

    
  }   


  public void drawBarricades(Graphics gMemory)
  {
    for (int i=0; i<barricades.size(); i++)
    {
      barricades.get(i).draw(gMemory);
    }

    
  }   



  public void drawShip(Graphics gMemory)
  {
    if (ship != null)
    {
      ship.draw(gMemory);
    }
    
  }
    

  public void drawShipBullets(Graphics gMemory)
  {
    for (MovableObject bullet : shipBullets)
    {
      bullet.draw(gMemory);
    }

    
  }   


  public void drawExplosions(Graphics gMemory)
  {
    for (MovableObject explosion : explosions)
    {
      explosion.draw(gMemory);
    }


    
  }


  public void moveAliens()
  {
    for (int r=0; r < numAliensVertical; r++)
      {
        for (int c=0; c < numAliensHorizontal; c++)
        {
            MovableObject alien = aliens[r][c];
            if (alien != null)
            {
              alien.shift();                          
            }
        }
      }
    
  }   
    
  public void moveAlienBullets()
  {
    for (int i=0; i<alienBullets.size(); i++)
    {
      alienBullets.get(i).move();
      
      if (alienBullets.get(i).getY() > getHeight())
      {
        alienBullets.remove(i);
        i--;
      }     
    }
    
  }   
    

  public void moveShipBullets()
  {
    // FINISH ME
    // move your ship bullets  you will need one loop
    // they should move upwards towards the aliens
    // if they go off the screen, remove them from the ArrayList
    for (int i=0; i<shipBullets.size(); i++)
    {
      shipBullets.get(i).move();
  
      // if they go off the screen, remove them from the ArrayList
      if (shipBullets.get(i).getY()+ shipBullets.get(i).getHeight() < 0)
      {
        shipBullets.remove(i);
        i--;
      }
      
    }
  }   


  public void moveExplosions()
  {
    // FINISH ME (Optional)
    // DO THIS METHOD LATER
    // move your explosions  you will need one loop
    for(MovableObject e : explosions)
      e.move();
    
  }   


  public void updateTimeForExplosions()
  {
    // FINISH ME
    // DO THIS METHOD LATER
    // update the time for each explosion (one loop)
    // if the time is up, remove them from your list
    // threadDelay is set to 20 ms, the amount of time that passes
    //    between each call to paintComponent
    // you will need an indexed for loop since you cannot remove objects
    //     when using a for each loop
    
    for (int i=0; i<explosions.size(); i++)   
    {
      MovableObject explosion = explosions.get(i);
      
      explosion.setTime(explosion.getTime()-threadDelay);
      
      if (explosion.getTime() <= 0)
      {
        explosions.remove(i);
        i--;
      }
    }
    
  }   


  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *** this Method shows the screen when the game is not active ************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
    
  public void showScreen(Graphics2D g2, boolean resetAllObjectsToStart)
  {
      // System.out.println("inside showScreen");
            
      back = (BufferedImage)(createImage(getWidth(),getHeight()));

      // create a graphics reference to the back ground image
      // we will draw all changes on the background image
      Graphics gMemory = back.createGraphics();

      // clear the screen
      gMemory.setColor(Color.BLACK);
      gMemory.fillRect(0,0,getWidth(),getHeight());

 
      // reset the number of aliens on the screen
      // if the screen size has changed

      if (screenWidth != getWidth() || screenHeight != getHeight())
      {           
        screenWidth = getWidth();
        screenHeight = getHeight();
      }


      // FINISH ME
      // DO THESE METHOD CALLS LATER
      // draw all of the objects
      // CALL a METHOD, do not write the method code here
            
      // draw the aliens
      // call a method
      drawAliens(gMemory);
      drawAlienBullets(gMemory);
    
      // draw the shipBullets
      // call a method
      drawShipBullets(gMemory);
    
      // draw the explosions
      // call a method
      drawExplosions(gMemory);
        
      if (doPositioning)
      {
        ship.setX(screenWidth/2-25);
        doPositioning = false;  
      }
      

      ship.setY(screenHeight-shipDistanceFromBottomOfScreen);

      // draw the ship
      // call a method
      drawShip(gMemory);

      positionBarricades();

      barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;
      
      // loop through the barricades     
      for (int i=0; i<barricades.size(); i++)
      {
        barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
      }

      // draw the barricades
      // call a method
      drawBarricades(gMemory);
      
      if (barricades!=null && barricades.size()>0)
      {
      System.out.println("x="+barricades.get(0).getX());
      System.out.println("x="+barricades.get(0).getY());
      }
      else if (barricades == null)
      {
        System.out.println("barricades does not exist!"); 
      }
      else if (barricades.size()==0)
      {
        System.out.println("barricades is empty!");           
      }
      
      Font font;
      
      // draw the score, level, etc.
      gMemory.setColor(Color.RED);
      font = new Font("Courier New",Font.BOLD,16); 
      
      // FINISH ME
      // draw your score, wins, and losses
      gMemory.drawString("Score: " + score + "  Wins: " + wins + "  Losses: " + losses + "  Level: "+level,10,10);
      
 
      gMemory.setColor(Color.RED);
      font = new Font("Courier New",Font.BOLD,32);

      gMemory.drawString(showMessage,gameOverMessageXValue,gameOverMessageYValue);

    
      // *** show the screen by copying the image to the graphics display ********
      g2.drawImage(back, null, 0, 0); 
    
  }


  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // *************************************************************************************
  // ***  this is the main event loop for the game ***************************************
  // *************************************************************************************
  // ***  check for collisions, move all objects, and draw all screen objects ************
  // *************************************************************************************
  // ***  add to your score, checks for a win and check for a loss ***********************
  // *************************************************************************************
  // *************************************************************************************


  
  public void paintComponent(Graphics g)
  {
    super.paintComponent((Graphics2D)g);
    Graphics2D g2 = (Graphics2D) g;

    if (showGameStartScreen)
    {
      showScreen(g2,true);
      winningScore = 5*numAliensVertical*numAliensHorizontal;
      return;
    }
    
    if (gameOver)
    {
      return;
    }

    if (gamePaused)
    {
      showScreen(g2,false);
      return;
    }

    //take a snap shop of the current screen and save it as an image
    //that is the exact same width and height as the current screen
    back = (BufferedImage)(createImage(getWidth(),getHeight()));

    //create a graphics reference to the back ground image
    //we will draw all changes on the background image
    Graphics gMemory = back.createGraphics();

    // clear the screen
    gMemory.setColor(Color.BLACK);
    gMemory.fillRect(0,0,getWidth(),getHeight());

    // System.out.println("game is playing");
    
    // DO NOT USE THESE 3 commands here unless you want to display 
    //   the coordinates of the mouse pointer
    //gMemory.setColor(Color.RED);
    //gMemory.drawString(testXY,10,50);
    //gMemory.setColor(Color.BLACK);

    if (screenWidth != getWidth() || screenHeight != getHeight())
    {           
      screenWidth = getWidth();
      screenHeight = getHeight();               

      resetAllAliensWithScreenResize();
        
      // reset the position of the ship
      ship.setY(screenHeight-shipDistanceFromBottomOfScreen);
      barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;
      
      // loop through the barricades     
      for (int i=0; i<barricades.size(); i++)
      {
        barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
      }
    }
    else
    {
      // reset the position of the ship
      ship.setY(screenHeight-shipDistanceFromBottomOfScreen);
      barricadeDistanceFromBottomOfScreen = (int) ship.getY() - 60;     
      for (int i=0; i<barricades.size(); i++)
      {
        barricades.get(i).setY(barricadeDistanceFromBottomOfScreen);
      }
      
    }


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *** Move your ship ******************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    
    // move the ship
    if (keys[0] && ship != null) // UP
    {
      // ship.setDirection(0);
      // ship.moveUp(); 
    }
    else if (keys[1] && ship != null) // DOWN
    {
      // ship.setDirection(180);
      // ship.moveDown();     
    }
    else if (keys[2] && ship != null) // LEFT
    {
      ship.setDirection(270);
      ship.moveLeft();    
    }
    else if (keys[3] && ship != null) // RIGHT
    {
      ship.setDirection(90);
      ship.moveRight();
    }
    else if (keys[4] && ship != null) // SPACE
    {
      // FINISH ME
      // fire a bullet
      // CALL A method to do this
      
      
      keys[4] = false; // this stops it from creating several bullets in the time frame
    
      
      createAShipBullet();
      
    }

 
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // ***  check for collisions here  *****************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    
    
    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call the method to check for a collision 
    // between the ship bullets and aliens
    // CALL a METHOD, do not write the method code here
    isCollisionBetweenShipBulletsAndAliens();

    
    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call the method to check for a collision 
    // between the ship bullets and alien bullets
    // CALL a METHOD, do not write the method code here
    isCollisionBetweenShipBulletsAndAlienBullets();
    

    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call the method to check for a collision 
    // between the barricades and alien bullets
    // CALL a METHOD, do not write the method code here
    isCollisionBetweenBarricadesAndAlienBullets();


    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call the method to check for a collision 
    // between the barricades and the aliens
    // CALL a METHOD, do not write the method code here
    isCollisionBetweenBarricadesAndAliens();


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // ***  move all of your objects here, but not the ship ********************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    // FINISH ME
    // call the method to move your aliens
    // ??????
    moveAliens();


    // FINISH ME
    // call the method to move your alien bullets
    moveAlienBullets();
    


    
    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call a method to move your ship bullets
    // CALL a METHOD, do not write the method code here
    moveShipBullets();



    // FINISH ME (OPTIONAL)
    // DO THIS METHOD CALL LATER
    // call your method to move your explosions 
    // (optional to make them get bigger)
    // CALL a METHOD, do not write the method code here
    // ?????????
    moveExplosions();


    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *** Create any random objects *******************************************
    // *** or use any graphic commands to draw lines, ovals, rects, etc. *******
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************    
    // FINISH ME
    // call a method to create random bullets for your aliens
    createRandomAlienBullets();



    // FINISH ME
    // DO THIS LATER
    // call a method to update the time for your explosions
    //????????????????
    updateTimeForExplosions();
    
    

    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************
    // *** DRAW all your objects here using gMemory ****************************
    // *** or use any graphic commands to draw lines, ovals, rects, etc. *******
    // *************************************************************************
    // *************************************************************************
    // *************************************************************************

    // FINISH ME
    // DO THIS METHOD CALL LATER
    // call methods to draw your alien bullets, ship bullets, and explosions
    // CALL METHODS, do not write the method code here
    drawShipBullets(g2);
    drawAlienBullets(g2);
    drawExplosions(g2);
    drawAliens(g2);
    drawShip(g2);
    drawBarricades(g2);
    showScreen(g2, false);

    
    Font font;

    
    // check to see if you win and if so, show a message and set gameOver=true;
    if (numAliensLeft()==0 || score >= winningScore)
    {
      gMemory.setColor(Color.RED);
      font = new Font("Courier New",Font.BOLD,32); 
      gMemory.drawString("Y O U   W I N ! !",(int)ship.getX(),(int)ship.getY()+50);
      gameOver = true;
      showGameStartScreen = true;
      gameOverMessageXValue = (int)getWidth()/2-2*"Y O U   W I N ! !".length();
      gameOverMessageYValue = (int)ship.getY()+ship.getHeight()+30;
      showMessage = "Y O U   W I N ! !";
      wins++;
      if (wins%2==0)
        level++;
    }
      
    // fix me
    // it should only look at visible aliens
    
    
    // else if (aliens[numAliensVertical-1][0].getY()+aliens[numAliensVertical-1][0].getHeight() >= ship.getY())
    
    else if (findFrontAlienPosition()>ship.getY() ||
           isCollisionBetweenShipAndAliens()
          ) 
    {
      gMemory.setColor(Color.RED);
      font = new Font("Courier New",Font.BOLD,32); 
      gameOverMessageXValue = (int)getWidth()/2-2*"Y O U   W I N ! !".length();
      gameOverMessageYValue = (int)ship.getY()+ship.getHeight()+30;
      gMemory.drawString("Y O U   L O S E ! !",gameOverMessageXValue,gameOverMessageYValue);
      System.out.println("YOU LOSE 2");
      gameOver = true;
      showGameStartScreen = true;
      showMessage = "Y O U   L O S E ! !";
      losses++;         
    }

    else if (isCollisionBetweenShipAndAlienBullets())
    {
      createAnExplosion(ship);
      drawExplosions(gMemory);
      ship.setHidden(true);
      gMemory.setColor(Color.RED);
      font = new Font("Courier New",Font.BOLD,32); 
      gameOverMessageXValue = (int)getWidth()/2-2*"Y O U   W I N ! !".length();
      gameOverMessageYValue = (int)ship.getY()+ship.getHeight()+30;
      gMemory.drawString("Y O U   L O S E ! !",gameOverMessageXValue,gameOverMessageYValue);
      System.out.println("YOU LOSE 2");
      gameOver = true;
      showGameStartScreen = true;
      showMessage = "Y O U   L O S E ! !";
      losses++;               
    }

    
    // draw the score, level, etc.
    gMemory.setColor(Color.RED);
    font = new Font("Courier New",Font.BOLD,16); 
    
    
      
    // FINISH ME
    // draw your score, wins, and losses
    String scoreAsStr = ""+score;
    if (scoreAsStr.length()< 2)
      scoreAsStr = " " + scoreAsStr;
    gMemory.drawString("Score: " + scoreAsStr+ "  Wins: " + wins + "  Losses: "+losses+"  Level: "+level,10,10);
      

      
    // *** show the screen by copying the image to the graphics display ********
      g2.drawImage(back, null, 0, 0); 
  }  // end of public void paintComponent(Graphics g)



  public void setShowGameStartScreen(boolean value)
  {
    showGameStartScreen = value;
  }

 
 
 
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *** if a key is pressed *************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  public void keyPressed(String keyString)
  {
    // nothing to do, as we will handle it else where
    //System.out.println("Key is pressed inside centerPanel="+keyString);
  } // end of method public void keyPressed(String keyString)


  // ***** MouseListener interface methods *****


  // start of mouseClicked(MouseEvent e) (MouseListener interface)
  public void mouseClicked(MouseEvent e) 
  {
    //int xPos = e.getX();
    //int yPos = e.getY();
  }  // end of public void mouseClicked(MouseEvent e) 


  // start of mousePressed(MouseEvent e) (MouseListener interface)
  public void mousePressed(MouseEvent e) 
  {
     // nothing to do
  }  // end of public void mousePressed(MouseEvent e)



  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *** if the mouse is released, fire a bullet  ****************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  // *************************************************************************
  public void mouseReleased(MouseEvent e) 
  {
    dragging = false;
    startMouseDragX = -1;
    startMouseDragY = -1;
    // nothing to do
  }  // end of public void mouseReleased(MouseEvent e)


  public void mouseEntered(MouseEvent e) 
  {
    // nothing to do
  }  // end of public void mouseEntered(MouseEvent e)


  public void mouseExited(MouseEvent e) 
  {

  }  // end of public void mouseExited(MouseEvent e)


  // ***** MouseMotionListener interface methods *****


  public void mouseMoved(MouseEvent e) 
  {
    int xPos = e.getX();
    int yPos = e.getY();
    testXY = "X=" + xPos + "  Y=" + yPos;
  }  // end of public void mouseMoved(MouseEvent e) 


  public void mouseDragged(MouseEvent e) 
  {
     // nothing to do
  }  // end of public void mouseDragged(MouseEvent e)

} // end of class DrawPanel


// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************
// this is the END of class DrawPanel for the game
// *************************************************************************************
// *************************************************************************************
// *************************************************************************************




} // end of class Main (SpaceInvadersClassic)