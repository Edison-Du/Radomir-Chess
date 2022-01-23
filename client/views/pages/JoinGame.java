package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

public class JoinGame extends ContentPanel implements ActionListener {
    
    // Constants
    private final JLabel joinGameLabel= new JLabel();
    private final JTextField joinGameField = new JTextField();

    public JoinGame() {
        joinGameLabel.setFont(UserInterface.TEXT_FONT_1);
        joinGameLabel.setText("Lobby Code: ");
        joinGameLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinGameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 70, 280, 30);
        this.add(joinGameLabel);

        joinGameField.setFont(UserInterface.TEXT_FONT_1);
        joinGameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 15, 280, 30);
        joinGameField.addActionListener(this);
        this.add(joinGameField);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        String joinGameCode = joinGameField.getText();

        //Validates code
        if (joinGameCode.length() != 4) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (!Character.isDigit(joinGameCode.charAt(i))) {
                return;
            }
        }

        Message message = new Message(MessageTypes.JOIN_GAME);
        message.addParam(joinGameCode);
        ServerConnection.sendMessage(message);
    }
}