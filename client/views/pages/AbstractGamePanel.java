package views.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;

import config.MessageTypes;
import logicai.ChessGame;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener {

    // Chess game
    public ChessGame chessGame;

    // subpanel chess game
    public ChessBoardPanel boardPanel;
    public MovesPanel movesPanel;
    public MessagePanel messagePanel;

    public AbstractGamePanel() {

        // Moves panel
        movesPanel = new MovesPanel();
        this.add(movesPanel, BorderLayout.CENTER);

        // Chess game
        chessGame = new ChessGame();
        boardPanel = new ChessBoardPanel(chessGame, this); // sub-panel 1
        
        // Use constants
        boardPanel.setBounds(120,120,480,480);
        this.add(boardPanel);

        // Message panel
        messagePanel = new MessagePanel();
        // Use constants

        messagePanel.setBounds(660,270,240,330);
        this.add(messagePanel);


        // // Leave lobby
        // leaveLobby = new JButton("Leave");
        // leaveLobby.setBounds(780, 630, 120, 30);
        // leaveLobby.addActionListener(this);
        // this.add(leaveLobby);
    }

    public abstract void processMove(String tile1, String tile2, String promotion);
}