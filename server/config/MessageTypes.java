package config;

/**
 * [MessageTypes.java]
 * Different types of messages that can be sent between
 * the server and the client
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public final class MessageTypes {

    public static final String UNDEFINED = "UNDEFINED";

    // Connection related
    public static final String CONNECTION_ACCEPTED = "CONNECTION_ACCEPTED";
    public static final String EXIT_PROGRAM = "EXIT_PROGRAM";

    // Authentication related
    public static final String REGISTER = "REGISTER";
    public static final String REGISTER_FAILED = "REGISTER_FAILED";
    public static final String LOGIN = "LOGIN";
    public static final String LOGIN_ACCEPTED = "LOGIN_ACCEPTED";
    public static final String LOGIN_FAILED = "LOGIN_FAILED";
    public static final String LOGOUT = "LOGOUT";

    // Getting list of joinable game lobbies
    public static final String BROWSE_GAMES = "BROWSE_GAMES";
    public static final String DISPLAY_GAMES = "DISPLAY_GAMES";
    
    // Creating a game
    public static final String CREATE_GAME = "CREATE_GAME";
    public static final String GAME_CREATED = "GAME_CREATED";
    public static final String CREATE_ERROR = "CREATE_ERROR";

    // Joining a game
    public static final String JOIN_GAME = "JOIN_GAME";
    public static final String JOIN_ERROR = "JOIN_ERROR";
    public static final String JOINED_GAME = "JOINED_GAME";
    public static final String GUEST_JOINED = "GUEST_JOINED";
    public static final String LEAVE_GAME = "LEAVE_GAME";
    public static final String LEFT_SUCCESFULLY = "LEFT_SUCCESSFULLY";
    public static final String OPPONENT_LEFT = "OPPONENT_LEFT";

    // Lobby settings
    public static final String LOCK_LOBBY = "LOCK_LOBBY";
    public static final String UNLOCK_LOBBY = "UNLOCK_LOBBY";
    public static final String LOBBY_VISIBILITY = "LOBBY_VISIBILITY";

    // Communication between two players
    public static final String SENT_TEXT = "SENT_TEXT";
    public static final String PLAY_AGAIN = "PLAY_AGAIN";

    // Chess game related
    public static final String PLAYER_COLOUR = "PLAYER_COLOUR";
    public static final String CHESS_MOVE = "CHESS_MOVE";
    public static final String WHITE_VICTORY_CHECKMATE = "WHITE_VICTORY_CHECKMATE";
    public static final String BLACK_VICTORY_CHECKMATE = "BLACK_VICTORY_CHECKMATE";
    public static final String STALEMATE = "STALEMATE";
    public static final String RESIGNATION = "RESIGNATION";

    public static final String DRAW_OFFERED  = "DRAW_OFFERED";
    public static final String DRAW_ACCEPTED = "DRAW_ACCEPTED";

    public static final String TAKEBACK_REQUESTED = "TAKEBACK_REQUESTED";
    public static final String TAKEBACK_ACCEPTED  = "TAKEBACK_ACCEPTED";

    // Saving a user's settings
    public static final String UPDATE_PREFERENCES = "UPDATE_PREFERENCES";
    
    // Getting number of users online
    public static final String GET_PLAYERS_ONLINE = "GET_PLAYERS_ONLINE";

    // This class should never be constructed
    private MessageTypes() {}
}