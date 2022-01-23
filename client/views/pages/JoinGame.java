package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import config.MessageTypes;
import config.Page;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import views.components.PanelButton;

public class JoinGame extends ContentPanel implements ActionListener {
    
    // Constants
    private final JLabel joinGameLabel= new JLabel();
    private final JTextField joinGameField = new JTextField();
    private PanelButton backButton;
    private Window window;

    public JoinGame(Window window) {

        this.window = window;

        joinGameLabel.setFont(UserInterface.TEXT_FONT_1);
        joinGameLabel.setText("Lobby Code: ");
        joinGameLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinGameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 70, 280, 30);
        this.add(joinGameLabel);

        joinGameField.setFont(UserInterface.TEXT_FONT_1);
        joinGameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 15, 280, 30);
        joinGameField.addActionListener(this);
        this.add(joinGameField);

        this.backButton = new PanelButton("Back", 40, 40);
        this.backButton.addActionListener(this);
        this.backButton.setFont(UserInterface.PLAY_BUTTONS_FONT);
        this.add(backButton);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameField) {
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
            
        } else if (e.getSource() == backButton) {
            joinGameField.setText("");
            window.changePage(Page.PLAY);
        }
    }
}