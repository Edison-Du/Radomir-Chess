package views.pages;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

import config.MessageTypes;
import network.Message;
import network.ServerConnection;

public class MultiplayerPanel extends AbstractGamePanel {

    private String lobbyCode = "";
    private boolean isHost;

    private int currentClient, otherClient;

    // Swing
    private JLabel codeLabel;
    private JLabel otherClientLabel;
    private JButton leaveLobby;


    // // subpanel chess game
    // public ChessBoardPanel subPanel;
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


        // Leave lobby
        leaveLobby = new JButton("Leave");
        leaveLobby.setBounds(780, 630, 120, 30);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);
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

    // Text input
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == leaveLobby) {
            
            try {
                Message message = new Message(MessageTypes.LEAVE_GAME);
                ServerConnection.sendMessage(message);

            } catch (Exception ex) {
                System.out.println("Failed to create message");
                ex.printStackTrace();
            }
        }
    } 
}