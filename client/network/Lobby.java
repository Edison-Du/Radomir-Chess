package network;

import java.util.ArrayList;

/**
 * [Lobby.java]
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
    
    public Lobby() {
        lobbyIndex = 0;
        lobbyCode = "";
        hostName = "";
        hostColour = 0;
        guestName = "";
        guestColour = 0;
    }

    public int getLobbyIndex() {
        return this.lobbyIndex;
    }

    public void setLobbyIndex(int index) {
        this.lobbyIndex = index;
    }

    public String getLobbyCode() {
        return this.lobbyCode;
    }

    public void setLobbyCode(String code) {
        this.lobbyCode = code;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String name) {
        this.hostName = name;
    }

    public int getHostColour() {
        return this.hostColour;
    }

    public void setHostColour(int colour) {
        this.hostColour = colour;
    }

    public String getHostColourString() {
        if (this.hostColour == 0) {
            return "White";
        } else {
            return "Black";
        }
    }

    public String getGuestName() {
        return this.guestName;
    }

    public void setGuestName(String name) {
        this.guestName = name;
    }

    public int getGuestColour() {
        return this.guestColour;
    }

    public void setGuestColour(int colour) {
        this.guestColour = colour;
    }

    public String getGuestColourString() {
        if (this.hostColour == 0) {
            return "Black";
        } else {
            return "White";
        }
    }
    
    public String getDisplayLobbyInfo() {
        return "  Lobby #" + lobbyCode + ",  Host: " + hostName + ",  Join as: " + getGuestColourString();
    }

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