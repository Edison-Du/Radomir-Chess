import javax.swing.SwingUtilities;

import network.ConnectionHandler;
import network.ServerConnection;
import network.ShutdownHook;
import views.Window;

/**
 * [Application.java]
 * Contains main method that runs the client program,
 * launching the window and connecting to the server
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Application {

    public static Window window;
    public static ConnectionHandler connectionHandler;
    
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Socket connection to server
                ServerConnection.createInstance();

                // Thread that runs when server shuts down
                Runtime.getRuntime().addShutdownHook(new ShutdownHook());

                // Initialize window and server connection thread
                window = new Window();
                connectionHandler = new ConnectionHandler(window);

                // This thread listens to messages from server
                connectionHandler.start();
            }
          });
    }
}