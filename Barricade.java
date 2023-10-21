import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Barricade extends MovableObject {

	// We can override any method that we want to change or
	// we can write completely new methods

	private int numHitsAllowed = 3;
	

	// We don't inherit constructors, so we have to write our own
	// We can call super to call our super class constructor    
    public Barricade(JPanel mainPanel, String filename, Image image, float x, float y, int width, int height)
    {
    	super(mainPanel, filename, image, x, y, width, height);
 	}


	// FINISH ME
	// write the move() method
	// since it was abstract in the MovableObject class
	public void move()
	{
     // Just kidding.  A Barricade does not move!
     
	}
 

	public void setNumHitsAllowed(int numHitsAllowed)
	{
		this.numHitsAllowed = numHitsAllowed;
	}

	public int getNumHitsAllowed()
	{
	 	return numHitsAllowed;
	}

	public void decrementNumHitsAllowed()
	{
		this.numHitsAllowed--;
	}
    
}