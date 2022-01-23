package server;

public class ClientsOnlineUpdater extends Thread {
    private ClientHandler clientHandler;

    public ClientsOnlineUpdater(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override   
    public void run() {
        try { 
            while (clientHandler.isAlive()) {
                clientHandler.updatePlayersOnline();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

