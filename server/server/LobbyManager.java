package server;

import java.util.HashMap;

import config.MessageTypes;
import game.Lobby;

public class LobbyManager {
    private HashMap<String, Lobby> activeGames;
    private HashMap<String, Lobby> activePublicGames;
    // private HashSet<ArrayList<String>> info;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
        activePublicGames = new HashMap<>();
        // info = new LinkedHashSet<>();
    }

    public boolean lobbyExists(String code) {
        return activeGames.get(code) != null;
    }

    public Lobby getLobby(String code) {
        return activeGames.get(code);
    }

    public Lobby createLobby(ClientHandler host) {
        Lobby lobby = new Lobby(host);
        // This is terrible but works for now
        // We may implement smarter algorithm later
        while (lobbyExists(lobby.getCode())) {
            lobby = new Lobby(host);
        }
        return lobby;
    }

    public void addLobby(Lobby lobby) {
        activeGames.put(lobby.getCode(), lobby);
        if (lobby.isPublic()) {
            activePublicGames.put(lobby.getCode(), lobby);
        }
    }

    public boolean removeLobby(String code) {
        Lobby lobby = activeGames.remove(code);
        if (lobby.isPublic()) {
            activePublicGames.remove(code);
        }
        return lobby != null;
    }

    public Message getPublicLobbyInfo() {
        Message message = new Message(MessageTypes.DISPLAY_GAMES);

        int lobbyIndex = 0;
        for (Lobby lobby : activePublicGames.values()) {
            String lobbyParameter = "";
            lobbyParameter += Integer.toString(++lobbyIndex) + ",";
            lobbyParameter += lobby.getCode() + ",";
            lobbyParameter += lobby.getHostName() + ",";
            lobbyParameter += lobby.getHostColour() + ",";
            message.addParam(lobbyParameter);
        }

        return message;
    }
}