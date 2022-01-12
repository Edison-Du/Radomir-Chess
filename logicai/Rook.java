package logicai;

/**
 * Class for a rook
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;

class Rook extends Piece {
    
    /**
     * Create a rook
     * @param col colour of the rook
     */
    public Rook(int col) {
        super(col, "R");
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
        int curX = pos.getX();
        int curY = pos.getY();
        output.add(pos);
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
        return output;
    }
    
}