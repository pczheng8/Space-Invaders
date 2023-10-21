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
 	 
    public void move()
    {
    	int dir = getDirection();

      if(dir==90)
        moveRight();
      else
        moveLeft();
      
      
    }
    
}