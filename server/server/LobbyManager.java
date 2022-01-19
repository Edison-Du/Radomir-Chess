package server;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import game.Lobby;

public class LobbyManager {
    private HashMap<String, Lobby> activeGames;
    private LinkedHashSet<ArrayList<String>> info;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
        info = new LinkedHashSet<>();
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

    public HashSet<ArrayList<String>> getInfo() {
        info = new LinkedHashSet<ArrayList<String>>();
        ArrayList<String> data;
        int lobbyNum = 0;
        for (Lobby lobby : activeGames.values()) {
            data = new ArrayList<>();
            data.add(Integer.toString(++lobbyNum));
            data.add(lobby.getCode());
            data.add(lobby.getHostName());
            if (lobby.getHostColour() == 1) {
                data.add("white");
            } else {
                data.add("black");
            }
            info.add(data);
        }
        return info;
    }
}
