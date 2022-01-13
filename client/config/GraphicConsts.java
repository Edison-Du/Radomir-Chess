package config;

import java.awt.Color;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class GraphicConsts {
    
    // JFrame related
    public static final String WINDOW_TITLE = "Radomir Chess";

    // Subject to change, not sure if we want any full screening or anything
    public static final int WINDOW_WIDTH  = 1280;
    public static final int WINDOW_HEIGHT = 720;

    public static final int NAVBAR_WIDTH  = WINDOW_WIDTH/5;
    public static final int CONTENT_WIDTH = WINDOW_WIDTH - NAVBAR_WIDTH;

    public static final int NAVBAR_BUTTON_HEIGHT = 60;
    
    // Navbar
    public static final Color NAVBAR_COLOUR = new Color(25,26,31);
    public static final Color NAVBAR_BUTTON_HOVER_COLOUR = new Color(37,38,43);
    public static final Border NAVBAR_BUTTON_MARGIN = new EmptyBorder(0, 20, 0, 0);
    public static final Border NAVBAR_BUTTON_HOVER_MARGIN = new EmptyBorder(0, 30, 0, 0);

    // Play page
    public static final int MENU_BUTTON_MARGIN = 150;
    public static final int MENU_BUTTON_HEIGHT = 100;
    public static final int MENU_BUTTON_WIDTH = CONTENT_WIDTH - MENU_BUTTON_MARGIN * 2;
    
    private GraphicConsts() {}
}
