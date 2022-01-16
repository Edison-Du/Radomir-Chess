package logicai;

/**
 * Class for a game of chess
 * Also holds main method
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.Stack;
import java.util.Scanner;
import java.io.IOException;

public class ChessGame {
    private Stack<Board> moves;
    private Board current;
    private Stack<String> stringMoves;
    
    /**
     * Create a game
     * @
     */
    public ChessGame()  {
        current = new Board();
        moves = new Stack<Board>();
        stringMoves = new Stack<String>();
    }
    
    /**
     * Make a move (change a piece's position, switch to next player's turn
     * Does nothing if the move is illegal
     * @param t1 position of piece to move
     * @param t2 position to move to
     * @
     */
    public void move(String t1, String t2, String p)  {
        this.current.legal(t1, t2);
        if(this.current.legal(t1, t2) && !(this.current.promotingMove(t1, t2) && !this.current.validPromotion(p))) {
            int[] pos1 = Constants.chessToCoord(t1);
            int[] pos2 = Constants.chessToCoord(t2);
            moves.push(current.copy());
            stringMoves.push(t1 + t2);
            if(current.getTiles()[pos1[0]][pos1[1]].getPiece().getName().equals("p") && pos2[0] != pos1[0] && current.getTiles()[pos2[0]][pos2[1]].getPiece() == null) { //remove a piece for en passant
                if(current.getToMove() == Constants.WHITE) {
                    current.getTiles()[pos2[0]][pos2[1] - 1].setPiece(null);
                }
                else if(current.getToMove() == Constants.BLACK) {
                    current.getTiles()[pos2[0]][pos2[1] + 1].setPiece(null);
                }
            }
            if(current.promotingMove(t1, t2)) {
                current.getTiles()[pos1[0]][pos1[1]].getPiece().move(current.getTiles()[pos1[0]][pos1[1]], current.getTiles()[pos2[0]][pos2[1]]);
                current.promotePawn(current.getTile(t2), p);
            }
            else {
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
                }
                else if(pos1[0] - pos2[0] == 2) {
                    current.getTiles()[0][pos2[1]].getPiece().move(current.getTiles()[0][pos2[1]], current.getTiles()[3][pos2[1]]);
                    current.getTiles()[3][pos2[1]].getPiece().setMoved(current.getTurn());
                }
            }
            current.nextTurn();
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

    public void undo() {
        if(!this.stringMoves.isEmpty()) {
            Tile t = this.current.getTile(this.stringMoves.peek().substring(2, 4));
            if(t.getPiece().getTurnMoved() == this.current.getTurn() - 1) {
                int x1 = (int) this.stringMoves.peek().charAt(0);
                int x2 = (int) this.stringMoves.peek().charAt(2);
                x1 = x1 - 97;
                x2 = x2 - 97;
                t.getPiece().setMoved(0);
                if(t.getPiece().getName().equals("K") && x2 - x1 == 2) {
                    this.current.getTiles()[x2 - 1][t.getY()].getPiece().setMoved(0);
                }
                else if(t.getPiece().getName().equals("K") && x1 - x2 == 2) {
                    this.current.getTiles()[x2 + 1][t.getY()].getPiece().setMoved(0);
                }
            }
            this.current = this.moves.peek();
            this.moves.pop();
            this.stringMoves.pop();
        }
    }
    
    // dummy program
    public int getColour(String username) {
        if(username.equals("user")) {
            return 0;
        } else { return 1;}
    }

    //main method
    public static void main(String[] args)  {
        Scanner s = new Scanner(System.in);
        String t1;
        String t2;
        String p;
        ChessGame g = new ChessGame();
        Bot bot = new DepthSearchBotP1(1, Constants.BLACK);
        String botMove;
        System.out.println("RADOMIRCHESS");
        System.out.println("Enter the square that the piece you want to move is on, followed by the square that you want the piece to move to, separated by a space");
        System.out.println("Example: e2 e4");
        while(!g.getCurrentPos().ended()) {
            System.out.println(g.getCurrentPos());
            if(g.getCurrentPos().getToMove() == Constants.WHITE) {
                System.out.println("White to move");
            }
            else if(g.getCurrentPos().getToMove() == Constants.BLACK) {
                System.out.println("Black to move");
            }
            do {
                t1 = s.next();
                t2 = s.next();
                s.nextLine();
            } while(!g.getCurrentPos().legal(t1, t2));
            if(g.getCurrentPos().promotingMove(t1, t2)) {
                System.out.println("What piece to promote to?");
                System.out.println("Q: Queen - R: Rook - B: Bishop - N: Knight");
                do {
                    p = s.next();
                    s.nextLine();
                } while(!g.getCurrentPos().validPromotion(p));
            }
            else {
                p = null;
            }
            g.move(t1, t2, p);
            System.out.println("done moving");
            /*
            if(!g.getCurrentPos().ended()) {
                botMove = bot.nextMove(g);
                System.out.println("Bot moved " + botMove.substring(0, 2) + " to " + botMove.substring(2, 4));
                if(g.getCurrentPos().promotingMove(botMove.substring(0, 2), botMove.substring(2, 4))) {
                    System.out.println("Bot promoted pawn to " + botMove.substring(4, 5));
                }
                g.move(botMove.substring(0, 2), botMove.substring(2, 4), botMove.substring(4, 5));
            }
            */
        }
        s.close();
    }
}