package views.pages;

import javax.swing.*;
import config.UserInterface;
import views.components.ContentPanel;
import network.Message;
import network.ServerConnection;
import config.MessageTypes;

import java.awt.*;
import java.awt.event.*;
import views.Window;

public class Login extends ContentPanel implements ActionListener{

    private Window window;
    
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JLabel usernameLabel= new JLabel();
    private final JTextField usernameField = new JTextField();
    private final JLabel passwordLabel = new JLabel();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton registerButton = new JButton("Register");
    private final JButton loginButton = new JButton("Login");
    private final JLabel errorMessage = new JLabel();
    
    public Login(Window window) {

        this.window = window;

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

        loginButton.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 400, 150, 25);
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        this.add(loginButton);

        registerButton.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 450, 150, 25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(false);
        this.add(registerButton);
    }

    public void displayError(){
        errorMessage.setForeground(UserInterface.TEXT_COLOUR);
        errorMessage.setText("Hey man your username or password is incorrect. Are you mentally slow or something?");
        errorMessage.setBounds(UserInterface.CONTENT_WIDTH / 2 - 75, 500, 500, 100);
        this.add(errorMessage);
    }

    public void actionPerformed(ActionEvent e){
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());
        try{
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
        } catch (Exception ex){
            System.out.println("User has inputed bad stuff");
            ex.printStackTrace();
        }
    }
}
