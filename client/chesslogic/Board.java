package chesslogic;

import java.util.ArrayList;

/**
 * A class for a board
 * Contains the player to move and all the tiles
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.HashSet;
import java.util.Iterator;

public class Board {
    
    private Tile[][] tiles;
    private Tile[] kingTiles; //tiles that the kings are on
    private King[] kings;     //the kings themselves
    private int turn;
    private int toMove;       //player to move (0 if white, 1 if black)
    private ArrayList<ArrayList<Tile>> pieces;
    /**
     * Create a new chessboard (the default starting position)
     * @
     */
    public Board() {
        this.turn = 1;
        this.toMove = ChessConsts.WHITE;
        this.tiles = new Tile[8][8];
        this.kings = new King[2];
        this.kings[0] = new King(ChessConsts.WHITE);
        this.kings[1] = new King(ChessConsts.BLACK);
        this.pieces = new ArrayList<ArrayList<Tile>>();
        this.pieces.add(new ArrayList<Tile>());
        this.pieces.add(new ArrayList<Tile>());
        int col = ChessConsts.WHITE;
        for(int i = 0; i < 8; i = i + 7) {
            this.tiles[0][i] = new Tile(0, i, new Rook(col));
            this.tiles[1][i] = new Tile(1, i, new Knight(col));
            this.tiles[2][i] = new Tile(2, i, new Bishop(col));
            this.tiles[3][i] = new Tile(3, i, new Queen(col));
            this.tiles[4][i] = new Tile(4, i, this.kings[col]);
            this.tiles[5][i] = new Tile(5, i, new Bishop(col));
            this.tiles[6][i] = new Tile(6, i, new Knight(col));
            this.tiles[7][i] = new Tile(7, i, new Rook(col));
            for(int j = 0; j < 8; j++) {
                this.pieces.get(col).add(this.tiles[j][i]);
            }
            col = ChessConsts.BLACK;
        }
        col = ChessConsts.WHITE;
        for(int i = 1; i < 8; i = i + 5) {
            for(int j = 0; j < 8; j++) {
                this.tiles[j][i] = new Tile(j, i, new Pawn(col));
                this.pieces.get(col).add(this.tiles[j][i]);
            }
            col = ChessConsts.BLACK;
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
    
    public ArrayList<ArrayList<Tile>> getPieces() {
        return this.pieces;
    }
    
    /**
     * return the player to move
     * @return 0 if white to move, 1 if black to move
     */
    public int getToMove() {
        return this.toMove;
    }
    
    public void setToMove(int toMove) {
        this.toMove = toMove;
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
    
    public void setTurn(int turn) {
       this.turn = turn;
    }
    
    /**
     * return a 2d array of all tiles
     * @return all tiles
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }
    
    public Tile getTile(String tileName) {
        int[] pos = ChessConsts.chessToCoord(tileName);
        return this.tiles[pos[0]][pos[1]];
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
        StringBuilder out = new StringBuilder();
        for(int i = 7; i >= 0; i--) {
            out.append((i + 1) + " ");
            for(int j = 0; j < 8; j++) {
                if(this.tiles[j][i].getPiece() == null) {
                    out.append("_ ");
                }
                else {
                    out.append(this.tiles[j][i].getPiece().getName() + this.tiles[j][i].getPiece().getColour());
                }
            }
            out.append("\n\n");
        }
        out.append(" ");
        for(int i = 'a'; i <= 'h' ; i++) {
            out.append(" " + (char) i);
        }
        return out.toString();
    }
    
    /**
     * checks if a move is legal
     * @param p1 position of piece that will move in chess notation (e.g. a1, b5, etc.)
     * @param p2 position of place that the piece will move in chess notation
     * @return true if the move is legal
     * @
     */
    public boolean legal(String p1, String p2)  {
        int[] pos1 = ChessConsts.chessToCoord(p1);
        int[] pos2 = ChessConsts.chessToCoord(p2);
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
        Piece pieceTaken;
        Tile tPieceTaken;
        Piece castler = null;
        Tile tCastler = null;
        Tile tCastled = null;
        Tile ogKingPos = this.kingTiles[toMove];
        if(this.getTiles()[pos1[0]][pos1[1]].getPiece().getName().equals("p") && pos2[0] != pos1[0] && this.getTiles()[pos2[0]][pos2[1]].getPiece() == null) { //remove a piece for en passant
            pieceTaken = this.getTiles()[pos2[0]][pos2[1] + 2*this.toMove - 1].getPiece();
            tPieceTaken = this.getTiles()[pos2[0]][pos2[1] + 2*this.toMove - 1];
            this.getTiles()[pos2[0]][pos2[1] + 2*this.toMove - 1].setPiece(null);
        }
        else {
            pieceTaken = this.getTiles()[pos2[0]][pos2[1]].getPiece();
            tPieceTaken = this.getTiles()[pos2[0]][pos2[1]];
        }
        this.getTiles()[pos1[0]][pos1[1]].getPiece().move(this.getTiles()[pos1[0]][pos1[1]], this.getTiles()[pos2[0]][pos2[1]]);
        if(this.kingTiles[toMove] == this.getTiles()[pos1[0]][pos1[1]]) {
            this.kingTiles[toMove] = this.tiles[pos2[0]][pos2[1]];
            if(pos2[0] - pos1[0] == 2) {
                castler = this.tiles[7][pos2[1]].getPiece();
                tCastler = this.tiles[7][pos2[1]];
                tCastled = this.tiles[5][pos2[1]];
                this.getTiles()[7][pos2[1]].getPiece().move(this.getTiles()[7][pos2[1]], this.getTiles()[5][pos2[1]]);
            }
            else if(pos1[0] - pos2[0] == 2) {
                castler = this.getTiles()[0][pos2[1]].getPiece();
                tCastler = this.getTiles()[0][pos2[1]];
                tCastled = this.getTiles()[3][pos2[1]];
                this.getTiles()[0][pos2[1]].getPiece().move(this.getTiles()[0][pos2[1]], this.getTiles()[3][pos2[1]]);
            }
        }
        if(this.getKings()[this.toMove].isChecked(this, this.getKingTiles()[this.toMove])) {
            this.getTile(p2).getPiece().move(this.getTile(p2), this.getTile(p1));
            tPieceTaken.setPiece(pieceTaken);
            if(castler != null) {
                castler.move(tCastled, tCastler);
            }
            this.kingTiles[toMove] = ogKingPos;
            return false;
        }
        this.getTile(p2).getPiece().move(this.getTile(p2), this.getTile(p1));
        tPieceTaken.setPiece(pieceTaken);
        if(castler != null) {
            castler.move(tCastled, tCastler);
        }
        this.kingTiles[toMove] = ogKingPos;
        return true;
    }
    
    //we assume that the move is legal
    //this method does not return algebraic notation
    public String moveInfo(String t1, String t2, String p) {
        String out = "";
        if(getTile(t1).getPiece().getName().equals("K") && getTile(t2).getX() - getTile(t1).getX() == 2) {
            return "0-0___";
        }
        else if(getTile(t1).getPiece().getName().equals("K") && getTile(t2).getX() - getTile(t1).getX() == -2) {
            return "0-0-0_";
        }
        else {
            //takes (e.g. xQ, xp, x0)
            out = out + "x";
            if(getTile(t2).getPiece() != null) {
                out = out + getTile(t2).getPiece().getName();
            }
            else {
                out = out + "0";
            }
            out = out + "P";
            if(promotingMove(t1, t2)) {
                out = out + p;
            }
            else {
                out = out + "0";
            }
            out = out + "E";
            if(getTile(t1).getPiece().getName().equals("p") && getTile(t2).getPiece() == null && getTile(t1).getX() != getTile(t2).getX()) {
                out = out + "t";
            }
            else {
                out = out + "f";
            }
        }
        return out;
    }
    
    public boolean promotingMove(String t1, String t2)  {
        return getTile(t1).getPiece() != null && getTile(t1).getPiece().getName().equals("p") && ChessConsts.chessToCoord(t2)[1] == 7*(1 - toMove);
    }
    
    public boolean validPromotion(String p)  {
        return p!= null && (p.equals("Q") || p.equals("R") || p.equals("B") || p.equals("N"));
    }
    
    public void promotePawn(Tile t, String p)  {
        if(p.equals("Q")) {
            t.setPiece(new Queen(toMove));
        }
        else if(p.equals("R")) {
            t.setPiece(new Rook(toMove));
        }
        else if(p.equals("B")) {
            t.setPiece(new Bishop(toMove));
        }
        else if(p.equals("N")) {
            t.setPiece(new Knight(toMove));
        }
    }

    /**
     * return the set of all legal moves of a piece on a given position (taking into account whose turn it is)
     * @param tile the tile the piece is on
     * @return read description
     * @
     */
    public HashSet<String> legalMoves(Tile tile)  {
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
     * @
     */
    public boolean ended()  {
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
     * @
     */
    public Board copy() {
        Board out = new Board();
        out.getPieces().get(0).clear();
        out.getPieces().get(1).clear();
        for(int i = 0; i < 64; i++) {
            if(this.tiles[i/8][i%8].getPiece() == null) {
                out.getTiles()[i/8][i%8].setPiece(null);
            }
            else {
                out.getTiles()[i/8][i%8].setPiece(this.tiles[i/8][i%8].getPiece().copy());
                out.getPieces().get(out.getTiles()[i/8][i%8].getPiece().getColour()).add(out.getTiles()[i/8][i%8]);
                if(out.getTiles()[i/8][i%8].getPiece().getName().equals("K")) {
                    out.getKings()[out.getTiles()[i/8][i%8].getPiece().getColour()] = (King) out.getTiles()[i/8][i%8].getPiece();
                    out.getKingTiles()[out.getTiles()[i/8][i%8].getPiece().getColour()] = out.getTiles()[i/8][i%8];
                }
            }
        }
        out.setTurn(this.turn);
        out.setToMove(this.toMove);
        return out;
    }
    
    //O(n) time - loops through each arraylist once
    public boolean equals(Board b) {
        if(this.pieces.get(0).size() != b.getPieces().get(0).size()) {
            return false;
        }
        else if(this.pieces.get(1).size() != b.getPieces().get(1).size()) {
            return false;
        }
        else {
            for(int i = 0; i < 2; i++) {
                for(int j = 0; j < this.pieces.get(i).size(); j++) {
                    if( (!(this.pieces.get(i).get(j).toString().equals(b.getPieces().get(i).get(j).toString()))) || this.pieces.get(i).get(j).getPiece() != b.getPieces().get(i).get(j).getPiece()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}