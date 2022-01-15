package network;

import config.MessageTypes;
import config.Page;
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
                    System.out.println("Received message: " + message.getText());
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

        } else if (message.getType().equals(MessageTypes.GAME_CREATED)) { 
            createGame(message);

        } else if (message.getType().equals(MessageTypes.JOINED_GAME)) {
            joinGame(message);

        } else if (message.getType().equals(MessageTypes.GAME_NOT_FOUND)) {


        } else if (message.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            isActive = false;
            ServerConnection.close();
        }
        
        
    }   

    public void createGame(Message message) {
        window.changePage(Page.GAME);
        window.gamePanel.setLobbyCode(message.getParam(0));
    }

    public void joinGame(Message message) {
        window.changePage(Page.GAME);
        window.gamePanel.setLobbyCode(message.getParam(0));
    }
}
