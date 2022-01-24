package views.components;

import javax.swing.JPanel;

import config.PathsConsts;
import config.UserInterface;

import java.awt.Dimension;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics;

public class ContentPanel extends JPanel {
    private final Image logoImage = getLogoImage();

    public ContentPanel() {
        this.setBackground(UserInterface.FRAME_COLOUR);
        this.setPreferredSize(
            new Dimension(UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT)
            );
        this.setBounds(UserInterface.NAVBAR_WIDTH, 0,  UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT);
        this.setLayout(null);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(logoImage, UserInterface.BACKGROUND_LOGO_X, UserInterface.BACKGROUND_LOGO_Y, this);
        this.revalidate();
        this.repaint();
    }

    public static Image getLogoImage() {
        try {
            return ImageIO.read(new File(PathsConsts.CHESS_LOGO)).getScaledInstance(UserInterface.LOGO_WIDTH, UserInterface.LOGO_HEIGHT, Image.SCALE_DEFAULT);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}