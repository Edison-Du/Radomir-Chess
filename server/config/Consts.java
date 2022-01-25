package config;

/**
 * [Consts.java]
 * Constants used by the program
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public final class Consts {
    public static final int PORT = 5000;

    public static final int NUM_SETTINGS = 5;
    public static final int UPDATE_DATABASE_INTERVAL = 600000;
    public static final int UPDATE_CLIENT_INTERVAL = 10000;

    public static final String PUBLIC_LOBBY_STATUS = "Public";
    public static final String PRIVATE_LOBBY_STATUS = "Private";

    // This class should never be constructed
    private Consts(){}
}