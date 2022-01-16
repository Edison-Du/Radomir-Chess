package views.chess;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.SwingUtilities;

import logicai.*;

import java.awt.image.BufferedImage;

public class MouseEventListenerBot implements MouseListener, MouseMotionListener {

    boolean isSelected = false;

    String t1 = "";
    String t2 = "";

    ChessGame game;

    BufferedImage heldPieceImage = null;

    int mouseX = 0;
    int mouseY = 0;

    int posX = 0;
    int posY = 0;

    Piece selectedPiece = null;

    Bot depthSearchBot = new DepthSearchBotP1(1, 1);

    String botMove;

    public MouseEventListenerBot(ChessGame game) {
        this.game = game;
    }

    public void mousePressed(MouseEvent e) {
        // Initialize mouse coordinates
        mouseX = e.getX();
        mouseY = e.getY();

        // for the tile array
        posX = mouseX / 60;
        posY = 7 - mouseY / 60;

        // Move piece
        if (!isSelected) {
            // Checking for which piece is currently being clicked
            if (mouseX < 480 && mouseY < 480) {
                if (game.getCurrentPos().getTiles()[posX][posY].getPiece() != null) {

                    // check if piece is correct colour
                    if(game.getCurrentPos().getTiles()[posX][posY].getPiece().getColour() == game.getColour("user")) {
                        t1 = String.valueOf((char) (posX + 97)) + "" + (posY + 1);
                        selectedPiece = game.getCurrentPos().getTiles()[posX][posY].getPiece();
                        heldPieceImage = selectedPiece.getImage();
                        isSelected = true;
                        System.out.print(t1);
                    }
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {

        // Initialize mouse coordinates
        int mouseX = e.getX();
        int mouseY = e.getY();

        // for the tile array
        posX = mouseX / 60;
        posY = 7 - mouseY / 60;

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
                }
                else {
                    game.move(t1, t2, null);
                }
                // bot code
                botMove = depthSearchBot.nextMove(game);
                System.out.println("Bot moved " + botMove.substring(0, 2) + ", " + botMove.substring(2, 4));
                game.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4,5));
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