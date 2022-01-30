package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import config.MessageTypes;
import config.Consts;

/**
 * [ClientHandler.java]
 * An individual thread that manages a client socket, sending
 * and receiving messages with the client.
 * @author Nicholas Chew 
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */

public class ClientHandler extends Thread {
    
    // General information
    private static int numClients = 0;
    public static int clientsOnline = 0;
    private int clientNum;
    private String clientName;
    private boolean userActive;

    // Server and Socket I/O related
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private Server server;

    // Game related
    private Lobby lobby;

    /**
     * ClientHandler
     * Creates input and output streams for the client's socket, stores
     * general information about the client such as their number and name
     * @param server The server object containing the server this handler is on 
     * @param socket The client's socket
     */
    public ClientHandler(Server server, Socket socket) {

        clientSocket = socket;
        clientNum = ++ClientHandler.numClients;
        clientsOnline++;
        this.server = server;
        this.clientName = "Guest " + this.clientNum;
        this.userActive = true;
        
        try {
            // Creating socket I/O streams
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(clientSocket.getOutputStream());

            // Send message to client letting them know they've connected
            Message accepted = new Message(MessageTypes.CONNECTION_ACCEPTED);
            accepted.addParam(Integer.toString(clientNum));
            sendMessage(accepted);

        } catch (Exception e) {
            System.out.println("Error opening client I/O streams");
            e.printStackTrace();
        }
    }

    /**
     * run
     * Continuously receives messages sent by the client
     * and performs tasks depending on the message type.
     */
    @Override   
    public void run() {
        try {
            // Thread that continuously sends certain messages that need to be updated in real time
            Thread thread = new ClientsDataUpdater(this);
            thread.start();

            // Continuously accept messages
            while (userActive) {
                if (input.ready()) {
                    String msg = input.readLine();
                    Message request = Message.parse(msg);
                    evalRequest(request);
                }
            }

            // Close streams once finished
            input.close();
            output.close();

        } catch (Exception e) {
            System.out.println("Failed to receive message from client #" + clientNum);
            e.printStackTrace();
        }
    }

    /**
     * getClientNum
     * Getter for client number
     * @return the client number
     */
    public int getClientNum() {
        return clientNum;
    }

    /**
     * getClientName
     * Getter for the client's name
     * @return the client's name
     */
    public String getClientName() {
        return this.clientName;
    }

    /**
     * setClientName
     * Setter for the client's name
     * @param newName the client's name
     */
    private void setClientName(String newName){
        this.clientName = newName;
    }

    /**
     * sendMessage
     * Sends a message to the client
     * @param message the message to send
     */
    public void sendMessage(Message message) {
        output.println(message.getText());
        output.flush();
    }

    /**
     * createUserMessage
     * Converts a user object to a message object
     * @param messageType the type of message
     * @param user the user object
     * @return message object containing the user
     */
    private Message createUserMessage(String messageType, User user) {
        Message message = new Message(messageType);
        
        message.addParam(user.getUsername());
        message.addParam(user.getBoard());
        message.addParam(user.getChessSet());
        message.addParam(user.getHighlightStatus());
        message.addParam(user.getHighlight());
        message.addParam(user.getSoundStatus());

        return message;
    }

