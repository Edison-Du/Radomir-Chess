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
    private int chessSet;
    private int highlightsOn;
    private int highlight;
    private int soundOn;

    /**
     * User object constructor
     */
    public User(String username, String password, int[] settingsPreferences) {
        this.username = username;
        this.password = password;
        this.board = settingsPreferences[0];
        this.chessSet = settingsPreferences[1];
        this.highlightsOn = settingsPreferences[2];
        this.highlight = settingsPreferences[3];
        this.soundOn = settingsPreferences[4];
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
     * updates the board variable
     * @param board
     */
    public void setBoard(String board) {
        this.board = Integer.parseInt(board);
    }

    /**
     * returns chess set number as string
     */
    public String getChessSet() {
        return Integer.toString(this.chessSet);
    }

    /**
     * updates the chess set variable
     * @param chessSet
     */
    public void setChessSet(String chessSet) {
        this.chessSet = Integer.parseInt(chessSet);
    }

    /**
     * returns whether highlight is on or not (1 and 0 instead of boolean) as string
     */
    public String getHighlightStatus() {
        return Integer.toString(this.highlightsOn);
    }
    
    /**
     * updates the highlight status variable
     * @param status
     */
    public void setHighlightStatus(String status) {
        this.highlightsOn = Integer.parseInt(status);
    }

    /**
     * returns highlight colour (index) as a string
     */
    public String getHighlight() {
        return Integer.toString(this.highlight);
    }
    
    /**
     * updates the highlight variable
     * @param highlight
     */
    public void setHighlight(String highlight) {
        this.highlight = Integer.parseInt(highlight);
    }

    /**
     * returns whether sound is on or not (1 or 0 as boolean) as string
     */
    public String getSoundStatus() {
        return Integer.toString(this.soundOn);
    }

    /**
     * updates the sound status variable
     * @param status
     */
    public void setSoundStatus(String status) {
        this.soundOn = Integer.parseInt(status);
    }

    /**
     * Parse message containing entire data set for user object
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

    public static int[] settingsToArray(Message message) {
        int[] settings = new int[Consts.NUM_SETTINGS];
        for (int i = 0; i < Consts.NUM_SETTINGS; i++) {
            settings[i] = Integer.parseInt(message.getParam(i+1));
        }
        return settings;
    }

    /**
     * returns all the data stored in this object as a string (separated by spaces)
     */
    @Override
    public String toString() {
        return (
            getUsername() + " " +
            getPassword() + " " +
            getBoard() + " " +
            getChessSet() + " " +
            getHighlightStatus() + " " +
            getHighlight() + " " +
            getSoundStatus() + "\n"
        );
    }
}