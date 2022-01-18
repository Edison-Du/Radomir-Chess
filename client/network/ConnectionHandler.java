package network;

import config.MessageTypes;
import config.Page;
import views.Window;
import views.pages.MultiplayerPanel;

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

        } else if (message.getType().equals(MessageTypes.JOIN_ERROR)) {

        } else if (message.getType().equals(MessageTypes.GUEST_JOINED)) {
            guestJoined(message);

        } else if (message.getType().equals(MessageTypes.OPPONENT_LEFT)) {
            opponentLeft(message);

        } else if (message.getType().equals(MessageTypes.LEFT_SUCCESFULLY)) {
            leaveGame(message);

        } else if (message.getType().equals(MessageTypes.PLAYER_COLOUR)) {
            setPlayerColour(message);

        } else if (message.getType().equals(MessageTypes.SENT_TEXT)) {
            addTextMessage(message);

        } else if (message.getType().equals(MessageTypes.CHESS_MOVE)) {
            processOpponentChessMove(message);

        } else if (message.getType().equals(MessageTypes.DISPLAY_GAMES)) {
            displayGames(message);

        } else if (message.getType().equals(MessageTypes.LOGIN_ACCEPTED)) {
            login(message.getParam(0));

        } else if (message.getType().equals(MessageTypes.LOGIN_FAILED)) {
            window.loginPanel.displayLoginError();
        
        } else if (message.getType().equals(MessageTypes.REGISTER_FAILED)){
            window.loginPanel.displayRegisterError();
    
        } else if (message.getType().equals(MessageTypes.LOGOUT)){
            logout();

        } else if (message.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            isActive = false;
            ServerConnection.close();
        }
    }   

    public void login(String username){
        window.navigationBar.setUsername(username);
        window.setLoggedIn(true);
        window.changePage(Page.PLAY);
    }

    public void logout() {
        window.setLoggedIn(false);
        window.changePage(Page.LOGIN);
    }

    public void createGame(Message message) {
        String code = message.getParam(0);

        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setHost(true);
    }

    public void joinGame(Message message) {
        String code = message.getParam(0);
        int host = Integer.parseInt(message.getParam(1));

        window.changePage(Page.GAME);
        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setHost(false);
        window.gamePanel.addOther(host);

    }

    public void guestJoined(Message message) {
        int guest = Integer.parseInt(message.getParam(0));

        window.gamePanel.addOther(guest);
    }

    public void opponentLeft(Message message) {
        window.gamePanel.messagePanel.addTextMessage("Opponent has left lobby");
    }

    public void leaveGame(Message message) {
        window.setInGame(false);
        window.changePage(Page.PLAY);
    }


    public void setPlayerColour(Message message) {
        int colour = Integer.parseInt(message.getParam(0));
        
        window.gamePanel.boardPanel.setPlayerColour(colour);
    }

    public void processOpponentChessMove(Message message) {
        String t1 = message.getParam(0);
        String t2 = message.getParam(1);
        String p = message.getParam(2);

        window.gamePanel.movesPanel.addMove(t2);
        window.gamePanel.boardPanel.makeOpponentMove(t1, t2, p);
    }


    public void addTextMessage(Message message) {
        String text = message.getParam(0);

        window.gamePanel.addMessageFromOther(text);
    }

    public void displayGames(Message message) {
        String games = message.getParam(0);
        window.browseGamesPanel.setLobbyList(games);
    }
}