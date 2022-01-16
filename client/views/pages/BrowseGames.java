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

public class BrowseGames extends ContentPanel {
    // Constants
    private final JLabel titleLabel = new JLabel();
    private final JList lobbyList;

    public BrowseGames() {
        String[] testLobbies = {"game 1", "game 2", "game 3"};

        lobbyList = new JList(testLobbies);
        lobbyList.setBounds(0, 0, UserInterface.WINDOW_WIDTH / 2, UserInterface.WINDOW_HEIGHT);
        this.add(lobbyList);

        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("sussy wussy");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 50, UserInterface.WINDOW_HEIGHT / 2 + 20, 210, 30);
        this.add(titleLabel);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
