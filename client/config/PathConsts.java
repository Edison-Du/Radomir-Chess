package config;

/**
 * [PathConsts.java]
 * Paths to files used by the program
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @author Alex Zhu
 * @author Jeffrey Xu
 * @author Peter Gu
 * @author Leo Guan
 * @version 1.0 Jan 24, 2022
 */
public final class PathConsts {

    // File types
    public static final String PNG_FILE = ".png";
    public static final String SOUND_FILE = ".wav";

    // Folders for different types of assets
    public static final String ASSETS = "assets/";
    public static final String IMAGES = ASSETS + "images/";
    public static final String SOUNDS = ASSETS + "sounds/";
    public static final String FONTS  = ASSETS + "fonts/";

    // Logos and icons
    public static final String CHESS_LOGO = IMAGES + "radomirchess3" + PNG_FILE;
    public static final String NAVBAR_HEADER = IMAGES + "navbarheader" + PNG_FILE;
    public static final String CHESS_ICON = IMAGES + "radomirchess2" + PNG_FILE;

    // Chess board themes
    public static final String THEMES = IMAGES + "themes/";
    public static final String WOOD_THEME = THEMES + "wood" + PNG_FILE;
    public static final String ICE_THEME = THEMES + "icysea" + PNG_FILE;

    // Paths to different piece sets
    public static final String[] PIECE_SETS = {
        IMAGES + "classic pieces/",
        IMAGES + "kosal pieces/",
        IMAGES + "leipzig pieces/",
        IMAGES + "pixel pieces/",
        IMAGES + "shoes pieces/"
    };

    // Name of individual piece image file
    public static final String[] PIECE_NAMES = {
        "p0", "p1",
        "b0", "b1",
        "N0", "N1",
        "R0", "R1",
        "Q0", "Q1",
        "K0", "K1"
    };

    // Chess promotion choices
    public static final String PROMOTION_PLATTER = IMAGES + "promotion" + PNG_FILE;

    // Sound Effects
    public static final String CHECK = SOUNDS + "check" + SOUND_FILE;
    public static final String STALEMATE = SOUNDS + "stalemate" + SOUND_FILE;
    public static final String CAPTURE = SOUNDS + "capture" + SOUND_FILE;
    public static final String CASTLE = SOUNDS + "castle" + SOUND_FILE;
    public static final String MOVE = SOUNDS + "move" + SOUND_FILE;
    public static final String CHECKMATE = SOUNDS + "checkmate" + SOUND_FILE;

    // Fonts
    public static final String ORKNEY_FONT = FONTS + "Orkney-Regular.ttf";

    // Chess openings
    public static final String OPENING_FILE = "chessfiles/openings.txt";

    // This class should never be constructed
    private PathConsts() {}
}