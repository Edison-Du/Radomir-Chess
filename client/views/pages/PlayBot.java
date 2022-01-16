package views.pages;

import javax.swing.JLabel;
import javax.swing.JButton;
// import javax.swing.JPanel;
// import config.UserInterface;
// import network.Message;
// import network.ServerConnection;
import views.chess.MouseEventListenerBot;
import views.components.ContentPanel;

import java.awt.Color;
// import java.awt.Dimension;
// import java.awt.Font;

// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseListener;
// import java.awt.event.KeyAdapter;
import java.awt.Graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
// import game.Pieces;

import logicai.ChessGame;
import logicai.Tile;

import java.awt.image.BufferedImage;

public class PlayBot extends ContentPanel {

    ChessGame game;
    final int tileSize = 60;

    private MouseEventListenerBot mouseEventListenerBot;
    BufferedImage heldPieceImage;

    int colour;

    public PlayBot(ChessGame game) {
        this.game = game;
        // temporary assuming colour
        colour = 0;

        mouseEventListenerBot = new MouseEventListenerBot(game);
        addMouseListener(mouseEventListenerBot);
        addMouseMotionListener(mouseEventListenerBot);
        
        /*
        title.setFont(new Font("Serif", Font.ITALIC, 36));
        title.setText(UserInterface.WINDOW_TITLE);
        title.setSize(new Dimension(280, 80));
        title.setBounds(UserInterface.CONTENT_WIDTH / 2 - title.getWidth() / 2, 0, title.getWidth(), title.getHeight());
        // this.add(title);

        profile.setFont(new Font("Serif", Font.ITALIC, 36));
        profile.setText("JGepheri Soo");
        this.add(profile);

        //Play Buttons
        againstAI.setBounds(buttonBounds);

        // Pieces is obsolete at this point, delete it later uwu
        // new Pieces();
        */
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );
        drawBoard(g);
        heldPieceImage = mouseEventListenerBot.getHeldPieceImage();
        // System.out.println(heldPieceImage);
        if(heldPieceImage != null) {
            // System.out.println(mouseEventListener.getMouseX() + ", " + mouseEventListener.getMouseY());
            g.drawImage(heldPieceImage, mouseEventListenerBot.getMouseX()-tileSize/2, mouseEventListenerBot.getMouseY()-tileSize/2, this);
        }
    }

    public void drawBoard(Graphics g) {
		Tile[][] checkerBoard = game.getCurrentPos().getTiles();

        // traverse entire maze and draw coloured square for each symbol in maze
        for (int x = 0; x < checkerBoard.length; x++) {
            for (int y = 0; y < checkerBoard[0].length; y++) {
                int xPos = x * tileSize;
                int yPos = (checkerBoard[0].length - y)*tileSize - 60;
                if (x%2-y%2==0) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(xPos, yPos, tileSize, tileSize);

                if(checkerBoard[x][y].getPiece() != null && checkerBoard[x][y].getPiece() != mouseEventListenerBot.getSelectedPiece()) {
                    g.drawImage(checkerBoard[x][y].getPiece().getImage(), xPos, yPos, this);
                }
            }
        }
	}

    // //@Override
    // public void actionPerformed(ActionEvent e) {
        
    // }
}