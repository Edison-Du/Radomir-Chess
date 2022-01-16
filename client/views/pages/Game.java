package views.pages;

import java.awt.Color;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;

import config.MessageTypes;
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
    private DefaultListModel<String> allTexts = new DefaultListModel<>();

    public Game() {

        // Lobby code
        codeLabel = new JLabel(lobbyCode);
        codeLabel.setFont(new Font("Serif", Font.ITALIC, 36));
        codeLabel.setForeground(Color.WHITE);
        codeLabel.setBounds(100, 0, 100, 100);
        this.add(codeLabel);

        // Showing lobby status (who is in and not)
        otherClientLabel = new JLabel("You are alone in this lobby.");
        otherClientLabel.setForeground(Color.WHITE);
        otherClientLabel.setBounds(200, 0,500, 100);
        this.add(otherClientLabel);

        // Texting field
        messageField = new JTextField();
        messageField.setBounds(100, 100, 500, 40);
        messageField.addActionListener(this);
        this.add(messageField);

        messageList = new JList<>(allTexts);
        messageList.setBounds(100, 150, 500, 400);
        this.add(messageList);
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
        this.otherClient = otherClient;

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
    } 
}