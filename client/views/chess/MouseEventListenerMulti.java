package views.chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.SwingUtilities;

import config.MessageTypes;
import logicai.*;
import network.Message;
import network.ServerConnection;

import java.awt.image.BufferedImage;

public class MouseEventListenerMulti implements MouseListener, MouseMotionListener {

    boolean isSelected = false;

    String t1 = "";
    String t2 = "";

    ChessGame game;
    int playerColour;
    
    BufferedImage heldPieceImage = null;

    int mouseX = 0;
    int mouseY = 0;

    int posX = 0;
    int posY = 0;

    boolean isYourTurn;

    Piece selectedPiece = null;

    public MouseEventListenerMulti(ChessGame game, int playerColour) {
        this.game = game;
        this.playerColour = playerColour;
    }

    public void mousePressed(MouseEvent e) {
        // Initialize mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();

        // adjust tile coords for the tile array based on playerColour
        posX = (7 * playerColour) + (1 - 2 * playerColour) * mouseX / 60;
        posY = (7 * (1 - playerColour)) + (2 * playerColour - 1) * mouseY / 60;

        // Move piece
        if (!isSelected) {
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

        // Initialize mouse coordinates
        int mouseX = e.getX();
        int mouseY = e.getY();

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

            if(game.getCurrentPos().legal(t1, t2)) {
                if(game.getCurrentPos().promotingMove(t1, t2)) {
                    game.move(t1, t2, "Q");
                    sendMove(t1, t2, "Q");
                }
                else {
                    game.move(t1, t2, "");
                    sendMove(t1, t2, "");
                }
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

    public void setTurn(boolean isTurn) {
        this.isYourTurn = isTurn;
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


    public void sendMove(String t1, String t2, String p) {
        try {
            Message message = new Message(MessageTypes.CHESS_MOVE);
            message.addParam(t1);
            message.addParam(t2);
            message.addParam(p);
            ServerConnection.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}