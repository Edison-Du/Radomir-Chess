package game;

import config.MessageTypes;
import server.ClientHandler;
import server.Message;

public class Lobby {
    
    private ClientHandler host, guest;
    private String hostName, guestName;
    private String code;
    private int hostColour;
    private String lobbyVisibility;

    private boolean joinable;


    public Lobby(ClientHandler host) {
        generateCode();
        this.host = host;
        this.hostName = this.host.getClientName();
        this.hostColour = (int)(Math.random() * 2);
        this.lobbyVisibility = "public";
        this.joinable = true;
    }

    public String getCode() {
        return this.code;
    }

    private void generateCode() {
        // Temporary
        this.code = Integer.toString((int) (Math.random() * (9999 - 1000)) + 1000);
    }

    public boolean isPublic() {
        if (this.lobbyVisibility.equals("Public")) {
            return true;
        }
        return false;
    }

    public String getLobbyVisibility() {
        return this.lobbyVisibility;
    }
    
    public void setPublicStatus(String lobbyVisibility) {
        this.lobbyVisibility = lobbyVisibility;
    }
    
    public String getHostName() {
        return this.hostName;
    }

    public String getGuestName() {
        return this.guestName;
    }

    public void setHost(ClientHandler host) {
        this.host = host;
        this.hostName = host.getClientName();
    }

    public boolean setGuest(ClientHandler guest) {

        if (this.guest != null || !joinable) {
            return false;
        }

        if (host == null) {
            setHost(guest);

        } else {
            this.guest = guest;
            this.guestName = this.guest.getClientName();

            // Alert host that a guest has joined
            Message message = new Message(MessageTypes.GUEST_JOINED);
            message.addParam(this.guestName);
            host.sendMessage(message);
        }
        return true;
    }
    

    public ClientHandler getHost() {
        return this.host;
    }

    public ClientHandler getGuest() {
        return this.guest;
    }

    public boolean isJoinable() {
        return this.joinable;
    }

    public void setJoinable(boolean joinable) {
        this.joinable = joinable;
    }

// Colours
    public int getHostColour() {
        return this.hostColour;
    }

    public int getGuestColour() {
        return (this.hostColour + 1) % 2; 
    }

    public void leaveLobby(ClientHandler client) {
        if (client != this.guest) {
            this.host = this.guest;
            this.hostColour = (hostColour + 1) % 2;
        }

        this.guest = null;

        // Send host message
        try {
            if (this.host != null) {
                Message messageToPlayerRemaining = new Message(MessageTypes.OPPONENT_LEFT);
                this.host.sendMessage(messageToPlayerRemaining);
            }
            Message messageToLeaver = new Message(MessageTypes.LEFT_SUCCESFULLY);
            client.sendMessage(messageToLeaver);

        } catch (Exception e) {
            System.out.println("Error sending message participants.");
        }
    } 

    public void sendMessage(ClientHandler from, Message message) {
        
        ClientHandler receiver;

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

    // public void resign(ClientHandler player) {
    //     if (player == host) {


    //     } else if (player == guest) {

    //     }
    // }
}
