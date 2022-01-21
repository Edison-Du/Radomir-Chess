package chesslogic;

/**
 * Class for a tile
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

public class Tile {
    
    private Piece piece;
    private int x;                           //0<=x<=7
    private int y;                           //0<=y<=7
    
    /**
     * Create a tile
     * @param x x position
     * @param y y position
     * @param p piece that is on this tile (can be null)
     */
    public Tile(int x, int y, Piece p) {
        this.piece = p;
        this.x = x;
        this.y = y;
    }
    
    /**
     * Get the piece that is on this tile
     * @return whatever it says above
     */
    public Piece getPiece() {
        return this.piece;
    }
    
    /**
     * change the piece on this tile
     * @param p piece to change to
     */
    public void setPiece(Piece p) {
        this.piece = p;
    }
    
    /**
     * get the x-coordinate of this tile
     * @return whatever it says above
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * get the y-coordinate of this tile
     * @return whatever it says above
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * convert this tile to a string
     * @return above
     */
    public String toString() {
        return Constants.coordToChess(this.x, this.y);
    }
}