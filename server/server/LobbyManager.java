package server;

import java.util.HashMap;

import config.MessageTypes;
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
        while (lobbyExists(lobby.getCode())) {
            lobby = new Lobby(host);
        }
        return lobby;
    }

    public void addLobby(Lobby lobby) {
        activeGames.put(lobby.getCode(), lobby);
    }

    public boolean removeLobby(String code) {
        Lobby lobby = activeGames.remove(code);
        return lobby != null;
    }

    public Message getPublicLobbyInfo() {
        Message message = new Message(MessageTypes.DISPLAY_GAMES);
        int lobbyIndex = 0;
        for (Lobby lobby : activeGames.values()) {
            if (lobby.isPublic() && lobby.isJoinable()) {
                String lobbyParameter = "";
                lobbyParameter += Integer.toString(++lobbyIndex) + ",";
                lobbyParameter += lobby.getCode() + ",";
                lobbyParameter += lobby.getHostName() + ",";
                lobbyParameter += lobby.getHostColour() + ",";
                lobbyParameter += lobby.getGuestName() + ",";
                lobbyParameter += lobby.getGuestColour() + ",";
                message.addParam(lobbyParameter);
            }
        }
        return message;
    }
}