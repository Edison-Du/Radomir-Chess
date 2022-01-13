
import network.Message;
import network.ServerConnection;
import network.ShutdownHook;
import views.Window;

public class Application {
    public static void main(String[] args) {

        ServerConnection.createInstance();

        Runtime.getRuntime().addShutdownHook(new ShutdownHook());

        new Window();
    }
}