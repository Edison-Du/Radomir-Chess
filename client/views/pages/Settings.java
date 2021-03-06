package views.pages;

import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.components.ContentPanel;
import views.components.CustomButton;
import config.UserInterface;
import views.Window;

/**
 * [Settings.java]
 * Settings page for user's to adjust their UI preferences
 * 
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class Settings extends ContentPanel implements ActionListener {
    // Combobox options
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
    
    // Graphics location constants
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
    
    private final EmptyBorder ORKNEY_OFFSET = new EmptyBorder(7, 0, 0, 0);

    private final CustomButton toggleHighlightButton = new CustomButton("Show Moves On");
    private final CustomButton toggleSoundButton = new CustomButton("Sound On");

    // Window object
    Window window;

    public Settings(Window window) {
        this.window = window;

        // Initialize comboboxes
        boardThemes = new JComboBox<>(BOARDS);
        pieceSets = new JComboBox<>(CHESS_PIECE_SETS);
        highlightThemes = new JComboBox<>(HIGHLIGHTS);

        // Settings header label
        titleLabel.setFont(UserInterface.orkney36);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(UserInterface.TITLE_BOUNDS);
        titleLabel.setBorder(ORKNEY_OFFSET);
        this.add(titleLabel);

        // Board themes
        boardThemeLabel.setFont(UserInterface.orkney18);
        boardThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        boardThemeLabel.setBounds(SETTINGS_LEFT, LABEL_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        boardThemeLabel.setBorder(ORKNEY_OFFSET);
        this.add(boardThemeLabel);

        boardThemes.setBounds(SETTINGS_LEFT, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        boardThemes.setForeground(UserInterface.FRAME_COLOUR);
        boardThemes.setFont(UserInterface.COMBOBOX_FONT);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);

        // Piece sets
        pieceSetLabel.setFont(UserInterface.orkney18);
        pieceSetLabel.setForeground(UserInterface.TEXT_COLOUR);
        pieceSetLabel.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, LABEL_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        pieceSetLabel.setBorder(ORKNEY_OFFSET);
        this.add(pieceSetLabel);

        pieceSets.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        pieceSets.setForeground(UserInterface.FRAME_COLOUR);
        pieceSets.setFont(UserInterface.COMBOBOX_FONT);
        pieceSets.setFocusable(false);
        pieceSets.addActionListener(this);
        this.add(pieceSets);

        // Highlight colours
        highlightLabel.setFont(UserInterface.orkney18);
        highlightLabel.setForeground(UserInterface.TEXT_COLOUR);
        highlightLabel.setBorder(ORKNEY_OFFSET);
        highlightLabel.setBounds(SETTINGS_LEFT + 2*COMBOBOX_GAP, LABEL_Y, COMBOBOX_WIDTH + 60, COMBOBOX_HEIGHT);
        this.add(highlightLabel);

        highlightThemes.setBounds(SETTINGS_LEFT + 2*COMBOBOX_GAP, COMBOBOX_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        highlightThemes.setForeground(UserInterface.FRAME_COLOUR);
        highlightThemes.setFont(UserInterface.COMBOBOX_FONT);
        highlightThemes.setFocusable(false);
        highlightThemes.addActionListener(this);
        this.add(highlightThemes);

        toggleHighlightButton.setBounds(SETTINGS_LEFT, BUTTON_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        toggleHighlightButton.setFont(UserInterface.orkney12);
        toggleHighlightButton.setForeground(UserInterface.TEXT_COLOUR);
        toggleHighlightButton.setBackground(UserInterface.ON_COLOUR);
        toggleHighlightButton.setHoverColor(UserInterface.ON_COLOUR.brighter());
        toggleHighlightButton.setFocusable(false);
        toggleHighlightButton.setBorder(ORKNEY_OFFSET);
        toggleHighlightButton.addActionListener(this);
        this.add(toggleHighlightButton);

        // Sounds
        toggleSoundButton.setBounds(SETTINGS_LEFT + COMBOBOX_GAP, BUTTON_Y, COMBOBOX_WIDTH, COMBOBOX_HEIGHT);
        toggleSoundButton.setFont(UserInterface.orkney12);
        toggleSoundButton.setForeground(UserInterface.TEXT_COLOUR);
        toggleSoundButton.setBackground(UserInterface.ON_COLOUR);
        toggleSoundButton.setHoverColor(UserInterface.ON_COLOUR.brighter());
        toggleSoundButton.setFocusable(false);
        toggleSoundButton.setBorder(ORKNEY_OFFSET);
        toggleSoundButton.addActionListener(this);
        this.add(toggleSoundButton);
    }

    /**
     * actionPerformed
     * action listener for the buttons and comboboxes in the settings page
     * @param ActionEvent the event that occurs (mouse clicks)
     */
    @Override
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
        // Set change made variable to true so changes in settings can be sent to server
        if (window.isLoggedIn()) UserInterface.changeMade = true;
    }

    /**
     * updateAfterLogin
     * updates buttons and combo boxes after log in
     * @param states int array with all saved settings of the user
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