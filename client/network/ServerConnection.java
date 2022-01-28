package network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import config.Consts;

 /**
 * [ServerConnection.java]
 * A singleton class containing a socket connecting to the server,
 * and allows you to send/receive messages from the server.
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class ServerConnection {

    private static ServerConnection instance = null;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    
    private static boolean connected = false;
    
    /**
     * ServerConnection
     * Creates a socket connecting to the server and creates
     * associated input/output readers
     */
    private ServerConnection() {
        System.out.println("Attempting to connect to server.");

        try {
            InputStreamReader stream;

            socket = new Socket(Consts.SERVER_ADDRESS, Consts.SERVER_PORT);
            
            stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(socket.getOutputStream());    

            connected = true;

            System.out.println("Connected to server.");
            
        } catch (Exception e) {
            System.out.println("Couldn't connect to server.");
        }
    }

    /**
     * createInstance
     * Creates an instance of server connection, containing 
     * the socket and input/output readers
     */
    public static void createInstance() {
        if (instance == null) {
            instance = new ServerConnection();
        }
    }

    /**
     * sendMessage
     * Sends a message object to the server
     */
    public static void sendMessage(Message message) {
        // Make sure that an instance is active
        createInstance();

        if (message == null || !connected) {
            return;
        }

        // Flush an output to the server
        instance.getOutput().println(message.getText());
        instance.getOutput().flush();
    }

    /**
     * getMessage
     * Reads a message from the server
     * @return the message received from the server
     */
    public static Message getMessage() {
        createInstance();

        if (!connected) return null;

        try {
            String text = instance.getInput().readLine();
            Message message = Message.parse(text);
            return message;

        } catch (Exception e) {
            System.out.println("Unable to read message from server.");
            return null;
        }
    }

    /**
     * hasMessage
     * Checks if there is a message from the server
     * @return whether or not there is a message from the server
     */
    public static boolean hasMessage() {

        try {
            createInstance();

            if (!connected) return false;

            return instance.getInput().ready();

        } catch (Exception e) {
            System.out.println("Error checking server messages");
            return false;
        }
    }

    /**
     * close
     * Closes input and output readers to the server
     */
    public static void close() {
        try {
            instance.getInput().close();
            instance.getOutput().close();
            instance = null;
            
        } catch (Exception e) {
            System.out.println("Unable to close input and output streams");
        }
    }

    /**
     * getInput
     * Gets the input reader for the socket connecting to the server
     */
    public BufferedReader getInput() {
        return this.input;
    }

    /**
     * getOutput
     * Gets the output writer for the socket connecting to the server
     */
    public PrintWriter getOutput() {
        return this.output;
    }
}