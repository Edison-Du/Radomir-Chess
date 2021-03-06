package config;

import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import views.components.CustomButton;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;

/**
 * [UserInterface.java]
 * 
 * @author Jeffrey Xu
 * @author Alex Zhu
 * @author Nicholas Chew
 * @author Edison Du
 * @author Peter Gu
 * @version 1.0 Jan 24, 2022
 */
public final class UserInterface {
    
    // JFrame related
    public static final String WINDOW_TITLE = "Radomir Chess";
    public static final int UPDATE_RATE = 10;

    /** Screen Size Constants **/
    public static final int WINDOW_WIDTH  = 1280;
    public static final int WINDOW_HEIGHT = 720;

    // Navigation Bar
    public static final int NAVBAR_WIDTH  = WINDOW_WIDTH/5;
    public static final int CONTENT_WIDTH = WINDOW_WIDTH - NAVBAR_WIDTH;
    public static final int NAVBAR_BUTTON_HEIGHT = 80;

    //Right Panel
    public static final Color FRAME_COLOUR = new Color(41, 43, 45);
    public static final Color TEXT_COLOUR = new Color(250, 250, 250);

    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    // Navigation bar
    public static final Color NAVBAR_COLOUR = new Color(26,26,27);
    public static final Color NAVBAR_BUTTON_HOVER_COLOUR = new Color(38,39,39);
    public static final Border NAVBAR_BUTTON_MARGIN = new EmptyBorder(5, 30, 0, 0);
    public static final Border NAVBAR_BUTTON_HOVER_MARGIN = new EmptyBorder(5, 45, 0, 0);
    public static final String GUEST = "Guest";
    public static final Font USERNAME_FONT = new Font("Arial", Font.PLAIN, 20);
    public static final int NAVBAR_LABEL_WIDTH = 200;
    public static final int NAVBAR_LABEL_HEIGHT = 25;
    public static final int NAVBAR_LABEL_X = 30;
    public static final int USERNAME_Y = UserInterface.WINDOW_HEIGHT - 47;
    public static final int PLAYERS_ONLINE_Y = UserInterface.WINDOW_HEIGHT - 123;
    public static final int PLAYERS_ONLINE_RADIUS = 12;
    public static final Color PLAYERS_ONLINE_COLOR = new Color(59, 165, 93);

    //Content panel
    public static final int BACKGROUND_LOGO_X = CONTENT_WIDTH / 2 - 180;
    public static final int BACKGROUND_LOGO_Y = WINDOW_HEIGHT / 2 - 280;
    public static final int LOGO_WIDTH = 360;
    public static final int LOGO_HEIGHT = 560;

    public static final Rectangle TITLE_BOUNDS = new Rectangle(30, 30, 210, 50);

    // Play page
    public static final int MENU_BUTTON_MARGIN = 40;
    public static final int MENU_BUTTON_Y_OFFSET = WINDOW_HEIGHT / 2 - 90;
    public static final int MENU_BUTTON_HEIGHT = WINDOW_HEIGHT / 4 - (int) (MENU_BUTTON_MARGIN * 1.5);
    public static final int MENU_BUTTON_WIDTH = CONTENT_WIDTH / 2 - (int) (MENU_BUTTON_MARGIN * 1.5);

    //Panel Button
    public static final int PANEL_BUTTON_WIDTH = 280;
    public static final int PANEL_BUTTON_HEIGHT = 60;
    public static final EmptyBorder PANEL_BUTTON_BORDER = new EmptyBorder(10, 0, 0, 0);
    
    // Join game page
    public static final int BACK_BUTTON_X = 20;
    public static final int BACK_BUTTON_Y = 20;
    public static final Color ERROR_COLOUR = new Color(255, 255, 0);

    public static final Color JOIN_GAME_BUTTON_BACKGROUND = new Color(200, 200, 200);

    public static final Color MENU_BUTTON_COLOUR = new Color(47, 78, 111);
    public static final Color MENU_BUTTON_HIGHLIGHT = MENU_BUTTON_COLOUR.brighter();
    public static final int MENU_BUTTON_RADIUS = 20;

    // Settings
    public static final int NUM_SETTINGS = 5;
    public static boolean changeMade = false;
    public static Font COMBOBOX_FONT = new Font("Serif", Font.PLAIN, 18);
    
    // About
    public static final int ABOUT_PADDING = 30;
    public static final int ABOUT_Y_BOUND = 120;
    public static final int ABOUT_WIDTH = 994;
    public static final int ABOUT_HEIGHT = 480;

