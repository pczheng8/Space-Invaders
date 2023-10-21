import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Barricade extends MovableObject {

	private int numHitsAllowed = 3;

    public Barricade(JPanel mainPanel, String filename, Image image, float x, float y, int width, int height)
    {
    	super(mainPanel, filename, image, x, y, width, height);
 	}

	public void move()
	{
     // Just kidding :). Barricade does not move
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