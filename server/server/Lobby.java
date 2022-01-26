package server;

import config.MessageTypes;
import config.Consts;

/**
 * [Lobby.java]
 * Represents a lobby with two clients playing chess
 * along with lobby information
 * @author Nicholas Chew
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Lobby {
    
    private ClientHandler host, guest;
    private int code;
    private int hostColour;
    private String lobbyVisibility;
    private boolean joinable;

    /**
     * Lobby
     * Creates a lobby object with a code and a host
     * @param host the client that is the host of the lobby
     * @param code the lobby code
     */
    public Lobby(ClientHandler host, int code) {
        this.code = code;
        this.host = host;
        this.hostColour = (int)(Math.random() * 2);
        this.lobbyVisibility = Consts.PUBLIC_LOBBY_STATUS;
        this.joinable = true;
    }

    /**
     * getCode
     * Gets the lobby code
     * @return code
     */
    public int getCode() {
        return this.code;
    }

    /**
     * isPublic
     * Checks if the lobby is public
     * @return if lobby is public
     */
    public boolean isPublic() {
        if (this.lobbyVisibility.equals(Consts.PUBLIC_LOBBY_STATUS)) {
            return true;
        }
        return false;
    }

    /**
     * getLobbyVisibility
     * Returns whether a lobby has public or private status
     * @return lobbyVisibility
     */
    public String getLobbyVisibility() {
        return this.lobbyVisibility;
    }
    
    /**
     * setLobbyVisibility
     * Sets the lobby visibility to public or private
     * @param lobbyVisibility
     */
    public void setLobbyVisibility(String lobbyVisibility) {
        this.lobbyVisibility = lobbyVisibility;
    }
    
    /**
     * getHostName
     * Gets host client name
     * @return host client name
     */
    public String getHostName() {
        if (this.host == null) {
            return null;
        }
        return this.host.getClientName();
    }

    /**
     * getGuestName
     * Gets guest client name
     * @return guest client name
     */
    public String getGuestName() {
        if (this.guest == null) {
            return null;
        }
        return this.guest.getClientName();
    }

    /**
     * setHost
     * Set the host of the lobby
     * @param host the client to set as the host
     */
    public void setHost(ClientHandler host) {
        this.host = host;
    }

    /**
     * setGuest
     * Sets the guest of the lobby
     * @param guest the client to set as the guest
     * @return whether or not the lobby can be joined or not
     */
    public boolean setGuest(ClientHandler guest) {
        if (this.guest != null || !joinable) {
            return false;
        }
        // If there is no host, the guest becomes the host
        if (host == null) {
            setHost(guest);
        } else {
            this.guest = guest;
            Message message = new Message(MessageTypes.GUEST_JOINED);
            message.addParam(getGuestName());
            host.sendMessage(message);
        }
        return true;
    }

    /**
     * getHost
     * Getter for the host
     * @return the client that is hosting this lobby
     */
    public ClientHandler getHost() {
        return this.host;
    }

    /**
     * getGuest
     * Getter for the guest
     * @return the client that is the guest in this lobby
     */
    public ClientHandler getGuest() {
        return this.guest;
    }

    /**
     * isJoinable
     * Checks if the lobby can be joined
     * @return whether or not the lobby can be joined
     */
    public boolean isJoinable() {
        return this.joinable;
    }

    /**
     * setJoinable
     * Set whether or not the lobby can be joined
     * @param joinable whether or not the lobby can be joined
     */
    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

    /**
     * setHostColour
     * Sets what colour the host is playing as in the chess game
     * @param colour the colour the host is playing as
     */
    public void setHostColour(int colour) {
        this.hostColour = colour;
    }

    /**
     * getHostColour
     * Gets what colour the host is playing as in the chess game
     * @return the colour the host is playing as
     */
    public int getHostColour() {
        return this.hostColour;
    }

    /**
     * getGuestColour
     * Gets what colour the guest is playing as in the chess game
     * @return the colour the guest is playing as
     */
    public int getGuestColour() {
        return (this.hostColour + 1) % 2; 
    }

    /**
     * leaveLobby
     * Removes the specified client from the lobby
     * @param client the client to remove
     */
    public void leaveLobby(ClientHandler client) {

        // Set the guest as the host if the host is removed
        if (client != this.guest) {
            this.host = this.guest;
            this.hostColour = (hostColour + 1) % 2;
        }
        this.guest = null;

        // Message host that the guest has left
        if (this.host != null) {
            Message messageToPlayerRemaining = new Message(MessageTypes.OPPONENT_LEFT);
            this.host.sendMessage(messageToPlayerRemaining);
        }

        // Message leaver that they've succesfully left
        Message messageToLeaver = new Message(MessageTypes.LEFT_SUCCESFULLY);
        client.sendMessage(messageToLeaver);
    } 

    /**
     * sendMessage
     * Sends a message from the specified client (guest or host) to the
     * other client in the lobby (guest or host)
     * @param from the client that is sending the message
     * @param message
     */
    public void sendMessage(ClientHandler from, Message message) {
        
        ClientHandler receiver;

        // Determine who is sending the message
        if (from == host) {
            receiver = guest;
        } else {
            receiver = host;
        }

        if (receiver == null) {
            return;
        }

        receiver.sendMessage(message);
    }
}