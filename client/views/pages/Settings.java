package views.pages;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import views.components.CustomButton;
import config.UserInterface;
import config.PathsConsts;
import views.Window;

public class Settings extends ContentPanel implements ActionListener {

    private final JLabel titleLabel = new JLabel("Settings");
    private final JLabel boardThemeLabel = new JLabel("Boards");
    private final JLabel backgroundThemeLabel = new JLabel("Backgrounds");
    private final JLabel pieceSetLabel = new JLabel("Piece Sets");
    private final JLabel highlightLabel = new JLabel("Highlights");

    private final String[] BOARDS = {
        "Greyscale",
        "Ice",
        "Wood",
        "Bubblegum",
        "Starburst",
        "Lavender",
        "Evergreen",
        "Arizona",
        "Chess.com",
        "Jazz Cup",
        "Ice Cweam"
    };
    private final String[] BACKGROUNDS = {
        "Default",
        "Light",
        "Dark",
        "Light Blue",
        "Dark Blue"
    };
    private final String[] CHESS_PIECE_SETS = {
        "Classic",
        "Kosal"
    };
    private final String[] HIGHLIGHTS = {
        "Blue",
        "Green",
        "Red",
        "Yellow",
        "Orange",
        "Purple",
        "White",
        "Black",
        "Beige"
    };

    private final JComboBox<String> boardThemes;
    private final JComboBox<String> backgroundThemes;
    private final JComboBox<String> pieceSets;
    private final JComboBox<String> highlightThemes;

    private final CustomButton toggleHighlightButton = new CustomButton("Turn Off Highlights");

    Window window;

    public Settings(Window window) {
        this.window = window;
        boardThemes = new JComboBox<>(BOARDS);
        backgroundThemes = new JComboBox<>(BACKGROUNDS);
        pieceSets = new JComboBox<>(CHESS_PIECE_SETS);
        highlightThemes = new JComboBox<>(HIGHLIGHTS);

        titleLabel.setFont(UserInterface.JOIN_GAME_FONT_1);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);


        boardThemeLabel.setFont(UserInterface.SETTINGS_FONT);
        boardThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        boardThemeLabel.setBounds(35, 110, 115, 35);
        this.add(boardThemeLabel);

        boardThemes.setBounds(35, 150, 115, 35);
        boardThemes.setForeground(UserInterface.FRAME_COLOUR);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);

        
        backgroundThemeLabel.setFont(UserInterface.SETTINGS_FONT);
        backgroundThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        backgroundThemeLabel.setBounds(170, 110, 115, 35);
        this.add(backgroundThemeLabel);

        backgroundThemes.setBounds(170, 150, 115, 35);
        backgroundThemes.setForeground(UserInterface.FRAME_COLOUR);
        backgroundThemes.setFocusable(false);
        backgroundThemes.addActionListener(this);
        this.add(backgroundThemes);


        pieceSetLabel.setFont(UserInterface.SETTINGS_FONT);
        pieceSetLabel.setForeground(UserInterface.TEXT_COLOUR);
        pieceSetLabel.setBounds(305, 110, 115, 35);
        this.add(pieceSetLabel);

        pieceSets.setBounds(305, 150, 115, 35);
        pieceSets.setForeground(UserInterface.FRAME_COLOUR);
        pieceSets.setFocusable(false);
        pieceSets.addActionListener(this);
        this.add(pieceSets);

        
        highlightLabel.setFont(UserInterface.SETTINGS_FONT);
        highlightLabel.setForeground(UserInterface.TEXT_COLOUR);
        highlightLabel.setBounds(35, 240, 115, 35);
        this.add(highlightLabel);

        highlightThemes.setBounds(35, 340, 115, 35);
        highlightThemes.setForeground(UserInterface.FRAME_COLOUR);
        highlightThemes.setFocusable(false);
        highlightThemes.addActionListener(this);
        this.add(highlightThemes);

        toggleHighlightButton.setBounds(35, 280, 160, 40);
        toggleHighlightButton.setFont(UserInterface.SETTINGS_FONT);
        toggleHighlightButton.setForeground(UserInterface.TEXT_COLOUR);
        toggleHighlightButton.setBackground(UserInterface.ON_COLOUR);
        toggleHighlightButton.setHoverColor(UserInterface.ON_COLOUR.brighter());
        toggleHighlightButton.setFocusable(false);
        toggleHighlightButton.addActionListener(this);
        this.add(toggleHighlightButton);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boardThemes) {
            UserInterface.changeBoard(boardThemes.getSelectedIndex());
        } else if (e.getSource() == backgroundThemes) {
            window.changeGameBackground(backgroundThemes.getSelectedIndex());
        } else if (e.getSource() == pieceSets) {
            UserInterface.changePieceSet(pieceSets.getSelectedIndex());
        } else if (e.getSource() == highlightThemes) {
            UserInterface.changeHighlights(highlightThemes.getSelectedIndex());
        } else if (e.getSource() == toggleHighlightButton) {
            UserInterface.toggleHighlight(toggleHighlightButton);
        }
    }

    /**
     * Updates toggle highlight button (specifically for when a user logs in)
     * @param state
     */
    public void updateAfterLogin(int[] settingStates) {
        if (settingStates[3] != (UserInterface.highlightToggle?1:0)) {
            UserInterface.toggleHighlight(toggleHighlightButton);
        }
        boardThemes.setSelectedIndex(settingStates[0]);
        backgroundThemes.setSelectedIndex(settingStates[1]);
        pieceSets.setSelectedIndex(settingStates[2]);
        highlightThemes.setSelectedIndex(settingStates[4]);
    }
}