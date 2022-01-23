package server;

import config.Consts;

public class ClientsDataUpdater extends Thread {
    private ClientHandler clientHandler;

    public ClientsDataUpdater(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    @Override   
    public void run() {
        try { 
            while (clientHandler.isAlive()) {
                clientHandler.updatePlayersOnline();
                clientHandler.browseGames();
                Thread.sleep(Consts.UPDATE_CLIENT_INTERVAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

