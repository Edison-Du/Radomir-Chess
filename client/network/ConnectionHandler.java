package network;

import config.GameState;
import config.MessageTypes;
import config.Page;
import views.Window;

import java.util.ArrayList;

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

        } else if(message.getType().equals(MessageTypes.CHECKMATE)) {
            processCheckmate();

        } else if(message.getType().equals(MessageTypes.STALEMATE)) {
            processStalemate();

        } else if (message.getType().equals(MessageTypes.RESIGNATION)) {
            processOpponentResignation();

        } else if (message.getType().equals(MessageTypes.PLAY_AGAIN)) {
            processPlayAgain();

        } else if (message.getType().equals(MessageTypes.TAKEBACK_REQUESTED)){
            processRequestTakeback();

        } else if (message.getType().equals(MessageTypes.TAKEBACK_ACCEPTED)){
            processTakeback();

        } else if (message.getType().equals(MessageTypes.DISPLAY_GAMES)) {
            displayLobbies(message);

        } else if (message.getType().equals(MessageTypes.LOBBY_VISIBILITY)) {
            setLobbyVisibility(message);

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
    
    public void processCheckmate() {
        window.gamePanel.setGameState(GameState.CHECKMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Checkmate");
    }

    public void processStalemate() {
        window.gamePanel.setGameState(GameState.STALEMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Stalemate");
    }

    public void processRequestTakeback(){
        window.gamePanel.addTakeback();
    }

    public void processTakeback(){
        window.gamePanel.undoMove();
    }

    public void login(String username){
        window.navigationBar.setUsername(username);
        window.setLoggedIn(true);
        window.changePage(Page.PLAY);
    }

    public void logout() {
        window.loginPanel.clearError();
        window.setLoggedIn(false);
        window.changePage(Page.LOGIN);
    }

    public void createGame(Message message) {
        String code = message.getParam(0);

        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setHost(true);

        window.gamePanel.resetGame();
        window.gamePanel.resetChat();
    }

    public void joinGame(Message message) {
        String code = message.getParam(0);
        String hostName = message.getParam(1);
        String visibility = message.getParam(2);

        window.changePage(Page.GAME);
        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setHost(false);
        window.gamePanel.addOther(hostName);
        window.gamePanel.setLobbyVisibility(visibility);

        window.gamePanel.resetGame();
        window.gamePanel.resetChat();
    }

    public void guestJoined(Message message) {
        String guestName = message.getParam(0);

        window.gamePanel.resetGame();
        window.gamePanel.setGameState(GameState.ONGOING);
        window.gamePanel.setAlone(false);

        window.gamePanel.addOther(guestName);
        window.gamePanel.messagePanel.addTextMessage(guestName + " has joined the lobby.");
        
    }

    public void opponentLeft(Message message) {
        window.gamePanel.messagePanel.addTextMessage("Opponent has left lobby");
        if (window.gamePanel.getGameState() == GameState.ONGOING) {
            processOpponentResignation();
        } else if ( (window.gamePanel.getGameState() != GameState.WAITING) 
                  && window.gamePanel.isPlayingAgain()) {
            window.gamePanel.setGameState(GameState.WAITING);
            window.gamePanel.resetGame();
        }
        window.gamePanel.setAlone(true);
    }

    public void leaveGame(Message message) {
        window.setInGame(false);
        window.changePage(Page.PLAY);
    }

    public void processPlayAgain() {
        window.gamePanel.setOpponentPlayAgain(true);
    }

    public void setPlayerColour(Message message) {
        int colour = Integer.parseInt(message.getParam(0));
        
        window.gamePanel.setPlayerColour(colour);
    }

    public void processOpponentChessMove(Message message) {
        String t1 = message.getParam(0);
        String t2 = message.getParam(1);
        String p = message.getParam(2);

        // window.gamePanel.movesPanel.addMove(t2);
        window.gamePanel.boardPanel.makeOpponentMove(t1, t2, p);
    }

    public void processOpponentResignation() {
        if (window.gamePanel.getPlayerColour() == 0) {
            window.gamePanel.setGameState(GameState.WHITE_VICTORY_RESIGN);
        } else {
            window.gamePanel.setGameState(GameState.BLACK_VICTORY_RESIGN);
        }
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Your Opponent Has Resigned");
    }


    public void addTextMessage(Message message) {
        String text = message.getParam(0);
        window.gamePanel.addMessageFromOther(text);
    }

    public void displayLobbies(Message message) {
        ArrayList<Lobby> lobbies = new ArrayList<>();

        for (int i = 0; i < message.getNumParams(); i++) {
            lobbies.add(Lobby.parseLobbyFromString(message.getParam(i)));
        }
        window.browseGamesPanel.setLobbyList(lobbies);
    }

    public void setLobbyVisibility(Message message) {
        String visibility = message.getParam(0);
        window.gamePanel.setLobbyVisibility(visibility);
    }
}