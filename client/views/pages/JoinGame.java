package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.Page;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.Window;
import views.components.ContentPanel;
import views.components.CustomTextField;
import views.components.PanelButton;

public class JoinGame extends ContentPanel implements ActionListener {

    // Constants
    private final EmptyBorder TEXT_FIELD_MARGIN = new EmptyBorder(7, 5, 0, 5);
    private final int TEXT_FIELD_PLACEHOLDER_Y = 33;
    private final int JOIN_LABEL_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int JOIN_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 90;
    private final int JOIN_LABEL_WIDTH = 280;
    private final int JOIN_LABEL_HEIGHT = 50;
    private final int JOIN_TEXT_FIELD_Y = UserInterface.WINDOW_HEIGHT / 2 - 15;
    private final int JOIN_ERROR_Y = JOIN_LABEL_Y + 30;
    
    private final int CODE_LENGTH = 4;

    private final JLabel joinGameLabel  = new JLabel();
    private final JLabel joinErrorLabel = new JLabel();
    private final CustomTextField joinGameField = new CustomTextField();

    private PanelButton backButton;
    private Window window;

    public JoinGame(Window window) {

        this.window = window;

        joinGameLabel.setFont(UserInterface.orkney36);
        joinGameLabel.setText("Enter lobby code");
        joinGameLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinGameLabel.setBounds(JOIN_LABEL_X, JOIN_LABEL_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
        this.add(joinGameLabel);

        joinGameField.setPlaceholder("Type code here");
        joinGameField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        joinGameField.setFont(UserInterface.orkney24);
        joinGameField.setBorder(TEXT_FIELD_MARGIN);
        joinGameField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        joinGameField.setBounds(JOIN_LABEL_X, JOIN_TEXT_FIELD_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
        joinGameField.addActionListener(this);
        this.add(joinGameField);

        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);

        joinErrorLabel.setFont(UserInterface.orkney18);
        joinErrorLabel.setForeground(UserInterface.ERROR_COLOUR);
        joinErrorLabel.setBounds(JOIN_LABEL_X, JOIN_ERROR_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
    }

    public void displayError(String errorMessage) {
        joinGameField.setText("");
        joinErrorLabel.setText(errorMessage);
        this.add(joinErrorLabel);
        this.revalidate();
    }

    public void removeError() {
        this.remove(joinErrorLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joinGameField) {
            String joinGameCode = joinGameField.getText();

            //Validates code
            if (joinGameCode.length() != CODE_LENGTH) {
                displayError("Please enter a 4-digit number");
                return;
            }
            for (int i = 0; i < CODE_LENGTH; i++) {
                if (!Character.isDigit(joinGameCode.charAt(i))) {
                    displayError("Please enter a 4-digit number");
                    return;
                }
            }

            Message message = new Message(MessageTypes.JOIN_GAME);
            message.addParam(joinGameCode);
            ServerConnection.sendMessage(message);
            
        } else if (e.getSource() == backButton) {
            joinGameField.setText("");
            removeError();
            window.changePage(Page.PLAY);
        }
    }
}