package network;

import config.GameState;
import config.MessageTypes;
import config.Page;
import config.UserInterface;
import views.Window;

import java.util.ArrayList;

import chesslogic.ChessConsts;

/**
 * [ConnectionHandler.java]
 * An individual thread that manages messages received by the server
 * and performs tasks depending on the message type
 * 
 * @author Edison Du
 * @author Nicholas Chew
 * @author Peter Gu
 * @version 1.0 Jan 24, 2022
 */
public class ConnectionHandler extends Thread {

    private Window window;
    private boolean isActive;

    private int clientNum;
    private String clientName;

    /**
     * ConnectionHandler
     * Initialize a connection handler between the server and client
     * @param window the window associated with the connection handler
     */
    public ConnectionHandler(Window window) {
        isActive = true;
        this.window = window;
    }

    /**
     * run
     * Starts continuously reading and evaluating messages from the server
     */
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
        }
    }

    /**
     * evalMessage
     * Performs tasks depending on the type of message sent by the server
     * @param message the message sent by the server
     */
    public void evalMessage(Message message) {

        if (message == null) {
            return;

        // Starting and closing the program ----------------------------------------------
        } else if (message.getType().equals(MessageTypes.CONNECTION_ACCEPTED)) {
            setClientInfo(message);

        } else if (message.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            isActive = false;
            ServerConnection.close();

        // Authentication related message ------------------------------------------------
        } else if (message.getType().equals(MessageTypes.LOGIN_ACCEPTED)) {
            login(message);

        } else if (message.getType().equals(MessageTypes.LOGIN_FAILED)) {
            handleLoginFailed();
        
        } else if (message.getType().equals(MessageTypes.REGISTER_FAILED)){
            handleRegisterFailed();

        } else if (message.getType().equals(MessageTypes.LOGOUT)){
            logout();

        // Joining and creating games ----------------------------------------------
        } else if (message.getType().equals(MessageTypes.GAME_CREATED)) { 
            createGame(message);

        } else if (message.getType().equals(MessageTypes.CREATE_ERROR)) { 
            processGameCreationError();

        } else if (message.getType().equals(MessageTypes.JOINED_GAME)) {
            joinGame(message);

        } else if (message.getType().equals(MessageTypes.JOIN_ERROR)) {
            processJoinError(message);

        // Leaving a game
        } else if (message.getType().equals(MessageTypes.LEFT_SUCCESFULLY)) {
            leaveGame();

        // The opponent player leaves/joins the game ------------------------------
        } else if (message.getType().equals(MessageTypes.GUEST_JOINED)) {
            guestJoined(message);

        } else if (message.getType().equals(MessageTypes.OPPONENT_LEFT)) {
            opponentLeft();

        // Player colour in chess game ---------------------------------------------------
        } else if (message.getType().equals(MessageTypes.PLAYER_COLOUR)) {
            setPlayerColour(message);

        // Communication between two players in a game -------------------------------------
        } else if (message.getType().equals(MessageTypes.SENT_TEXT)) {
            addTextMessage(message);

        } else if (message.getType().equals(MessageTypes.CHESS_MOVE)) {
            processOpponentChessMove(message);

        // Game over states ----------------------------------------------------------------
        } else if(message.getType().equals(MessageTypes.WHITE_VICTORY_CHECKMATE)) {
            processWhiteCheckmate();

        } else if(message.getType().equals(MessageTypes.BLACK_VICTORY_CHECKMATE)) {
            processBlackCheckmate();

        } else if(message.getType().equals(MessageTypes.STALEMATE)) {
            processStalemate();

        } else if (message.getType().equals(MessageTypes.RESIGNATION)) {
            processOpponentResignation();

        // Take backs in chess game ---------------------------------------------------------
        } else if (message.getType().equals(MessageTypes.TAKEBACK_REQUESTED)){
            processTakebackRequest();

        } else if (message.getType().equals(MessageTypes.TAKEBACK_ACCEPTED)){
            processTakebackAcceptance();

        // Draws in chess game --------------------------------------------------------------
        } else if (message.getType().equals(MessageTypes.DRAW_OFFERED)){
            processDrawOffer();

        } else if (message.getType().equals(MessageTypes.DRAW_ACCEPTED)){
            processDraw();

        // Chess game rematch --------------------------------------------------------------
        } else if (message.getType().equals(MessageTypes.PLAY_AGAIN)) {
            processPlayAgain();

        // Lobby related ------------------------------------------------------------------
        } else if (message.getType().equals(MessageTypes.DISPLAY_GAMES)) {
            displayLobbies(message);

        } else if (message.getType().equals(MessageTypes.LOBBY_VISIBILITY)) {
            setLobbyVisibility(message);

        // Number of players online --------------------------------------------------------
        } else if (message.getType().equals(MessageTypes.GET_PLAYERS_ONLINE)) {
            setPlayersOnline(message);
        }
    }

    /**
     * setClientInfo
     * Sets the client's name and number
     * @param message the message containing the client's name and number
     */
    public void setClientInfo(Message message) {
        clientNum = Integer.parseInt(message.getParam(0)); 
        clientName = "Guest " + clientNum;
        window.navigationBar.setUsername(clientName);
    }

    /**
     * login
     * Logs the user in, keeping track of their username and setting preferences
     * @param message message containing the user's data
     */
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

    /**
     * handleLoginFailed
     * Displays an error on the login page for failed login
     */
    public void handleLoginFailed() {
        window.loginPanel.displayError("Invalid credentials");
    }

    /**
     * handleRegisterFailed
     * Displays an error on the register page for failed register
     */
    public void handleRegisterFailed() {
        window.loginPanel.displayError("Username is taken");
    }

    /**
     * logout
     * Logs out the user, remove their username
     */
    public void logout() {
        clientName = "Guest " + clientNum;
        window.navigationBar.setUsername(clientName);

        window.setLoggedIn(false);
        window.changePage(Page.LOGIN);
    }

    /**
     * createGame
     * Creates a new multiplayer game for the user, resetting the game panel
     * @param message the message containing the lobby code
     */
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

    /**
     * processGameCreationError
     * Displays an error on the game setup page for failed game creation
     */
    public void processGameCreationError() {
        window.gameSetupPanel.displayError();
    }

    /**
     * joinGame
     * Updates the multiplayer game panel to display information about the
     * opponent player
     * @param message the message containing information about the opposing player
     */
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

    /**
     * processJoinError
     * Displays an error on the join game page for failing to join a game
     * @param message the message containing error details
     */
    public void processJoinError(Message message) {
        window.joinGamePanel.displayError(message.getParam(0));
    }
    
    /**
     * leaveGame
     * Removes the user from their current game
     */
    public void leaveGame() {
        window.setInGame(false);
        window.changePage(Page.PLAY);
    }

    /**
     * guestJoined
     * Adds opposing player to the user's lobby
     * @param message message containing information about the opposing player
     */
    public void guestJoined(Message message) {
        String guestName = message.getParam(0);

        window.gamePanel.resetGame();
        window.gamePanel.setGameState(GameState.ONGOING);
        window.gamePanel.setAlone(false);

        window.gamePanel.addOther(guestName);
    }
    
    /**
     * opponentLeft
     * Removes opposing player from the user's lobby, which
     * counts as a resignation in the chess game
     */
    public void opponentLeft() {
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

    /**
     * setPlayerColour
     * Sets the colour the user is play as in the chess game
     * @param message message containing player colour
     */
    public void setPlayerColour(Message message) {
        int colour = Integer.parseInt(message.getParam(0));
        window.gamePanel.setPlayerColour(colour);
    }

    /**
     * addTextMessage
     * Adds a text message from the opponent to the chat
     * @param message message containing text message from opponent
     */
    public void addTextMessage(Message message) {
        String text = message.getParam(0);
        window.gamePanel.addMessageFromOther(text);
    }

    /**
     * processOpponentChessMove
     * Makes the opponent's chess move, removing takeback proposals
     * if there are any
     * @param message message containing details of the opponents moves
     */
    public void processOpponentChessMove(Message message) {
        String tile1 = message.getParam(0);
        String tile2 = message.getParam(1);
        String promotion = message.getParam(2);

        if (window.gamePanel.getActiveProposal() != null && 
            window.gamePanel.getActiveProposal().equals(MessageTypes.TAKEBACK_REQUESTED)) {
            window.gamePanel.removeProposal();
        }

        window.gamePanel.boardPanel.makeOpponentMove(tile1, tile2, promotion);
    }

    /**
     * processWhiteCheckmate
     * Displays a game-over overlay in the game panel for white checkmating black
     */
    public void processWhiteCheckmate() {
        window.gamePanel.setGameState(GameState.WHITE_VICTORY_CHECKMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("White wins by checkmate");
    }

    /**
     * processBlackCheckmate
     * Displays a game-over overlay in the game panel for black checkmating white
     */
    public void processBlackCheckmate() {
        window.gamePanel.setGameState(GameState.BLACK_VICTORY_CHECKMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Black wins by checkmate");
    }

    /**
     * processStalemate
     * Displays a game-over overlay in the game panel for stalemate
     */
    public void processStalemate() {
        window.gamePanel.setGameState(GameState.STALEMATE);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Stalemate");
    }

    /**
     * processOpponentResignation
     * Displays a game-over overlay in the game panel for opponent resignation
     */
    public void processOpponentResignation() {
        if (window.gamePanel.getPlayerColour() == ChessConsts.WHITE) {
            window.gamePanel.setGameState(GameState.WHITE_VICTORY_RESIGN);
            window.gamePanel.boardPanel.gameResultOverlay.setMessage("Black has resigned");
        } else {
            window.gamePanel.setGameState(GameState.BLACK_VICTORY_RESIGN);
            window.gamePanel.boardPanel.gameResultOverlay.setMessage("White has resigned");
        }
    }

    /**
     * processTakebackRequest
     * Adds a takeback request from the opponent to the user's game panel
     */
    public void processTakebackRequest(){
        window.gamePanel.addTakebackRequest();
    }

    /**
     * processTakebackAcceptance
     * Undo moves on the user's chess game due to takebacks
     */
    public void processTakebackAcceptance(){
        window.gamePanel.performTakeback();
    }

    /**
     * processDrawOffer
     * Adds a draw offer from the opponent to the user's game panel
     */
    public void processDrawOffer() {
        window.gamePanel.addDrawOffer();
    }

    /**
     * processDraw
     * Displays a game-over overlay in the game panel for game draw
     */
    public void processDraw() {
        window.gamePanel.setGameState(GameState.DRAW);
        window.gamePanel.boardPanel.gameResultOverlay.setMessage("Game Drawn");
    }

    /**
     * processPlayAgain
     * Set a value indicating that the opponent would like to play again
     */
    public void processPlayAgain() {
        window.gamePanel.setOpponentPlayAgain(true);
    }

    /**
     * displayLobbies
     * Adds list of joinable lobbies for the user to see in the browse games panel
     * @param message message containing list of lobbies
     */
    public void displayLobbies(Message message) {
        ArrayList<Lobby> lobbies = new ArrayList<>();
        
        for (int i = 0; i < message.getNumParams(); i++) {
            lobbies.add(Lobby.parseLobbyFromString(message.getParam(i)));
        }
        window.browseGamesPanel.setLobbyList(lobbies);
    }

    /**
     * setLobbyVisibility
     * Sets whether or not the player's lobby is public/private
     * @param message message containing the lobby visibility
     */
    public void setLobbyVisibility(Message message) {
        String visibility = message.getParam(0);
        window.gamePanel.setLobbyVisibility(visibility);
    }

    /**
     * setPlayersOnline
     * Update the navigation bar display for how many players are online
     * @param message message containing how many players are online
     */
    public void setPlayersOnline(Message message) {
        int playersOnline = Integer.parseInt(message.getParam(0));
        window.navigationBar.setPlayersOnline(playersOnline);
    }
}