package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.omg.CORBA.DynAnyPackage.Invalid;

import config.MessageTypes;
import game.Lobby;

public class ClientHandler extends Thread{
    
    private static int numClients = 0;
    private int clientNum;
    private boolean userActive;

    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    private Server server;

    private Lobby lobby;

    public ClientHandler(Server server, Socket socket) {
        clientSocket = socket;

        this.server = server;
        this.userActive = true;

        clientNum = ++ClientHandler.numClients;
        
        try {
            InputStreamReader stream = new InputStreamReader(socket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(clientSocket.getOutputStream());

            System.out.println("Succesfully connected client #" + clientNum);

        } catch (Exception e) {
            System.out.println("Error opening client I/O streams");
            e.printStackTrace();
        }
    }

    public int getClientNum() {
        return clientNum;
    }

    public void sendMessage(Message message) {
        output.println(message.getText());
        output.flush();
    }

    @Override   
    public void run() {
        try {
            while (userActive) {
                if (input.ready()) {
                    String msg = input.readLine();
                    Message request = Message.parse(msg);
                    evalRequest(request);
                }
            }
            input.close();
            output.close();

        } catch (Exception e) {
            System.out.println("Failed to receive message from client #" + clientNum);
            e.printStackTrace();
        }
    }

    public void evalRequest(Message request){
        // Register
        if (request == null) {
            return;
    
        } else if (request.getType().equals(MessageTypes.REGISTER)){
            // registerUser(request);
    
        } else if (request.getType().equals(MessageTypes.LOGIN)){
            // do stuff

        } else if (request.getType().equals(MessageTypes.JOIN_GAME)) {
            joinGame(request);

        } else if (request.getType().equals(MessageTypes.CREATE_GAME)) {
            createGame();

        } else if (request.getType().equals(MessageTypes.BROWSE_GAMES)) {
            // try {
            //     Message message = new Message(server.getLobbyManager().getActiveGames().toString());
            //     this.sendMessage(message);
            // } catch (Exception e) {

            // }
        } else if (request.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            disconnectClient(request);
        }

        System.out.println(request.getText());
    }

    private void registerUser(Message message) throws InvalidMessageException{
        try{
            String username = message.getParam(0);
            String password = message.getParam(1);
            if (server.getDatabase().addUser(username, password)){
                sendMessage(new Message(MessageTypes.REGISTER_ACCEPTED)); // Success
            } else{
                sendMessage(new Message(MessageTypes.REGISTER_FAILED)); // Failiure
            }
        } catch (Exception e){
            System.out.println("nice one Eddison Ddu");
            e.printStackTrace();
        }
    }

    private void loginUser(Message message){
        try{
            String username = message.getParam(0);
            String password = message.getParam(1);
            if (server.getDatabase().addUser(username, password)){
                sendMessage(new Message(MessageTypes.LOGIN_ACCEPTED)); // Success
            } else{
                sendMessage(new Message(MessageTypes.LOGIN_FAILED)); // Failiure
            }
        } catch (Exception e){
            System.out.println("nice one Eddison Ddu");
            e.printStackTrace();
        }
    }

    private void joinGame(Message message) {
        // int code = Integer.parseInt(request.getParam(0));
        // Lobby lobby = server.getLobbyManager().getLobby(code);

        // try {

        //     if (lobby == null) {
        //         Message message = new Message("BAD");
        //         this.sendMessage(message);

        //     } else {
        //         lobby.setGuest(this);

        //         Message message = new Message("GOOD");
        //         this.sendMessage(message);
        //     }


        // } catch (Exception e) {

        // }


    }

    private void createGame() {
        lobby = server.getLobbyManager().createLobby();
        lobby.setHost(this);

        try {
            Message message = new Message(MessageTypes.CREATE_GAME);
            message.addParam(lobby.getCode());
            this.sendMessage(message);

            System.out.println(message.getText());

        } catch (Exception e) {

        }
    }

    private void disconnectClient(Message message) {
        this.userActive = false; // stops this thread

        // Echo the message back to let client know we heard the message
        sendMessage(message);
    }
}