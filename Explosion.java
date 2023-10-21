import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Explosion extends MovableObject {

	// We can override any method that we want to change or
	// we can write completely new methods


	// We don't inherit constructors, so we have to write our own
	// We can call super to call our super class constructor    
    public Explosion(JPanel mainPanel, String filename, Image image, float x, float y, int width, int height)
    {
    	super(mainPanel, filename, image, x, y, width, height);
 	}

 	// notice that this was defined as abstract in the 
 	// MovableObject class
 	// optional
    public void move()
    {
    	// increase its size by 1 pixel in all directions
    	// NOTE: You have a width and height in your super class
    	
    	// ?????????
      super.setWidth(super.getWidth()+1);
      super.setHeight(super.getHeight()+1);
    	
    }
    
}