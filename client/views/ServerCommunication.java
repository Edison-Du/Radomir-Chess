package views;

import javax.swing.*;
import config.GraphicConsts;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ServerCommunication extends Thread { 
    private String HOST;
    private int port;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;

    ServerCommunication (String host, int port){
        this.HOST = host;
        this.port = port;
        try {
            clientSocket = new Socket(HOST, port);
            InputStreamReader stream = new InputStreamReader(clientSocket.getInputStream());
            input = new BufferedReader(stream);
            output = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }
    }

    public void run() { 
        while(true){
            // Yep
            try{
                Thread.sleep(5);
            } catch (Exception exc){}
        }
    }
}    