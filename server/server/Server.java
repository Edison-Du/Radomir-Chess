package server;

import java.net.ServerSocket;
import java.net.Socket;

import config.Consts;

/**
 * [Server.java]
 * This class represents a server with a server socket on a chosen port,
 * it continuously accepts connections from clients and adds a new thread
 * to handle each client.
 * 
 * It also manages a lobby system for chess games, and a database for users.
 */
public class Server {

    private ServerSocket serverSocket;
    private LobbyManager lobbyManager;
    private Database database;
    private DatabaseUpdater databaseUpdater;

    /**
     * Server
     * Creates server socket, lobby manager and database
     * and continuousely accepts connections
     */
    public Server() {
        try {
            serverSocket = new ServerSocket(Consts.PORT);
            lobbyManager = new LobbyManager();
            database = new Database();
            databaseUpdater = new DatabaseUpdater(database);

            databaseUpdater.start();

            System.out.println("Server has started.");
            
            // Continuously accept connections
            while (true) {
                this.acceptConnection();
            }

        } catch (Exception e) {
            System.out.println("Error starting server.");
            e.printStackTrace();
        }
    }

    /**
     * acceptConnection
     * Accepts a client and creates a new thread for that client
     * @return Whether or not the connection was accepted
     */
    private boolean acceptConnection() {
        try {
            // Accept socket and create new thread
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