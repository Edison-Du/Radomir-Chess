package network;

import java.util.ArrayList;

public class Lobby {

    private static char SEPARATOR = ',';

    private int lobbyIndex;
    private String lobbyCode;
    private String hostName;
    private int hostColour;

    public Lobby() {
        lobbyIndex = 0;
        lobbyCode = "";
        hostName = "";
        hostColour = 0;
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

    public String getDisplayLobbyInfo() {
        return "Lobby #" + lobbyIndex + ", Code: " + lobbyCode + ", Host: " + hostName + ", Host Colour: " + hostColour;
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
        return lobby;
    }
}