package chesslogic;

import java.util.Scanner;

/**
 * Class for a game of chess
 * Also holds main method
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.Stack;

public class ChessGame {
    private Stack<Board> moves;
    private Board current;
    private Stack<String> stringMoves;
    private Stack<Piece> piecesTaken;
    private Stack<Piece> pawnsPromoted;
    private int fiftyMoves;
    
    /**
     * Create a game
     * @
     */
    public ChessGame()  {
        current = new Board();
        stringMoves = new Stack<String>();
        piecesTaken = new Stack<Piece>();
        pawnsPromoted = new Stack<Piece>();
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
                fiftyMoves = 0;
            }
            else {
                piecesTaken.push(current.getTile(t2).getPiece());
                if(piecesTaken.peek() != null) {
                    current.getPieces().get(1 - current.getToMove()).remove(current.getTile(t2));
                    fiftyMoves = 0;
                }
                else if(current.getTile(t1).getPiece().getName().equals("p")) {
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
                }
                else if(pos1[0] - pos2[0] == 2) {
                    current.getTiles()[0][pos2[1]].getPiece().move(current.getTiles()[0][pos2[1]], current.getTiles()[3][pos2[1]]);
                    current.getTiles()[3][pos2[1]].getPiece().setMoved(current.getTurn());
                    current.getPieces().get(current.getToMove()).add(current.getTiles()[3][pos2[1]]);
                    current.getPieces().get(current.getToMove()).remove(current.getTiles()[0][pos2[1]]);
                }
            }
            current.nextTurn();
        }
        else {
            System.out.println(t1 + t2 + p + " is not legal");
        }
    }
    
    /**
     * return the state of the board
     * @return the state of the board
     */
    public Board getCurrentPos() {
        return this.current;
    }
    
    /**
     * return a record of all previous moves
     * @return a stack of Boards
     */
    public Stack<Board> getMoves() {
        return this.moves;
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
                }
                else if(t.getPiece().getName().equals("K") && x1 - x2 == 2) {
                    this.current.getTiles()[x2 + 1][t.getY()].getPiece().setMoved(0);
                    this.current.getTiles()[x2 + 1][t.getY()].getPiece().move(this.current.getTiles()[x2 + 1][t.getY()], this.current.getTiles()[0][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).remove(this.current.getTiles()[x2 + 1][t.getY()]);
                    this.current.getPieces().get(1 - this.current.getToMove()).add(this.current.getTiles()[0][t.getY()]);
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
        }
    }
    
    public boolean whiteWins() {
        return current.ended() && current.getKings()[Constants.BLACK].isChecked(current, current.getKingTiles()[Constants.BLACK]);
    }
    
    public boolean blackWins() {
        return current.ended() && current.getKings()[Constants.WHITE].isChecked(current, current.getKingTiles()[Constants.WHITE]);
    }
    
    public boolean stalemate() {
        if(fiftyMoves == 50) {
            return true;
        }
        else if(!current.ended()) {
            return false;
        }
        for(int i = 0; i < 2; i++) {
            if(current.getKings()[i].isChecked(current, current.getKingTiles()[i])) {
                return false;
            }
        }
        return true;
    }

    //main method
//    public static void main(String[] args)  {
//        Scanner s = new Scanner(System.in);
//        String t1;
//        String t2;
//        String p;
//        ChessGame g = new ChessGame();
//        Bot bot = new DepthSearchBotP2(2, Constants.BLACK);
//        String botMove;
//        System.out.println("RADOMIRCHESS");
//        System.out.println("Enter the square that the piece you want to move is on, followed by the square that you want the piece to move to, separated by a space");
//        System.out.println("Example: e2 e4");
//g.move("a2" ,"a3", null);
//g.move("d7" ,"d5", null);
//g.move("a1" ,"a2", null);
//g.move("c8" ,"e6", null);
//g.move("a2" ,"a1", null);
//g.move("b8" ,"c6", null);
//g.move("a1" ,"a2", null);
//g.move("b7" ,"b5", null);
//g.move("a2" ,"a1", null);
//g.move("a7" ,"a6", null);
//g.move("a1" ,"a2", null);
//g.move("g7" ,"g6", null);
//g.move("a2" ,"a1", null);
//g.move("f8" ,"g7", null);
//g.move("a1" ,"a2", null);
//g.move("e6" ,"g4", null);
//g.move("a2" ,"a1", null);
//g.move("c6" ,"d4", null);
//g.move("a1" ,"a2", null);
//g.move("g4" ,"e2", null);
//g.move("c1" ,"e2", null);
//g.move("d4" ,"e2", null);
//g.move("d1" ,"e2", null);
//g.move("e7" ,"e5", null);
//g.move("e2" ,"e5", null);
//g.move("g7" ,"e5", null);
//g.move("f1" ,"b5", null);
//g.move("d8" ,"d7", null);
//g.move("a2" ,"a1", null);
//
//
//        while(!g.getCurrentPos().ended()) {
//            System.out.println(g.getCurrentPos());
//            if(g.getCurrentPos().getToMove() == Constants.WHITE) {
//                System.out.println("White to move");
//            }
//            else if(g.getCurrentPos().getToMove() == Constants.BLACK) {
//                System.out.println("Black to move");
//            }
//            do {
//                t1 = s.next();
//                t2 = s.next();
//                s.nextLine();
//            } while(!(g.getCurrentPos().legal(t1, t2) || (t1.equals("undo") && t2.equals("move"))));
//            if(t1.equals("undo") && t2.equals("move")) {
//                g.undo();
//            }
//            else {
//                if(g.getCurrentPos().promotingMove(t1, t2)) {
//                    System.out.println("What piece to promote to?");
//                    System.out.println("Q: Queen - R: Rook - B: Bishop - N: Knight");
//                    do {
//                        p = s.next();
//                        s.nextLine();
//                    } while(!g.getCurrentPos().validPromotion(p));
//                }
//                else {
//                    p = null;
//                }
//                g.move(t1, t2, p);
//            }
//            
//            System.out.println(g.getCurrentPos().getPieces());
//            System.out.println(g.getStringMoves());
//            System.out.println(g.getPiecesTaken());
//            System.out.println(g.getPawnsPromoted());
//            System.out.println("move successful");
//            if(!g.getCurrentPos().ended()) {
//                botMove = bot.nextMove(g);
//                System.out.println("Bot moved " + botMove.substring(0, 2) + " to " + botMove.substring(2, 4));
//                if(g.getCurrentPos().promotingMove(botMove.substring(0, 2), botMove.substring(2, 4))) {
//                    System.out.println("Bot promoted pawn to " + botMove.substring(4, 5));
//                }
//                g.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4, 5));
//            }   
//        }
//        s.close();
//    }
//    
//    public int getColour(String string) {
//        return 0;
//    }
}