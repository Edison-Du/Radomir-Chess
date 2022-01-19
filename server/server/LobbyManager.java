package server;

import java.util.HashMap;
import java.util.Set;

import config.MessageTypes;
import game.Lobby;

public class LobbyManager {
    private HashMap<String, Lobby> activeGames;
    // private HashSet<ArrayList<String>> info;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
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
        activeGames.put(lobby.getCode(), lobby);
        return lobby;
    }

    public boolean removeLobby(String code) {
        Lobby lobby = activeGames.remove(code);
        return lobby != null;
    }

    public Set<String> getActiveGames() {
        return this.activeGames.keySet();
    }

    public Message getLobbyInfo() {
        Message message = new Message(MessageTypes.DISPLAY_GAMES);

        int lobbyIndex = 0;
        for (Lobby lobby : activeGames.values()) {
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
