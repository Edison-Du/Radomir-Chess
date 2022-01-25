package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import config.Consts;

 /**
 * [ServerConnection.java]
 * 
 * THIS CLASS IS A SINGLETON, MEANING 
 * YOU CANNOT CONSTRUCT IT AND ONLY 
 * ONE INSTANCE EXISTS AT ALL TIMES
 * @author
 * @version 1.0 Jan 24, 2022
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

            socket = new Socket(Consts.SERVER_ADDRESS, Consts.SERVER_PORT);
            
            stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(socket.getOutputStream());    

            System.out.println("Connected to server.");
            
        } catch (Exception e) {
            System.out.println("Couldn't connect to server.");
            e.printStackTrace();
        }
    }


    // Static methods

    public static void createInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
    }

    public static void sendMessage(Message message) {

        if (message == null) {
            return;
        }

        // Make sure that an instance is active
        createInstance();

        instance.getOutput().println(message.getText());
        instance.getOutput().flush();
    }

    public static Message getMessage() {
        createInstance();

        try {
            String text = instance.getInput().readLine();
            Message message = Message.parse(text);
            return message;

        } catch (Exception e) {
            System.out.println("Unable to read message from server.");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean hasMessage() {

        try {
            createInstance();
            return instance.getInput().ready();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void close() {
        try {
            instance.getInput().close();
            instance.getOutput().close();
            instance = null;
            
        } catch (Exception e) {
            System.out.println("Unable to close input and output streams");
            e.printStackTrace();
        }
    }

    // Instance methods
    public BufferedReader getInput() {
        return this.input;
    }

    public PrintWriter getOutput() {
        return this.output;
    }
}