import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;


public class Alien extends MovableObject {

    public Alien(JPanel mainPanel, String filename, Image image, float x, float y, int width,
            int height) {
        super(mainPanel, filename, image, x, y, width, height);
    }

    public void move() {
        super.moveInCurrentDirection();
    }

}