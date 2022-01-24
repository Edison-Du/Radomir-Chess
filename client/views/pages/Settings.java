package views.pages;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;

import views.components.ContentPanel;
import views.components.CustomButton;
import config.UserInterface;
import views.Window;

public class Settings extends ContentPanel implements ActionListener {
    // Constants
    private final String[] BOARDS = {
        "Greyscale",
        "Icy Sea",
        "Wood",
        "Bubblegum",
        "Starburst",
        "Lavender",
        "Evergreen",
        "Arizona",
        "Chess.com",
        "Jazz Cup",
        "Nanaimo",
        "Ice Cweam",
        "Mouth Wash",
        "Test Dummy",
        "Rado Chess",
        "Watermelon",
        "Halloween",
        "Vapourwave"
    };
    private final String[] CHESS_PIECE_SETS = {
        "Classic",
        "Kosal",
        "Leipzig",
        "Pixel",
        "Shoes"
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
        "Beige",
        "Chartreuse",
        "Fuchsia"
    };

    
    private final int SETTINGS_LEFT = 35;
    private final int COMBOBOX_WIDTH = 160;
    private final int COMBOBOX_HEIGHT = 37;
    private final int LABEL_Y = 110;
    private final int COMBOBOX_Y = LABEL_Y + COMBOBOX_HEIGHT + 5;
    private final int COMBOBOX_GAP = COMBOBOX_WIDTH + 20;
    private final int BUTTON_Y = 253;

    // Buttons, labels, etc
    private final JLabel titleLabel = new JLabel("Settings");
    private final JLabel boardThemeLabel = new JLabel("Board Themes");
    private final JLabel pieceSetLabel = new JLabel("Piece Sets");
    private final JLabel highlightLabel = new JLabel("Possible Move Colours");


    private final JComboBox<String> boardThemes;
    private final JComboBox<String> pieceSets;
    private final JComboBox<String> highlightThemes;

    private final CustomButton toggleHighlightButton = new CustomButton("Show Moves On");
    private final CustomButton toggleSoundButton = new CustomButton("Sound On");

    Window window;

    public Settings(Window window) {
        this.window = window;

        boardThemes = new JComboBox<>(BOARDS);
        pieceSets = new JComboBox<>(CHESS_PIECE_SETS);
        highlightThemes = new JComboBox<>(HIGHLIGHTS);

        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        this.add(titleLabel);


        boardThemeLabel.setFont(UserInterface.orkney18);
        boardThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        boardThemeLabel.setBounds(SETTINGS_LEFT, LABEL_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        this.add(boardThemeLabel);

        boardThemes.setBounds(SETTINGS_LEFT, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        boardThemes.setForeground(UserInterface.FRAME_COLOUR);
        boardThemes.setFont(UserInterface.orkney18);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);


        pieceSetLabel.setFont(UserInterface.orkney18);
        pieceSetLabel.setForeground(UserInterface.TEXT_COLOUR);
        pieceSetLabel.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, LABEL_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        this.add(pieceSetLabel);

        pieceSets.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        pieceSets.setForeground(UserInterface.FRAME_COLOUR);
        pieceSets.setFont(UserInterface.orkney18);
        pieceSets.setFocusable(false);
        pieceSets.addActionListener(this);
        this.add(pieceSets);

        //change constant for width
        highlightLabel.setFont(UserInterface.orkney18);
        highlightLabel.setForeground(UserInterface.TEXT_COLOUR);
        highlightLabel.setBounds(SETTINGS_LEFT + 2*COMBOBOX_GAP, LABEL_Y, COMBOBOX_WIDTH + 60, COMBOBOX_HEIGHT);
        this.add(highlightLabel);

        highlightThemes.setBounds(SETTINGS_LEFT + 2*COMBOBOX_GAP, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        highlightThemes.setForeground(UserInterface.FRAME_COLOUR);
        highlightThemes.setFont(UserInterface.orkney18);
        highlightThemes.setFocusable(false);
        highlightThemes.addActionListener(this);
        this.add(highlightThemes);

        toggleHighlightButton.setBounds(SETTINGS_LEFT, BUTTON_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        toggleHighlightButton.setFont(UserInterface.orkney12);
        toggleHighlightButton.setForeground(UserInterface.TEXT_COLOUR);
        toggleHighlightButton.setBackground(UserInterface.ON_COLOUR);
        toggleHighlightButton.setHoverColor(UserInterface.ON_COLOUR.brighter());
        toggleHighlightButton.setFocusable(false);
        toggleHighlightButton.addActionListener(this);
        this.add(toggleHighlightButton);

        toggleSoundButton.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, BUTTON_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        toggleSoundButton.setFont(UserInterface.orkney12);
        toggleSoundButton.setForeground(UserInterface.TEXT_COLOUR);
        toggleSoundButton.setBackground(UserInterface.ON_COLOUR);
        toggleSoundButton.setHoverColor(UserInterface.ON_COLOUR.brighter());
        toggleSoundButton.setFocusable(false);
        toggleSoundButton.addActionListener(this);
        this.add(toggleSoundButton);
    }

    //@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boardThemes) {
            UserInterface.changeBoard(boardThemes.getSelectedIndex());
        } else if (e.getSource() == pieceSets) {
            UserInterface.changePieceSet(pieceSets.getSelectedIndex());
        } else if (e.getSource() == highlightThemes) {
            UserInterface.changeHighlights(highlightThemes.getSelectedIndex());
        } else if (e.getSource() == toggleHighlightButton) {
            UserInterface.toggleHighlight(toggleHighlightButton);
        } else if (e.getSource() == toggleSoundButton) {
            UserInterface.toggleSound(toggleSoundButton);
        }   
        if (window.isLoggedIn()) UserInterface.changeMade = true;
    }

    /**
     * Updates toggle highlight button (specifically for when a user logs in)
     * @param state
     */
    public void updateAfterLogin(int[] settingStates) {
        if (settingStates[2] != (UserInterface.highlightToggle?1:0)) {
            UserInterface.toggleHighlight(toggleHighlightButton);
        }
        if (settingStates[4] != (UserInterface.soundOn?1:0)) {
            UserInterface.toggleSound(toggleSoundButton);
        }
        boardThemes.setSelectedIndex(settingStates[0]);
        pieceSets.setSelectedIndex(settingStates[1]);
        highlightThemes.setSelectedIndex(settingStates[3]);
    }
}