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

/**
 * [JoinGame.java]
 * A page that allows the user to join a game with a certain code
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class JoinGame extends ContentPanel implements ActionListener {

    private final int CODE_LENGTH = 4;

    // UI Constants
    private final EmptyBorder TEXT_FIELD_MARGIN = new EmptyBorder(7, 5, 0, 5);
    private final int TEXT_FIELD_PLACEHOLDER_Y = 33;
    private final int JOIN_LABEL_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int JOIN_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 90;
    private final int JOIN_LABEL_WIDTH = 280;
    private final int JOIN_LABEL_HEIGHT = 50;
    private final int JOIN_TEXT_FIELD_Y = UserInterface.WINDOW_HEIGHT / 2 - 15;
    private final int JOIN_ERROR_Y = JOIN_LABEL_Y + 30;

    // JComponents
    private JLabel joinGameLabel  = new JLabel();
    private JLabel joinErrorLabel = new JLabel();
    private CustomTextField joinGameField = new CustomTextField();
    private PanelButton backButton;

    private Window window;

    /**
     * JoinGame
     * Creates the panel with necessary input fields and buttons
     * @param window the window this page is on
     */
    public JoinGame(Window window) {

        this.window = window;

        // Join game label
        joinGameLabel.setFont(UserInterface.orkney36);
        joinGameLabel.setText("Enter lobby code");
        joinGameLabel.setForeground(UserInterface.TEXT_COLOUR);
        joinGameLabel.setBounds(JOIN_LABEL_X, JOIN_LABEL_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
        this.add(joinGameLabel);

        // Join game input field
        joinGameField.setPlaceholder("Type code here");
        joinGameField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        joinGameField.setFont(UserInterface.orkney24);
        joinGameField.setBorder(TEXT_FIELD_MARGIN);
        joinGameField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        joinGameField.setBounds(JOIN_LABEL_X, JOIN_TEXT_FIELD_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
        joinGameField.addActionListener(this);
        this.add(joinGameField);

        // Back button
        this.backButton = new PanelButton("Back", UserInterface.BACK_BUTTON_X, UserInterface.BACK_BUTTON_Y);
        this.backButton.addActionListener(this);
        this.add(backButton);

        // Error label
        joinErrorLabel.setFont(UserInterface.orkney18);
        joinErrorLabel.setForeground(UserInterface.ERROR_COLOUR);
        joinErrorLabel.setBounds(JOIN_LABEL_X, JOIN_ERROR_Y, JOIN_LABEL_WIDTH, JOIN_LABEL_HEIGHT);
    }

    /**
     * displayError
     * Displays error message for failing to join a game
     * @param errorMessage the message to display
     */
    public void displayError(String errorMessage) {
        joinGameField.setText("");
        joinErrorLabel.setText(errorMessage);
        this.add(joinErrorLabel);
        this.revalidate();
    }

    /**
     * removeError
     * Removes the error message label
     */
    public void removeError() {
        this.remove(joinErrorLabel);
    }

    /**
     * actionPerformed
     * Detects if the user has entered a code and sends the code to
     * the server, or displays relevant error messages
     * @param e the event that occurred
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        // Validate code inputted by user and send it to the server
        if (e.getSource() == joinGameField) {
            String joinGameCode = joinGameField.getText();
            Message message;

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

            message = new Message(MessageTypes.JOIN_GAME);
            message.addParam(joinGameCode);
            ServerConnection.sendMessage(message);
        
        // Go back to the play page
        } else if (e.getSource() == backButton) {
            joinGameField.setText("");
            removeError();
            window.changePage(Page.PLAY);
        }
    }
}