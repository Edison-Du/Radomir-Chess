package views.pages;

import javax.swing.*;
import config.GraphicConsts;
import views.components.ContentPanel;

import java.awt.*;
import java.awt.event.*;

public class Login extends ContentPanel implements ActionListener{
    
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JLabel usernameLabel= new JLabel();
    private final JTextField usernameField = new JTextField();
    private final JLabel passwordLabel = new JLabel();
    private final JTextField passwordField = new JPasswordField();
    private final JButton registerButton = new JButton("Register");
    private final JButton loginButton = new JButton("Login");
    
    public Login() {

        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(GraphicConsts.WINDOW_TITLE);
        title.setSize(new Dimension(280, 80));
        title.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - title.getWidth() / 2, 0, title.getWidth(), title.getHeight());
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("Register");
        this.add(profile);

        usernameLabel.setText("Username: ");
        usernameLabel.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 300, 150, 25);
        this.add(usernameLabel);

        usernameField.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 320, 150, 25);
        this.add(usernameField);

        passwordLabel.setText("Password: ");
        passwordLabel.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 350, 150, 25);
        this.add(passwordLabel);

        passwordField.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 370, 150, 25);
        this.add(passwordField);

        loginButton.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 400, 150, 25);
        loginButton.addActionListener(this);
        loginButton.setFocusable(false);
        this.add(loginButton);

        registerButton.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 450, 150, 25);
        registerButton.addActionListener(this);
        registerButton.setFocusable(false);
        this.add(registerButton);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            System.out.println("Login");
        } else if (e.getSource() == registerButton){
            System.out.println("Register");
        }
    }
}
