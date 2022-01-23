package config;

public class PathsConsts {

    public static final String PNG_FILE = ".png";
    public static final String SOUND_FILE = ".wav";

    public static final String ASSETS = "assets/";
    public static final String IMAGES = ASSETS + "images/";
    public static final String SOUNDS = ASSETS + "sounds/";

    public static final String THEMES = IMAGES + "themes/";

    public static final String[] PIECE_SETS = {
        IMAGES + "classic pieces/",
        IMAGES + "kosal pieces/"
    };
    public static final String PROMOTION_PLATTER = IMAGES + "promotion" + PNG_FILE;
    public static final String CHESS_LOGO = IMAGES + "radomirchess3" + PNG_FILE;
    public static final String NAVBAR_HEADER = IMAGES + "navbarheader" + PNG_FILE;

    public static final String WOOD_THEME = THEMES + "wood" + PNG_FILE;
    public static final String ICE_THEME = THEMES + "icysea" + PNG_FILE;

    public static final String CHECK = SOUNDS + "check" + SOUND_FILE;
    public static final String STALEMATE = SOUNDS + "stalemate" + SOUND_FILE;
    public static final String CAPTURE = SOUNDS + "capture" + SOUND_FILE;
    public static final String CASTLE = SOUNDS + "castle" + SOUND_FILE;
    public static final String MOVE = SOUNDS + "move" + SOUND_FILE;
    public static final String CHECKMATE = SOUNDS + "checkmate" + SOUND_FILE;

}