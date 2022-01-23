package server;

import config.Consts;

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
                Thread.sleep(Consts.UPDATE_CLIENT_INTERVAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}