package views.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.CustomButton;

public class MultiplayerPanel extends AbstractGamePanel {

    private String lobbyCode = "";
    private boolean isHost;

    private int currentClient, otherClient;

    // Swing
    private JLabel codeLabel;
    private JLabel otherClientLabel;
    private CustomButton leaveLobby;

    private JLabel localUsername, enemyUsername;

    private CustomButton undoButton;
    private CustomButton takeBackButton;

    // // subpanel chess game
    public ChessBoardPanel subPanel;
    // public MovesPanel movesPanel;
    // public MessagePanel messagePanel;

    public MultiplayerPanel() {

        // CHESS GAME
        // ChessGame chessGame = new ChessGame();
        // subPanel = new ChessBoardPanel(chessGame, this); // sub-panel 1
        // this.add(subPanel);

        // subPanel.setBounds(120,120,480,480);

        // Lobby code
        codeLabel = new JLabel(lobbyCode);
        codeLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        codeLabel.setForeground(Color.WHITE);
        codeLabel.setBounds(660, 30, 100, 100);
        this.add(codeLabel);

        System.out.println("Construction");

        // Showing lobby status (who is in and not)
        otherClientLabel = new JLabel("You are alone in this lobby.");
        otherClientLabel.setForeground(Color.WHITE);
        otherClientLabel.setBounds(760, 30,500, 100);
        this.add(otherClientLabel);

        // Message panel
        // messagePanel = new MessagePanel();
        // messagePanel.setBounds(660,270,240,330);
        // this.add(messagePanel);

        this.localUsername = new JLabel();
        this.localUsername.setForeground(UserInterface.TEXT_COLOUR);
        this.localUsername.setBounds(UserInterface.NAVBAR_WIDTH / 2 - 70, UserInterface.WINDOW_HEIGHT - 45, 200, 25);
        this.localUsername.setFont(UserInterface.USERNAME_FONT);
        this.localUsername.setText(UserInterface.GUEST);

        // Leave lobby
        leaveLobby = new CustomButton("Leave");
        leaveLobby.setBounds(780, 630, 120, 30);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);

        undoButton = new CustomButton("Takeback");
        undoButton.setBounds(0, 600, 150, 25);
        undoButton.addActionListener(this);
        this.add(undoButton);

        takeBackButton = new CustomButton("Accept Takeback");
        takeBackButton.setBounds(200, 600, 150, 25);
        takeBackButton.addActionListener(this);
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        codeLabel.setText(lobbyCode);
        System.out.println("Lobby change");
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
    }

    // Text
    public void addMessageFromOther(String message) {
        messagePanel.addTextMessage("Client " + otherClient + ": " + message);
    }

    public void addTakeback() {
        System.out.println("HRYY");
        this.add(takeBackButton);
    }

    public void removeTakeBack() {
        this.remove(takeBackButton);
    }

    @Override
    public void processMove(String t1, String t2, String p) {
        try {
            Message message = new Message(MessageTypes.CHESS_MOVE);
            message.addParam(t1);
            message.addParam(t2);
            message.addParam(p);
            ServerConnection.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            if (e.getSource() == takeBackButton){
                ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_ACCEPTED));
                this.undoMove();
                removeTakeBack();
            }
            if (e.getSource() == undoButton){
                ServerConnection.sendMessage(new Message(MessageTypes.TAKEBACK_REQUESTED));
            } else if (e.getSource() == leaveLobby) {
                Message message = new Message(MessageTypes.LEAVE_GAME);
                ServerConnection.sendMessage(message);
            }
        } catch (Exception ex) {
            System.out.println("Failed to create message");
            ex.printStackTrace();
        }
    } 
}