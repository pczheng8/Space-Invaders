import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Ship extends MovableObject {

    public Ship(JPanel mainPanel, String filename, Image image, float x, float y, int width, int height)
    {
    	super(mainPanel, filename, image, x, y, width, height);
 	}

	// FINISH ME
	// finish writing the move() method
	// since it was abstract in the MovableObject class
	
 	// this method is an example of polymorphism
 	// since in the main program it is of type
 	// MovableObject ship;  (a super class reference)
 	// but yet at runtime it will call this method,
 	//    NOT the method in the MovableObject class
 	// this method overrides the same method in the MovableObject class
 	
 	 
    public void move()
    {
    	int dir = getDirection(); // where is this defined???????
    	// this should return 90 degrees or 270 degrees
    	// Look for methods to call in the super class

      if(dir==90)
        moveRight();
      else
        moveLeft();
      
      
    }
    
}