package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

public class MessagePanel extends ContentPanel implements ActionListener {

    private JTextField messageField;
    private DefaultListModel<String> allTexts = new DefaultListModel<>();
    private JScrollPane pane;
    private JPanel messageListPanel;
    private JList<String> messageList;

    private int numMessages = 0;

    public MessagePanel() {

        // this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        messageListPanel = new JPanel();
        messageListPanel.setBounds(0, 0, 240, 270);
        messageListPanel.setLayout(new BoxLayout(messageListPanel, BoxLayout.X_AXIS));
        messageList = new JList<>(allTexts);
        pane = new JScrollPane(messageList);
        // pane.setBounds(0, 0, 240, 330);

        messageListPanel.add(pane);        
        this.add(messageListPanel);

        messageField = new JTextField();
        messageField.setBounds(0, 300, 240, 30);
        messageField.addActionListener(this);
        this.add(messageField);

        // messageList = new JList<>(allTexts);
        // messageList.setBounds(0, 0, 240, 240);
        
        // pane = new JScrollPane(messageList);
        // pane.setBounds(0, 0, 240, 240);
        // pane.add(messageList);

        // messageListPanel = new JPanel();
        // messageListPanel.setLayout(new BorderLayout());

        // messageListPanel.setBounds(0, 0, 240, 270);

        // messageListPanel.add(pane, BorderLayout.CENTER);
        // this.add(messageListPanel);
    }

    public void addTextMessage(String message) {
        allTexts.addElement(message);
        messageList.ensureIndexIsVisible(this.numMessages++);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == messageField) {
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