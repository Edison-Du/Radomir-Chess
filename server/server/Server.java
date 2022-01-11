package server;

import java.net.ServerSocket;
import java.net.Socket;

import config.Consts;

public class Server {

    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(Consts.PORT);
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
            // Later we may want to assign ID to each client
            Thread thread = new ClientHandler(socket);
            thread.start();
            return true;

        } catch (Exception e) {
            System.out.println("Error accepting connection.");
            e.printStackTrace();
            return false;
        }
    }
}