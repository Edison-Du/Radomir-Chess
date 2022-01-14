package network;

import java.applet.Applet;

import config.MessageTypes;

public class ShutdownHook extends Thread {
    @Override
    public void run() {
        try {
            // Shuts down client handler on the server side
            Message msg = new Message(MessageTypes.EXIT_PROGRAM);
            ServerConnection.sendMessage(msg);
            
        } catch (Exception e) {
            System.out.println("Error shutting down server connection.");
            e.printStackTrace();
        }
    }
}
