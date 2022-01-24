package chesslogic;

import java.util.HashSet;

import config.UserInterface;

/**
 * class for a Pawn piece
 * @author JPANEL
 * @version 1.0
 */

public class Pawn extends Piece {
    
    /**
     * Create a pawn
     * @param col colour of the pawn
     * @
     */
    public Pawn(int col) {
        super(col, "p", 15, UserInterface.PIECES.get(col));
    }
    
    @Override
    /**
     * Given a board and the position of this piece on that board, compute the set of tiles that this piece is 'attacking'
     * @param b board to use
     * @param pos position of this piece on that board
     * @return a HashSet containing all tiles on the board that this piece is attacking, including the tile that this piece is standing on
     */
    public HashSet<Tile> range(Board b, Tile pos) {
        HashSet<Tile> output = new HashSet<Tile>();
        output.add(pos);
        if(pos.getY() + 1 - 2*this.getColour() > 7 || pos.getY() + 1 - 2*this.getColour() < 0) {
            return output;
        }
        if(b.getTiles()[pos.getX()][pos.getY() + 1 - 2*this.getColour()].getPiece() == null) {
            output.add(b.getTiles()[pos.getX()][pos.getY() + 1 - 2*this.getColour()]);
            if(!this.hasMoved() && b.getTiles()[pos.getX()][pos.getY() + 2 - 4*this.getColour()].getPiece() == null) {
                output.add(b.getTiles()[pos.getX()][pos.getY() + 2 - 4*this.getColour()]);
            }
        }
        if(pos.getX() + 1 < 8 && b.getTiles()[pos.getX() + 1][pos.getY() + 1 - 2*this.getColour()].getPiece() != null 
               && b.getTiles()[pos.getX() + 1][pos.getY() + 1 - 2*this.getColour()].getPiece().getColour() == 1 - this.getColour()) {
            output.add(b.getTiles()[pos.getX() + 1][pos.getY() + 1 - 2*this.getColour()]);
        }
        if(pos.getX() - 1 >= 0 && b.getTiles()[pos.getX() - 1][pos.getY() + 1 - 2*this.getColour()].getPiece() != null 
               && b.getTiles()[pos.getX() - 1][pos.getY() + 1 - 2*this.getColour()].getPiece().getColour() == 1 - this.getColour()) {
            output.add(b.getTiles()[pos.getX() - 1][pos.getY() + 1 - 2*this.getColour()]);
        }
        //en passant
        if(pos.getY() == 4 - this.getColour()) {
            if(pos.getX() - 1 >= 0 && b.getTiles()[pos.getX() - 1][4 - this.getColour()].getPiece() != null 
                   && b.getTiles()[pos.getX() - 1][4 - this.getColour()].getPiece().getName().equals("p") 
                   && b.getTiles()[pos.getX() - 1][4 - this.getColour()].getPiece().getColour() == 1 - this.getColour() 
                   && b.getTiles()[pos.getX() - 1][4 - this.getColour()].getPiece().getTurnMoved() == b.getTurn() - 1) {
                output.add(b.getTiles()[pos.getX() - 1][5 - 3*this.getColour()]);
            }
            else if(pos.getX() + 1 < 8 && b.getTiles()[pos.getX() + 1][4 - this.getColour()].getPiece() != null 
                        && b.getTiles()[pos.getX() + 1][4 - this.getColour()].getPiece().getName().equals("p") 
                        && b.getTiles()[pos.getX() + 1][4 - this.getColour()].getPiece().getColour() == 1 - this.getColour() 
                        && b.getTiles()[pos.getX() + 1][4 - this.getColour()].getPiece().getTurnMoved() == b.getTurn() - 1) {
                output.add(b.getTiles()[pos.getX() + 1][5 - 3*this.getColour()]);
            }
        }
        return output;
    }
    
    @Override
    public Piece copy() {
        Pawn out = new Pawn(this.getColour());
        out.setMoved(this.getTurnMoved());
        return out;
    }
}