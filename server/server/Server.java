package server;

import java.net.ServerSocket;
import java.net.Socket;

import config.Consts;

/**
 * [Server.java]
 * This class represents a server with a server socket on a chosen port,
 * it continuously accepts connections from clients and creates a new thread
 * to handle each client. It also manages a lobby system for chess games and
 * a database for user information.
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Server extends Thread {

    private ServerSocket serverSocket;
    private LobbyManager lobbyManager;
    private Database database;
    private DatabaseUpdater databaseUpdater;
    private boolean isRunning;

    /**
     * Server
     * Creates server socket, lobby manager and database
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(Consts.PORT);
            lobbyManager = new LobbyManager();
            database = new Database();
            databaseUpdater = new DatabaseUpdater(database);

        } catch (Exception e) {
            System.out.println("Error starting server.");
            e.printStackTrace();
        }
    }

    /**
     * run
     * Continuously accepts connections from clients and
     * starts a thread to update the user database periodically
     */
    @Override
    public void run() {
        System.out.println("Server has started.");

        isRunning = true;
        databaseUpdater.start();
        
        while (isRunning) {
            this.acceptConnection();
        }
    }

    /**
     * acceptConnection
     * Accepts a client and creates and starts a new thread for that client
     * @return Whether or not the connection was accepted
     */
    private boolean acceptConnection() {
        try {
            Socket socket = serverSocket.accept();
            Thread thread = new ClientHandler(this, socket);
            thread.start();
            return true;

        } catch (Exception e) {
            System.out.println("Error accepting connection.");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * getLobbyManager
     * Getter for lobby manager object
     * @return the lobby manager object
     */
    public LobbyManager getLobbyManager() {
        return this.lobbyManager;
    }

    /**
     * getDatabase
     * Getter for database object
     * @return the database object
     */
    public Database getDatabase() {
        return this.database;
    }
}