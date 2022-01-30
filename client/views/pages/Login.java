package views.pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.CustomPasswordField;
import views.components.CustomTextField;
import views.components.PanelButton;

/**
 * Login.java]
 * Displays a login page for users to login into their accounts
 * 
 * @author Edison Du
 * @author Peter Gu
 * @author Nicholas Chew
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class Login extends ContentPanel implements ActionListener{

    // UI Constants
    private final EmptyBorder TEXT_FIELD_MARGIN = new EmptyBorder(7, 5, 0, 5);
    private final int TEXT_FIELD_PLACEHOLDER_Y = 33;
    private final int BUTTON_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int REGISTER_BUTTON_Y = UserInterface.WINDOW_HEIGHT / 2 + 40;
    private final int LOGIN_BUTTON_Y = UserInterface.WINDOW_HEIGHT / 2 + 130;
    private final int ERROR_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2;
    private final int ERROR_LABEL_HEIGHT = 30;
    private final int MAX_INPUT_LENGTH = 16;
    private final int USERNAME_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int PASSWORD_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int USERNAME_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 200;
    private final int USERNAME_FIELD_Y = UserInterface.WINDOW_HEIGHT / 2 - 160;
    private final int PASSWORD_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 - 90;
    private final int PASSWORD_FIELD_Y = UserInterface.WINDOW_HEIGHT / 2 - 50;
    private final int USERNAME_WIDTH = 280;
    private final int PASSWORD_WIDTH = 280;
    private final int LOGIN_LABEL_HEIGHT = 30;
    private final int LOGIN_FIELD_HEIGHT = 50;
    private final String REGISTER_BUTTON_TEXT = "Register";
    private final String LOGIN_BUTTON_TEXT = "Login";
    private final String USERNAME_LABEL_TEXT = "Username";
    private final String PASSWORD_LABEL_TEXT = "Password";
    private final String ERROR_TEXT = "Invalid username/password";

    // JComponents 
    private JLabel titleLabel = new JLabel("Login");
    private JLabel usernameLabel= new JLabel();
    private JLabel passwordLabel = new JLabel();
    private JLabel errorLabel = new JLabel();
    private CustomTextField usernameField = new CustomTextField("Enter Username");
    private CustomPasswordField passwordField = new CustomPasswordField("Enter Password");
    private PanelButton registerButton;
    private PanelButton loginButton;
    
    /**
     * Login
     * Creates new login panel with necessary input fields
     */
    public Login() {        

        // Page title
        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        this.add(titleLabel);

        // Username Label
        usernameLabel.setFont(UserInterface.orkney24);
        usernameLabel.setText(USERNAME_LABEL_TEXT);
        usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        usernameLabel.setBounds(
            USERNAME_X,
            USERNAME_LABEL_Y,
            USERNAME_WIDTH,
            LOGIN_LABEL_HEIGHT
        );
        this.add(usernameLabel);

        // Username input field
        usernameField.setFont(UserInterface.orkney24);
        usernameField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        usernameField.setBorder(TEXT_FIELD_MARGIN);
        usernameField.setForeground(UserInterface.GAME_MOVES_HEADER_BACKGROUND);
        usernameField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        usernameField.setBounds(
            USERNAME_X,
            USERNAME_FIELD_Y,
            USERNAME_WIDTH,
            LOGIN_FIELD_HEIGHT
        );
        usernameField.addActionListener(this);
        this.add(usernameField);

        // Password label
        passwordLabel.setFont(UserInterface.orkney24);
        passwordLabel.setText(PASSWORD_LABEL_TEXT);
        passwordLabel.setForeground(UserInterface.TEXT_COLOUR);
        passwordLabel.setBounds(
            PASSWORD_X,
            PASSWORD_LABEL_Y,
            PASSWORD_WIDTH,
            LOGIN_LABEL_HEIGHT
        );
        this.add(passwordLabel);

        // Password input field
        passwordField.setFont(UserInterface.orkney24);
        passwordField.setPlaceholderColour(UserInterface.GAME_CHAT_TEXTFIELD_COLOUR);
        passwordField.setFont(UserInterface.orkney24);
        passwordField.setBorder(TEXT_FIELD_MARGIN);
        passwordField.setForeground(UserInterface.GAME_MOVES_HEADER_BACKGROUND);
        passwordField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        passwordField.setBounds(
            PASSWORD_X,
            PASSWORD_FIELD_Y,
            PASSWORD_WIDTH,
            LOGIN_FIELD_HEIGHT
        );
        passwordField.addActionListener(this);
        this.add(passwordField);

        // Register button
        registerButton = new PanelButton(REGISTER_BUTTON_TEXT, BUTTON_X, REGISTER_BUTTON_Y);
        registerButton.addActionListener(this);
        this.add(registerButton);

        // Login button
        loginButton = new PanelButton(LOGIN_BUTTON_TEXT, BUTTON_X, LOGIN_BUTTON_Y);
        loginButton.addActionListener(this);
        this.add(loginButton);

        // Error text for failed logins/registers
        errorLabel.setFont(UserInterface.orkney18);
        errorLabel.setForeground(UserInterface.ERROR_COLOUR);
        errorLabel.setBounds(BUTTON_X, ERROR_LABEL_Y, USERNAME_WIDTH, ERROR_LABEL_HEIGHT);
    }

    /**
     * displayError
     * Displays error message 
     * @param errorMessage the error message to display
     */
    public void displayError(String errorMessage) {
        errorLabel.setText(errorMessage);
        this.add(errorLabel);
        this.revalidate();
    }

    /**
     * removeError
     * Removes the error label 
     */
    public void removeError() {
        this.remove(errorLabel);
    }

    
    /**
     * actionPerformed
     * Validates user input and sends it to the server to create an account/verify for login
     * @param e the action event that occured
     */
    @Override
    public void actionPerformed(ActionEvent e){
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Ensure inputs have correct characteres
        if (validateInput(username) && validateInput(password)){
            
            // Send message for logging in user
            if (e.getSource() == loginButton){
                Message message = new Message(MessageTypes.LOGIN);
                message.addParam(username);
                message.addParam(password);
                ServerConnection.sendMessage(message);

            // Send message for creating user
            } else if (e.getSource() == registerButton){
                Message message = new Message(MessageTypes.REGISTER);
                message.addParam(username);
                message.addParam(password);

                int[] settings = UserInterface.getCurrentSettings();
                for (int i = 0; i < UserInterface.NUM_SETTINGS; i++) {
                    message.addParam(Integer.toString(settings[i]));
                }
                ServerConnection.sendMessage(message);
            }
        } else {
            displayError(ERROR_TEXT);
        }
    }

    /**
     * validateInput
     * Ensures user input is correct length and has no invalid characters
     * @param input user input
     * @return whether or not the input is valid
     */
    public boolean validateInput(String input){
        if (input.length() > MAX_INPUT_LENGTH || input.length() < 1) {
            return false;
        }
        for (int i = 0; i < input.length(); i++){
            Character c = input.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }
}