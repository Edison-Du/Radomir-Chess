package network;

import config.MessageTypes;

 /**
 * [ShutdownHook.java]
 * Sends a message to the server to alert them that the user is closing
 * their application and connection
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class ShutdownHook extends Thread {

    /**
     * run
     * Code executed when the program closes
     */
    @Override
    public void run() {
        Message msg = new Message(MessageTypes.EXIT_PROGRAM);
        ServerConnection.sendMessage(msg);
    }
}