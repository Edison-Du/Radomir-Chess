package network;

import config.MessageTypes;

 /**
 * [ShutdownHook.java]
 * 
 * @author
 * @version 1.0 Jan 24, 2022
 */
public class ShutdownHook extends Thread {
    @Override
    public void run() {
        // Shuts down client handler on the server side
        Message msg = new Message(MessageTypes.EXIT_PROGRAM);
        ServerConnection.sendMessage(msg);
    }
}