    /**
     * evalRequest
     * Performs tasks depending on the type of message sent by the client
     * @param request The message sent by the client
     */
    public void evalRequest(Message request) {
        
        if (request == null) {
            return;
    
        // Authentication Related Messages -----------------------------------------
        } else if (request.getType().equals(MessageTypes.REGISTER)){
            registerUser(request);
    
        } else if (request.getType().equals(MessageTypes.LOGIN)){
            loginUser(request);

        } else if (request.getType().equals(MessageTypes.LOGOUT)){
            logoutUser(request);

        // Entering Game Related Messages -----------------------------------------
        } else if (request.getType().equals(MessageTypes.JOIN_GAME)) {
            joinGame(request);

        } else if (request.getType().equals(MessageTypes.CREATE_GAME)) {
            createGame(request);

        } else if (request.getType().equals(MessageTypes.LEAVE_GAME)) {
            leaveGame();

        // Lobby Related ---------------------------------------------------------
        } else if (request.getType().equals(MessageTypes.LOCK_LOBBY)) {
            lockLobby();

        } else if (request.getType().equals(MessageTypes.UNLOCK_LOBBY)) {
            unlockLobby();

        } else if (request.getType().equals(MessageTypes.BROWSE_GAMES)) {
            updatePublicLobbies();

        // Player chess game colour ----------------------------------------------
        } else if (request.getType().equals(MessageTypes.PLAYER_COLOUR)) {
            updatePlayerColour(request);

        // User account settings -------------------------------------------------
        } else if (request.getType().equals(MessageTypes.UPDATE_PREFERENCES)) {
            updatePreferences(request);

        // Get number of players online ------------------------------------------
        } else if (request.getType().equals(MessageTypes.GET_PLAYERS_ONLINE)) {
            updatePlayersOnline();

        // Disconnect user -------------------------------------------------------
        } else if (request.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            disconnectClient(request);

        // Messages that are passed between two clients in a lobby
        } else if(request.getType().equals(MessageTypes.SENT_TEXT)               ||
                  request.getType().equals(MessageTypes.CHESS_MOVE)              ||
                  request.getType().equals(MessageTypes.WHITE_VICTORY_CHECKMATE) ||
                  request.getType().equals(MessageTypes.BLACK_VICTORY_CHECKMATE) ||
                  request.getType().equals(MessageTypes.STALEMATE)               ||
                  request.getType().equals(MessageTypes.RESIGNATION)             ||
                  request.getType().equals(MessageTypes.PLAY_AGAIN)              ||
                  request.getType().equals(MessageTypes.DRAW_OFFERED)            ||
                  request.getType().equals(MessageTypes.DRAW_ACCEPTED)           ||
                  request.getType().equals(MessageTypes.TAKEBACK_REQUESTED)      ||
                  request.getType().equals(MessageTypes.TAKEBACK_ACCEPTED)) {
                        
            sendMessageToOther(request);
        }
    }
    
    /*================================================================================================== */
    /*-----------------The methods below handle each type of message sent by the client------------------*/
    /*================================================================================================== */

    /**
     * registerUser
     * Adds a user to the user database with the requested credentials, 
     * or sends an error message if the user could not be added
     * @param message The message containing the user's credentials
     */
    private void registerUser(Message message) {
        User newUser = User.convertMessageToUser(message);

        if (server.getDatabase().addUser(newUser.getUsername(), newUser)){
            Message returnMessage = createUserMessage(MessageTypes.LOGIN_ACCEPTED, newUser);
            sendMessage(returnMessage);
            setClientName(newUser.getUsername());

        } else{
            sendMessage(new Message(MessageTypes.REGISTER_FAILED));
        }
    }

    /**
     * loginUser
     * Checks if a user's credentials are valid, sending an error message otherwise
     * @param message the message containing the user's credentials
     */
    private void loginUser(Message message){
        String username = message.getParam(0);
        String password = message.getParam(1);

        if (server.getDatabase().validateUser(username, password)) {

            User existingUser = server.getDatabase().getUser(username);
            Message returnMessage = createUserMessage(MessageTypes.LOGIN_ACCEPTED, existingUser);

            sendMessage(returnMessage);
            setClientName(existingUser.getUsername());        

        } else {
            sendMessage(new Message(MessageTypes.LOGIN_FAILED)); 
        }
    }

    /**
     * logoutUser
     * Resets the client's name and sends a message to the user to reaffirm them they've been logged out
     * @param message the message to echo back to the user
     */
    private void logoutUser(Message message) {
        this.clientName = "Guest " + clientNum;
        sendMessage(message);
    }

    /**
     * joinGame
     * Adds the user to a lobby with the requested code, sending back the
     * lobby information or an error message if the lobby could not be joined
     * @param message the message containing the lobby code to join
     */
    private void joinGame(Message message) {

        if (lobby != null) {
            leaveGame();
        }

        int code = Integer.parseInt(message.getParam(0));
        lobby = server.getLobbyManager().getLobby(code);

        // Lobby does not exist
        if (lobby == null) {
            Message errorMessage = new Message(MessageTypes.JOIN_ERROR);
            errorMessage.addParam("Game not found");
            sendMessage(errorMessage);

        // Lobby is full
        } else if (!lobby.setGuest(this)) {
            Message errorMessage = new Message(MessageTypes.JOIN_ERROR);
            errorMessage.addParam("Game is full");
            sendMessage(errorMessage);

        /**
         * Otherwise, add the user to the lobby and send the lobby code, host name,
         * lobby visibility and the chess colour they are playing as
         */
        } else {
            Message joinedMessage = new Message(MessageTypes.JOINED_GAME);
            Message colourMessage = new Message(MessageTypes.PLAYER_COLOUR);

            joinedMessage.addParam(Integer.toString(lobby.getCode()));
            joinedMessage.addParam(lobby.getHost().getClientName());
            joinedMessage.addParam(lobby.getLobbyVisibility());
            this.sendMessage(joinedMessage);

            colourMessage.addParam(Integer.toString(lobby.getGuestColour()));
            this.sendMessage(colourMessage);
        }
    }

