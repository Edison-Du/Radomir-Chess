package views.chess;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;

import chesslogic.ChessGame;
import chesslogic.Piece;
import javafx.scene.image.Image;
import views.components.ContentPanel;

public class CapturedPiecesPanel extends ContentPanel {

    ChessGame game;

    public int playerColour;

    public GameResultOverlay gameResultOverlay;

    public Stack<Piece> capturedPieces;

    public int pieceOffset;

    public CapturedPiecesPanel(ChessGame game) {
        this.game = game;

        capturedPieces = game.getPiecesTaken();

        pieceOffset = 0;
    }
    
    @Override
    public void paintComponent(Graphics g) {

        if(!capturedPieces.isEmpty()) {
            Graphics2D g2d = (Graphics2D)g;
            g2d.addRenderingHints(
                new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON)
            );

            // System.out.println(capturedPieces);

            for(Piece capturedPiece : capturedPieces) {
                if(capturedPiece != null) {
                    g2d.drawImage(capturedPiece.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_FAST), pieceOffset, 0, this);
                    pieceOffset = pieceOffset + 30;
                }

                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     // TODO Auto-generated catch block
                //     e.printStackTrace();
                // }

            }
            pieceOffset = 0;

        }
        
    }

}