    // Game board themes/options
    public static int activeTheme = 0;
    public static final int ICE_BOARD = 1;
    public static final int WOOD_BOARD = 2;
    public static final int[] IMAGE_THEMES = {
        ICE_BOARD,
        WOOD_BOARD
    };
    public static boolean isImageTheme = false;
    // All colour names in settings page
    public static final Color[] LIGHTER_TILE_COLOURS = new Color[]{
        new Color(192, 192, 193),
        new Color(218, 226, 234),
        new Color(206, 158, 106),
        new Color(255, 135, 178),
        new Color(253, 164, 72),
        new Color(162, 144, 178),
        new Color(135, 211, 74),
        new Color(29, 209, 186),
        new Color(238, 238, 210),
        new Color(34, 203, 209),
        new Color(211, 199, 181),
        new Color(252, 241, 234),
        new Color(120, 226, 247),
        new Color(246, 205, 74),
        new Color(200, 215, 227),
        new Color(92, 240, 89),
        new Color(239, 144, 49),
        new Color(193, 165, 255)
    };
    public static final Color[] DARKER_TILE_COLOURS = new Color[]{
        new Color(64, 64, 65),
        new Color(121, 156, 178),
        new Color(124, 88, 57),
        new Color(161, 216, 224),
        new Color(252, 63, 134),
        new Color(128, 74, 145),
        new Color(23, 90, 21),
        new Color(236, 124, 158),
        new Color(118, 150, 86),
        new Color(55, 37, 135),
        new Color(95, 51, 31),
        new Color(194, 170, 134),
        new Color(38, 179, 129),
        new Color(57, 55, 46),
        new Color(47, 78, 111),
        new Color(247, 57, 119),
        new Color(55, 47, 44),
        new Color(244, 46, 144)
    };
    public static Color lighterTile = LIGHTER_TILE_COLOURS[activeTheme];
    public static Color darkerTile = DARKER_TILE_COLOURS[activeTheme];

    // Chess Sets
    public static int NUM_SETS = PathConsts.PIECE_SETS.length;
    private static int NUM_PIECES = PathConsts.PIECE_NAMES.length;
    public static int activeSetNum = 0;
    public static boolean[] setChanged = new boolean[]{false, false};

    public static final ArrayList<BufferedImage[]> PIECES = new ArrayList<BufferedImage[]>();
    
    // Piece/board highlights (all highlight names in settings page)
    public static final Color[] POSSIBLE_MOVE_COLOURS = new Color[]{
        new Color(61, 89, 169, 192),
        new Color(23, 213, 77, 192),
        new Color(208, 14, 37, 192),
        new Color(255, 225, 60, 192),
        new Color(254, 92, 13, 192),
        new Color(173, 0, 234, 192),
        new Color(252, 252, 252, 192),
        new Color(2, 2, 2, 192),
        new Color(226, 185, 131, 192),
        new Color(218, 245, 41, 192),
        new Color(232, 59, 149, 192)
    };
    private static int activeHighlight = 0;
    public static Color activeHighlightColour = POSSIBLE_MOVE_COLOURS[0];
    public static BasicStroke HIGHLIGHT_LINE_THICKNESS = new BasicStroke(5);
    
    public static boolean highlightToggle = true;
    public static Color ON_COLOUR = new Color(7, 107, 29);
    public static Color OFF_COLOUR = new Color(131, 35, 29);

    // Audio
    public static boolean soundOn = true;

    // Borders
    public static final Border EMPTY_BORDER = new EmptyBorder(0, 0, 0, 0);
    public static final Border FONT_OFFSET_BORDER = new EmptyBorder(5, 0, 0, 0);

    // Game Page
    public static final int GAME_BOARD_LENGTH = 480;
    public static final int GAME_BOARD_X = 120;
    public static final int GAME_BOARD_Y = 120;
    public static final int GAME_SIDE_PANEL_WIDTH = 240;
    public static final int GAME_INFO_BORDER_RADIUS = 15;
    
    // UI font related constants
    public static final Color CHAT_MESSAGE_COLOUR = new Color(230, 230, 230);
    public static final Color GAME_SIDE_BORDER_COLOR = new Color(128, 128, 128);
    public static final Color GAME_SIDE_HIGHLIGHT_COLOR = new Color(77, 77, 77);
    public static final Color GAME_MOVES_HEADER_BACKGROUND = new Color(51, 51, 51);
    public static final Color GAME_CHAT_TEXTFIELD_COLOUR = new Color(179, 179, 179);

    public static final Border GAME_CHAT_MARGIN = new EmptyBorder(10, 10, 0, 0);
    public static final Border GAME_CHAT_BORDER = BorderFactory.createLineBorder(GAME_SIDE_BORDER_COLOR, 1);
    public static final Border GAME_TEXTFIELD_MARGIN  = new EmptyBorder(5, 10, 5, 10);

    public static final Font LOBBY_INFO = new Font("Serif", Font.PLAIN, 40);

