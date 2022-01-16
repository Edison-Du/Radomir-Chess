package config;

import java.awt.Color;
import java.awt.Font;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

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

    // Play page
    public static final int MENU_BUTTON_MARGIN = 70;
    // public static final int MENU_BUTTON_Y_OFFSET = 100;
    public static final int MENU_BUTTON_HEIGHT = WINDOW_HEIGHT / 2 - (int) (MENU_BUTTON_MARGIN * 1.5);
    public static final int MENU_BUTTON_WIDTH = CONTENT_WIDTH / 2 - (int) (MENU_BUTTON_MARGIN * 1.5);
    public static final Font PLAY_BUTTONS_FONT = new Font("Serif", Font.PLAIN, 40);
    // public static final int MENU_BUTTON_GAP = MENU_BUTTON_HEIGHT + 25;

    // Join game page
    public static final Color JOIN_GAME_BUTTON_BACKGROUND = new Color(200, 200, 200);
    public static final Font JOIN_GAME_FONT_1 = new Font("Serif", Font.PLAIN, 40);
    public static final Font JOIN_GAME_FONT_2 = new Font("Serif", Font.PLAIN, 25);

    public static final Color MENU_BUTTON_COLOUR = new Color(128, 164, 84);
    public static final Color MENU_BUTTON_HIGHLIGHT = new Color(144, 186, 93);
    public static final int MENU_BUTTON_RADIUS = 20;
    
    private UserInterface() {}
}
