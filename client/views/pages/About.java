package views.pages;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import config.UserInterface;

public class About extends ContentPanel implements ActionListener {

    private final JLabel titleLabel = new JLabel("About");

    public About() {
        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}