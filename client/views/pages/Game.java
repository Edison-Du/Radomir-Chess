package views.pages;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.MessageTypes;
import logicai.ChessGame;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomButton;

public class Game extends ContentPanel implements ActionListener {

    private String lobbyCode = "";
    private boolean isHost;

    private int currentClient, otherClient;

    // Swing
    private JLabel codeLabel;
    private JLabel otherClientLabel;
    private JTextField messageField;
    private JList<String> messageList;
    private JButton leaveLobby;
    private DefaultListModel<String> allTexts = new DefaultListModel<>();

    // subpanel chess game
    public MultiPanel subPanel;

    public Game() {

        // CHESS GAME HERE???
        ChessGame chessGame = new ChessGame();
        subPanel = new MultiPanel(chessGame); // sub-panel 1
        this.add(subPanel);

        subPanel.setBounds(120,120,480,480);

        // Lobby code
        codeLabel = new JLabel(lobbyCode);
        codeLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        codeLabel.setForeground(Color.WHITE);
        codeLabel.setBounds(660, 30, 100, 100);
        this.add(codeLabel);

        // Showing lobby status (who is in and not)
        otherClientLabel = new JLabel("You are alone in this lobby.");
        otherClientLabel.setForeground(Color.WHITE);
        otherClientLabel.setBounds(760, 30,500, 100);
        this.add(otherClientLabel);

        // Texting field
        messageField = new JTextField();
        messageField.setBounds(660, 570, 240, 30);
        messageField.addActionListener(this);
        this.add(messageField);

        // Message list
        messageList = new JList<>(allTexts);
        messageList.setBounds(660,120,240,420);
        this.add(messageList);

        // Leave lobby
        leaveLobby = new JButton("Leave");
        leaveLobby.setBounds(600, 550, 200, 50);
        leaveLobby.addActionListener(this);
        this.add(leaveLobby);
    }





    public void setLobbyCode(String code) {
        this.lobbyCode = code;
        codeLabel.setText(lobbyCode);
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

    public void addTextMessageFromOther(String message) {
        allTexts.addElement("Client " + otherClient + ": " + message);
    }

    // Text input
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == messageField) {
            String userMessage = messageField.getText();
            messageField.setText("");

            try {
                Message message = new Message(MessageTypes.SENT_TEXT);
                message.addParam(userMessage);
                ServerConnection.sendMessage(message);

                allTexts.addElement("You: " + userMessage);
                
            } catch(Exception ex) {
                System.out.println("Failed to create message");
                ex.printStackTrace();
            }
        } else if (e.getSource() == leaveLobby) {
            
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