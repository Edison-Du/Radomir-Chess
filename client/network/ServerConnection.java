package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import config.Consts;

public class ServerConnection {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    
    public ServerConnection() {
        System.out.println("Attempting to connect to server.");

        try {
            InputStreamReader stream;
            
            socket = new Socket(Consts.SERVER_ADDRESS, Consts.SERVER_PORT);
            
            stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(socket.getOutputStream());    
            
            String msg = input.readLine();
            System.out.println(msg);

        } catch (Exception e) {
            System.out.println("Couldn't connect to server.");
            e.printStackTrace();
        }
    }
}
