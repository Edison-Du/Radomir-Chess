package config;

public class MessageTypes {

    public static final String UNDEFINED = "UNDEFINED";

    public static final String BROWSE_GAMES = "BROWSE_GAMES";
    public static final String DISPLAY_GAMES = "DISPLAY_GAMES";
    public static final String LOGIN = "LOGIN";
    public static final String REGISTER = "REGISTER";
    public static final String EXIT_PROGRAM = "EXIT_PROGRAM";
    public static final String LOGIN_ACCEPTED = "LOGIN_ACCEPTED";
    public static final String LOGIN_FAILED = "LOGIN_FAILED";
    public static final String LOGOUT = "LOGOUT";
    public static final String REGISTER_ACCEPTED = "REGISTER_ACCEPTED";
    public static final String REGISTER_FAILED = "REGISTER_FAILED";

    // Lobby related
    public static final String CREATE_GAME = "CREATE_GAME";
    public static final String GAME_CREATED = "GAME_CREATED";

    public static final String LOBBY_VISIBILITY = "LOBBY_VISIBILITY";

    public static final String JOIN_GAME = "JOIN_GAME";
    public static final String JOIN_ERROR = "JOIN_ERROR";
    public static final String JOINED_GAME = "JOINED_GAME";
    public static final String GAME_FULL = "GAME_FULL";
    public static final String GUEST_JOINED = "GUEST_JOINED";
    public static final String LEAVE_GAME = "LEAVE_GAME";
    public static final String LEFT_SUCCESFULLY = "LEFT_SUCCESSFULLY";
    public static final String OPPONENT_LEFT = "OPPONENT_LEFT";
    
    public static final String SENT_TEXT = "SENT_TEXT";
    public static final String PLAYER_COLOUR = "PLAYER_COLOUR";
    public static final String CHESS_MOVE = "CHESS_MOVE";

    public static final String RESIGNATION = "RESIGNATION";
    public static final String PLAY_AGAIN = "PLAY_AGAIN";
    
    public static final String TAKEBACK_REQUESTED = "TAKEBACK_REQUESTED";
    public static final String TAKEBACK_ACCEPTED = "TAKEBACK_ACCEPTED";
}