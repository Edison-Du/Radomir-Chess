package chesslogic;

import java.util.Stack;

/**
 * Class for a game of chess
 * @author JPANEL
 * @version 1.0
 */

public class ChessGame {
    private Board current;
    private Stack<String> stringMoves;
    private Stack<Piece> piecesTaken;
    private Stack<Piece> pawnsPromoted;
    private int fiftyMoves;
    private Stack<Integer> fiftyMoveStack;
    
    /**
     * Create a game
     * @
     */
    public ChessGame()  {
        current = new Board();
        stringMoves = new Stack<String>();
        piecesTaken = new Stack<Piece>();
        pawnsPromoted = new Stack<Piece>();
        fiftyMoveStack = new Stack<Integer>();
        fiftyMoves = 0;
    }
    
    /**
     * Make a move (change a piece's position, switch to next player's turn
     * Does nothing if the move is illegal
     * @param t1 position of piece to move
     * @param t2 position to move to
     * @
     */
    public void move(String t1, String t2, String p) {
        if(this.current.legal(t1, t2) && !(this.current.promotingMove(t1, t2) && !this.current.validPromotion(p))) {
            int[] pos1 = Constants.chessToCoord(t1);
            int[] pos2 = Constants.chessToCoord(t2);
            stringMoves.push(t1 + t2 + current.moveInfo(t1, t2, p));
            current.getPieces().get(current.getToMove()).remove(current.getTile(t1));
            current.getPieces().get(current.getToMove()).add(current.getTile(t2));
            if(current.getTiles()[pos1[0]][pos1[1]].getPiece().getName().equals("p") && pos2[0] != pos1[0] && current.getTiles()[pos2[0]][pos2[1]].getPiece() == null) { //remove a piece for en passant
                piecesTaken.push(current.getTiles()[pos2[0]][pos2[1] + 2*current.getToMove() - 1].getPiece());
                current.getTiles()[pos2[0]][pos2[1] + 2*current.getToMove() - 1].setPiece(null);
                current.getPieces().get(1 - current.getToMove()).remove(current.getTiles()[pos2[0]][pos2[1] + 2*current.getToMove() - 1]);
                fiftyMoveStack.push(fiftyMoves);
                fiftyMoves = 0;
            }
            else {
                piecesTaken.push(current.getTile(t2).getPiece());
                if(piecesTaken.peek() != null) {
                    current.getPieces().get(1 - current.getToMove()).remove(current.getTile(t2));
                    fiftyMoveStack.push(fiftyMoves);
                    fiftyMoves = 0;
                }
                else if(current.getTile(t1).getPiece().getName().equals("p")) {
                    fiftyMoveStack.push(fiftyMoves);
                    fiftyMoves = 0;
                }
                else {
                    fiftyMoves++;
                }
            }
            if(current.promotingMove(t1, t2)) {
                pawnsPromoted.push(current.getTile(t1).getPiece());
                current.getTiles()[pos1[0]][pos1[1]].getPiece().move(current.getTiles()[pos1[0]][pos1[1]], current.getTiles()[pos2[0]][pos2[1]]);
                current.promotePawn(current.getTile(t2), p);
            }
            else {
                pawnsPromoted.push(null);
                current.getTiles()[pos1[0]][pos1[1]].getPiece().move(current.getTiles()[pos1[0]][pos1[1]], current.getTiles()[pos2[0]][pos2[1]]);
            }
            if(!current.getTiles()[pos2[0]][pos2[1]].getPiece().hasMoved()) {
                current.getTiles()[pos2[0]][pos2[1]].getPiece().setMoved(current.getTurn());
            }
            if(current.getKingTiles()[current.getToMove()] == current.getTiles()[pos1[0]][pos1[1]]) {
                current.getKingTiles()[current.getToMove()] = current.getTiles()[pos2[0]][pos2[1]];
                if(pos2[0] - pos1[0] == 2) {
                    current.getTiles()[7][pos2[1]].getPiece().move(current.getTiles()[7][pos2[1]], current.getTiles()[5][pos2[1]]);
                    current.getTiles()[5][pos2[1]].getPiece().setMoved(current.getTurn());
                    current.getPieces().get(current.getToMove()).add(current.getTiles()[5][pos2[1]]);
                    current.getPieces().get(current.getToMove()).remove(current.getTiles()[7][pos2[1]]);
                    current.getKings()[current.getToMove()].setCastled(true);
                }
                else if(pos1[0] - pos2[0] == 2) {
                    current.getTiles()[0][pos2[1]].getPiece().move(current.getTiles()[0][pos2[1]], current.getTiles()[3][pos2[1]]);
                    current.getTiles()[3][pos2[1]].getPiece().setMoved(current.getTurn());
                    current.getPieces().get(current.getToMove()).add(current.getTiles()[3][pos2[1]]);
                    current.getPieces().get(current.getToMove()).remove(current.getTiles()[0][pos2[1]]);
                    current.getKings()[current.getToMove()].setCastled(true);
                }
            }
            current.nextTurn();
        }
        else {
            System.out.println(t1 + t2 + p + " is not legal");
        }
    }
    
