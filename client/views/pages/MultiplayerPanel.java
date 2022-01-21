package views.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import config.GameState;
import config.MessageTypes;
import config.UserInterface;
import network.Lobby;
import network.Message;
import network.ServerConnection;
import views.components.CustomButton;

public class MultiplayerPanel extends AbstractGamePanel {

    private String lobbyCode = "AAAAAAA";
    private boolean isHost;

    private int currentClient, otherClient;

    // Swing
    private JLabel codeLabel;
    private JLabel otherClientLabel;
    private CustomButton leaveLobby;

    private JLabel lobbyVisibilityLabel;

    public MultiplayerPanel() {

        // CHESS GAME
        setGameState(GameState.WAITING);

        // Lobby code
        codeLabel = new JLabel(lobbyCode);
        codeLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        codeLabel.setForeground(Color.WHITE);
        codeLabel.setBounds(660, 30, 100, 100);
        this.add(codeLabel);

        // Showing lobby status (who is in and not)
        otherClientLabel = new JLabel("You are alone in this lobby.");
        otherClientLabel.setForeground(Color.WHITE);
        otherClientLabel.setBounds(760, 30, 500, 100);
        this.add(otherClientLabel);

        // Leave lobby
        leaveLobby = new CustomButton("Leave");
        leaveLobby.setBounds(780, 630, 120, 30);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);

        this.lobbyVisibilityLabel = new JLabel();
        this.lobbyVisibilityLabel.setForeground(UserInterface.TEXT_COLOUR);
        this.lobbyVisibilityLabel.setBounds(100, 0, 400, 200);
        this.lobbyVisibilityLabel.setFont(UserInterface.USERNAME_FONT);
        this.add(lobbyVisibilityLabel);

        undoButton = new CustomButton("Takeback");
        undoButton.setBounds(0, 600, 150, 25);
        undoButton.addActionListener(this);
        this.add(undoButton);

        takebackButton = new CustomButton("Accept Takeback");
        takebackButton.setBounds(200, 600, 150, 25);
        takebackButton.addActionListener(this);

        this.hostName = new JLabel();
        this.hostName.setForeground(UserInterface.TEXT_COLOUR);
        this.hostName.setBounds(100, 0, 400, 200);
        this.hostName.setFont(UserInterface.USERNAME_FONT);
        this.add(hostName);
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        codeLabel.setText(lobbyCode);
        System.out.println("Lobby change");
    }

    public void setLobbyVisibility(String visibility) {
        // this.lobbyVisibilityLabel.setText(visibility.toUpperCase() + " LOBBY");;
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    public void setClient(int client) {
        this.currentClient = client;
    }

    // Not sure if this will be redisgned when we get to chess
    // For texting purposes this works.
    public void addOther(int client) {
        this.otherClient = client;

        if (isHost) {
            otherClientLabel.setText("Client #" + client + " is in this lobby.");
        } else {
            otherClientLabel.setText("Client #" + client + " is the host of this lobby.");
        }

        setGameState(GameState.ONGOING);
    }

    // Text
    public void addMessageFromOther(String message) {
        messagePanel.addTextMessage("Client " + otherClient + ": " + message);
    }

    public void addTakeback() {
        this.add(takebackButton);
    }

    public void removeTakeback() {
        this.remove(takebackButton);
    }

    @Override
    public void processMove(String t1, String t2, String p) {
        Message message = new Message(MessageTypes.CHESS_MOVE);
        message.addParam(t1);
        message.addParam(t2);
        message.addParam(p);
        ServerConnection.sendMessage(message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(getGameState());
        if (e.getSource() == takebackButton){
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_ACCEPTED));
            this.undoMove();
            removeTakeback();

        } else if (e.getSource() == undoButton){
            ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));

        } else if ((e.getSource() == resign) && (getGameState() == GameState.ONGOING)) {
            if (getPlayerColour() == 0) {
                setGameState(GameState.BLACK_VICTORY_RESIGN);
            } else {
                setGameState(GameState.WHITE_VICTORY_RESIGN);
            }
            boardPanel.gameResultOverlay.setMessage("You have resigned");
            ServerConnection.sendMessage(new Message(MessageTypes.RESIGNATION));

        } else if (e.getSource() == leaveLobby) {
            Message message = new Message(MessageTypes.LEAVE_GAME);
            ServerConnection.sendMessage(message);
        }
        this.revalidate();
        this.repaint();
    } 
}   