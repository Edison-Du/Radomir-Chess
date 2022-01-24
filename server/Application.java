import server.Server;
import server.ShutdownHook;

public class Application {
    public static void main(String[] args) {
        Server server = new Server();
        
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(server));

        server.start();
    }
}