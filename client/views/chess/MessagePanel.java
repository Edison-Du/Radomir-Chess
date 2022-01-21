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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomTextField;

public class MessagePanel extends ContentPanel implements ActionListener {

    // private JTextField messageField;
    private CustomTextField messageField;
    private DefaultListModel<String> allTexts = new DefaultListModel<>();
    private JScrollPane pane;
    private JPanel messageListPanel;
    private JList<String> messageList;

    private int numMessages = 0;

    public MessagePanel() {

        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        // messageListPanel (JPanel) contains pane (JScrollPane) which contains messageList (JList)
        messageList = new JList<>(allTexts);
        messageList.setBackground(UserInterface.NAVBAR_COLOUR);
        messageList.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);

        pane = new JScrollPane(messageList);
        pane.setBackground(UserInterface.NAVBAR_COLOUR);
        pane.setBorder(UserInterface.GAME_CHAT_MARGIN);

        messageListPanel = new JPanel();
        messageListPanel.setBounds(0, 0, UserInterface.GAME_SIDE_PANEL_WIDTH, 270);
        messageListPanel.setLayout(new BoxLayout(messageListPanel, BoxLayout.X_AXIS));

        messageListPanel.setBorder(UserInterface.GAME_CHAT_BORDER);
        messageListPanel.setBackground(UserInterface.NAVBAR_COLOUR);
        messageListPanel.add(pane);   

        this.add(messageListPanel);

        // For entering message
        messageField = new CustomTextField("Send message");
        messageField.setBounds(0, 270, UserInterface.GAME_SIDE_PANEL_WIDTH, 30);
        messageField.setBackground(UserInterface.GAME_SIDE_HIGHLIGHT_COLOR);
        messageField.setForeground(UserInterface.CHAT_MESSAGE_COLOUR);
        messageField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        messageField.setCaretColor(Color.WHITE);
        messageField.setBorder(BorderFactory.createCompoundBorder(
            UserInterface.GAME_CHAT_BORDER,
            UserInterface.GAME_TEXTFIELD_MARGIN));
        messageField.addActionListener(this);
        this.add(messageField);
    }

    public void addTextMessage(String message) {
        allTexts.addElement(message);
        messageList.ensureIndexIsVisible(this.numMessages++);
        this.revalidate();
        // this.repaint();
    }

    public void clearMessages() {
        allTexts.clear();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if ( (e.getSource() == messageField) && (messageField.getText().length() > 0) ) {
            String userMessage = messageField.getText();
            messageField.setText("");

            try {
                Message message = new Message(MessageTypes.SENT_TEXT);
                message.addParam(userMessage);
                ServerConnection.sendMessage(message);

                addTextMessage("You: " + userMessage);
                
            } catch(Exception ex) {
                System.out.println("Failed to create message");
                ex.printStackTrace();
            }
        }
    } 
}
