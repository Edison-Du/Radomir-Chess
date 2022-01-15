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
    private final CustomButton joinGameButton = new CustomButton("Join");

    public JoinGame() {
        joinGameLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        joinGameLabel.setText("Room Code: ");
        joinGameLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinGameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 100, UserInterface.WINDOW_HEIGHT / 2 - 70, 210, 30);
        this.add(joinGameLabel);

        joinGameField.setFont(UserInterface.JOIN_GAME_FONT_2);
        joinGameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 100, UserInterface.WINDOW_HEIGHT / 2 - 15, 200, 30);
        this.add(joinGameField);

        joinGameButton.setFont(UserInterface.JOIN_GAME_FONT_2);
        joinGameButton.setBounds(UserInterface.CONTENT_WIDTH / 2 - 100, UserInterface.WINDOW_HEIGHT / 2 + 50, 200, 30);
        // joinGameButton.setHoverColor();
        // joinGameButton.setBorder(new EmptyBorder(0, 0, 0, 0));
        // joinGameButton.setBackground(new Color());
        joinGameButton.addActionListener(this);
        joinGameButton.setFocusable(false);
        this.add(joinGameButton);
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

        try{
            Message m = new Message(MessageTypes.JOIN_GAME);
            m.addParam(joinGameCode);
            ServerConnection.sendMessage(m);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}