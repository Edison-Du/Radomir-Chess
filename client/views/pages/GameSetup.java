package views.pages;


import javax.swing.JLabel;

import java.awt.event.ActionListener;
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

    private final int INSTRUCTION_LABEL_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int INSTRUCTION_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 95;
    private final int INSTRUCTION_LABEL_WIDTH = 280;
    private final int INSTRUCTION_LABEL_HEIGHT = 50;
    private final int PUBLIC_BUTTON_Y = 330;
    private final int PRIVATE_BUTTON_Y = 420;

    private Window window;
    private JLabel instructionsLabel = new JLabel();
    private PanelButton createPublicLobbyBtn;
    private PanelButton createPrivateLobbyBtn;
    private PanelButton backButton;

    public GameSetup(Window window) {
        this.window = window;
        this.setLayout(null);

        instructionsLabel.setFont(UserInterface.orkney30);
        instructionsLabel.setText("Choose Lobby Type");
        instructionsLabel.setForeground(UserInterface.TEXT_COLOUR);
        instructionsLabel.setBounds(INSTRUCTION_LABEL_X, INSTRUCTION_LABEL_Y, INSTRUCTION_LABEL_WIDTH, INSTRUCTION_LABEL_HEIGHT);
        this.add(instructionsLabel);

        createPublicLobbyBtn = new PanelButton(
            "Public",
            INSTRUCTION_LABEL_X,
            PUBLIC_BUTTON_Y
        );
        createPublicLobbyBtn.addActionListener(this);
        this.add(createPublicLobbyBtn);

        createPrivateLobbyBtn = new PanelButton(
            "Private",
            INSTRUCTION_LABEL_X,
            PRIVATE_BUTTON_Y
        );
        createPrivateLobbyBtn.addActionListener(this);
        this.add(createPrivateLobbyBtn);

        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);
    }

    @Override
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