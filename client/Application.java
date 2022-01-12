import network.Request;
import network.ServerConnection;
import views.Window;

public class Application {
    public static void main(String[] args) {

        ServerConnection.createInstance();

        new Window();
    }
}