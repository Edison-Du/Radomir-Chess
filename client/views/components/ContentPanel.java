package views.components;

import javax.swing.JPanel;

import config.UserInterface;

import java.awt.Dimension;

public class ContentPanel extends JPanel {
    public ContentPanel() {
        this.setBackground(UserInterface.FRAME_COLOUR);
        this.setPreferredSize(
            new Dimension(UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT)
            );
        this.setBounds(UserInterface.NAVBAR_WIDTH, 0,  UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT);
        this.setLayout(null);
    }
}