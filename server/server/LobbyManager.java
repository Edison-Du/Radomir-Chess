package server;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import config.MessageTypes;

/**
 * [LobbyManager.java]
 * Contains and manages all active game lobbies for clients
 * @author Nicholas Chew
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class LobbyManager {

    private final int MIN_CODE = 1000;
    private final int MAX_CODE = 9999;
    private final char LOBBY_INFO_SEPARATOR = ',';

    private HashMap<Integer, Lobby> activeGames;
    private Queue<Integer> lobbyCodes;
    
    /**
     * LobbyManager
     * Initializes a hashmap to store all active lobbies, as well
     * as a queue containing all possible lobby codes
     */
    public LobbyManager() {
        activeGames = new HashMap<>();
        lobbyCodes = new LinkedList<>();

        generateCodes();
    }

    /**
     * Shuffles all possible lobby codes and adds it to the
     * queue of lobby codes
     */
    public void generateCodes() {
        int[] codes = new int[MAX_CODE - MIN_CODE + 1];

        // Add all possible codes in order
        for (int i = MIN_CODE; i <= MAX_CODE; i++) {
            codes[i-MIN_CODE] = i;
        }

        // Shuffle codes by swapping each index with a random index before it
        for (int i = 0; i <= MAX_CODE - MIN_CODE; i++) {
            int index = (int)(Math.random() * (MAX_CODE - MIN_CODE));
            lobbyCodes.add(codes[index]);
            codes[index] = codes[i];
        }
    }

    /**
     * lobbyExists
     * Checks whether or not a lobby with a certain code exists
     * @param code the code of the lobby
     * @return whether or not the lobby exists
     */
    public boolean lobbyExists(int code) {
        return activeGames.get(code) != null;
    }

    /**
     * getLobby
     * Gets a lobby by code
     * @param code the code of the lobby
     * @return the lobby object, or null if the lobby does not exist
     */
    public Lobby getLobby(int code) {
        return activeGames.get(code);
    }

    /**
     * createLobby
     * Creates a lobby with the specified client as the host
     * @param host the client
     * @return the created lobby object, or null if there are no more lobby codes
     */
    public Lobby createLobby(ClientHandler host) {
        if (lobbyCodes.size() == 0) {
            return null;

        } else {
            int code = lobbyCodes.poll();
            Lobby lobby = new Lobby(host, code);
            activeGames.put(lobby.getCode(), lobby);
            return lobby;
        }
    }

    /**
     * removeLobby
     * Removes a lobby from the list of active lobbies
     * @param code the code of the lobby to remove
     * @return whether or not the lobby exists
     */
    public boolean removeLobby(int code) {
        if (lobbyExists(code)) {
            Lobby lobby = activeGames.remove(code);
            lobbyCodes.add(lobby.getCode());
            return true;
        } else {
            return false;
        }
    }

    /**
     * getPublicLobbyInfo
     * Prepares all relevant public lobby info that will be displayed to clients
     * The message will be parsed on client side and displayed as a list of public lobbies that can be joined
     * @return message containing all public lobby information
     */
    public Message getPublicLobbyInfo() {
        Message message = new Message(MessageTypes.DISPLAY_GAMES);
        int lobbyIndex = 0;
        for (Lobby lobby : activeGames.values()) {
            if (lobby.isPublic() && lobby.isJoinable()) {
                String lobbyParameter = "";
                lobbyParameter += Integer.toString(++lobbyIndex) + LOBBY_INFO_SEPARATOR;
                lobbyParameter += Integer.toString(lobby.getCode()) + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getHostName() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += Integer.toString(lobby.getHostColour()) + LOBBY_INFO_SEPARATOR;
                lobbyParameter += lobby.getGuestName() + LOBBY_INFO_SEPARATOR;
                lobbyParameter += Integer.toString(lobby.getGuestColour()) + LOBBY_INFO_SEPARATOR;
                message.addParam(lobbyParameter);
            }
        }
        return message;
    }
}