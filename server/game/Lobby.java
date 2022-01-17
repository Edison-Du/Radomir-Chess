package game;

import config.MessageTypes;
import server.ClientHandler;
import server.Message;

public class Lobby {
    
    private ClientHandler host, guest;
    private String code;
    private int hostColour;

    public Lobby(ClientHandler host) {
        generateCode();
        this.host = host;

        this.hostColour = (int)(Math.random() * 2);
    }

<<<<<<< HEAD
// Participants
=======
    public String getCode() {
        return code;
    }

    private void generateCode() {
        // Temporary
        this.code = Integer.toString((int) (Math.random() * (9999 - 1000)) + 1000);
    }

>>>>>>> ddfd27f58c5a7605d893b710695d9853d11bb65b
    public void setHost(ClientHandler host) {
        this.host = host;
    }

    public void setGuest(ClientHandler guest) {
        this.guest = guest;
        // Alert host that a guest has joined
        try {
            Message message = new Message(MessageTypes.GUEST_JOINED);
            message.addParam(Integer.toString(guest.getClientNum()));
            host.sendMessage(message);

        } catch (Exception e) {
            System.out.println("Could not send message to guest: client #" + guest.getClientNum());
        }
    }
<<<<<<< HEAD

=======
    
>>>>>>> ddfd27f58c5a7605d893b710695d9853d11bb65b
    public ClientHandler getHost() {
        return this.host;
    }

    public ClientHandler getGuest() {
        return this.guest;
    }

<<<<<<< HEAD

// Colours
    public int getHostColour() {
        return this.hostColour;
    }

    public int getGuestColour() {
        return (this.hostColour + 1) % 2; 
    }


    public boolean isFull() {
        return this.guest != null;
    }
    


    public String getCode() {
        return code;
=======
    public boolean isFull() {
        return this.guest != null;
>>>>>>> ddfd27f58c5a7605d893b710695d9853d11bb65b
    }

    public void leaveLobby(ClientHandler client) {
        if (client == this.guest) {
            this.guest = null;
            // Guest has left lobby

        } else {
            this.host = this.guest;

            // Empty lobby, delete it
            if (this.host == null) {

                
            // Host has left lobby, you are the host now
            } else {

            }
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


    public void sendChessMove(ClientHandler from, Message move) {
        
        ClientHandler receiver;
        
        if (from == host) {
            receiver = guest;
        } else {
            receiver = host;
        }

        if (receiver == null) {
            return;
        }

        receiver.sendMessage(move);
    }
}
