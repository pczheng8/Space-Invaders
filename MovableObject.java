import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class MovableObject implements MovableInterface
{
  private int direction;
  private int speed;
  private float xRatio;
  private float yRatio;
  
  private float shiftXValue;
  private float shiftYValue;
  private float shiftTime;
  private float shiftTimeStart;
  private float shiftTimeInterval;
  
  private float x;
  private float y;
  private int width;
  private int height;

  private float time;
  
  
  private boolean hidden;
  
  private boolean cloaked;
  
  private int speedX;
  private int speedY;
  
  private ArrayList <Image> imageList;
  
  private String [] imageFilenames;
  
  private Image currentImage;

  private String currentFilename;
  
  private JPanel mainPanel;


    public MovableObject(JPanel mainPanel, String filename, int x, int y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null;
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

    public MovableObject(JPanel mainPanel, String filename, float x, float y, int width, int height)
    {
    direction = 0; // Location.NORTH;
    hidden = false;
    speed = 3;
    xRatio = 3;
    yRatio = 4;
    this.mainPanel = null;
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
    this.mainPanel = null;
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
    this.mainPanel = null;
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
  
    public int getDirection()
    {
        return direction;
    }

    public void setDirection(int direction)
    {
        this.direction = direction + 360; // allows it to handle negative directions
        this.direction = this.direction % 360;
    }

    public float getX()
    {
        return x;
    }

    public int getXRounded()
    {
        return Math.round(x);
    }

    public void setX(float x)
    {
      this.x = x;
    }

    public float getY()
    {
      return y;
    }

    public int getYRounded()
    {
      return Math.round(y);
    }

    public void setY(float y)
    {
      this.y = y;
    }

    public void setXY(float x, float y)
    {
      this.x = x;
      this.y = y;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
      this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
      this.height = height;
    }

  public void setCloaked(boolean cloaked)
  {
    this.cloaked = cloaked;
  }
  
  public boolean getCloaked()
  {
    return cloaked;
  }
  
  public boolean isCloaked()
  {
    return cloaked;
  }

    public int getSpeedX()
    {
        return speedX;
    }

    public void setSpeedX(int speedX)
    {
      this.speedX = speedX;
    }

    public int getSpeedY()
    {
        return speedY;
    }

    public void setSpeed(int speed)
    {
      this.speed = speed;
    }

    public int getSpeed()
    {
        return speed;
    }

    public void setXRatio(float xRatio)
    {
      this.xRatio = xRatio;
    }

    public float getXRatio()
    {
        return xRatio;
    }

    public void setYRatio(float yRatio)
    {
      this.yRatio = yRatio;
    }

    public float getYRatio()
    {
        return yRatio;
    }


    public void setSpeedXY(int speedX, int speedY)
    {
      this.speedX = speedX;
      this.speedY = speedY;
    }

    public void setSpeedY(int speedY)
    {
      this.speedY = speedY;
    }

    public float getTime()
    {
      return time;
    }

    public void setTime(float time)
    {
      this.time = time;
    }


    public boolean getHidden()
    {
      return hidden;
    }

    public boolean isHidden()
    {
      return hidden;
    }

    public void setHidden(boolean hidden)
    {
      this.hidden = hidden;
    }


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


  public Rectangle getRect()

  {
    return new Rectangle(getXRounded(),getYRounded(),getWidth(),getHeight());
  }



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


  
  public void turnRight()
  {
    setDirection(getDirection()+90);
  }
  
  public void turnByAmount(int amount)
  {
    setDirection(getDirection()+amount);
  }


  
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




  public void moveUpBy(float amount)
  {
    setY(getY()-amount);
  }

  public void moveUp()
  {
    setY(getY()-speedY);
  }

  public void moveDownBy(float amount)
  {
    setY(getY() + amount);
  }

  public void moveDown()
  {
    setY(getY()+speedY);
  }

  public void moveLeft()
  {
    setX(getX()-speedX);
  }

  public void moveLeftBy(float amount)
  {
    setX(getX()-amount);
  }


  public void moveRight()
  {
    setX(getX()+speedX);
  }

  public void moveRightBy(float amount)
  {
    setX(getX()+amount);
  }

  public void move()
  {
    setX(getX()+speedX);
    setY(getY()+speedY);
  }


  public void moveTo(int x, int y)
  {
    setX(x);
    setY(y);
  }

  
  public void moveInCurrentDirection()
  {
    int   currentDirection = getDirection();
    float currentDegrees = getDegreesFromCompassDirection(currentDirection);
    
    double radDegrees = 0;
    
    if (speed > 0)
    {
      float newX = 0;
      float newY = 0;
            
      if (currentDirection >= 0 && currentDirection <= 90)
      {
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
        currentDegrees = 270-currentDegrees;
        radDegrees = degreesToRadians(currentDegrees);
        newX = xRatio*((float) Math.abs(Math.sin(radDegrees)*speed));
        newY = yRatio*((float) Math.abs(Math.cos(radDegrees)*speed));
        newX = getX() - newX;
        newY = getY() + newY; 
      }
      else if (currentDirection > 270 && currentDirection < 360)
      {
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

}

/*
boolean drawImage(Image img, int x, int y, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer)
  boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer)
*/