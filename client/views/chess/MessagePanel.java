package views.chess;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomTextField;

/**
 * [MessagePanel.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class MessagePanel extends ContentPanel implements ActionListener {

    private final EmptyBorder MESSAGE_FIELD_MARGIN = new EmptyBorder(5, 10, 0, 10);
    private final int MESSAGE_PANEL_HEIGHT = 270;
    private final int MESSAGE_FIELD_HEIGHT = 30;
    private final int MESSAGE_FIELD_PLACEHOLDER_Y = 20;

    private CustomTextField messageField;
    private DefaultListModel<String> allTexts = new DefaultListModel<>();
    private JScrollPane pane;
    private JPanel messageListPanel;
    private JList<String> messageList;

    private int numMessages = 0;

    public MessagePanel() {

        // JList to store messages
        messageList = new JList<>(allTexts);
        messageList.setFont(UserInterface.orkney12);
        messageList.setBackground(UserInterface.NAVBAR_COLOUR);
        messageList.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);

        // Scroll pane containing message list
        pane = new JScrollPane(messageList);
        pane.setBackground(UserInterface.NAVBAR_COLOUR);
        pane.setBorder(UserInterface.GAME_CHAT_MARGIN);

        // Panel containing scroll pane
        messageListPanel = new JPanel();
        messageListPanel.setBounds(0, 0, UserInterface.GAME_SIDE_PANEL_WIDTH, MESSAGE_PANEL_HEIGHT);
        messageListPanel.setLayout(new BoxLayout(messageListPanel, BoxLayout.X_AXIS));
        messageListPanel.setBorder(UserInterface.GAME_CHAT_BORDER);
        messageListPanel.setBackground(UserInterface.NAVBAR_COLOUR);
        messageListPanel.add(pane);   
        this.add(messageListPanel);

        // Text field to enter messages
        messageField = new CustomTextField("Send message");
        messageField.setPlaceholderY(MESSAGE_FIELD_PLACEHOLDER_Y);
        messageField.setFont(UserInterface.orkney12);
        messageField.setBounds(0, MESSAGE_PANEL_HEIGHT, UserInterface.GAME_SIDE_PANEL_WIDTH, MESSAGE_FIELD_HEIGHT);
        messageField.setBackground(UserInterface.GAME_SIDE_HIGHLIGHT_COLOR);
        messageField.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        messageField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            UserInterface.GAME_CHAT_BORDER,
            MESSAGE_FIELD_MARGIN));
        messageField.addActionListener(this);
        this.add(messageField);
    }

    public void addTextMessage(String message) {
        allTexts.addElement(message);
        messageList.ensureIndexIsVisible(this.numMessages++);
        this.revalidate();
    }

    public void clearMessages() {
        allTexts.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if ( (e.getSource() == messageField) && (messageField.getText().length() > 0) ) {
            String userMessage = messageField.getText();
            messageField.setText("");

            Message message = new Message(MessageTypes.SENT_TEXT);
            message.addParam(userMessage);
            ServerConnection.sendMessage(message);

            addTextMessage("You: " + userMessage);
        }
    } 
}