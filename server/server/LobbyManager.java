package server;

import java.util.HashMap;

import javax.swing.Action;

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

    public Lobby createLobby() {
        Lobby lobby = new Lobby();

        // This is terrible but works for now
        // We may implement smarter algorithm later
        while (lobbyExists(lobby.getCode())) {
            lobby = new Lobby();
            
        }

        activeGames.put(lobby.getCode(), lobby);

        return lobby;
    }

    public HashMap<String, Lobby> getActiveGames() {
        return this.activeGames;
    }
}
