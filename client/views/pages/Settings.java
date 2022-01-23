package views.pages;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import views.components.ContentPanel;
import views.components.CustomButton;
import config.UserInterface;
import network.Message;
import network.ServerConnection;
import config.MessageTypes;
import config.PathsConsts;
import views.Window;

public class Settings extends ContentPanel implements ActionListener {

    private final JLabel titleLabel = new JLabel("Settings");
    private final JLabel boardThemeLabel = new JLabel("Board Themes");
    private final JLabel pieceSetLabel = new JLabel("Piece Sets");
    private final JLabel highlightLabel = new JLabel("Highlight Colours");

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
        "Test Dummy"
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
        "Beige",
        "Chartreuse",
        "Fuchsia"
    };

    private final JComboBox<String> boardThemes;
    private final JComboBox<String> pieceSets;
    private final JComboBox<String> highlightThemes;

    private final CustomButton toggleHighlightButton = new CustomButton("Show Moves On");

    Window window;

    public Settings(Window window) {
        this.window = window;

        boardThemes = new JComboBox<>(BOARDS);
        pieceSets = new JComboBox<>(CHESS_PIECE_SETS);
        highlightThemes = new JComboBox<>(HIGHLIGHTS);

        titleLabel.setFont(UserInterface.HEADER_FONT);
        titleLabel.setForeground(UserInterface.TEXT_COLOUR);
        titleLabel.setBounds(30, 30, 210, 50);
        this.add(titleLabel);


        boardThemeLabel.setFont(UserInterface.SETTINGS_FONT);
        boardThemeLabel.setForeground(UserInterface.TEXT_COLOUR);
        boardThemeLabel.setBounds(35, 110, 160, 37);
        this.add(boardThemeLabel);

        boardThemes.setBounds(35, 152, 160, 37);
        boardThemes.setForeground(UserInterface.FRAME_COLOUR);
        boardThemes.setFont(UserInterface.SETTINGS_FONT);
        boardThemes.setFocusable(false);
        boardThemes.addActionListener(this);
        this.add(boardThemes);


        pieceSetLabel.setFont(UserInterface.SETTINGS_FONT);
        pieceSetLabel.setForeground(UserInterface.TEXT_COLOUR);
        pieceSetLabel.setBounds(215, 110, 160, 37);
        this.add(pieceSetLabel);

        pieceSets.setBounds(215, 152, 160, 37);
        pieceSets.setForeground(UserInterface.FRAME_COLOUR);
        pieceSets.setFont(UserInterface.SETTINGS_FONT);
        pieceSets.setFocusable(false);
        pieceSets.addActionListener(this);
        this.add(pieceSets);

        
        highlightLabel.setFont(UserInterface.SETTINGS_FONT);
        highlightLabel.setForeground(UserInterface.TEXT_COLOUR);
        highlightLabel.setBounds(395, 110, 160, 37);
        this.add(highlightLabel);

        highlightThemes.setBounds(395, 152, 160, 37);
        highlightThemes.setForeground(UserInterface.FRAME_COLOUR);
        highlightThemes.setFont(UserInterface.SETTINGS_FONT);
        highlightThemes.setFocusable(false);
        highlightThemes.addActionListener(this);
        this.add(highlightThemes);

        toggleHighlightButton.setBounds(395, 203, 160, 37);
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
        } else if (e.getSource() == pieceSets) {
            UserInterface.changePieceSet(pieceSets.getSelectedIndex());
        } else if (e.getSource() == highlightThemes) {
            UserInterface.changeHighlights(highlightThemes.getSelectedIndex());
        } else if (e.getSource() == toggleHighlightButton) {
            UserInterface.toggleHighlight(toggleHighlightButton);
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
        boardThemes.setSelectedIndex(settingStates[0]);
        pieceSets.setSelectedIndex(settingStates[1]);
        highlightThemes.setSelectedIndex(settingStates[3]);
    }
}