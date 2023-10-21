// only fill in methods marked as   // FINISH ME

// FINISH ME
// YOU MUST ALSO make sure that you write the methods in the interface
//    that are not already finished

// DO NOT MODIFY THE OTHER METHODS UNLESS YOU KNOW WHAT YOU ARE DOING!!!!


// all of your visible objects in the main program will be 
// of type MovableObject
// examples of how to create a MovableObject are shown below
// ship = new MovableObject(centerPanel,"filename.png",x,y,width,height);
//    or if you already have a reference to an image, you can call
// ship = new MovableObject(centerPanel,"filename.png",image,x,y,width,height);

// to do:
// 



import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class MovableObject implements MovableInterface
{
  // direction to move
  // 0 to 360 compass direction
  // you can also use a slope instead
  private int direction;
  private int speed;  // used to move in the direction
  private float xRatio;
  private float yRatio;
  
  private float shiftXValue; // shift to the left or to the right amount
  private float shiftYValue; // shift down
  private float shiftTime; // amount of time left before the shift occurs
  private float shiftTimeStart; // amount of time the shift starts at
  private float shiftTimeInterval; // amount of time elapsed for each draw cycle
  
  // current location of object
  private float x;
  private float y;
  private int width;
  private int height;

  // amount of time on the screen
  private float time;
  
  
  private boolean hidden;
  
  private boolean cloaked;
  
  // speed of object using the slope of a line
  // (you can also use a direction and speed instead)
  private int speedX;
  private int speedY;
  
  // list of possible images to use
  private ArrayList <Image> imageList;
  
  // list of filenames for the images (jpg, ...)
  private String [] imageFilenames;
  
  // current image being displayed
  private Image currentImage;

  // filename of current image being displayed
  private String currentFilename;
  
      
  // the JPanel that this object is being displayed on
  private JPanel mainPanel;


  // centerPanel should be passed as the first parameter when you create a MovableObject
    public MovableObject(JPanel mainPanel, String filename, int x, int y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null; // make sure you call setPanel
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    time = 0;
    speedX = 3;
    speedY = 3;
    imageList = null;
    currentImage = null;
    imageFilenames = null;
    currentFilename = "";
    shiftTime = 0;
    shiftXValue = 0;
    shiftYValue = 0;
    cloaked = false;
    setPanel(mainPanel);
    setCurrentFilename(filename);
  }

  // centerPanel should be passed as the first parameter when you create a MovableObject
    public MovableObject(JPanel mainPanel, String filename, float x, float y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null; // make sure you call setPanel
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    time = 0;
    speedX = 3;
    speedY = 3;
    imageList = null;
    currentImage = null;
    imageFilenames = null;
    currentFilename = "";
    shiftTime = 0;
    shiftXValue = 0;
    shiftYValue = 0;
    cloaked = false;
    setPanel(mainPanel);
    setCurrentFilename(filename);
  }

  
    public MovableObject(JPanel mainPanel, String filename, Image image, int x, int y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null; // make sure you call setPanel
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    time = 0;
    speedX = 3;
    speedY = 3;
    imageList = null;
    currentImage = null;
    imageFilenames = null;
    currentFilename = "";
    shiftTime = 0;
    shiftXValue = 0;
    shiftYValue = 0;
    cloaked = false;
    setPanel(mainPanel);
    currentImage = image;
    currentFilename = filename;
  }

    public MovableObject(JPanel mainPanel, String filename, Image image, float x, float y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null; // make sure you call setPanel
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    time = 0;
    speedX = 3;
    speedY = 3;
    imageList = null;
    currentImage = null;
    imageFilenames = null;
    currentFilename = "";
    shiftTime = 0;
    shiftXValue = 0;
    shiftYValue = 0;
    cloaked = false;
    setPanel(mainPanel);
    currentImage = image;
    currentFilename = filename;
  }

  public void setPanel(JPanel mainPanel)
  {
    this.mainPanel = mainPanel;
  }

  public JPanel getPanel()
  {
    return mainPanel;
  }

  public void setImageFilenames(String [] filenames)
  {
    imageFilenames = filenames;
    imageList = new ArrayList<Image>();
    for (int i=0; i < filenames.length; i++)
    {
      try
      {
        Image pic = Toolkit.getDefaultToolkit().getImage(filenames[i]);
        imageList.add(pic);
      }
      catch (Exception e)
      {
        // FINISH ME
        // print out an error message
        System.out.println("error setImageFilenames with toolkit");
      }
    }
    if (filenames.length > 0)
    {
      currentFilename = filenames[0];
      setImage();
    }
  }

  private void setImage()
  {
    if (currentFilename != null && currentFilename != "")
    {
      try
      {
        currentImage = Toolkit.getDefaultToolkit().getImage(currentFilename);
        System.out.println("toolkit ok");
      }
      catch (Exception e)
      {
        currentImage = null;
        currentFilename = "";
        System.out.println("error getImage with toolkit");
      }
    }
  }

  public void setCurrentFilename(String filename)
  {
    currentFilename = filename;
    setImage();
  }

  public void setCurrentFilenamePosition(int position)
  {
    if (imageFilenames == null)
      return;
    if (position < imageFilenames.length && position > -1)
      {
        currentFilename = imageFilenames[position];
        setImage();
      }
    else
      {
        currentFilename = "";
        currentImage = null;
      }
  }


  // to start the shift process
  // this is in sync with the draw method
  
  // call setShiftXValue(float shiftValue) which is the amount to shift
  // call setShiftTimeInterval(float shiftTimeInterval) which should be in time with the clock
  // call setShiftTimeStart(float shiftTimeStart) which is the amount of time between shifts
  

    public float getShiftTimeInterval()
    {
        return shiftTimeInterval;
    }


    public void setShiftTimeInterval(float shiftTimeInterval)
    {
        this.shiftTimeInterval = shiftTimeInterval;
    }


    public float getShiftTimeStart()
    {
        return shiftTimeStart;
    }

    public void setShiftTimeStart(float shiftTimeStart)
    {
        this.shiftTimeStart = shiftTimeStart;
    }


    public float getShiftTime()
    {
        return shiftTime;
    }

    public void setShiftTime(float shiftTime)
    {
        this.shiftTime = shiftTime;
    }

    public float getShiftXValue()
    {
        return shiftXValue;
    }

  public float getShiftYValue()
  {
    return shiftYValue;
  }
  
  
    public void setShiftXValue(float shiftXValue)
    {
        this.shiftXValue = shiftXValue;
    }

    public void setShiftYValue(float shiftYValue)
    {
        this.shiftYValue = shiftYValue;
    }
 
  public void shift()
  {
    // do the shift inside the draw method
    if (shiftTimeStart > 0)
    {
      setShiftTime(getShiftTime()-getShiftTimeInterval());
      if (getShiftTime() <= 0) 
      {
        setShiftTime(getShiftTimeStart());
        setX(getX()+getShiftXValue());
        setShiftXValue(getShiftXValue()*-1);
        setY(getY() + getShiftYValue());
      }
    }
  
  }
  
  
  // FINISH ME (replace the 0)
    public int getDirection()
    {
        // ?????????
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction + 360; // allows it to handle negative directions
        this.direction = this.direction % 360;
    }

    // FINISH ME (replace the 0)
    public float getX()
    {
        return x;
    }

    public int getXRounded()
    {
        return Math.round(x);
    }

    // FINISH ME
    public void setX(float x)
    {
      // ?????
      this.x = x;
    }

    // FINISH ME (replace the 0)
    public float getY()
    {
      return y;
    }

    public int getYRounded()
    {
      return Math.round(y);
    }

    // FINISH ME
    public void setY(float y)
    {
      // ????????
      this.y = y;
    }

    // FINISH ME
    public void setXY(float x, float y)
    {
      // ???????
      // ???????
      this.x = x;
      this.y = y;
    }

    // FINISH ME (replace the 0)
    public int getWidth()
    {
        return width;
    }

    // FINISH ME
    public void setWidth(int width)
    {
      // ?????????
      this.width = width;
    }

    // FINISH ME (replace the 0)
    public int getHeight()
    {
        return height;
    }

    // FINISH ME
    public void setHeight(int height)
    {
      // ?????????
      this.height = height;
    }

  // FINISH ME
  public void setCloaked(boolean cloaked)
  {
    // ?????????
    this.cloaked = cloaked;
  }
  
  // FINISH ME
  public boolean getCloaked()
  {
    // ???????????
    return cloaked;
  }
  
  // FINISH ME
  public boolean isCloaked()
  {
    // ??????????
    return cloaked;
  }

    // FINISH ME (replace the 0)
    public int getSpeedX()
    {
        return speedX;
    }

    // FINISH ME
    public void setSpeedX(int speedX)
    {
      // ?????????
      this.speedX = speedX;
    }

    // FINISH ME (replace the 0)
    public int getSpeedY()
    {
        return speedY;
    }

    // FINISH ME
    public void setSpeed(int speed)
    {
      // ?????????
      this.speed = speed;
    }

    // FINISH ME (replace the 0)
    public int getSpeed()
    {
        return speed;
    }


    // FINISH ME
    public void setXRatio(float xRatio)
    {
      // ??????????????
      this.xRatio = xRatio;
    }

    // FINISH ME (replace the 0)
    public float getXRatio()
    {
        return xRatio;
    }


    // FINISH ME
    public void setYRatio(float yRatio)
    {
      // ???????????
      this.yRatio = yRatio;
    }

    // FINISH ME (replace the 0)
    public float getYRatio()
    {
        return yRatio;
    }


    // FINISH ME
    public void setSpeedXY(int speedX, int speedY)
    {
       // ?????????
       // ?????????
      this.speedX = speedX;
      this.speedY = speedY;
    }

    // FINISH ME
    public void setSpeedY(int speedY)
    {
      // ???????????????
      this.speedY = speedY;
    }

    // FINISH ME (replace the 0)
    public float getTime()
    {
      return time;
    }

    // FINISH ME
    public void setTime(float time)
    {
      // ??????????????
      this.time = time;
    }



    // FINISH ME (replace the false)
    public boolean getHidden()
    {
      return hidden;
    }

    // FINISH ME (replace the false)
    public boolean isHidden()
    {
      return hidden;
    }

    // FINISH ME
    public void setHidden(boolean hidden)
    {
      // ??????????????????
      this.hidden = hidden;
    }


  // *************************************************************
  // *** bounce methods ******************************************
  // *************************************************************
  
  public void bounceOffLeftWall()
  {       
    if (getSpeedX() < 0 && getSpeedY() > 0)
    {
      setSpeedX(getSpeedX()*-1);
    }
    else if (getSpeedX() < 0 && getSpeedY() < 0)
    {
      setSpeedX(getSpeedX()*-1);  
    }           
  }


  public void bounceOffRightWall()
  {       
    if (getSpeedX() > 0 && getSpeedY() > 0)
    {
      setSpeedX(getSpeedX()*-1);
    }
    else if (getSpeedX() > 0 && getSpeedY() < 0)
    {
      setSpeedX(getSpeedX()*-1);  
    }       
  }


  public void bounceOffTopWall()
  {       
        if (getSpeedX() > 0 && getSpeedY() < 0)
        {
          setSpeedY(getSpeedY()*-1);
        }
        else if (getSpeedX() < 0 && getSpeedY() < 0)
        {
          setSpeedY(getSpeedY()*-1);  
        }       
    
  }
  

  // *************************************************************
  // *** is hitting a wall ***************************************
  // *************************************************************
  
  
  public boolean isHittingTopSideOfWall(float wallY)
  {
    if (getY() <= wallY)  
      return true;    
    return false;     
  }

  public boolean isHittingBottomSideOfWall(float wallY)
  {
    if (getY()+getHeight() >= wallY)  
      return true;    
    return false;     
  }

  public boolean isHittingRightSideOfWall(float wallX)
  {
    if (getX()+getWidth() >= wallX) 
      return true;    
    return false;     
  }
  

  public boolean isHittingLeftSideOfWall(float wallX)
  {
    if (getX() <= wallX)  
      return true;    
    return false;     
  }
  
  
  // *************************************************************
  // *** is hitting side of other object *************************
  // *************************************************************
  
  
  // checks for an intersection from the left
  // width is the number of pixels on the object
  // closeness is how close to the object to detect an intersection
  public float isHittingLeftSideOfOtherObjectByAmount(MovableObject other, int closeness, int width)
  {
    Rectangle rect1 = new Rectangle((int)(getX()+getWidth()-width+closeness),(int)getY(),width,(int)getHeight());
    Rectangle rect2 = new Rectangle((int)other.getX(),(int)other.getY(),width,(int)other.getHeight());

    if (rect1.intersects(rect2))
    {
      return Math.abs((getX()+getWidth())-other.getX());
    }
    return -1;
  }
  

  public float isHittingRightSideOfOtherObjectByAmount(MovableObject other, int closeness, int width)
  {
    Rectangle rect1 = new Rectangle((int)getX()-closeness,(int)getY(),width,(int)getHeight());
    Rectangle rect2 = new Rectangle((int)(other.getX()+other.getWidth()-width),(int)other.getY(),width,(int)other.getHeight());

    if (rect1.intersects(rect2))
    {
      return Math.abs(getX()-(other.getX()+other.getWidth()));
    }
    return -1;
  }


  public float isHittingTopSideOfOtherObjectByAmount(MovableObject other, int closeness, int height)
  {
    Rectangle rect1 = new Rectangle((int) getX(),(int)(getY()+getHeight()-height+closeness),getWidth(),height);
    Rectangle rect2 = new Rectangle((int) other.getX(),(int)other.getY(),(int) other.getWidth(),height);

    if (rect1.intersects(rect2))
    {
      return Math.abs((getY() + getHeight()) - other.getY());
    }
    return -1;
  }
  

  public float isHittingBottomSideOfOtherObjectByAmount(MovableObject other, int closeness, int height)
  {
    Rectangle rect1 = new Rectangle((int) getX(),(int)(getY()-closeness),getWidth(),height);
    Rectangle rect2 = new Rectangle((int) other.getX(),(int)(other.getY()+other.getHeight()-height),(int) other.getWidth(),height);

    if (rect1.intersects(rect2))
    {
      return Math.abs(getY()-(other.getY()+other.getHeight()));
    }
    return -1;  // not hitting it   
  }


  // *************************************************************
  // *** rectangle methods ***************************************
  // *************************************************************
    
  public Rectangle getRect()

  {
    return new Rectangle(getXRounded(),getYRounded(),getWidth(),getHeight());
  }


  // *************************************************************
  // *** intersection methods ***************************************
  // *************************************************************

  public boolean intersects(Rectangle rect1, Rectangle rect2)
  {
    if (rect1.intersects(rect2))
      return true;
    return false;
  }
  
    
  public boolean intersects(Rectangle rect)
  {
    if (isHidden())
      return false;
    if (getRect().intersects(rect))
      return true;
    return false;
  }
  

  public boolean intersects(MovableObject other)
  {
    if (isCloaked())
      return false;
    if (isHidden())
      return false;
    if (other.isHidden())
      return false;
    if (getRect().intersects(other.getRect()))
      return true;
    return false;
  }

  // *************************************************************
  // *** point methods *******************************************
  // *************************************************************
    
  public Point getPoint()
  {
    return new Point(getXRounded(),getYRounded());
  }

  public boolean containsPoint(Point point)
  {
    if (getRect().contains(point))
      return true;
    return false;
  }


  // *************************************************************
  // *** rectangle methods ***************************************
  // *************************************************************
    
  // *************************************************************
  // *** turn methods ********************************************
  // *************************************************************
    
  public void turnRight()
  {
    setDirection(getDirection()+90);
  }
  
  public void turnByAmount(int amount)
  {
    setDirection(getDirection()+amount);
  }


  // *************************************************************
  // *** compass and degree methods ******************************
  // *************************************************************
    
  public double degreesToRadians(double degrees)
  {
      return degrees*Math.PI/180;
  }


  public static float getDegreesFromCompassDirection(float compassDirection)
  {
    if (compassDirection >= 0 && compassDirection <= 90)    
    {
      // this works
      float degrees = Math.abs(compassDirection - 90);
      return degrees;
    }
    if (compassDirection > 90 && compassDirection <= 180)   
    {
      // this works
      float degrees = Math.abs(360 - (compassDirection-90));
      return degrees;
    }
    
    if (compassDirection > 180 && compassDirection <= 270)    
    {
      // this does not work
      float degrees = Math.abs(270 - (compassDirection-180));
      return degrees;
    }

    if (compassDirection > 270 && compassDirection <= 359)    
    {
      // this works
      float degrees = Math.abs(180 - (compassDirection-270));
      return degrees;
    }

    return 0; 
  }



  // *************************************************************
  // *** move methods ********************************************
  // *************************************************************

  // FINISH ME
  public void moveUpBy(float amount)
  {
    // get your current y value and subtract amount
    setY(getY()-amount);
  }

  // FINISH ME
  public void moveUp()
  {
    // get your current y value and subtract speedY
    setY(getY()-speedY);
  }


  // FINISH ME
  public void moveDownBy(float amount)
  {
    // get your current y value and add ??????  
    setY(getY() + amount);
  }

  // FINISH ME
  public void moveDown()
  {
    // get your current y value and add speedY
    setY(getY()+speedY);
  }


  // FINISH ME
  public void moveLeft()
  {
    // get your current x value and subtract speedX  
    setX(getX()-speedX);
  }


  // FINISH ME
  public void moveLeftBy(float amount)
  {
    setX(getX()-amount);
  }


  // FINISH ME
  public void moveRight()
  {
    setX(getX()+speedX);
  }

  // FINISH ME
  public void moveRightBy(float amount)
  {
    setX(getX()+amount);
  }


  // FINISH ME
  // moves the object using speedX and speedY
  public void move()
  {
    setX(getX()+speedX);
    setY(getY()+speedY);
  }


  // FINISH ME
  public void moveTo(int x, int y)
  {
    setX(x);
    setY(y);
  }

  
  // move using the direction and speed
  public void moveInCurrentDirection()
  {
    int   currentDirection = getDirection();// get the compass direction
    float currentDegrees = getDegreesFromCompassDirection(currentDirection);
    
    double radDegrees = 0;
    
    if (speed > 0)
    {
      float newX = 0;
      float newY = 0;
            
      if (currentDirection >= 0 && currentDirection <= 90)
      {
        // this works
        currentDegrees = 360-currentDegrees;
        radDegrees = degreesToRadians(currentDegrees);
        newX = xRatio*((float) Math.abs(Math.cos(radDegrees)*speed));
        newY = yRatio*((float) Math.abs(Math.sin(radDegrees)*speed));
        newX = getX() + newX;
        newY = getY() - newY; 
      }
      else if (currentDirection > 90 && currentDirection <= 180)
      {
        currentDegrees = 360-currentDegrees;
        radDegrees = degreesToRadians(currentDegrees);
        newX = xRatio*((float) Math.abs(Math.cos(radDegrees)*speed));
        newY = yRatio*((float) Math.abs(Math.sin(radDegrees)*speed));
        newX = getX() + newX;
        newY = getY() + newY; 
      }
      else if (currentDirection > 180 && currentDirection <= 270)
      {
        // this works
        currentDegrees = 270-currentDegrees;
        radDegrees = degreesToRadians(currentDegrees);
        newX = xRatio*((float) Math.abs(Math.sin(radDegrees)*speed));
        newY = yRatio*((float) Math.abs(Math.cos(radDegrees)*speed));
        newX = getX() - newX;
        newY = getY() + newY; 
      }
      else if (currentDirection > 270 && currentDirection < 360)
      {
        // this works
        currentDegrees = 180-currentDegrees;
        radDegrees = degreesToRadians(currentDegrees);
        newX = xRatio*((float) Math.abs(Math.cos(radDegrees)*speed));
        newY = yRatio*((float) Math.abs(Math.sin(radDegrees)*speed));
        newX = getX() - newX;
        newY = getY() - newY; 
      }
      
      setX(newX);
      setY(newY);
      
    }
    
     
  }
  
  // moves toward the other object using the direction and speed
  // uses similar triangles
  public void moveTowards(MovableObject other)
  {
    double sideX = Math.abs(other.getX() - getX());
    double sideY = Math.abs(other.getY() - getY());
    double d = Math.sqrt(sideX*sideX + sideY*sideY);
    
    if (d == 0)
      return;
      
    int speedX = (int)Math.round((getSpeed()*sideX)/d);
    int speedY = (int)Math.round((getSpeed()*sideY)/d);
    
    if (getX() < other.getX())
      setX(getX() + speedX);
    else if (getX() > other.getX())
      setX(getX() - speedX);
    
    if (getY() < other.getY())
      setY(getY() + speedY);
    else if (getY() > other.getY())
      setY(getY() - speedY);      
  
  }



  // *************************************************************
  // *** draw methods ********************************************
  // *************************************************************
    

    // draws the image using the direction
  public void draw2(Graphics g)
  {
    if (hidden)
      return;

        Graphics2D g2 = (Graphics2D)g;
        AffineTransform transform = new AffineTransform();
        transform.setToTranslation(getX(), getY());
        //transform.rotate(degreesToRadians(getDirection()),24,24);
        transform.rotate(degreesToRadians(getDirection()),getWidth()/2,getHeight()/2);
        
        g2.drawImage(currentImage, transform, mainPanel);        
  }
    
  
  // draws the image in its original direction
  public void draw(Graphics g)
  {   
    if (hidden)
      return;

    Graphics2D g2 = (Graphics2D) g;
          
    g2.setColor(Color.black);

    g2.drawImage(currentImage,
      getXRounded(), 
      getYRounded(), 
      getWidth(),
      getHeight(),
      mainPanel);
  }

}  // end of class MovableObject

/*
boolean drawImage(Image img, int x, int y, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
*/