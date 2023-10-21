public interface MovableInterface {
    
    // NOTE THAT THE KEYWORD public is optional 
    // when it is defined in an interface
    // THE accessor keyword public is automatic
    //     in an interface
    // YOU CANNOT MARK IT private, if you do you
    // will get an error!
    
    // NOTE THAT THE KEYWORD abstract is optional 
    // when it is defined in an interface
    
    // YOU WILL NEED TO REMOVE THE KEYWORD abstract when
    //    you write the method in class MovableObject
    //    but you must keep the keyword public
    
    
    public abstract float getX();

    public abstract void setX(float x);

    public abstract float getY();

    public abstract void setY(float y);

    public abstract void setXY(float x, float y);

    public abstract int getWidth();

    public abstract void setWidth(int width);

    public abstract int getHeight();

    public abstract void setHeight(int height);



		// direction methods (uses speed value for the amount to move)

		// NOTE THAT WE DON'T HAVE TO MARK THE METHOD AS 
		// ABSTRACT SINCE IT IS AUTOMATICALLY ABSTRACT IN
		// THE INTERFACE
    public int getDirection();

    public void setDirection(int direction);



	  // time methods 

    public abstract float getTime();
    
	  public abstract void setTime(float time);



  	// hidden methods 
	
    public abstract boolean getHidden();

    public abstract boolean isHidden();

    public abstract void setHidden(boolean hidden);
  


	  // speedX and speedY methods used for moving left, right, up, and down

    public int getSpeedX();

    public void setSpeedX(int speedX);

    public int getSpeedY();

    public void setSpeedY(int speedY);
    	
    public void setSpeedXY(int speedX, int speedY);





	  // speed methods used for moving in a direction

    public void setSpeed(int speed);

    public int getSpeed();
  
  
   
		// cloaking methods (extra credit only)
		// but you still have to define the methods
		// in the MovableObject class

		public void setCloaked(boolean cloaked);	

		public boolean getCloaked();
	
		public boolean isCloaked();

    
}