    protected void setFiftyMoves(int x) {
        this.fiftyMoves = x;
    }
    
    public int getFiftyMoves() {
        return this.fiftyMoves;
    }
    
    protected void setFiftyMoveStack(Stack<Integer> x) {
        this.fiftyMoveStack = x;
    }
    
    public Stack<Integer> getFiftyMoveStack() {
        return this.fiftyMoveStack;
    }
    
    protected void setCurrentPos(Board b) {
        this.current = b;
    }
    
    protected void setStringMoves(Stack<String> s) {
        this.stringMoves = s;
    }
    
    /**
     * return the state of the board
     * @return the state of the board
     */
    public Board getCurrentPos() {
        return this.current;
    }
    
    public Stack<String> getStringMoves() {
        return this.stringMoves;
    }
    
    public Stack<Piece> getPiecesTaken() {
        return this.piecesTaken;
    }
    
    public Stack<Piece> getPawnsPromoted() {
        return this.pawnsPromoted;
    }

    public void undo() {
        if(!this.stringMoves.isEmpty()) {
            Tile t = this.current.getTile(this.stringMoves.peek().substring(2, 4));
            if(t.getPiece().getTurnMoved() == this.current.getTurn() - 1) {
                int x1 = (int) this.stringMoves.peek().charAt(0);
                int x2 = (int) this.stringMoves.peek().charAt(2);
                x1 = x1 - 'a';
                x2 = x2 - 'a';
                t.getPiece().setMoved(0);
                if(t.getPiece().getName().equals("K") && x2 - x1 == 2) {
                    this.current.getTiles()[x2 - 1][t.getY()].getPiece().setMoved(0);
                    this.current.getTiles()[x2 - 1][t.getY()].getPiece().move(this.current.getTiles()[x2 - 1][t.getY()], this.current.getTiles()[7][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).remove(this.current.getTiles()[x2 - 1][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).add(this.current.getTiles()[7][t.getY()]);
                    this.current.getKings()[1 - this.current.getToMove()].setCastled(false);
                }
                else if(t.getPiece().getName().equals("K") && x1 - x2 == 2) {
                    this.current.getTiles()[x2 + 1][t.getY()].getPiece().setMoved(0);
                    this.current.getTiles()[x2 + 1][t.getY()].getPiece().move(this.current.getTiles()[x2 + 1][t.getY()], this.current.getTiles()[0][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).remove(this.current.getTiles()[x2 + 1][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).add(this.current.getTiles()[0][t.getY()]);
                    this.current.getKings()[1 - this.current.getToMove()].setCastled(false);
                }
            }
            this.current.setToMove(1 - this.current.getToMove());
            this.current.setTurn(this.current.getTurn() - 1);
            t.getPiece().move(t, this.current.getTile(this.stringMoves.peek().substring(0, 2)));
            this.current.getPieces().get(this.current.getToMove()).remove(t);
            this.current.getPieces().get(this.current.getToMove()).add(this.current.getTile(this.stringMoves.peek().substring(0, 2)));
            if(this.current.getTile(this.stringMoves.peek().substring(0, 2)).getPiece().getName().equals("K")) {
                this.current.getKingTiles()[this.current.getToMove()] = this.current.getTile(this.stringMoves.peek().substring(0, 2));
            }
            if(pawnsPromoted.peek() != null) {
                this.current.getTile(this.stringMoves.peek().substring(0, 2)).setPiece(pawnsPromoted.peek());
            }
            if(stringMoves.peek().substring(9, 10).equals("t")) {
                this.current.getTiles()[t.getX()][t.getY() + 2*current.getToMove() - 1].setPiece(piecesTaken.peek());
                this.current.getPieces().get(1 - this.current.getToMove()).add(this.current.getTiles()[t.getX()][t.getY() + 2*current.getToMove() - 1]);
            }
            else {
                t.setPiece(piecesTaken.peek());
                if(piecesTaken.peek() != null) {
                    this.current.getPieces().get(1 - this.current.getToMove()).add(t);
                }
            }
            this.piecesTaken.pop();
            this.pawnsPromoted.pop();
            this.stringMoves.pop();
            if(fiftyMoves > 0) {
                fiftyMoves--;
            }
            else {
                fiftyMoves = fiftyMoveStack.peek();
                fiftyMoveStack.pop();
            }
        }
    }
    
    public boolean whiteWins() {
        return current.ended() && current.getKings()[Constants.BLACK].isChecked(current, current.getKingTiles()[Constants.BLACK]);
    }
    
    public boolean blackWins() {
        return current.ended() && current.getKings()[Constants.WHITE].isChecked(current, current.getKingTiles()[Constants.WHITE]);
    }
    
    public boolean stalemate() {
        if(fiftyMoves == 100) {
            return true;
        }
        else if(current.ended() && !current.getKings()[Constants.WHITE].isChecked(current, current.getKingTiles()[Constants.WHITE]) 
        && !current.getKings()[Constants.BLACK].isChecked(current, current.getKingTiles()[Constants.BLACK])) {
            return true;
        }
        else if(drawByInsufficientPieces()) {
            return true;
        }
        return false;
    }
    
    public boolean ended() {
        return stalemate() || whiteWins() || blackWins();
    }

    private boolean drawByInsufficientPieces() {
        if(current.getPieces().get(0).size() > 2 || current.getPieces().get(1).size() > 2) {
            return false;
        }
        if(current.getPieces().get(0).size() == 1 && current.getPieces().get(1).size() == 1) {
            return true;
        }
        else if(current.getPieces().get(0).size() == 1 && current.getPieces().get(1).size() == 2) {
            return !(!current.getPieces().get(1).get(0).getPiece().getName().equals("N")
                   && !current.getPieces().get(1).get(1).getPiece().getName().equals("N")
                   && !current.getPieces().get(1).get(0).getPiece().getName().equals("B")
                   && !current.getPieces().get(1).get(1).getPiece().getName().equals("B"));
        }
        else if(current.getPieces().get(0).size() == 2 && current.getPieces().get(1).size() == 1) {
            return !(!current.getPieces().get(0).get(0).getPiece().getName().equals("N")
                   && !current.getPieces().get(0).get(1).getPiece().getName().equals("N")
                   && !current.getPieces().get(0).get(0).getPiece().getName().equals("B")
                   && !current.getPieces().get(0).get(1).getPiece().getName().equals("B"));
        }
        else {
            int whiteBish = 0;
            int blackBish = 0;
            for(int i = 0; i < 2; i++) {
                if(current.getPieces().get(0).get(i).getPiece().getName().equals("B")) {
                    whiteBish = i;
                }
                if(current.getPieces().get(1).get(i).getPiece().getName().equals("B")) {
                    blackBish = i;
                }
            }
            if(!current.getPieces().get(0).get(whiteBish).getPiece().getName().equals("B")) {
                return false;
            }
            else if(!current.getPieces().get(1).get(whiteBish).getPiece().getName().equals("B")) {
                return false;
            }
            return (current.getPieces().get(0).get(whiteBish).getX() + current.getPieces().get(0).get(whiteBish).getY() - current.getPieces().get(0).get(blackBish).getX() - current.getPieces().get(0).get(blackBish).getY())%2 == 0;
        }
    }
    
    public String toAlgebraic(String t1, String t2, String p) {
        String out = "";
        if(this.current.getTile(t1).getPiece().getName().equals("K") && this.current.getTile(t2).getX() - this.current.getTile(t1).getX() == 2) {
            return "O-O";
        }
        else if(this.current.getTile(t1).getPiece().getName().equals("K") && this.current.getTile(t2).getX() - this.current.getTile(t1).getX() == -2) {
            return "O-O-O";
        }
        if(!this.current.getTile(t1).getPiece().getName().equals("p")) {
            out = out + this.current.getTile(t1).getPiece().getName();
        }
        else if(this.current.getTile(t2).getX() == this.current.getTile(t1).getX()) { }
        else {
            out = out + t1.substring(0, 1) + "x";
            out = out + t2;
            if(this.current.getTile(t2).getPiece() == null) {
                out = out + " e.p.";
            }
            this.move(t1, t2, p);
            if(stalemate()) {
                out = out + " 1/2 - 1/2 ";
            }
            if(current.getKings()[0].isChecked(current, current.getKingTiles()[0]) || current.getKings()[1].isChecked(current, current.getKingTiles()[1])) {
                out = out + "+";
                if(whiteWins() || blackWins()) {
                    out = out + "+";
                }
            }
            this.undo();
            return out;
        }
        if(this.current.getTile(t2).getPiece() != null) {
            out = out + "x";
        }
        out = out + t2;
        this.move(t1, t2, p);
        if(stalemate()) {
            out = out + " 1/2 - 1/2 ";
        }
        if(current.getKings()[0].isChecked(current, current.getKingTiles()[0]) || current.getKings()[1].isChecked(current, current.getKingTiles()[1])) {
            out = out + "+";
            if(whiteWins() || blackWins()) {
                out = out + "+";
            }
        }
        this.undo();
        return out;
    }
    
    public ChessGame copy() {
        ChessGame out = new ChessGame();
        out.setCurrentPos(this.getCurrentPos().copy());
        out.getStringMoves().addAll(this.stringMoves);
        Stack<Piece> temp1 = new Stack<Piece>();
        Stack<Piece> temp2 = new Stack<Piece>();
        while(!this.piecesTaken.isEmpty()) {
            temp1.push(this.piecesTaken.peek());
            if(this.piecesTaken.peek() != null) {
                temp2.push(this.piecesTaken.peek().copy());
            }
            else {
                temp2.push(null);
            }
            this.piecesTaken.pop();
        }
        while(!temp1.isEmpty()) {
            this.piecesTaken.push(temp1.peek());
            out.getPiecesTaken().push(temp2.peek());
            temp1.pop();
            temp2.pop();
        }
        while(!this.pawnsPromoted.isEmpty()) {
            temp1.push(this.pawnsPromoted.peek());
            if(this.pawnsPromoted.peek() != null) {
                temp2.push(this.pawnsPromoted.peek().copy());
            }
            else {
                temp2.push(null);
            }
            this.pawnsPromoted.pop();
        }
        while(!temp1.isEmpty()) {
            this.pawnsPromoted.push(temp1.peek());
            out.getPawnsPromoted().push(temp2.peek());
            temp1.pop();
            temp2.pop();
        }
        out.setFiftyMoves(this.fiftyMoves);
        out.getFiftyMoveStack().addAll(this.fiftyMoveStack);
        return out;
    }
    
}