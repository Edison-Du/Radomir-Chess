package views.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;
import java.awt.Image;

import chesslogic.*;
import config.UserInterface;
import views.components.ContentPanel;

public class CapturedPiecesPanel extends ContentPanel {

    ChessGame game;

    public int playerColour;

    public GameResultOverlay gameResultOverlay;

    // public Stack<Piece> capturedPieces;

    public int offset;
    
    // p B N R Q
    public final Image[] pieceImages;
    public final int[] capturedPieceCount;    

    public CapturedPiecesPanel(ChessGame game, int playerColour) {
        this.game = game;

        // capturedPieces = game.getPiecesTaken();

        offset = 0;

        this.playerColour = playerColour;

        capturedPieceCount = new int[5];

        Image[] pieceImagesTemp = {Piece.getImage("p", playerColour).getScaledInstance(30, 30, java.awt.Image.SCALE_FAST),
            Piece.getImage("B", playerColour).getScaledInstance(30, 30, java.awt.Image.SCALE_FAST),
            Piece.getImage("N", playerColour).getScaledInstance(30, 30, java.awt.Image.SCALE_FAST),
            Piece.getImage("R", playerColour).getScaledInstance(30, 30, java.awt.Image.SCALE_FAST),
            Piece.getImage("Q", playerColour).getScaledInstance(30, 30, java.awt.Image.SCALE_FAST)
        };
        this.pieceImages = pieceImagesTemp;

    }

    public void addCapturedPiece(Piece piece) {

        if(piece != null) {
            if (piece instanceof Pawn) capturedPieceCount[0]++;
            if (piece instanceof Bishop) capturedPieceCount[1]++;
            if (piece instanceof Knight) capturedPieceCount[2]++;
            if (piece instanceof Rook) capturedPieceCount[3]++;
            if (piece instanceof Queen) capturedPieceCount[4]++;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(UserInterface.BACKGROUNDS[UserInterface.activeBackground]);
        g.fillRect(-11, -11, UserInterface.CONTENT_WIDTH, UserInterface.WINDOW_HEIGHT);  // What are even the dimensions of this panel and how do I fill it
        
        for(int i = 0; i < capturedPieceCount.length; i++) {

            int numPieces = capturedPieceCount[i];
            
            for(int j = 0; j < numPieces; j++) {
                g.fillRect(offset, 0, 30, 30);
                g.drawImage(pieceImages[i], offset, 0, this);
                offset += 20;
            }
            if(numPieces > 0) offset += 10;
        }
        offset = 0;
    }
}