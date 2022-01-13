package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    // Later we may want to assign ID to each client
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
            // possible make function later
            output.println("Succesfully connected as client #" + clientNum);
            output.flush();

        } catch (Exception e) {
            System.out.println("Error opening client I/O streams");
            e.printStackTrace();
        }
    }

    public int getClientNum() {
        return clientNum;
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

        } catch (Exception e) {
            System.out.println("Failed to receive message from client #" + clientNum);
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        output.println(message.getText());
        output.flush();
    }

    public String registerRequest(String username, String password){
        return "Hey";
    }

    public void evalRequest(Message request){
        // Register
        if (request.getType().equals(MessageTypes.REGISTER)){
            String username = request.getParam(0);
            String password = request.getParam(1);
            registerRequest(username, password);
        } else if (request.getType().equals(MessageTypes.LOGIN)){
            // do stuff
        } else if (request.getType().equals(MessageTypes.JOIN_GAME)) {
            // This is bad
            int code = Integer.parseInt(request.getParam(0));
            Lobby lobby = server.getLobbyManager().getLobby(code);

            try {

                if (lobby == null) {
                    Message message = new Message("BAD");
                    this.sendMessage(message);

                } else {
                    lobby.setGuest(this);

                    Message message = new Message("GOOD");
                    this.sendMessage(message);
                }

                server.getLobbyManager().print();

            } catch (Exception e) {

            }

        } else if (request.getType().equals(MessageTypes.CREATE_GAME)) {
            Lobby lobby = server.getLobbyManager().createLobby();
            lobby.setHost(this);
            try {
                Message message = new Message("GOOD");
                this.sendMessage(message);
            } catch (Exception e) {

            }

        } else if (request.getType().equals(MessageTypes.BROWSE_GAMES)) {
            try {
                Message message = new Message(server.getLobbyManager().getActiveGames().toString());
                this.sendMessage(message);
            } catch (Exception e) {

            }
        } else if (request.getType().equals(MessageTypes.EXIT_PROGRAM)) {
            this.userActive = false;
        }
    }
}