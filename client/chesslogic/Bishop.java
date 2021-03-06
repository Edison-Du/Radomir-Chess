package chesslogic;

/**
 * Class for a Bishop
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import config.UserInterface;

public class Bishop extends Piece {

    /**
     * create a bishop
     * @param col colour to create it in
     * @
     */
    public Bishop(int col) {
        super(col, "B", 50, UserInterface.PIECES.get(col + 2));
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
        int vecX = 1; //direction vectors
        int vecY = 1;
        int curX = pos.getX(); //position of bishop
        int curY = pos.getY();
        output.add(pos);
        for(int i = 0; i < 4; i++) {
            //go in the direction of the direction vector until it hits a dead end
            while(curX + vecX < 8 && curX + vecX >= 0 && curY + vecY < 8 && curY + vecY >= 0 
                   && b.getTiles()[curX + vecX][curY + vecY].getPiece() == null) {
                output.add(b.getTiles()[curX + vecX][curY + vecY]);
                curX += vecX;
                curY += vecY;
            }
            if(curX + vecX < 8 && curX + vecX >= 0 && curY + vecY < 8 && curY + vecY >= 0 
                        && b.getTiles()[curX + vecX][curY + vecY].getPiece().getColour() != this.getColour()) {
                output.add(b.getTiles()[curX + vecX][curY + vecY]);
            }
            //rotate the direction vector
            vecX = vecX + vecY;
            vecY = vecX - vecY;
            vecX = vecX - vecY;
            vecX = -vecX;
            curX = pos.getX(); //reset the base position
            curY = pos.getY();
        }
        return output;
    }

    public int adjustPoints(){
        return this.getPoints()+5;
    }

    /**
     * Copies a piece
     * @return the piece
     */
    @Override
    public Piece copy() {
        Bishop out = new Bishop(this.getColour());
        out.setMoved(this.getTurnMoved());
        return out;
    }
}