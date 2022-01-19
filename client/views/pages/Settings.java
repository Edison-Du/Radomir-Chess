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
    private final JLabel titleLabel = new JLabel("Settings");
    private final JLabel boardThemeLabel = new JLabel("Boards");
    private final JLabel backgroundThemeLabel = new JLabel("Backgrounds");

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
        "Red",
        "Green",
        "Blue"
    };

    private final JComboBox<String> boardThemes;
    private final JComboBox<String> backgroundThemes;

    Window window;

    public Settings(Window window) {
        this.window = window;
        boardThemes = new JComboBox<>(THEMES);
        backgroundThemes = new JComboBox<>(BACKGROUNDS);

        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);

        //Maybe add for loop to make these drop down menus

        boardThemeLabel.setFont(UserInterface.SETTINGS_FONT);
        boardThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        boardThemeLabel.setBounds(35, 110, 115, 35);
        this.add(boardThemeLabel);

        boardThemes.setBounds(35, 150, 115, 35);
        boardThemes.setForeground(UserInterface.TEXT_COLOUR);
        boardThemes.setBackground(UserInterface.FRAME_COLOUR);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);

        
        backgroundThemeLabel.setFont(UserInterface.SETTINGS_FONT);
        backgroundThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        backgroundThemeLabel.setBounds(170, 110, 115, 35);
        this.add(backgroundThemeLabel);

        backgroundThemes.setBounds(170, 150, 115, 35);
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