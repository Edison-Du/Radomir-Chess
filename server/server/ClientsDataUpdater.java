package server;

import config.Consts;

/**
 * [ClientsDataUpdater.java]
 * Thread that consistently sends updates to clients
 * through the client handler
 * @author Nicholas Chew
 * @version 1.0 Jan 24, 2022
 */
public class ClientsDataUpdater extends Thread {
    private ClientHandler clientHandler;

    /**
     * ClientsDataUpdater
     * @param clientHandler clientHandler that will be updated by this thread
     */
    public ClientsDataUpdater(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * run
     * Continuously sends server side information to clients
     * Updates number of players online and the list of public lobbies that can be joined
     */
    @Override   
    public void run() {
        try { 
            while (clientHandler.isAlive()) {
                clientHandler.updatePlayersOnline();
                clientHandler.updatePublicLobbies();
                Thread.sleep(Consts.UPDATE_CLIENT_INTERVAL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}