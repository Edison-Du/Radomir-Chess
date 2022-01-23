package views.pages;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;
import views.components.PanelButton;

public class Login extends ContentPanel implements ActionListener{

    private JLabel titleLabel = new JLabel("Login");
    private JLabel profile = new JLabel();
    private JLabel usernameLabel= new JLabel();
    private JTextField usernameField = new JTextField();
    private JLabel passwordLabel = new JLabel();
    private JPasswordField passwordField = new JPasswordField();
    private PanelButton registerButton;
    private PanelButton loginButton;
    private JLabel errorMessage = new JLabel();
    
    public Login() {        

        titleLabel.setFont(UserInterface.HEADER_FONT);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);
        
        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("Register");
        this.add(profile);

        usernameLabel.setFont(UserInterface.TEXT_FONT_1);
        usernameLabel.setText("Username: ");
        usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        usernameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 160, 280, 30);
        this.add(usernameLabel);

        usernameField.setFont(UserInterface.TEXT_FONT_1);
        usernameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 110, 280, 30);
        usernameField.addActionListener(this);
        this.add(usernameField);

        passwordLabel.setFont(UserInterface.TEXT_FONT_1);
        passwordLabel.setText("Password: ");
        passwordLabel.setForeground(UserInterface.TEXT_COLOUR);
        passwordLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 60, 280, 30);
        this.add(passwordLabel);

        passwordField.setFont(UserInterface.TEXT_FONT_1);
        passwordField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 140, UserInterface.WINDOW_HEIGHT / 2 - 10, 280, 30);
        passwordField.addActionListener(this);
        this.add(passwordField);

        registerButton = new PanelButton(
            "Register",
            UserInterface.CONTENT_WIDTH / 2 - 140,
            UserInterface.WINDOW_HEIGHT / 2 + 50
        );
        registerButton.addActionListener(this);
        registerButton.setFont(UserInterface.PLAY_BUTTONS_FONT);
        this.add(registerButton);

        //change constants
        loginButton = new PanelButton(
            "Login",
            UserInterface.CONTENT_WIDTH / 2 - 140,
            UserInterface.WINDOW_HEIGHT / 2 + 140
        );
        loginButton.addActionListener(this);
        loginButton.setFont(UserInterface.PLAY_BUTTONS_FONT);
        this.add(loginButton);

        errorMessage.setForeground(UserInterface.TEXT_COLOUR);
        errorMessage.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 500, 500, 100);
        this.add(errorMessage);
    }

    public void displayLoginError(){
        errorMessage.setText("Hey man your username or password is incorrect. Are you mentally slow or something?");
    }

    public void displayRegisterError(){
        errorMessage.setText("Username is taken u dumbdumb");
    }

    public void displayInputError(){
        errorMessage.setText("Alright buddy only numbers or letters allowed");
    }

    public void clearError(){
        errorMessage.setText("");
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
            displayInputError();
        }
        usernameField.setText("");
        passwordField.setText("");
    }

    public boolean validateInput(String input){
        if (input.length() > 16) return false;
        for (int i = 0; i < input.length(); i++){
            Character c = input.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c)){
                return false;
            }
        }
        return true;
    }
}
