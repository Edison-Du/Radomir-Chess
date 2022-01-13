package server;

import java.util.HashMap;

import javax.swing.Action;

import game.Lobby;

public class LobbyManager {
    private HashMap<Integer, Lobby> activeGames;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
    }

    public boolean lobbyExists(int code) {
        return activeGames.get(code) != null;
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

    public Lobby getLobby(int code) {
        return activeGames.get(code);
    }

    public void print() {
        System.out.println(activeGames);
    }

    public HashMap<Integer, Lobby> getActiveGames() {
        return this.activeGames;
    }
}
