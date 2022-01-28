package chesslogic;

/**
 * Class for a King
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import java.util.Iterator;

import config.UserInterface;

public class King extends Piece {
    
    private boolean hasCastled;
    
    /**
     * Create a king
     * @param col colour of king
     * @
     */
    public King(int col) {
        super(col, "K", 0, UserInterface.PIECES.get(col + 10));
        this.hasCastled = false;
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
        int vecX = 0;
        int vecY = 1;
        output.add(pos);
        for(int j = 0; j < 2; j++) {
            for(int i = 0; i < 4; i++) {
                if(pos.getX() + vecX < 8 && pos.getX() + vecX >= 0 && pos.getY() + vecY < 8 && pos.getY() + vecY >= 0 
                       && b.getTiles()[pos.getX() + vecX][pos.getY() + vecY].getPiece() == null) {
                    output.add(b.getTiles()[pos.getX() + vecX][pos.getY() + vecY]);
                }
                else if(pos.getX() + vecX < 8 && pos.getX() + vecX >= 0 && pos.getY() + vecY < 8 && pos.getY() + vecY >= 0 
                            && b.getTiles()[pos.getX() + vecX][pos.getY() + vecY].getPiece().getColour() != this.getColour()) {
                    output.add(b.getTiles()[pos.getX() + vecX][pos.getY() + vecY]);
                }
                vecX = vecX + vecY;
                vecY = vecX - vecY;
                vecX = vecX - vecY;
                vecX = -vecX;
            }
            vecX = 1;
            vecY = 1;
        }
        //castling - if the king has not moved and two/three squares right or left of it are empty and the rook of that 
        //the order in which the conditions are listed are from least computationally demanding to most computationally demanding
        if( (!this.hasMoved()) 
               && (b.getTiles()[pos.getX() + 1][pos.getY()].getPiece() == null) 
               && (b.getTiles()[pos.getX() + 2][pos.getY()].getPiece() == null) 
               && (b.getTiles()[pos.getX() + 3][pos.getY()].getPiece() != null) 
               && (!b.getTiles()[pos.getX() + 3][pos.getY()].getPiece().hasMoved())
               && (!isChecked(b, b.getTiles()[pos.getX() + 1][pos.getY()])) 
               && (!isChecked(b, b.getTiles()[pos.getX() + 2][pos.getY()])) 
               && (!isChecked(b, pos))) {
            output.add(b.getTiles()[pos.getX() + 2][pos.getY()]);
        }
        if( (!this.hasMoved()) 
               && (b.getTiles()[pos.getX() - 1][pos.getY()].getPiece() == null) 
               && (b.getTiles()[pos.getX() - 2][pos.getY()].getPiece() == null) 
               && (b.getTiles()[pos.getX() - 3][pos.getY()].getPiece() == null) 
               && (b.getTiles()[pos.getX() - 4][pos.getY()].getPiece() != null) 
               && (!b.getTiles()[pos.getX() - 4][pos.getY()].getPiece().hasMoved()) 
               && (!isChecked(b, b.getTiles()[pos.getX() - 1][pos.getY()]))
               && (!isChecked(b, b.getTiles()[pos.getX() - 2][pos.getY()])) 
               && (!isChecked(b, b.getTiles()[pos.getX() -3][pos.getY()])) 
               && (!isChecked(b, pos))) {
            output.add(b.getTiles()[pos.getX() - 2][pos.getY()]);
        }
        return output;
    }
    
    /**
     * given a board and the position of the king on this board, finds the set of all points that the king could be attacked from
     * @param b board that we are working with
     * @param pos position of the king
     * @return the set of all tiles that the king could be attacked from, excluding itself
     */
    public HashSet<Tile> attackedRange(Board b, Tile pos) {
        HashSet<Tile> output = new HashSet<Tile>();
        int vecX = 1;
        int vecY = 1;
        int curX = pos.getX();
        int curY = pos.getY();
        for(int j = 0; j < 2; j++) {
            for(int i = 0; i < 4; i++) {
                while(curX + vecX < 8 && curX + vecX >= 0 && curY + vecY < 8 
                          && curY + vecY >= 0 
                          && b.getTiles()[curX + vecX][curY + vecY].getPiece() == null) {
                    output.add(b.getTiles()[curX + vecX][curY + vecY]);
                    curX += vecX;
                    curY += vecY;
                }
                if(curX + vecX < 8 && curX + vecX >= 0 && curY + vecY < 8 
                       && curY + vecY >= 0 
                       && b.getTiles()[curX + vecX][curY + vecY].getPiece().getColour() != this.getColour()) {
                    output.add(b.getTiles()[curX + vecX][curY + vecY]);
                }
                vecX = vecX + vecY;
                vecY = vecX - vecY;
                vecX = vecX - vecY;
                vecX = -vecX;
                curX = pos.getX();
                curY = pos.getY();
            }
            vecX = 0;
            vecY = 1;
        }
        vecX = 2;
        vecY = 1;
        for(int j = 0; j < 2; j++) {
            for(int i = 0; i < 4; i++) {
                if(pos.getX() + vecX < 8 && pos.getX() + vecX >= 0 && pos.getY() + vecY < 8 
                       && pos.getY() + vecY >= 0 
                       && b.getTiles()[pos.getX() + vecX][pos.getY() + vecY].getPiece() == null) {
                    output.add(b.getTiles()[pos.getX() + vecX][pos.getY() + vecY]);
                }
                else if(pos.getX() + vecX < 8 && pos.getX() + vecX >= 0 && pos.getY() + vecY < 8 
                            && pos.getY() + vecY >= 0 
                            && b.getTiles()[pos.getX() + vecX][pos.getY() + vecY].getPiece().getColour() != this.getColour()) {
                    output.add(b.getTiles()[pos.getX() + vecX][pos.getY() + vecY]);
                }
                vecX = vecX + vecY;
                vecY = vecX - vecY;
                vecX = vecX - vecY;
                vecX = -vecX;
            }
            vecX = 1;
            vecY = 2;
        }
        return output;
    }
    
    /**
     * given a board b and the position of the king on that board, check whether the king is being attacked, or 'checked'
     * @param b board that we are working with
     * @return true if the king is in check, false otherwise
     */
    public boolean isChecked(Board b, Tile t) {
        HashSet<Tile> toCheck = this.attackedRange(b, t);
        Iterator<Tile> i = toCheck.iterator();
        Tile dummy;
        while(i.hasNext()) {
            dummy = i.next();
            if(dummy.getPiece() != null 
                   && !dummy.getPiece().getName().equals("K")
                   && dummy.getPiece().range(b, dummy).contains(t)) {
                return true;
            }
            else if(dummy.getPiece() != null
                        && dummy.getPiece().getName().equals("K")
                        && dummy.getX() - t.getX() < 2
                        && dummy.getX() - t.getX() > -2
                        && dummy.getY() - t.getY() < 2
                        && dummy.getY() - t.getY() > -2) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return the points for the king
     * @param int points for king
     */
    @Override
    public int getPoints() {
        if(this.hasCastled) {
            return 10;
        }
        else if(this.hasMoved()) {
            return -10;
        }
        else {
            return 0;
        }
    }
    
    /**
     * Set whether or not the king castled
     * @param boolean whether or not the king castled
     */
    public void setCastled(boolean x) {
        this.hasCastled = x;
    }
    
    /**
     * Copies a piece
     * @return the piece
     */
    @Override
    public Piece copy() {
        King out = new King(this.getColour());
        out.setCastled(this.hasCastled);
        out.setMoved(this.getTurnMoved());
        return out;
    }
}