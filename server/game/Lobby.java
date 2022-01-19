package game;

import config.MessageTypes;
import server.ClientHandler;
import server.Message;

public class Lobby {
    
    private ClientHandler host, guest;
    private String hostName, guestName;
    private String code;
    private int hostColour;
    private boolean publicStatus;

    public Lobby(ClientHandler host) {
        generateCode();
        this.host = host;
        this.hostName = this.host.getClientName();
        this.hostColour = (int)(Math.random() * 2);
    }

    public String getCode() {
        return this.code;
    }

    private void generateCode() {
        // Temporary
        this.code = Integer.toString((int) (Math.random() * (9999 - 1000)) + 1000);
    }

    public boolean getPublicStatus() {
        return this.publicStatus;
    }
    
    public void setPublicStatus(boolean publicStatus) {
        this.publicStatus = publicStatus;
    }
    
    public String getHostName() {
        return this.hostName;
    }

    public String getGuestName() {
        return this.guestName;
    }

    public void setHost(ClientHandler host) {
        this.host = host;
    }

    public boolean setGuest(ClientHandler guest) {

        if (this.guest != null) {
            return false;
        }

        this.guest = guest;
        this.guestName = this.guest.getClientName();
        // Alert host that a guest has joined
        try {
            Message message = new Message(MessageTypes.GUEST_JOINED);
            message.addParam(Integer.toString(guest.getClientNum()));
            host.sendMessage(message);

        } catch (Exception e) {
            System.out.println("Could not send message to guest: client #" + guest.getClientNum());
        }

        return true;
    }

    public ClientHandler getHost() {
        return this.host;
    }

    public ClientHandler getGuest() {
        return this.guest;
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

        System.out.println("Host: " + host.getClientNum() + ", " + "Guest: " + guest.getClientNum());

        receiver.sendMessage(message);
    }


    // public void sendChessMove(ClientHandler from, Message move) {
        
    //     ClientHandler receiver;
        
    //     if (from == host) {
    //         receiver = guest;
    //     } else {
    //         receiver = host;
    //     }

    //     if (receiver == null) {
    //         return;
    //     }

    //     receiver.sendMessage(move);
    // }
}
