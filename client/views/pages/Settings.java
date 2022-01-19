package views.pages;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import config.UserInterface;
import views.Window;

public class Settings extends ContentPanel implements ActionListener {
    // Constants
    private final JLabel titleLabel = new JLabel();
    private final String[] THEMES = {
        "Default",
        "Icey",
        "Wood",
        "Bubblegum",
        "Eye cancer",
        "Purple"
    };
    private final String[] BACKGROUNDS = {
        "Default",
        "Forest",
        "Iceberg"
    };

    private final JComboBox<String> boardThemes;
    private final JComboBox<String> backgroundThemes;

    Window window;

    public Settings(Window window) {
        this.window = window;
        boardThemes = new JComboBox<>(THEMES);
        backgroundThemes = new JComboBox<>(BACKGROUNDS);

        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setText("Settings");
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);

        boardThemes.setBounds(35, 90, 115, 35);
        boardThemes.setForeground(UserInterface.TEXT_COLOUR);
        boardThemes.setBackground(UserInterface.FRAME_COLOUR);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);

        backgroundThemes.setBounds(160, 90, 115, 35);
        backgroundThemes.setForeground(UserInterface.TEXT_COLOUR);
        backgroundThemes.setBackground(UserInterface.FRAME_COLOUR);
        backgroundThemes.setFocusable(false);
        backgroundThemes.addActionListener(this);
        this.add(backgroundThemes);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boardThemes) {
            UserInterface.changeBoard(boardThemes.getSelectedIndex());
        } else if (e.getSource() == backgroundThemes) {
            window.changeGameBackground(backgroundThemes.getSelectedIndex());
        }
    }
}