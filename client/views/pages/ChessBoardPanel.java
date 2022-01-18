package views.pages;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
// import game.Pieces;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import chesslogic.ChessGame;
import chesslogic.Tile;
import views.chess.ChessGameMouseListener;
import views.components.ContentPanel;
import config.UserInterface;

public class ChessBoardPanel extends ContentPanel {

    ChessGame game;
    final int tileSize = 60;

    BufferedImage heldPieceImage;

    public int playerColour;

    private ChessGameMouseListener chessGameMouseListener;
    

    public ChessBoardPanel(ChessGame game, AbstractGamePanel gamePanel) {
        this.game = game;

        chessGameMouseListener = new ChessGameMouseListener(game, playerColour, gamePanel);
        addMouseListener(chessGameMouseListener);
        addMouseMotionListener(chessGameMouseListener);
    }

    public void setPlayerColour(int colour) {
        this.playerColour = colour;
        // chessGameMouseListener.setPlayerColour(colour);

        chessGameMouseListener.setPlayerColour(colour);
    }
    
    public void makeOpponentMove(String t1, String t2, String p) {
        this.game.move(t1, t2, p);
        // chessGameMouseListener.setTurn(true);

        chessGameMouseListener.setTurn(true);

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        
        g2d.addRenderingHints(
            new RenderingHints(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON)
        );
        drawBoard(g);
        heldPieceImage = chessGameMouseListener.getHeldPieceImage();
        // System.out.println(heldPieceImage);
        if(heldPieceImage != null) {
            // System.out.println(mouseEventListener.getMouseX() + ", " + mouseEventListener.getMouseY());
            g.drawImage(heldPieceImage, chessGameMouseListener.getMouseX()-tileSize/2, chessGameMouseListener.getMouseY()-tileSize/2, this);
        }

        if(chessGameMouseListener.isPromoting) {
            BufferedImage promotionPlatter;
            try {
                promotionPlatter = ImageIO.read(new File("chesslogic/promotion.png"));
                g.drawImage(promotionPlatter, 110, 200, this);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
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
                if (x % 2 - y % 2 == 0) {
                    g.setColor(UserInterface.darkerTile);
                } else {
                    g.setColor(UserInterface.lighterTile);
                }
                g.fillRect(xPos, yPos, tileSize, tileSize);

                // write tile notation
                if(x == 7) {
                    if((y+playerColour) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Integer.toString(y + 1), 1, yPos + 12);
                }
                if(y == 7) {
                    if((x+playerColour) % 2 == 0) g.setColor(UserInterface.lighterTile);
                    else g.setColor(UserInterface.darkerTile);
                    g.drawString(Character.toString((char)(x + 'a')), xPos + 52, 479);
                }

                // change based on playerColour
                if(checkerBoard[x][y].getPiece() != null && checkerBoard[x][y].getPiece() != chessGameMouseListener.getSelectedPiece()) {
                    g.drawImage(checkerBoard[x][y].getPiece().getImage(), xPos, yPos, this);
                }
            }
        }
	}
}