package chesslogic;

import java.util.HashSet;

public class Knight extends Piece {
    
    /**
     * Create a knight
     * @param col colour of the knight
     * @
     */
    public Knight(int col) {
        super(col, "N", 40, getImage("N", col));
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
        int vecX = 2;
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
            vecY = 2;
        }
        return output;
    }
    
}