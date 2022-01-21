package views.pages;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import config.MessageTypes;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

public class Login extends ContentPanel implements ActionListener{

    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JLabel usernameLabel= new JLabel();
    private final JTextField usernameField = new JTextField();
    private final JLabel passwordLabel = new JLabel();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton registerButton = new JButton("Register");
    private final JButton loginButton = new JButton("Login");
    private final JLabel errorMessage = new JLabel();
    
    public Login() {
        title.setForeground(UserInterface.TEXT_COLOUR);
        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(UserInterface.WINDOW_TITLE);
        title.setSize(new Dimension(280, 80));
        title.setBounds(UserInterface.CONTENT_WIDTH / 2 - title.getWidth() / 2, 0, title.getWidth(), title.getHeight());
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("Register");
        this.add(profile);

        usernameLabel.setForeground(UserInterface.TEXT_COLOUR);
        usernameLabel.setText("Username: ");
        usernameLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 300, 150, 25);
        this.add(usernameLabel);

        usernameField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 320, 150, 25);
        this.add(usernameField);

        passwordLabel.setForeground(UserInterface.TEXT_COLOUR);
        passwordLabel.setText("Password: ");
        passwordLabel.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 350, 150, 25);
        this.add(passwordLabel);

        passwordField.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 370, 150, 25);
        this.add(passwordField);

        loginButton.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 410, 150, 25);
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        this.add(loginButton);

        registerButton.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 450, 150, 25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(false);
        this.add(registerButton);

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
