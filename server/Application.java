import server.Server;
import server.ShutdownHook;

/**
 * [Application.java]
 * Contains main method that runs the server program.
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Application {
    public static void main(String[] args) {
        Server server = new Server();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(server));
        server.start();
    }
}