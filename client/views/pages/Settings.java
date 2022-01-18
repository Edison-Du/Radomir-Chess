package views.pages;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import views.components.ContentPanel;
import config.UserInterface;

public class Settings extends ContentPanel {
    // Constants
    private final JLabel titleLabel = new JLabel();

    public Settings() {
        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("Settings");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
