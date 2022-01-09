package config;

import java.awt.Color;

public class Consts {
    // public final String SERVER_ADDRESS = "";
    // public final int SERVER_PORT = 0;

    // JFrame related
    public final static String WINDOW_TITLE = "Radomir Chess";

    // Subject to change, not sure if we want any full screening or anything
    public final static int WINDOW_WIDTH  = 1280;
    public final static int WINDOW_HEIGHT = 720;

    public final static int NAVBAR_WIDTH  = WINDOW_WIDTH/5;
    public final static int CONTENT_WIDTH = WINDOW_WIDTH - NAVBAR_WIDTH;

    public final static int NAVBAR_BUTTON_HEIGHT = 60;
    
    // Colors
    public final static Color NAVBAR_COLOUR = new Color(13,9,51);
    public final static Color BUTTON_HOVER_COLOUR = new Color(62, 80, 84);


    private Consts() {}

}
