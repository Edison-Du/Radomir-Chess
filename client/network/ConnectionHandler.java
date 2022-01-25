package network;

import config.GameState;
import config.MessageTypes;
import config.Page;
import config.UserInterface;
import views.Window;

import java.util.ArrayList;

/**
 * [ConnectionHandler.java]
 * 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class ConnectionHandler extends Thread {

    private Window window;
    private boolean isActive;

    private int clientNum;
    private String clientName;

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

        } else if (message.getType().equals(MessageTypes.CONNECTION_ACCEPTED)) {
            setClientInfo(message);

        } else if (message.getType().equals(MessageTypes.GAME_CREATED)) { 
            createGame(message);

        } else if (message.getType().equals(MessageTypes.CREATE_ERROR)) { 
            processGameCreationError();

        } else if (message.getType().equals(MessageTypes.JOINED_GAME)) {
            joinGame(message);

        } else if (message.getType().equals(MessageTypes.JOIN_ERROR)) {
            processJoinError(message);

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

        } else if(message.getType().equals(MessageTypes.WHITE_VICTORY_CHECKMATE)) {
            processWhiteCheckmate(message);

        } else if(message.getType().equals(MessageTypes.BLACK_VICTORY_CHECKMATE)) {
            processBlackCheckmate(message);

        } else if(message.getType().equals(MessageTypes.STALEMATE)) {
            processStalemate();

        } else if (message.getType().equals(MessageTypes.RESIGNATION)) {
            processOpponentResignation();

        } else if (message.getType().equals(MessageTypes.PLAY_AGAIN)) {
            processPlayAgain();

        } else if (message.getType().equals(MessageTypes.TAKEBACK_REQUESTED)){
            processTakebackRequest();

        } else if (message.getType().equals(MessageTypes.TAKEBACK_ACCEPTED)){
            processTakebackAcceptance();

        } else if (message.getType().equals(MessageTypes.DRAW_OFFERED)){
            processDrawOffer();

        } else if (message.getType().equals(MessageTypes.DRAW_ACCEPTED)){
            processDraw();

        } else if (message.getType().equals(MessageTypes.DISPLAY_GAMES)) {
            displayLobbies(message);

        } else if (message.getType().equals(MessageTypes.LOBBY_VISIBILITY)) {
            setLobbyVisibility(message);

        } else if (message.getType().equals(MessageTypes.LOGIN_ACCEPTED)) {
            login(message);

        } else if (message.getType().equals(MessageTypes.LOGIN_FAILED)) {
            handleLoginFailed();
        
        } else if (message.getType().equals(MessageTypes.REGISTER_FAILED)){
            handleRegisterFailed();
    
        } else if (message.getType().equals(MessageTypes.LOGOUT)){
            logout();

        } else if (message.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            isActive = false;
            ServerConnection.close();

        } else if (message.getType().equals(MessageTypes.GET_PLAYERS_ONLINE)) {
            setPlayersOnline(message);
        }
    }

    public void setClientInfo(Message message) {
        clientNum = Integer.parseInt(message.getParam(0)); 
        clientName = "Guest " + clientNum;
        window.navigationBar.setUsername(clientName);
    }

    public void processJoinError(Message message) {
        window.joinGamePanel.displayError(message.getParam(0));
    }
    
    public void processGameCreationError() {
        window.gameSetupPanel.displayError();
    }
    
    public void processWhiteCheckmate(Message message) {
        window.gamePanel.setGameState(GameState.WHITE_VICTORY_CHECKMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("White wins by checkmate");
    }

    public void processBlackCheckmate(Message message) {
        window.gamePanel.setGameState(GameState.BLACK_VICTORY_CHECKMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Black wins by checkmate");
    }

    public void processStalemate() {
        window.gamePanel.setGameState(GameState.STALEMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Stalemate");
    }

    public void processTakebackRequest(){
        window.gamePanel.addTakebackRequest();
    }

    public void processTakebackAcceptance(){
        window.gamePanel.performTakeback();
    }

    public void login(Message message){
        clientName = message.getParam(0);
        window.navigationBar.setUsername(clientName);

        int[] userPreferences = new int[UserInterface.NUM_SETTINGS];
        for (int i = 0; i < UserInterface.NUM_SETTINGS; i++) {
            userPreferences[i] = Integer.parseInt(message.getParam(i+1));
        }
        window.setCurrentSettings(userPreferences);

        window.setLoggedIn(true);
        window.changePage(Page.PLAY);
    }

    public void logout() {
        clientName = "Guest " + clientNum;
        window.navigationBar.setUsername(clientName);

        window.setLoggedIn(false);
        window.changePage(Page.LOGIN);
    }

    public void handleLoginFailed() {
        window.loginPanel.displayError("Invalid credentials");
    }

    public void handleRegisterFailed() {
        window.loginPanel.displayError("Username is taken");
    }

    public void createGame(Message message) {
        String code = message.getParam(0);

        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setClient(clientName);

        window.gamePanel.resetGame();
        window.gamePanel.resetChat();
        window.gamePanel.setGameState(GameState.WAITING);
        window.changePage(Page.GAME);
    }

    public void joinGame(Message message) {
        String code = message.getParam(0);
        String hostName = message.getParam(1);
        String visibility = message.getParam(2);

        window.changePage(Page.GAME);
        window.setInGame(true);
        window.gamePanel.setLobbyCode(code);
        window.gamePanel.setClient(clientName);

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
    }

    public void opponentLeft(Message message) {
        String opponentName = window.gamePanel.getOpponent();
        window.gamePanel.messagePanel.addTextMessage(opponentName + " has left the lobby.");

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

        // If takeback request is active, remove it
        if (window.gamePanel.getActiveProposal() != null && 
            window.gamePanel.getActiveProposal().equals(MessageTypes.TAKEBACK_REQUESTED)) {
            
            window.gamePanel.removeProposal();
        }

        window.gamePanel.boardPanel.makeOpponentMove(t1, t2, p);
    }

    public void processOpponentResignation() {
        if (window.gamePanel.getPlayerColour() == 0) {
            window.gamePanel.setGameState(GameState.WHITE_VICTORY_RESIGN);
            window.gamePanel.boardPanel.gameResultOverlay.setMessage("Black has resigned");
        } else {
            window.gamePanel.setGameState(GameState.BLACK_VICTORY_RESIGN);
            window.gamePanel.boardPanel.gameResultOverlay.setMessage("White has resigned");
        }
    }

    public void processDrawOffer() {
        window.gamePanel.addDrawOffer();
    }

    public void processDraw() {
        window.gamePanel.setGameState(GameState.DRAW);

        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Game Drawn");
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

    public void setPlayersOnline(Message message) {
        int playersOnline = Integer.parseInt(message.getParam(0));
        window.navigationBar.setPlayersOnline(playersOnline);
    }
}