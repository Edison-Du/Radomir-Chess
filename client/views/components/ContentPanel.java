package views.components;

import javax.swing.JPanel;

import config.PathConsts;
import config.UserInterface;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Graphics;

import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/**
 * [ContentPanel.java]
 * Any panel that is displayed to the right of the navigation bar
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class ContentPanel extends JPanel {
    private final Image logoImage = getLogoImage();

    /**
     * ContentPanel
     * Set panel settings
     */
    public ContentPanel() {
        this.setBackground(UserInterface.FRAME_COLOUR);
        this.setPreferredSize(
            new Dimension(UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT)
            );
        this.setBounds(UserInterface.NAVBAR_WIDTH, 0,  UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT);
        this.setLayout(null);
    }

    /**
     * paintComponent
     * Draws the application logo in the background of the panel
     * @param g the graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logoImage, UserInterface.BACKGROUND_LOGO_X, UserInterface.BACKGROUND_LOGO_Y, this);
        this.revalidate();
        this.repaint();
    }

    /**
     * getLogoImage
     * Reads the logo image
     * @return the logo image
     */
    public static Image getLogoImage() {
        try {
            return ImageIO.read(new File(PathConsts.CHESS_LOGO)).getScaledInstance(UserInterface.LOGO_WIDTH, UserInterface.LOGO_HEIGHT, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}