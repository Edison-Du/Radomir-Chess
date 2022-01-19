package views.pages;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;

import chesslogic.ChessGame;
import config.MessageTypes;
import network.Message;
import network.ServerConnection;
import views.components.ContentPanel;

abstract public class AbstractGamePanel extends ContentPanel implements ActionListener{

    // Chess game
    public ChessGame chessGame;

    // subpanel chess game
    public ChessBoardPanel boardPanel;
    public MovesPanel movesPanel;
    public MessagePanel messagePanel;

    BufferedImage background;

    public AbstractGamePanel() {

        initialize();

        // // Leave lobby
        // leaveLobby = new JButton("Leave");
        // leaveLobby.setBounds(780, 630, 120, 30);
        // leaveLobby.addActionListener(this);
        // this.add(leaveLobby);
    }

    public abstract void processMove(String tile1, String tile2, String promotion);

    private void initialize() {
        chessGame = new ChessGame();

        // Chess game
        boardPanel = new ChessBoardPanel(chessGame, this); // sub-panel 1
        // Use constants
        boardPanel.setBounds(120, 120, 480, 480);
        this.add(boardPanel);

        // Moves panel
        movesPanel = new MovesPanel();
        this.add(movesPanel, BorderLayout.CENTER);

        // Message panel
        messagePanel = new MessagePanel();
        // Use constants
        messagePanel.setBounds(660, 270, 240, 330);
        this.add(messagePanel);
    }

   public void undoMove() {
        this.boardPanel.undoMove();
    }
    
    public void resetPanel() {
        this.remove(boardPanel);
        this.remove(movesPanel);
        this.remove(messagePanel);

        initialize();
        this.revalidate();
    }

    public void updateBackground(String image) {
        try {
            background = ImageIO.read(new File(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}