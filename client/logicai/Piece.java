package logicai;

/**
 * Class for a generic piece
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import java.awt.image.BufferedImage;

public abstract class Piece {
    private int colour;
    private int turnMoved;
    private String name;
    private BufferedImage image;
    
    /**
     * Create a piece
     * @param col colour of the piece
     * @param name name of the piece (fixed depending on the type of piece)
     */
    Piece(int col, String name, BufferedImage image) {
        this.colour = col;
        this.name = name;
        this.image = image;
        this.turnMoved = 0;
    }
    
    /**
     * Given a board and the position of this piece on that board, compute the set of tiles that this piece is 'attacking'
     * @param b board to use
     * @param pos position of this piece on that board
     * @return a HashSet containing all tiles on the board that this piece is attacking, including the tile that this piece is standing on
     */
    public abstract HashSet<Tile> range(Board b, Tile t);
    
    /**
     * move the piece from one tile to another
     * @param curPos the position that the piece is currently on
     * @param nexPos the position that the piece should move to
     */
    public void move(Tile curPos, Tile nextPos) {
        curPos.setPiece(null);
        nextPos.setPiece(this);
    }
    
    /**
     * get the name of this piece
     * @return the name of the piece
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * get the colour of the piece
     * @return white or black, depending on the colour of this piece
     */
    public int getColour() {
        return this.colour;
    }

    /**
     * get the image of the piece
     * @return the image of the piece
     */
    public BufferedImage getImage() {
        return this.image;
    }
    
    /**
     * get whether this piece has moved yet or not
     * @return true if the piece has move, false otherwise
     */
    public boolean hasMoved() {
        return this.turnMoved != 0;
    }
    
    /**
     * get the turn that this piece first moved
     * @return description
     */
    public int getTurnMoved() {
        return this.turnMoved;
    }
    
    /**
     * set the turn moved
     * @param the turn this piece moved
     */
    public void setMoved(int turn) {
        this.turnMoved = turn;
    }
    
}