    /**
     * changeBoard
     * Changes the colour/theme of the board
     * @param int the theme chosen
     */
    public static void changeBoard(int theme) {
        activeTheme = theme;
        lighterTile = LIGHTER_TILE_COLOURS[theme];
        darkerTile = DARKER_TILE_COLOURS[theme];
        for (int i = 0; i < IMAGE_THEMES.length; i++) {
            if (theme == IMAGE_THEMES[i]) {
                isImageTheme = true;
                return;
            }
        }
        isImageTheme = false;
    }
    
    /**
     * readAllPieceImages
     * reads all the piece sets and stores them
     */
    public static void readAllPieceImages() {
        for (int piece = 0; piece < NUM_PIECES; piece++) {
            BufferedImage[] allImages = new BufferedImage[NUM_SETS];
            for (int set = 0; set < NUM_SETS; set++) {
                allImages[set] = readImage(PathConsts.PIECE_SETS[set] + PathConsts.PIECE_NAMES[piece] + PathConsts.PNG_FILE);
            }
            PIECES.add(allImages);
        }
    }

    /**
     * changePieceSet
     * Changes the active chess set
     * @param int the chess set chosen
     */
    public static void changePieceSet(int set) {
        activeSetNum = set;
        setChanged[0] = true;
        setChanged[1] = true;
    }

    /**
     * changeHighlights
     * Changes highlight colour
     * @param int the colour chosen
     */
    public static void changeHighlights(int colour) {
        activeHighlight = colour;
        activeHighlightColour = POSSIBLE_MOVE_COLOURS[colour];
    }

    /**
     * toggleHighlight
     * Turns highlight on or off
     * @param CustomButton the highlight button
     */
    public static void toggleHighlight(CustomButton button) {
        highlightToggle ^= true;
        if (highlightToggle) {
            button.setText("Show Moves On");
            button.setBackground(ON_COLOUR);
            button.setHoverColor(UserInterface.ON_COLOUR.brighter());
        } else {
            button.setText("Show Moves Off");
            button.setBackground(OFF_COLOUR);
            button.setHoverColor(UserInterface.OFF_COLOUR.brighter());
        }
    }

    /**
     * toggleSound
     * Turns sound on or off
     * @param CustomButton the sound toggle button
     */
    public static void toggleSound(CustomButton button) {
        soundOn ^= true;
        if (soundOn) {
            button.setText("Sound On");
            button.setBackground(ON_COLOUR);
            button.setHoverColor(UserInterface.ON_COLOUR.brighter());
        } else {
            button.setText("Sound Off");
            button.setBackground(OFF_COLOUR);
            button.setHoverColor(UserInterface.OFF_COLOUR.brighter());
        }
    }

    /**
     * getCurrentSettings
     * returns all the current settings states
     * @return int array containing state of all settings
     */
    public static int[] getCurrentSettings() {
        int[] settings = new int[NUM_SETTINGS];
        settings[0] = activeTheme;
        settings[1] = activeSetNum;
        settings[2] = highlightToggle?1:0;
        settings[3] = activeHighlight;
        settings[4] = soundOn?1:0;
        return settings;
    }

    // Fonts
    public static Font orkney;

    // Different sizes of fonts deriving from Orkney
    public static Font orkney12;
    public static Font orkney14;
    public static Font orkney16;
    public static Font orkney18;
    public static Font orkney24;
    public static Font orkney30;
    public static Font orkney36;
    public static Font orkney48;
    public static Font orkney96;

    /**
     * loadFonts
     * read all external fonts
     */
    public static void loadFonts() {
        if (readFonts()) {
            orkney12 = orkney.deriveFont(Font.PLAIN, 12);
            orkney14 = orkney.deriveFont(Font.PLAIN, 14);
            orkney16 = orkney.deriveFont(Font.PLAIN, 16);
            orkney18 = orkney.deriveFont(Font.PLAIN, 18);
            orkney24 = orkney.deriveFont(Font.PLAIN, 24);
            orkney30 = orkney.deriveFont(Font.PLAIN, 30);
            orkney36 = orkney.deriveFont(Font.PLAIN, 36);
            orkney48 = orkney.deriveFont(Font.PLAIN, 48);
            orkney96 = orkney.deriveFont(Font.PLAIN, 96);
        }
    }

    /**
     * readFonts
     * read all external fonts
     * @return boolean whether the read was successful
     */
    public static boolean readFonts() {
        try {
            orkney = Font.createFont(Font.TRUETYPE_FONT, new File(PathConsts.ORKNEY_FONT));
            return true;
            
        } catch (Exception e) {
            System.out.println("Could not load fonts.");
            e.printStackTrace();
        }
        return false;
    }



    public static BufferedImage readImage(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // This class should never be constructed
    private UserInterface() {}
}