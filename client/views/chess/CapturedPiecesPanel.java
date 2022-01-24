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

    private ChessGame game;

    public int playerColour;

    // public GameResultOverlay gameResultOverlay;

    public Stack<Piece> capturedPieces;

    public int offset;
    
    // p B N R Q
    public final Image[] pieceImages = new Image[5];
    public final int[] capturedPieceCount;    

    public CapturedPiecesPanel(ChessGame game, int playerColour) {
        this.game = game;

        capturedPieces = game.getPiecesTaken();

        offset = 0;

        this.playerColour = playerColour;

        capturedPieceCount = new int[5];

        for (int i = 0; i < capturedPieceCount.length; i++) {
            this.pieceImages[i] = UserInterface.PIECES.get(this.playerColour + i*2)[UserInterface.activeSetNum].getScaledInstance(30, 30, java.awt.Image.SCALE_FAST);
        }
    }

    public void setChessGame(ChessGame game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        
        // Reset piece count array
        for (int i = 0; i < capturedPieceCount.length; i++) {
            capturedPieceCount[i] = 0;
        }

        for (Piece piece : game.getPiecesTaken()) {
            if(piece != null && piece.getColour() == playerColour) {
                if (piece instanceof Pawn) capturedPieceCount[0]++;
                if (piece instanceof Bishop) capturedPieceCount[1]++;
                if (piece instanceof Knight) capturedPieceCount[2]++;
                if (piece instanceof Rook) capturedPieceCount[3]++;
                if (piece instanceof Queen) capturedPieceCount[4]++;
            }
        }

        for(int i = 0; i < capturedPieceCount.length; i++) {

            int numPieces = capturedPieceCount[i];
            
            for(int j = 0; j < numPieces; j++) {
                // g.fillRect(offset, 0, 30, 30);
                g.drawImage(pieceImages[i], offset, 0, this);
                offset += 20;
            }
            if(numPieces > 0) offset += 10;
        }
        offset = 0;

        // Check if we need to update the piece set
        if (UserInterface.setChanged) {
            updatePieces();
        }
    }

    /**
     * Updates the piece set theme
     */
    public void updatePieces() {
        for (int i = 0; i < capturedPieceCount.length; i++) {
            this.pieceImages[i] = UserInterface.PIECES.get(this.playerColour + i*2)[UserInterface.activeSetNum].getScaledInstance(30, 30, java.awt.Image.SCALE_FAST);
        }
    }
}