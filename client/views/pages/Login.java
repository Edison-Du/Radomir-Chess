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

public class Login extends ContentPanel implements ActionListener{

    private final EmptyBorder TEXT_FIELD_MARGIN = new EmptyBorder(7, 5, 0, 5);
    private final int TEXT_FIELD_PLACEHOLDER_Y = 33;
    private final int BUTTON_X = UserInterface.CONTENT_WIDTH / 2 - 140;
    private final int REGISTER_BUTTON_Y = UserInterface.WINDOW_HEIGHT / 2 + 90;
    private final int LOGIN_BUTTON_Y = UserInterface.WINDOW_HEIGHT / 2 + 180;
    private final int ERROR_LABEL_Y = UserInterface.WINDOW_HEIGHT / 2 + 50;
    private final int ERROR_LABEL_HEIGHT = 30;
    private final int MAX_INPUT_LENGTH = 16;


    private JLabel titleLabel = new JLabel("Login");
    private JLabel profile = new JLabel();
    private JLabel usernameLabel= new JLabel();
    private JLabel passwordLabel = new JLabel();
    private JLabel errorLabel = new JLabel();
    private CustomTextField usernameField = new CustomTextField();
    private CustomPasswordField passwordField = new CustomPasswordField();
    private PanelButton registerButton;
    private PanelButton loginButton;
    
    public Login() {        

        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        this.add(titleLabel);
        
        profile.setFont(UserInterface.orkney36);
        profile.setText("Register");
        this.add(profile);

        usernameLabel.setFont(UserInterface.orkney24);
        usernameLabel.setText("Username");
        usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        usernameLabel.setBounds(
            UserInterface.USERNAME_X,
            UserInterface.USERNAME_LABEL_Y,
            UserInterface.USERNAME_WIDTH,
            UserInterface.LOGIN_LABEL_HEIGHT
        );
        this.add(usernameLabel);

        usernameField.setFont(UserInterface.orkney24);
        usernameField.setPlaceholder("Enter Username");
        usernameField.setFont(UserInterface.orkney24);
        usernameField.setBorder(TEXT_FIELD_MARGIN);
        usernameField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        usernameField.setBounds(
            UserInterface.USERNAME_X,
            UserInterface.USERNAME_FIELD_Y,
            UserInterface.USERNAME_WIDTH,
            UserInterface.LOGIN_FIELD_HEIGHT
        );
        usernameField.addActionListener(this);
        this.add(usernameField);

        passwordLabel.setFont(UserInterface.orkney24);
        passwordLabel.setText("Password");
        passwordLabel.setForeground(UserInterface.TEXT_COLOUR);
        passwordLabel.setBounds(
            UserInterface.PASSWORD_X,
            UserInterface.PASSWORD_LABEL_Y,
            UserInterface.PASSWORD_WIDTH,
            UserInterface.LOGIN_LABEL_HEIGHT
        );
        this.add(passwordLabel);

        passwordField.setFont(UserInterface.orkney24);
        passwordField.setPlaceholder("Enter Password");
        passwordField.setFont(UserInterface.orkney24);
        passwordField.setBorder(TEXT_FIELD_MARGIN);
        passwordField.setPlaceholderY(TEXT_FIELD_PLACEHOLDER_Y);
        passwordField.setBounds(
            UserInterface.PASSWORD_X,
            UserInterface.PASSWORD_FIELD_Y,
            UserInterface.PASSWORD_WIDTH,
            UserInterface.LOGIN_FIELD_HEIGHT
        );
        passwordField.addActionListener(this);
        this.add(passwordField);


        registerButton = new PanelButton("Register", BUTTON_X, REGISTER_BUTTON_Y);
        registerButton.addActionListener(this);
        this.add(registerButton);


        loginButton = new PanelButton("Login", BUTTON_X, LOGIN_BUTTON_Y);
        loginButton.addActionListener(this);
        this.add(loginButton);

        errorLabel.setFont(UserInterface.orkney18);
        errorLabel.setForeground(UserInterface.ERROR_COLOUR);
        errorLabel.setBounds(BUTTON_X, ERROR_LABEL_Y, UserInterface.USERNAME_WIDTH, ERROR_LABEL_HEIGHT);
    }

    public void displayError(String errorMessage) {
        errorLabel.setText(errorMessage);
        this.add(errorLabel);
        this.revalidate();
    }

    public void removeError() {
        this.remove(errorLabel);
    }

    public void actionPerformed(ActionEvent e){
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        if (validateInput(username) && validateInput(password)){
            if (e.getSource() == loginButton){
                Message m = new Message(MessageTypes.LOGIN);
                m.addParam(username);
                m.addParam(password);
                ServerConnection.sendMessage(m);
            } else if (e.getSource() == registerButton){
                Message m = new Message(MessageTypes.REGISTER);
                m.addParam(username);
                m.addParam(password);

                int[] settings = UserInterface.getCurrentSettings();
                for (int i = 0; i < UserInterface.NUM_SETTINGS; i++) {
                    m.addParam(Integer.toString(settings[i]));
                }
                ServerConnection.sendMessage(m);
            }
        } else {
            displayError("Invalid characters used");
        }
    }

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