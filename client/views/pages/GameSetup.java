package views.pages;

import java.awt.event.ActionListener;

import javax.swing.JLabel;

import java.awt.event.ActionEvent;

import config.UserInterface;
import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import views.components.PanelButton;
import config.Page;

public class GameSetup extends ContentPanel implements ActionListener {

    private Window window;
    private JLabel instructionsLabel = new JLabel();
    private PanelButton createPublicLobbyBtn;
    private PanelButton createPrivateLobbyBtn;
    private PanelButton backButton;

    public GameSetup(Window window) {
        this.window = window;
        this.setLayout(null);

        instructionsLabel.setFont(UserInterface.TEXT_FONT_1);
        instructionsLabel.setText("Choose Lobby Type: ");
        instructionsLabel.setForeground(UserInterface.TEXT_COLOUR);
        instructionsLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 115, UserInterface.WINDOW_HEIGHT / 2 - 95, 280, 30);
        this.add(instructionsLabel);

        //change and put the font in button class
        createPublicLobbyBtn = new PanelButton(
            "Public",
            UserInterface.CONTENT_WIDTH / 2 - 140,
            330
        );
        createPublicLobbyBtn.addActionListener(this);
        this.add(createPublicLobbyBtn);

        //change constants
        createPrivateLobbyBtn = new PanelButton(
            "Private",
            UserInterface.CONTENT_WIDTH / 2 - 140,
            420
        );
        createPrivateLobbyBtn.addActionListener(this);
        this.add(createPrivateLobbyBtn);

        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            window.changePage(Page.PLAY);
        } else if (e.getSource() == createPublicLobbyBtn) {
            Message createLobby = new Message(MessageTypes.CREATE_GAME);
            createLobby.addParam("public");
            window.changePage(Page.GAME);
            ServerConnection.sendMessage(createLobby);
        } else if (e.getSource() == createPrivateLobbyBtn) {
            Message createLobby = new Message(MessageTypes.CREATE_GAME);
            createLobby.addParam("private");
            window.changePage(Page.GAME);
            ServerConnection.sendMessage(createLobby);
        }
    }
}