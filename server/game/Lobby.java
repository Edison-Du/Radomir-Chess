package game;

import config.MessageTypes;
import javafx.scene.shape.Mesh;
import server.ClientHandler;
import server.Message;

public class Lobby {
    
    private ClientHandler host, guest;
    private String code;

    public Lobby() {
        generateCode();
    }

    public void setHost(ClientHandler host) {
        this.host = host;
    }

    public void setGuest(ClientHandler guest) {
        this.guest = guest;
        // New guest message
        try {
            Message message = new Message(MessageTypes.GUEST_JOINED);
            
            guest.sendMessage(message);
        } catch (Exception e) {
            System.out.println("Could not send message to guest: client #" + guest.getClientNum());
        }
    }
    
    public ClientHandler getHost() {
        return this.host;
    }

    public ClientHandler getGuest() {
        return this.guest;
    }

    public String getCode() {
        return code;
    }

    private void generateCode() {
        // Temporary
        this.code = Integer.toString((int) (Math.random() * (9999 - 1000)) + 1000);
    }
}
