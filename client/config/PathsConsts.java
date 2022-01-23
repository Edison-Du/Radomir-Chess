package config;
import chesslogic.Board;

public class PathsConsts {

    public static final String PNG_FILE = ".png";

    public static final String ASSETS = "assets/";
    public static final String THEMES = ASSETS + "themes/";

    public static final String[] PIECE_SETS = {
        ASSETS + "classic pieces/",
        ASSETS + "kosal pieces/"
    };
    public static final String PROMOTION_PLATTER = ASSETS + "promotion" + PNG_FILE;
    public static final String CHESS_LOGO = ASSETS + "radomirchess" + PNG_FILE;

    public static final String WOOD_THEME = THEMES + "wood" + PNG_FILE;
    public static final String ICE_THEME = THEMES + "icysea" + PNG_FILE;
}