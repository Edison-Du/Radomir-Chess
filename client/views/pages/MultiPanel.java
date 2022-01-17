package views.pages;

import javax.swing.JLabel;
import javax.swing.JButton;
// import javax.swing.JPanel;
// import config.UserInterface;
// import network.Message;
// import network.ServerConnection;
import views.chess.MouseEventListenerMulti;
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

public class MultiPanel extends ContentPanel {

    ChessGame game;
    final int tileSize = 60;

    private MouseEventListenerMulti mouseEventListenerMulti;
    BufferedImage heldPieceImage;

    public int playerColour;

    public MultiPanel(ChessGame game) {
        this.game = game;
        
        // playerColour = (int)(Math.random()*2);
        // temporary assuming colour
        // playerColour = 0;
        
        mouseEventListenerMulti = new MouseEventListenerMulti(game, playerColour);
        addMouseListener(mouseEventListenerMulti);
        addMouseMotionListener(mouseEventListenerMulti);
        
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

    public void setPlayerColour(int colour) {
        this.playerColour = colour;
        mouseEventListenerMulti.setPlayerColour(colour);
    }
    
    public void makeOpponentMove(String t1, String t2, String p) {
        this.game.move(t1, t2, p);
        mouseEventListenerMulti.setTurn(true);
    }


    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );
        drawBoard(g);
        heldPieceImage = mouseEventListenerMulti.getHeldPieceImage();
        // System.out.println(heldPieceImage);
        if(heldPieceImage != null) {
            // System.out.println(mouseEventListener.getMouseX() + ", " + mouseEventListener.getMouseY());
            g.drawImage(heldPieceImage, mouseEventListenerMulti.getMouseX()-tileSize/2, mouseEventListenerMulti.getMouseY()-tileSize/2, this);
        }
    }

    public void drawBoard(Graphics g) {
		Tile[][] checkerBoard = game.getCurrentPos().getTiles();

        // traverse entire maze and draw coloured square for each symbol in maze
        for (int x = 0; x < checkerBoard.length; x++) {
            for (int y = 0; y < checkerBoard[0].length; y++) {

                // flip board
                int xPos = (7 * playerColour + (1 - 2 * playerColour) * x) * tileSize;
                int yPos = (7 * (1 - playerColour) + (2 * playerColour - 1) * y) * tileSize;
                
                // checkerboard code
                if (x%2-y%2==0) {
                    g.setColor(Color.DARK_GRAY);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                }
                g.fillRect(xPos, yPos, tileSize, tileSize);

                // change based on playerColour
                if(checkerBoard[x][y].getPiece() != null && checkerBoard[x][y].getPiece() != mouseEventListenerMulti.getSelectedPiece()) {
                    g.drawImage(checkerBoard[x][y].getPiece().getImage(), xPos, yPos, this);
                }
            }
        }
	}

    // //@Override
    // public void actionPerformed(ActionEvent e) {
        
    // }
}