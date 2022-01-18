package views.pages;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import config.UserInterface;

public class Settings extends ContentPanel implements ActionListener {
    // Constants
    private final JLabel titleLabel = new JLabel();
    private final String[] THEMES = {
        "Default",
        "Icey",
        "Wood",
        "Bubblegum",
        "Eye cancer"
    };
    private final JComboBox<String> boardThemes = new JComboBox<>(THEMES);

    public Settings() {
        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("Settings");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);

        boardThemes.setBounds(35, 90, 115, 35);
        boardThemes.setForeground(UserInterface.TEXT_COLOUR);
        boardThemes.setBackground(UserInterface.FRAME_COLOUR);
        boardThemes.addActionListener(this);
        this.add(boardThemes);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boardThemes) {
            UserInterface.changeBoard(boardThemes.getSelectedIndex());
        }
    }
}