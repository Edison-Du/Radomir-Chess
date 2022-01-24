package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import config.MessageTypes;
import game.Lobby;

public class LobbyManager {
    private final int MIN_CODE = 1000;
    private final int MAX_CODE = 9999;
    private final char LOBBY_INFO_SEPARATOR = ',';

    private HashMap<Integer, Lobby> activeGames;
    private Queue<Integer> lobbyCodes;
    
    public LobbyManager() {
        activeGames = new HashMap<>();
        lobbyCodes = new LinkedList<>();

        generateCodes();
    }

    public void generateCodes() {
        int[] codes = new int[MAX_CODE - MIN_CODE + 1];
        for (int i = MIN_CODE; i <= MAX_CODE; i++) {
            codes[i-MIN_CODE] = i;
        }
        // Shuffle codes
        for (int i = 0; i <= MAX_CODE - MIN_CODE; i++) {
            int index = (int)(Math.random() * (MAX_CODE - MIN_CODE));
            lobbyCodes.add(codes[index]);
            codes[index] = codes[i];
        }
    }

    public boolean lobbyExists(int code) {
        return activeGames.get(code) != null;
    }

    public Lobby getLobby(int code) {
        return activeGames.get(code);
    }

    public Lobby createLobby(ClientHandler host) {
        if (lobbyCodes.size() == 0) {
            return null;
        }
        int code = lobbyCodes.poll();
        Lobby lobby = new Lobby(host, code);
        return lobby;
    }

    public void addLobby(Lobby lobby) {
        activeGames.put(lobby.getCode(), lobby);
    }

    public boolean removeLobby(int code) {
        if (lobbyExists(code)) {
            Lobby lobby = activeGames.remove(code);
            lobbyCodes.add(lobby.getCode());
            return true;
        } else {
            return false;
        }
    }

    public Message getPublicLobbyInfo() {
        Message message = new Message(MessageTypes.DISPLAY_GAMES);
        int lobbyIndex = 0;
        for (Lobby lobby : activeGames.values()) {
            if (lobby.isPublic() && lobby.isJoinable()) {
                String lobbyParameter = "";
                lobbyParameter += Integer.toString(++lobbyIndex) + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getCode() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getHostName() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getHostColour() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getGuestName() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getGuestColour() + LOBBY_INFO_SEPARATOR;
                message.addParam(lobbyParameter);
            }
        }
        return message;
    }
}