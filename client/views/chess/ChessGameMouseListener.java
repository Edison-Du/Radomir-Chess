package views.chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import chesslogic.*;
import config.GameState;
import views.pages.AbstractGamePanel;

import java.awt.image.BufferedImage;

public class ChessGameMouseListener implements MouseListener, MouseMotionListener {

    private AbstractGamePanel gamePanel;
    ChessGame game;
    int playerColour;

    String t1 = "";
    String t2 = "";

    int mouseX = 0;
    int mouseY = 0;

    int posX = 0;
    int posY = 0;

    BufferedImage heldPieceImage = null;
    Piece selectedPiece = null;
    boolean isSelected = false;

    public boolean isPromoting = false;
    private String promotionChoice;
    private String promotionT1;
    private String promotionT2;

    public ChessGameMouseListener(ChessGame game, int playerColour, AbstractGamePanel gamePanel) {
        this.game = game;
        this.playerColour = playerColour;
        this.gamePanel = gamePanel;
    }

    public void mousePressed(MouseEvent e) {

        // if (gamePanel.getGameState() != GameState.ONGOING) {
        //     return;
        // }

        // Initialize mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();

        if(isPromoting) {
            if (mouseX > 110 && mouseX < 370 && mouseY > 200 && mouseY < 280) {
                if(mouseX > 110 && mouseX < 180) {
                    promotionChoice = "Q";
                } else if (mouseX > 180 && mouseX < 240) {
                    promotionChoice = "B";
                } else if (mouseX > 240 && mouseX < 300) {
                    promotionChoice = "N";
                } else if (mouseX > 300 && mouseX < 370) {
                    promotionChoice = "R";
                }
                System.out.println("checked for mouse");
                System.out.println(promotionChoice);

                gamePanel.movesPanel.addMove(game.getCurrentPos().toAlgebraic(promotionT1, promotionT2, promotionChoice));
                game.move(promotionT1, promotionT2, promotionChoice);
                gamePanel.processMove(promotionT1, promotionT2, promotionChoice);

                promotionT1 = null;
                promotionT2 = null;

                isPromoting = false;
            }
        }

        // adjust tile coords for the tile array based on playerColour
        posX = (7 * playerColour) + (1 - 2 * playerColour) * mouseX / 60;
        posY = (7 * (1 - playerColour)) + (2 * playerColour - 1) * mouseY / 60;

        // Move piece
        if (!isSelected && !isPromoting) {
            // Checking for which piece is currently being clicked
            if (mouseX < 480 && mouseY < 480) {
                if (game.getCurrentPos().getTiles()[posX][posY].getPiece() != null) {

                    // check if piece is correct colour
                    if(game.getCurrentPos().getTiles()[posX][posY].getPiece().getColour() == playerColour) {
                        t1 = String.valueOf((char) (posX + 97)) + "" + (posY + 1);
                        selectedPiece = game.getCurrentPos().getTiles()[posX][posY].getPiece();
                        heldPieceImage = selectedPiece.getImage();
                        isSelected = true;
                        System.out.print(t1);
                    } else {
                        System.out.println("NOT YOUR PIECE!");
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        
        // if (gamePanel.getGameState() != GameState.ONGOING) {
        //     return;
        // }

        // Initialize mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();

        // adjust tile coords for the tile array based on playerColour
        posX = (7 * playerColour) + (1 - 2 * playerColour) * mouseX / 60;
        posY = (7 * (1 - playerColour)) + (2 * playerColour - 1) * mouseY / 60;

        // solve if piece dragged
        t2 = String.valueOf((char) (posX + 97)) + "" + (posY + 1);

        if (isSelected) {
            isSelected = false;
            selectedPiece = null;
            heldPieceImage = null;
            System.out.println(", " + t2);

            if(gamePanel.getGameState() == GameState.ONGOING && game.getCurrentPos().legal(t1, t2)) {
                
                if(game.getCurrentPos().promotingMove(t1, t2)) {
                    isPromoting = true;
                    promotionT1 = t1;
                    promotionT2 = t2;
                }
                else {
                    gamePanel.movesPanel.addMove(game.getCurrentPos().toAlgebraic(t1, t2, ""));
                    game.move(t1, t2, "");
                    gamePanel.processMove(t1, t2, "");
                    // sendMove(t1, t2, "");
                }

                // add move to move list
            }
        }
        // reset t1 and t2
        t1 = "";
        t2 = "";
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public BufferedImage getHeldPieceImage() {
        return heldPieceImage;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setPlayerColour(int colour) {
        this.playerColour = colour;
    }

    /**
     * MouseMotionEventListener
     * A mouse motion listener that receives mouse motion inputs to
     * have a table follow the user's cursor when dragged.
     */
    @Override
    public void mouseDragged(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e) && isSelected) {
            // System.out.println("DRAG");
            // Initialize mouse coordinates
            mouseX = e.getX();
            mouseY = e.getY();



        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

}