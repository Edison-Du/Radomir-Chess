package views.pages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

import views.components.ContentPanel;
import views.components.CustomButton;

import config.UserInterface;
import config.MessageTypes;
import network.InvalidMessageException;
import network.Message;
import network.ServerConnection;

public class Settings extends ContentPanel {
    // Constants
    private final JLabel titleLabel = new JLabel();

    public Settings() {
        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("Settings");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
