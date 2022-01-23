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
    private JLabel instructionsLabel = new JLabel();
    private PanelButton createPublicLobbyBtn;
    private PanelButton createPrivateLobbyBtn;

    public GameSetup(Window window) {
        this.window = window;
        this.setLayout(null);

        instructionsLabel.setFont(UserInterface.TEXT_FONT_1);
        instructionsLabel.setText("Choose Lobby Type: ");
        instructionsLabel.setForeground(UserInterface.TEXT_COLOUR);
        instructionsLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 120, UserInterface.WINDOW_HEIGHT / 2 - 115, 280, 30);
        this.add(instructionsLabel);

        //change and put the font in button class
        createPublicLobbyBtn = new PanelButton(
            "Public",
            150,
            365
        );
        createPublicLobbyBtn.addActionListener(this);
        createPublicLobbyBtn.setFont(UserInterface.PLAY_BUTTONS_FONT);
        this.add(createPublicLobbyBtn);

        //change constants
        createPrivateLobbyBtn = new PanelButton(
            "Private",
            550,
            365
        );
        createPrivateLobbyBtn.addActionListener(this);
        createPrivateLobbyBtn.setFont(UserInterface.PLAY_BUTTONS_FONT);
        this.add(createPrivateLobbyBtn);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        Message createLobby = new Message(MessageTypes.CREATE_GAME);
        if (e.getSource() == createPublicLobbyBtn) {
            createLobby.addParam("public");
        } else if (e.getSource() == createPrivateLobbyBtn) {
            createLobby.addParam("private");
        }
        ServerConnection.sendMessage(createLobby);
        window.changePage(Page.GAME);
    }
}