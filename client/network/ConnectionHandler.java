package network;

import config.MessageTypes;
import views.Window;

public class ConnectionHandler extends Thread {

    private Window window;
    private boolean isActive;

    public ConnectionHandler(Window window) {
        isActive = true;
        this.window = window;
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                if (ServerConnection.hasMessage()) {
                    Message message = ServerConnection.getMessage();
                    evalMessage(message);
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to receive message from server.");
            e.printStackTrace();
        }
    }

    public void evalMessage(Message message) {

        if (message == null) {
            return;

        } else if (message.getType() == MessageTypes.EXIT_PROGRAM) {
            isActive = false;
            ServerConnection.close();
        }
        System.out.println(message.getText());
        
        // else if (message.getType() == MessageTypes.GAME_CREATED) {

        // }
    }   
}
