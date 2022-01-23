package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import config.MessageTypes;
import game.Lobby;

/**
 * [ClientHandler.java]
 * An individual thread manager a client socket, sending
 * and receiving messages with the client.
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
     * Creates input and output streams for the client's socket, generates
     * general information about the client (unique number associated with client)
     * @param server The server object containing the server this handler is on 
     * @param socket The client's socket
     */
    public ClientHandler(Server server, Socket socket) {

        clientSocket = socket;
        clientNum = ++ClientHandler.numClients;
        clientsOnline++;
        this.server = server;
        this.clientName = "Guest#" + this.clientNum;
        this.userActive = true;
        
        try {
            // Creating socket I/O streams
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(clientSocket.getOutputStream());

            Message accepted = new Message(MessageTypes.CONNECTION_ACCEPTED);
            accepted.addParam(Integer.toString(clientNum));
            sendMessage(accepted);

            System.out.println("Succesfully connected client #" + clientNum);

        } catch (Exception e) {
            System.out.println("Error opening client I/O streams");
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

    // change to login name after
    public String getClientName() {
        return this.clientName;
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
     * run
     * Continuously receives messages send by the client
     * and performs tasks depending on the message type.
     */
    @Override   
    public void run() {
        try {
            Thread thread = new ClientsDataUpdater(this);
            thread.start();
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
     * evalRequest
     * Performs tasks depending on the type of message sent by the client
     * @param request The message sent by the client
     */
    public void evalRequest(Message request) {
        
        if (request == null) {
            return;
    
        } else if (request.getType().equals(MessageTypes.REGISTER)){
            registerUser(request);
    
        } else if (request.getType().equals(MessageTypes.LOGIN)){
            loginUser(request);

        } else if (request.getType().equals(MessageTypes.LOGOUT)){
            logoutUser(request);

        } else if (request.getType().equals(MessageTypes.JOIN_GAME)) {
            joinGame(request);

        } else if (request.getType().equals(MessageTypes.CREATE_GAME)) {
            createGame(request);

        } else if (request.getType().equals(MessageTypes.LEAVE_GAME)) {
            leaveGame();

        } else if (request.getType().equals(MessageTypes.SENT_TEXT)) {
            sendMessageToOther(request);

        } else if (request.getType().equals(MessageTypes.LOCK_LOBBY)) {
            lockLobby();

        } else if (request.getType().equals(MessageTypes.UNLOCK_LOBBY)) {
            unlockLobby();
        
        } else if (request.getType().equals(MessageTypes.PLAYER_COLOUR)) {
            updatePlayerColour(request);

            // TODO Merge all these if statements that use the same method
        } else if (request.getType().equals(MessageTypes.CHESS_MOVE)) {
            sendMessageToOther(request);

        } else if(request.getType().equals(MessageTypes.WHITE_VICTORY_CHECKMATE) ||
                  request.getType().equals(MessageTypes.BLACK_VICTORY_CHECKMATE) ||
                  request.getType().equals(MessageTypes.STALEMATE)) {
            sendMessageToOther(request);

        } else if (request.getType().equals(MessageTypes.RESIGNATION)) {
            sendMessageToOther(request);

        } else if (request.getType().equals(MessageTypes.PLAY_AGAIN)) {
            sendMessageToOther(request);
            
        } else if (request.getType().equals(MessageTypes.DRAW_OFFERED)) {
            sendMessageToOther(request);

        } else if (request.getType().equals(MessageTypes.DRAW_ACCEPTED)) {
            sendMessageToOther(request);

        } else if (request.getType().equals(MessageTypes.TAKEBACK_REQUESTED)){
            sendTakebackRequest(request);

        } else if (request.getType().equals(MessageTypes.TAKEBACK_ACCEPTED)){
            acceptTakebackRequest(request);

        } else if (request.getType().equals(MessageTypes.BROWSE_GAMES)) {
            browseGames();

        } else if (request.getType().equals(MessageTypes.UPDATE_PREFERENCES)) {
            updatePreferences(request);

        } else if (request.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            disconnectClient(request);

        } else if (request.getType().equals(MessageTypes.GET_PLAYERS_ONLINE)) {
            updatePlayersOnline();

        }

        System.out.println("RECEIVED from #" + clientNum + ": " + request.getText());
    }

    
    /*================================================================================================== */
    /*-----------------The methods below handle each type of message sent by the client------------------*/
    /*================================================================================================== */

    private void registerUser(Message message) {
        User newUser = User.convertMessageToUser(message);
        if (server.getDatabase().addUser(newUser.getUsername(), newUser)){
            setClientName(newUser.getUsername());
            Message returnMessage = new Message(MessageTypes.LOGIN_ACCEPTED);
            Message.addUserAsParams(returnMessage, newUser);
            sendMessage(returnMessage); // Success
        } else{
            sendMessage(new Message(MessageTypes.REGISTER_FAILED)); // Failiure
        }
    }

    private void loginUser(Message message){
        String username = message.getParam(0);
        String password = message.getParam(1);
        if (server.getDatabase().validateUser(username, password)) {
            User existingUser = server.getDatabase().getUser(username);
            setClientName(existingUser.getUsername());        
            Message returnMessage = new Message(MessageTypes.LOGIN_ACCEPTED);
            Message.addUserAsParams(returnMessage, existingUser);
            sendMessage(returnMessage); // Success
        } else {
            sendMessage(new Message(MessageTypes.LOGIN_FAILED)); // Failure
        }
    }

    private void sendTakebackRequest(Message message){
        if (lobby == null) return;
        lobby.sendMessage(this, message);
    }

    private void acceptTakebackRequest(Message message){
        if (lobby == null) return;
        lobby.sendMessage(this, message);
    }

    private void setClientName(String newName){
        this.clientName = newName;
    }

    private void logoutUser(Message message) {
        this.clientName = "Guest #" + clientNum;
        sendMessage(message);
    }

    private void joinGame(Message message) {

        String code = message.getParam(0);
        lobby = server.getLobbyManager().getLobby(code);

        if (lobby == null) {
            // Return error message
            Message errorMessage = new Message(MessageTypes.JOIN_ERROR);
            errorMessage.addParam("Game not found");
            sendMessage(errorMessage);

        } else if (!lobby.setGuest(this)) {
            Message gameFull = new Message(MessageTypes.GAME_FULL);
            sendMessage(gameFull);

        } else {
            Message joinedMessage = new Message(MessageTypes.JOINED_GAME);
            joinedMessage.addParam(lobby.getCode());
            joinedMessage.addParam(lobby.getHost().getClientName());
            joinedMessage.addParam(lobby.getLobbyVisibility());
            this.sendMessage(joinedMessage);

            // Player Colour
            Message colourMessage = new Message(MessageTypes.PLAYER_COLOUR);
            colourMessage.addParam(Integer.toString(lobby.getGuestColour()));
            this.sendMessage(colourMessage);
        }
    }

    private void createGame(Message message) {
        lobby = server.getLobbyManager().createLobby(this);
        if (message.getParam(0).equals("public")) {
            lobby.setPublicStatus("Public");
        } else if (message.getParam(0).equals("private")) {
            lobby.setPublicStatus("Private");
        }


        server.getLobbyManager().addLobby(lobby);
        lobby.setHost(this);

        // Create lobby
        Message createGameMessage = new Message(MessageTypes.GAME_CREATED);
        createGameMessage.addParam(lobby.getCode());
        this.sendMessage(createGameMessage);

        // Player colour
        Message colourMessage = new Message(MessageTypes.PLAYER_COLOUR);
        colourMessage.addParam(Integer.toString(lobby.getHostColour()));
        this.sendMessage(colourMessage);

        Message lobbyVisibilityMessage = new Message(MessageTypes.LOBBY_VISIBILITY);
        lobbyVisibilityMessage.addParam(lobby.getLobbyVisibility());
        this.sendMessage(lobbyVisibilityMessage);

    }

    private void leaveGame() {
        if (lobby == null) {
            return;
        }

        lobby.leaveLobby(this);

        if (lobby.getHost() == null) {
            server.getLobbyManager().removeLobby(lobby.getCode());
        }

        lobby = null;
    }

    private void updatePlayerColour(Message message) {
        int colour = Integer.parseInt(message.getParam(0));
        if (lobby == null) return;
        if (lobby.getHost() == this) {
            lobby.setHostColour(colour);
        }
    }

    // The following 4 methods can be merged into one, maybe
    private void sendMessageToOther(Message message) {
        if(lobby==null) return;
        lobby.sendMessage(this, message);
    }
    // End of similar methods

    private void lockLobby() {
        if (lobby==null) return;
        lobby.setJoinable(false);
    }

    private void unlockLobby() {
        if (lobby==null) return;
        lobby.setJoinable(true);
    }

    private void updatePreferences(Message message) {
        String username = message.getParam(0);
        int[] updatedSettings = User.settingsToArray(message);
        server.getDatabase().updatePreferences(username, updatedSettings);
    }

    private void disconnectClient(Message message) {
        this.userActive = false; // stops this thread
        clientsOnline--;
        if (lobby != null) {
            leaveGame();
        }
        // Echo the message back to let client know we heard the message
        sendMessage(message);
    }

    public void browseGames() {
        Message message = server.getLobbyManager().getPublicLobbyInfo();
        this.sendMessage(message);
    }

    public void updatePlayersOnline() {
        Message message = new Message(MessageTypes.GET_PLAYERS_ONLINE);
        message.addParam(Integer.toString(clientsOnline));
        this.sendMessage(message);
    }
}