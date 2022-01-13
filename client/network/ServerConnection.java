package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import config.Consts;

/**
 * THIS CLASS IS A SINGLETON, MEANING 
 * YOU CANNOT CONSTRUCT IT AND ONLY 
 * ONE INSTANCE EXISTS AT ALL TIMES
 */

public class ServerConnection {

    private static ServerConnection instance = null;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    
    private ServerConnection() {
        System.out.println("Attempting to connect to server.");

        try {
            InputStreamReader stream;
            String msg;

            socket = new Socket(Consts.SERVER_ADDRESS, Consts.SERVER_PORT);
            
            stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(socket.getOutputStream());    
            
            msg = input.readLine();
            System.out.println(msg);

        } catch (Exception e) {
            System.out.println("Couldn't connect to server.");
            e.printStackTrace();
        }
    }

    public static void createInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
    }

    // Just testing for now, change this later to have a return message (not void).
    public static void sendMessage(Message message) {
        // Make sure that an instance is active
        createInstance();

        // Debugging code
        System.out.println("Sending message to server: " + message.getText());
        instance.writeText(message.getText());
    }

    public static Message getMessage() {
        createInstance();

        Message message = Message.parse(instance.readText());
        return message;
    }

    public void writeText(String text) {
        this.output.println(text);
        this.output.flush();
    }

    public String readText() {
        try {
            return this.input.readLine();

        } catch (Exception e) {
            System.out.println("Unable to read message from server.");
            e.printStackTrace();
            return null;
        }
    }
}
