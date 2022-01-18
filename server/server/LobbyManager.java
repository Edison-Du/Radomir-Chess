package server;

import java.util.HashMap;

import game.Lobby;

public class LobbyManager {
    private HashMap<String, Lobby> activeGames;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
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

    public HashMap<String, Lobby> getActiveGames() {
        return this.activeGames;
    }
}
