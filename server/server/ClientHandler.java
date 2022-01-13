package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    
    private static int numClients = 0;
    private int clientNum;

    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    // Later we may want to assign ID to each client
    public ClientHandler(Socket socket) {
        clientSocket = socket;
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

    @Override   
    public void run() {
        try {
            while (true) {
                if (input.ready()) {
                    String msg = input.readLine();

                    Message request = Message.parse(msg);
                    
                    System.out.println("Message received from client #" + clientNum + ":");
                    System.out.println("TYPE:  " + request.getType());
                    for (int i = 0; i < request.getNumParams(); i++) {
                        System.out.println("PARAM: " + request.getParam(i));
                    }

                    Message response = new Message("SUCCESS");
                    response.addParam("I got your msg.");

                    // Make separate method
                    output.println(response.getText());
                    output.flush();
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to receive message from client #" + clientNum);
            e.printStackTrace();
        }
    }

    public String evalRequest(Message request){
        if (request.getType() == "LOGIN"){
            String username = request.getParam(0);
            String password = request.getParam(1);
            
        }
    }

    public int getClientNum() {
        return clientNum;
    }
}