package server;

import java.net.ServerSocket;
import java.net.Socket;

import config.Consts;
import game.Lobby;

import java.util.*;

public class Server {

    private ServerSocket serverSocket;
    private LobbyManager lobbyManager;
    private Database database;

    public Server() {
        try {
            serverSocket = new ServerSocket(Consts.PORT);
            lobbyManager = new LobbyManager();
            database = new Database();

            System.out.println("Server has started.");
            
            while (true) {
                this.acceptConnection();
            }

        } catch (Exception e) {
            System.out.println("Error starting server.");
            e.printStackTrace();
        }

    }

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

    public LobbyManager getLobbyManager() {
        return this.lobbyManager;
    }

    public Database getDatabase() {
        return this.database;
    }
}