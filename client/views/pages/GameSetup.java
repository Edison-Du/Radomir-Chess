package views.pages;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;

import config.UserInterface;
import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import config.Page;

public class GameSetup extends ContentPanel implements ActionListener {

    private Window window;

    private String[] lobbyOptions = {"Public", "Private"};
    private JComboBox<String> lobbyVisibilitySelection = new JComboBox<>(lobbyOptions);
    private JButton playButton = new JButton();

    public GameSetup(Window window) {
        this.window = window;
        this.setLayout(null);

        lobbyVisibilitySelection.setBounds(650, 250, 115, 35);
        lobbyVisibilitySelection.setForeground(UserInterface.FRAME_COLOUR);
        lobbyVisibilitySelection.addActionListener(this);
        lobbyVisibilitySelection.setVisible(true);
        this.add(lobbyVisibilitySelection);
        

        playButton.setBounds(UserInterface.CONTENT_WIDTH / 2 + 195, UserInterface.WINDOW_HEIGHT / 2 + 20, 150, 25);
        playButton.addActionListener(this);
        playButton.setText("PLAY");
        this.add(playButton);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playButton) {
            Message createLobby = new Message(MessageTypes.CREATE_GAME);
            if (lobbyVisibilitySelection.getSelectedIndex() == 0) {
                createLobby.addParam("public");
            } else if (lobbyVisibilitySelection.getSelectedIndex() == 1) {
                createLobby.addParam("private");
            }
            ServerConnection.sendMessage(createLobby);
            window.changePage(Page.GAME);
        }
    }
}