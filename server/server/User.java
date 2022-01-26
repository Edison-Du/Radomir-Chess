package server;

import config.Consts;

/**
 * [User.java]
 * This class represents a user by storing all relevant data
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
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
     * User
     * Creates a user object with a username, password and an array representing
     * the user's preferred settings
     * @param username the user's username
     * @param password the user's password
     * @param settingsPreferences the user's preferred settings
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
     * getUsername
     * Getter for the username
     * @return username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * getPassword
     * Getter for the password
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * getBoard
     * Getter for the user's chess board preference
     * @return the chess board preference
     */
    public String getBoard() {
        return Integer.toString(this.board);
    }


    /**
     * setBoard
     * Sets the user's chess board preference
     * @param board the user's chess board preference
     */
    public void setBoard(String board) {
        this.board = Integer.parseInt(board);
    }

    /**
     * getChessSet
     * Getter for the user's chess set preference
     * @return the user's chess set preference
     */
    public String getChessSet() {
        return Integer.toString(this.chessSet);
    }

    /**
     * setChessSet
     * Sets the user's chess set preference
     * @param chessSet the user's chess set preference
     */
    public void setChessSet(String chessSet) {
        this.chessSet = Integer.parseInt(chessSet);
    }

    /**
     * getHighlightStatus
     * Gets whether or not the user has highlights are on
     * @return whether or not the user has highlights are on
     */
    public String getHighlightStatus() {
        return Integer.toString(this.highlightsOn);
    }
    
    /**
     * setHighlightStatus
     * Sets whether or not the user has highlights are on
     * @param status whether or not the user has highlights are on
     */
    public void setHighlightStatus(String status) {
        this.highlightsOn = Integer.parseInt(status);
    }

    /**
     * getHighlight
     * Getter for the user's highlight preference
     * @return the user's highlight preference
     */
    public String getHighlight() {
        return Integer.toString(this.highlight);
    }
    
    /**
     * setHighlight
     * Set the user's highlight preference
     * @param highlight the user's highlight preference
     */
    public void setHighlight(String highlight) {
        this.highlight = Integer.parseInt(highlight);
    }

    /**
     * getSoundStatus
     * Getter for whether the user has sound on
     * @return whether the user has sound on
     */
    public String getSoundStatus() {
        return Integer.toString(this.soundOn);
    }

    /**
     * setSoundStatus
     * Setter for whether the user has sound on
     * @param status whether the user has sound on
     */
    public void setSoundStatus(String status) {
        this.soundOn = Integer.parseInt(status);
    }

    /**
     * convertMessageToUser
     * Converts a message object to a user object
     * @param message the message object to convert from
     * @return the converted user object
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

    /**
     * settingsToArray
     * Converts a message object containing the user's settings to an array
     * @param message the message object to convert from
     * @return the converted array
     */
    public static int[] settingsToArray(Message message) {
        int[] settings = new int[Consts.NUM_SETTINGS];
        for (int i = 0; i < Consts.NUM_SETTINGS; i++) {
            settings[i] = Integer.parseInt(message.getParam(i+1));
        }
        return settings;
    }

    /**
     * toString
     * Gets string representation of a user
     * @return the string representation of a user
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