    /**
     * createGame
     * Creates a lobby for the user, or sending an error message if a lobby 
     * could not be created
     * @param message a message containing the lobby settings (public/private)
     */
    private void createGame(Message message) {

        if (lobby != null) {
            leaveGame();
        }

        Message createGameMessage = new Message(MessageTypes.GAME_CREATED);
        Message colourMessage = new Message(MessageTypes.PLAYER_COLOUR);
        Message lobbyVisibilityMessage = new Message(MessageTypes.LOBBY_VISIBILITY);

        lobby = server.getLobbyManager().createLobby(this);

        // Could not create lobby
        if (lobby == null) {
            Message createError = new Message(MessageTypes.CREATE_ERROR);
            this.sendMessage(createError);
            return;
        }

        // Set lobby visibility
        if (message.getParam(0).equals(Consts.PUBLIC_LOBBY_STATUS)) {
            lobby.setLobbyVisibility(Consts.PUBLIC_LOBBY_STATUS);

        } else if (message.getParam(0).equals(Consts.PRIVATE_LOBBY_STATUS)) {
            lobby.setLobbyVisibility(Consts.PRIVATE_LOBBY_STATUS);
        }

        // Create lobby
        createGameMessage.addParam(Integer.toString(lobby.getCode()));
        this.sendMessage(createGameMessage);

        // Player colour
        colourMessage.addParam(Integer.toString(lobby.getHostColour()));
        this.sendMessage(colourMessage);

        // Lobby visibility
        lobbyVisibilityMessage.addParam(lobby.getLobbyVisibility());
        this.sendMessage(lobbyVisibilityMessage);
    }

    /**
     * leaveGame
     * Removes the user from the lobby they are in
     */
    private void leaveGame() {
        if (lobby == null) {
            return;
        }

        lobby.leaveLobby(this);

        // Remove the lobby if the user was the only person inside
        if (lobby.getHost() == null) {
            server.getLobbyManager().removeLobby(lobby.getCode());
        }

        lobby = null;
    }

    /**
     * lockLobby
     * Locks the user's lobby, not allowing others to join
     */
    private void lockLobby() {
        if (lobby != null) { 
            lobby.setJoinable(false);
        }
    }

    /**
     * unlockLobby
     * Unlocks the user's lobby, allowing others to join
     */
    private void unlockLobby() {
        if (lobby != null) {
            lobby.setJoinable(true);
        }
    }

    /**
     * updatePublicLobbies
     * Sends user information about all the lobbies they could join
     */
    public void updatePublicLobbies() {
        Message message = server.getLobbyManager().getPublicLobbyInfo();
        this.sendMessage(message);
    }

    /**
     * updatePlayerColour
     * Updates the chess colour that the user is playing as
     * @param message message containing the user's colour
     */
    private void updatePlayerColour(Message message) {
        int colour = Integer.parseInt(message.getParam(0));
        if (lobby != null && lobby.getHost() == this) {
            lobby.setHostColour(colour);
        }
    }

    /**
     * updatePreferences
     * Updates the user's setting preferences
     * @param message message containing the user's settings preferences
     */
    private void updatePreferences(Message message) {
        String username = message.getParam(0);
        int[] updatedSettings = User.settingsToArray(message);
        server.getDatabase().updatePreferences(username, updatedSettings);
    }

    /**
     * updatePlayersOnline
     * Sends a message to the server to let them know how many players are online
     */
    public void updatePlayersOnline() {
        Message message = new Message(MessageTypes.GET_PLAYERS_ONLINE);
        message.addParam(Integer.toString(clientsOnline));
        this.sendMessage(message);
    }

    /**
     * disconnectClient
     * 1. Stop this thread to no long accept messages
     * 2. Update players count
     * 3. Remove the user from their lobby
     * 4. Send a message to reaffirm that the client has left
     * @param message the message to echo back to the client
     */
    private void disconnectClient(Message message) {
        this.userActive = false; 
        clientsOnline--;
        if (lobby != null) {
            leaveGame();
        }
        sendMessage(message);
    }

    /**
     * sendMessageToOther
     * Passes a message from this client to the other client in the same lobby
     * @param message the message to pass on
     */
    private void sendMessageToOther(Message message) {
        if(lobby == null) {
            return;
        }
        lobby.sendMessage(this, message);
    }
}