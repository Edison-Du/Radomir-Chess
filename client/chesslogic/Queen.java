package chesslogic;

/**
 * Class for a Queen
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import config.UserInterface;

public class Queen extends Piece {
    
    /**
     * create a queen
     * @param col colour of the queen
     * @
     */
    public Queen(int col) {
        super(col, "Q", 140, UserInterface.PIECES.get(col + 8));
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
        int vecX = 1;
        int vecY = 1;
        int curX = pos.getX();
        int curY = pos.getY();
        output.add(pos);
        for(int j = 0; j < 2; j++) {
            for(int i = 0; i < 4; i++) {
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
        return output;
    }
    
    /**
     * Copies a piece
     * @return the piece
     */
    @Override
    public Piece copy() {
        Queen out = new Queen(this.getColour());
        out.setMoved(this.getTurnMoved());
        return out;
    }
}