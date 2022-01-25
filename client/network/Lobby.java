package network;

import java.util.ArrayList;

/**
 * [Lobby.java]
 * Class representing the lobby object that is received from the server
 * Used to display lobby information to client 
 *
 * @author Nicholas Chew
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Lobby {

    private static char SEPARATOR = ',';

    private int lobbyIndex;
    private String lobbyCode;
    private String hostName;
    private int hostColour;
    private String guestName;
    private int guestColour;
    
    /**
     * Lobby
     * initializes lobby values
     */
    public Lobby() {
        lobbyIndex = 0;
        lobbyCode = "";
        hostName = "";
        hostColour = 0;
        guestName = "";
        guestColour = 0;
    }

    /**
     * getLobbyIndex
     * @return index of the lobby
     */
    public int getLobbyIndex() {
        return this.lobbyIndex;
    }

    /**
     * setLobbyIndex
     * sets the lobby index
     * @param index the index of the lobby
     */
    public void setLobbyIndex(int index) {
        this.lobbyIndex = index;
    }

    /**
     * getLobbyCode
     * gets the four digit lobby code
     * @return lobbyCode
     */
    public String getLobbyCode() {
        return this.lobbyCode;
    }

    /**
     * setLobbyCode
     * sets the four digit lobby code
     * @param code the code of the lobby
     */
    public void setLobbyCode(String code) {
        this.lobbyCode = code;
    }

    /**
     * getHostName
     * @return hostName
     */
    public String getHostName() {
        return this.hostName;
    }

    /**
     * setHostName
     * sets the name of the host
     * @param name the name of the host
     */
    public void setHostName(String name) {
        this.hostName = name;
    }

    /**
     * getHostColour
     * @return hostColour as 0 or 1
     */
    public int getHostColour() {
        return this.hostColour;
    }

    /**
     * setHostColour
     * sets the colour of the host
     * @param colour the colour of the host
     */
    public void setHostColour(int colour) {
        this.hostColour = colour;
    }

    /**
     * getHostColourString
     * @return String colour of the host in lobby
     */
    public String getHostColourString() {
        if (this.hostColour == 0) {
            return "White";
        } else {
            return "Black";
        }
    }

    /**
     * getGuestName
     * @return guestName
     */
    public String getGuestName() {
        return this.guestName;
    }

    /**
     * setGuestName
     * sets the name of the guest
     * @param name the name of the guest
     */
    public void setGuestName(String name) {
        this.guestName = name;
    }

    /**
     * getGuestColour
     * @return guestColour as 0 or 1
     */
    public int getGuestColour() {
        return this.guestColour;
    }

    /**
     * setGuestColour
     * sets the colour of the guest
     * @param colour the colour of the guest
     */
    public void setGuestColour(int colour) {
        this.guestColour = colour;
    }

    /**
     * getGuestColourString
     * @return String colour of the guest in lobby
     */
    public String getGuestColourString() {
        if (this.hostColour == 0) {
            return "Black";
        } else {
            return "White";
        }
    }
    
    /**
     * getDisplayLobbyInfo
     * @return String of lobby information to be shown on the browse games page
     */
    public String getDisplayLobbyInfo() {
        return "  Lobby #" + lobbyCode + ",  Host: " + hostName + ",  Join as: " + getGuestColourString();
    }

    /**
     * parseLobbyFromString
     * Takes the raw string of information from server and parses it into a lobby object
     * @param string message received from server
     * @return Lobby object containing all lobby information
     */
    public static Lobby parseLobbyFromString(String string) {
        ArrayList<String> lobbyInfo = new ArrayList<>();
        int lastIndex = 0;
        Lobby lobby = new Lobby();

        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == SEPARATOR) {
                lobbyInfo.add(string.substring(lastIndex, i));
                lastIndex = i+1;
            }
        }
        lobby.setLobbyIndex(Integer.parseInt(lobbyInfo.get(0)));
        lobby.setLobbyCode(lobbyInfo.get(1));
        lobby.setHostName(lobbyInfo.get(2));
        lobby.setHostColour(Integer.parseInt(lobbyInfo.get(3)));
        lobby.setGuestName(lobbyInfo.get(4));
        lobby.setGuestColour(Integer.parseInt(lobbyInfo.get(5)));
        return lobby;
    }
}