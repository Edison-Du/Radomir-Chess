package chesslogic;

/**
 * Class for a generic piece
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;


import java.awt.image.BufferedImage;
import config.UserInterface;

public abstract class Piece {
    private int colour;
    private int turnMoved;
    private String name;
    private BufferedImage[] images;
    private int points;
    
    /**
     * Create a piece
     * @param col colour of the piece
     * @param name name of the piece (fixed depending on the type of piece)
     */
    Piece(int col, String name, int points, BufferedImage[] images) {
        this.colour = col;
        this.name = name;
        this.images = new BufferedImage[UserInterface.NUM_SETS];
        for (int i = 0; i < UserInterface.NUM_SETS; i++) {
            this.images[i] = images[i];
        }
        this.turnMoved = 0;
        this.points = points;
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
    
    public int getPoints() {
        return this.points;
    }

    /**
     * get the image of the piece
     * @return the image of the piece
     */
    public BufferedImage getImage() {
        return this.images[UserInterface.activeSetNum];
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
    
    public String toString() {
        return this.name + " " + this.turnMoved;
    }
    
    public abstract Piece copy();
}