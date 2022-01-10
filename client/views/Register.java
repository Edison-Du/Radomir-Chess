package views;

import javax.swing.JLabel;
import javax.swing.JPanel;
import config.GraphicConsts;
import java.awt.Color;
import java.awt.Font;

public class Register extends JPanel{
    private final JLabel title = new JLabel();
    private final JLabel profile = new JLabel();
    
    public Register() {
        this.setBackground(Color.WHITE);
        this.setBounds(GraphicConsts.NAVBAR_WIDTH, 0,  GraphicConsts.CONTENT_WIDTH, GraphicConsts.WINDOW_HEIGHT);

        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(GraphicConsts.WINDOW_TITLE);
        this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("Register");
        this.add(profile);
    }
}
