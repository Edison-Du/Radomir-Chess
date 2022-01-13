package game;

import server.ClientHandler;

public class Lobby {
    
    private ClientHandler host, guest;
    private int code;

    public Lobby() {
        generateCode();
    }

    public void setHost(ClientHandler host) {
        this.host = host;
    }

    public void setGuest(ClientHandler guest) {
        this.guest = guest;
    }

    public int getCode() {
        return code;
    }

    private void generateCode() {
        // Temporary
        this.code = (int) (Math.random() * (9999 - 1000)) + 1000;
    }
}
