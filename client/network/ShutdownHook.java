package network;

import config.MessageTypes;

public class ShutdownHook extends Thread {
    @Override
    public void run() {
        // Shuts down client handler on the server side
        Message msg = new Message(MessageTypes.EXIT_PROGRAM);
        ServerConnection.sendMessage(msg);
    }
}