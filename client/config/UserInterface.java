package config;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import views.pages.AbstractGamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class UserInterface {
    
    // JFrame related
    public static final String WINDOW_TITLE = "Radomir Chess";
    public static final int UPDATE_RATE = 10;

    // Subject to change, not sure if we want any full screening or anything
    public static final int WINDOW_WIDTH  = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final int NAVBAR_WIDTH  = WINDOW_WIDTH/5;
    public static final int CONTENT_WIDTH = WINDOW_WIDTH - NAVBAR_WIDTH;

    public static final int NAVBAR_BUTTON_HEIGHT = 60;

    //Right Panel
    public static final Color FRAME_COLOUR = new Color(41, 43, 45);
    public static final Color TEXT_COLOUR = new Color(250, 250, 250);
    
    // Navbar
    // public static final Color NAVBAR_COLOUR = new Color(25,26,31);
    // public static final Color NAVBAR_BUTTON_HOVER_COLOUR = new Color(37,38,43);
    public static final Color NAVBAR_COLOUR = new Color(26,26,27);
    public static final Color NAVBAR_BUTTON_HOVER_COLOUR = new Color(38,39,39);
    public static final Border NAVBAR_BUTTON_MARGIN = new EmptyBorder(0, 20, 0, 0);
    public static final Border NAVBAR_BUTTON_HOVER_MARGIN = new EmptyBorder(0, 30, 0, 0);
    public static final String GUEST = "Guest";
    public static final Font USERNAME_FONT = new Font("Arial", Font.PLAIN, 20);

    // Play page
    public static final int MENU_BUTTON_MARGIN = 40;
    public static final int MENU_BUTTON_Y_OFFSET = WINDOW_HEIGHT / 2 - 40;
    public static final int MENU_BUTTON_HEIGHT = WINDOW_HEIGHT / 4 - (int) (MENU_BUTTON_MARGIN * 1.5);
    public static final int MENU_BUTTON_WIDTH = CONTENT_WIDTH / 2 - (int) (MENU_BUTTON_MARGIN * 1.5);
    public static final Font PLAY_BUTTONS_FONT = new Font("Serif", Font.PLAIN, 40);
    // public static final int MENU_BUTTON_GAP = MENU_BUTTON_HEIGHT + 25;

    // Join game page
    public static final Color JOIN_GAME_BUTTON_BACKGROUND = new Color(200, 200, 200);
    public static final Font JOIN_GAME_FONT_1 = new Font("Serif", Font.PLAIN, 40);
    public static final Font JOIN_GAME_FONT_2 = new Font("Serif", Font.PLAIN, 25);

    public static final Color MENU_BUTTON_COLOUR = new Color(47, 78, 111);
    public static final Color MENU_BUTTON_HIGHLIGHT = MENU_BUTTON_COLOUR.brighter();
    public static final int MENU_BUTTON_RADIUS = 20;

    // Game board
    public static byte activeTheme = 0;
    public static final Color[] LIGHTER_TILE_COLOURS = new Color[]{
        new Color(192, 192, 193),
        new Color(218, 226, 234),
        new Color(206, 158, 106),
        new Color(255, 135, 178),
        new Color(255, 56, 9),
        new Color(159, 144, 176)
    };
    public static final Color[] DARKER_TILE_COLOURS = new Color[]{
        new Color(64, 64, 65),
        new Color(121, 156, 178),
        new Color(124, 88, 57),
        new Color(161, 216, 224),
        new Color(132, 99, 247),
        new Color(125, 74, 141)
    };
    
    public static int activeBackground = 0;
    public static Color[] BACKGROUNDS = new Color[]{FRAME_COLOUR, new Color(100, 250, 150), new Color(10, 20, 170)};

    //public static BufferedImage[] BACKGROUNDS = new BufferedImage[2]

    // public static boolean loadImages() {
    //     // Game background
    //     try {
    //         BACKGROUNDS = new BufferedImage[]{
    //             ImageIO.read(new File("chesslogic/themes/forest.png")),
    //             ImageIO.read(new File("chesslogic/themes/iceberg.png"))
    //         };
    //         return true;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //     }
    //     return false;
    // }

    public static Color lighterTile = LIGHTER_TILE_COLOURS[0];
    public static Color darkerTile = DARKER_TILE_COLOURS[0];
    
    public static void changeBoard(int theme) {
        activeTheme = (byte)theme;
        lighterTile = LIGHTER_TILE_COLOURS[theme];
        darkerTile = DARKER_TILE_COLOURS[theme];
    }

    public static Color changeBackground(int colour) {
        activeBackground = colour;
        return BACKGROUNDS[colour];
    }
}