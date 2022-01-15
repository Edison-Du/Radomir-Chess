package views.pages;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import views.components.ContentPanel;
import views.components.CustomButton;

import config.UserInterface;
import config.MessageTypes;
import network.InvalidMessageException;
import network.Message;
import network.ServerConnection;

public class JoinGame extends ContentPanel implements ActionListener {
    
    // Constants
    private final JLabel joinGameLabel= new JLabel();
    private final JTextField joinGameField = new JTextField();
    private String joinGameCode;

    public JoinGame() {
        joinGameLabel.setText("Username: ");
        joinGameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 300, 150, 25);
        this.add(joinGameLabel);

        joinGameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 320, 150, 25);
        this.add(joinGameField);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
       
    }
}