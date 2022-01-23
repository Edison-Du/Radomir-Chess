package config;

public class PathsConsts {

    public static final String PNG_FILE = ".png";

    public static final String ASSETS = "assets/";
    public static final String IMAGES = ASSETS + "images/";


    public static final String THEMES = IMAGES + "themes/";

    public static final String[] PIECE_SETS = {
<<<<<<< HEAD
        ASSETS + "classic pieces/",
        ASSETS + "kosal pieces/",
        ASSETS + "leipzig pieces/",
        ASSETS + "pixel pieces/",
        ASSETS + "shoes pieces/"
    };

    public static final String[] PIECE_NAMES = {
        "p0", "p1",
        "b0", "b1",
        "N0", "N1",
        "R0", "R1",
        "Q0", "Q1",
        "K0", "K1"
    };

    public static final String PROMOTION_PLATTER = ASSETS + "promotion" + PNG_FILE;
    public static final String CHESS_LOGO = ASSETS + "radomirchess3" + PNG_FILE;
    public static final String NAVBAR_HEADER = ASSETS + "navbarheader" + PNG_FILE;
=======
        IMAGES + "classic pieces/",
        IMAGES + "kosal pieces/"
    };
    public static final String PROMOTION_PLATTER = IMAGES + "promotion" + PNG_FILE;
    public static final String CHESS_LOGO = IMAGES + "radomirchess3" + PNG_FILE;
    public static final String NAVBAR_HEADER = IMAGES + "navbarheader" + PNG_FILE;
>>>>>>> 068174285a77415e383b255651987303a3c9feff

    public static final String WOOD_THEME = THEMES + "wood" + PNG_FILE;
    public static final String ICE_THEME = THEMES + "icysea" + PNG_FILE;
}