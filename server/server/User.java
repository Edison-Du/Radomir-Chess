package server;

import java.net.ServerSocket;
import java.net.Socket;

import config.Consts;

/**
 * [User.java]
 * This class represents a user by storing all relevant data
 */
public class User {
    private String username;
    private String password;
    private int board;
    private int background;
    private int chessSet;
    private int highlightsOn;
    private int highlight;

    /**
     * User object constructor
     */
    public User(String username, String password, int[] settingsPreferences) {
        this.username = username;
        this.password = password;
        this.board = settingsPreferences[0];
        this.background = settingsPreferences[1];
        this.chessSet = settingsPreferences[2];
        this.highlightsOn = settingsPreferences[3];
        this.highlight = settingsPreferences[4];
    }

    /**
     * returns username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * returns password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * returns board colour (index) as string
     */
    public String getBoard() {
        return Integer.toString(this.board);
    }

    /**
     * returns background colour (index) as a string
     */
    public String getBackground() {
        return Integer.toString(this.background);
    }

    /**
     * returns chess set number as string
     */
    public String getChessSet() {
        return Integer.toString(this.chessSet);
    }

    /**
     * returns whether highlight is on or not (1 and 0 instead of boolean) as string
     */
    public String getHighlightStatus() {
        return Integer.toString(this.highlightsOn);
    }

    /**
     * returns highlight colour (index) as a string
     */
    public String getHighlight() {
        return Integer.toString(this.highlight);
    }

    /**
     * Parse message and store in user object
     * @param message
     */
    public static User convertMessageToUser(Message message) {
        int[] settings = new int[Consts.NUM_SETTINGS];
        String username = message.getParam(0);
        String password = message.getParam(1);
        
        for (int i = 0; i < Consts.NUM_SETTINGS; i++) {
            settings[i] = Integer.parseInt(message.getParam(i+2));
        }

        return new User(username, password, settings);
    }
}