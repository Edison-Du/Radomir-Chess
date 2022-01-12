package logicai;

/**
 * A class for a board
 * Contains the player to move and all the tiles
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import java.util.Iterator;

class Board {
    
    private Tile[][] tiles;
    private Tile[] kingTiles; //tiles that the kings are on
    private King[] kings;     //the kings themselves
    private int turn;
    private int toMove;       //player to move (0 if white, 1 if black)
    
    /**
     * Create a new chessboard (the default starting position)
     */
    public Board() {
        this.turn = 1;
        this.toMove = 0;
        this.tiles = new Tile[8][8];
        this.kings = new King[2];
        this.kings[0] = new King(Constants.WHITE);
        this.kings[1] = new King(Constants.BLACK);
        int col = Constants.WHITE;
        for(int i = 0; i < 8; i = i + 7) {
            this.tiles[0][i] = new Tile(0, i, new Rook(col));
            this.tiles[1][i] = new Tile(1, i, new Knight(col));
            this.tiles[2][i] = new Tile(2, i, new Bishop(col));
            this.tiles[3][i] = new Tile(3, i, new Queen(col));
            this.tiles[4][i] = new Tile(4, i, this.kings[col]);
            this.tiles[5][i] = new Tile(5, i, new Bishop(col));
            this.tiles[6][i] = new Tile(6, i, new Knight(col));
            this.tiles[7][i] = new Tile(7, i, new Rook(col));
            col = Constants.BLACK;
        }
        col = Constants.WHITE;
        for(int i = 1; i < 8; i = i + 5) {
            for(int j = 0; j < 8; j++) {
                this.tiles[j][i] = new Tile(j, i, new Pawn(col));
            }
            col = Constants.BLACK;
        }
        for(int i = 2; i < 6; i++) {
            for(int j = 0; j < 8; j++) {
                this.tiles[j][i] = new Tile(j, i, null);
            }
        }
        this.kingTiles = new Tile[2];
        this.kingTiles[0] = this.tiles[4][0];
        this.kingTiles[1] = this.tiles[4][7];
    }
    
    /**
     * return the player to move
     * @return 0 if white to move, 1 if black to move
     */
    public int getToMove() {
        return this.toMove;
    }
    
    /**
     * change whose turn it is (i.e. White->Black and Black->White)
     */
    public void nextTurn() {
        this.toMove = (this.toMove + 1)%2;
        this.turn++;
    }
    
    public int getTurn() {
        return this.turn;
    }
    
    /**
     * return a 2d array of all tiles
     * @return all tiles
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }
    
    /**
     * get the tiles containing the white and black king respectively
     * @return [tile that white king is on, tile that black king is on]
     */
    public Tile[] getKingTiles() {
        return this.kingTiles;
    }
    
    public void setKingTiles(Tile[] kT) {
        this.kingTiles = kT;
    }
    
    /**
     * get the objects corresponding to the kings themselves
     * @return [white king, black king]
     */
    public King[] getKings() {
        return this.kings;
    }
    
    /**
     * convert the board to a string
     * @return the board as a string
     */
    public String toString() {
        String out = "";
        char letter;
        for(int i = 7; i >= 0; i--) {
            out = out + (i + 1) + " ";
            for(int j = 0; j < 8; j++) {
                if(this.tiles[j][i].getPiece() == null) {
                    out = out + "_";
                }
                else {
                    out = out + this.tiles[j][i].getPiece().getName();
                }
                out = out + " ";
            }
            out = out + "\n" + "\n";
        }
        out = out + " ";
        for(int i = 97; i < 105; i++) {
            out = out + " ";
            letter = (char) i;
            out = out + letter;
        }
        return out;
    }
    
    /**
     * checks if a move is legal
     * @param p1 position of piece that will move in chess notation (e.g. a1, b5, etc.)
     * @param p2 position of place that the piece will move in chess notation
     * @return true if the move is legal
     */
    public boolean legal(String p1, String p2) {
        int[] pos1 = Constants.chessToCoord(p1);
        int[] pos2 = Constants.chessToCoord(p2);
        //check if the position is on the board
        if(pos1[0] < 0 || pos1[0] > 7 || pos2[0] < 0 || pos2[0] > 7 || pos1[1] < 0 || pos1[1] > 7 || pos2[1] < 0 || pos2[1] > 7) {
            return false;
        }
        //check if the positions are the same
        if(p1.equals(p2)) {
            return false;
        }
        //check if the position contains a piece
        if(this.tiles[pos1[0]][pos1[1]].getPiece() == null) {
            return false;
        }
        //check if the position's piece is the right colour
        if(this.tiles[pos1[0]][pos1[1]].getPiece().getColour() != this.toMove) {
            return false;
        }
        //check if the position to move is in the range of the squares that the piece can move in
        if(!this.tiles[pos1[0]][pos1[1]].getPiece().range(this, this.tiles[pos1[0]][pos1[1]]).contains(this.tiles[pos2[0]][pos2[1]])) {
            return false;
        }
        //check if this move places the king in check
        Board check = this.copy();
        if(check.getTiles()[pos1[0]][pos1[1]].getPiece().getName().equals("p") && pos2[0] != pos1[0] && check.getTiles()[pos2[0]][pos2[1]].getPiece() == null) { //remove a piece for en passant
            if(this.toMove == Constants.WHITE) {
                check.getTiles()[pos2[0]][pos2[1] - 1].setPiece(null);
            }
            else if(this.toMove == Constants.BLACK) {
                check.getTiles()[pos2[0]][pos2[1] + 1].setPiece(null);
            }
        }
        check.getTiles()[pos1[0]][pos1[1]].getPiece().move(check.getTiles()[pos1[0]][pos1[1]], check.getTiles()[pos2[0]][pos2[1]]);
        if(this.kingTiles[toMove] == this.getTiles()[pos1[0]][pos1[1]]) {
            check.getKingTiles()[toMove] = check.getTiles()[pos2[0]][pos2[1]];
            if(pos2[0] - pos1[0] == 2) {
                check.getTiles()[7][pos2[1]].getPiece().move(check.getTiles()[7][pos2[1]], check.getTiles()[5][pos2[1]]);
            }
            else if(pos1[0] - pos2[0] == 2) {
                check.getTiles()[0][pos2[1]].getPiece().move(check.getTiles()[0][pos2[1]], check.getTiles()[3][pos2[1]]);
            }
        }
        if(check.getKings()[this.toMove].isChecked(check, check.getKingTiles()[this.toMove])) {
            return false;
        }
        return true;
    }
    
    /**
     * return the set of all legal moves of a piece on a given position (taking into account whose turn it is)
     * @param tile the tile the piece is on
     * @return read description
     */
    public HashSet<String> legalMoves(Tile tile) {
        if(tile.getPiece() == null) {
            return null;
        }
        else if(tile.getPiece().getColour() != this.toMove) {
            return null;
        }
        else {
            HashSet<String> output = new HashSet<String>();
            HashSet<Tile> check = tile.getPiece().range(this, tile);
            Iterator<Tile> i = check.iterator();
            Tile dummy;
            while(i.hasNext()) {
                dummy = i.next();
                if(legal(tile.toString(), dummy.toString())) {
                    output.add(dummy.toString());
                }
            }
            return output;
        }
    }
    
    /**
     * check if there are any legal moves from the current position
     * @return true if there are legal moves, false otherwise
     */
    public boolean ended() {
        int counter = 63*this.toMove;
        int direction  = 1 - 2*this.toMove;
        while(counter >= 0 && counter < 64) {
            if(legalMoves(this.tiles[counter/8][counter%8]) != null && !legalMoves(this.tiles[counter/8][counter%8]).isEmpty()) {
                return false;
            }
            counter += direction;
        }
        return true;
    }
    
    /**
     * make a copy of the board
     * @return a distinct copy of this board
     */
    public Board copy() {
        Board out = new Board();
        out.setKingTiles(new Tile[2]);
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                out.getTiles()[i][j].setPiece(this.getTiles()[i][j].getPiece());
                if(out.getTiles()[i][j].getPiece() != null && out.getTiles()[i][j].getPiece().getName().equals("K")) {
                    out.getKingTiles()[out.getTiles()[i][j].getPiece().getColour()] = out.getTiles()[i][j];
                }
            }
        }
        return out;
    }
    
}