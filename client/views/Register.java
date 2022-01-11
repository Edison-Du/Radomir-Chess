package views;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.GraphicConsts;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Register extends JPanel{

    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();

    private final JLabel usernameLabel= new JLabel();
    private final JTextField usernameField = new JTextField();
    private final JLabel passwordLabel = new JLabel();
    private final JTextField passwordField = new JTextField();
    private final JButton registerButton = new JButton("Register");
    
    public Register() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);
        this.setLayout(null);

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

        registerButton.setBounds(GraphicConsts.CONTENT_WIDTH / 2 - 75, 400, 150, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(usernameField.getText());
                System.out.println(passwordField.getText() + "\n");
                usernameField.setText("");
                passwordField.setText("");
            }
        });
        this.add(registerButton);
    }
}