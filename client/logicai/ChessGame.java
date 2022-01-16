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
    
    /**
     * Create a game
     * @throws IOException
     */
    public ChessGame() throws IOException {
        current = new Board();
        moves = new Stack<Board>();
    }
    
    /**
     * Make a move (change a piece's position, switch to next player's turn
     * Does nothing if the move is illegal
     * @param t1 position of piece to move
     * @param t2 position to move to
     * @throws IOException
     */
    public void move(String t1, String t2) throws IOException {
        if(this.current.legal(t1, t2)) {
            int[] pos1 = Constants.chessToCoord(t1);
            int[] pos2 = Constants.chessToCoord(t2);
            moves.push(current.copy());
            if(current.getTiles()[pos1[0]][pos1[1]].getPiece().getName().equals("p") && pos2[0] != pos1[0] && current.getTiles()[pos2[0]][pos2[1]].getPiece() == null) { //remove a piece for en passant
                if(current.getToMove() == Constants.WHITE) {
                    current.getTiles()[pos2[0]][pos2[1] - 1].setPiece(null);
                }
                else if(current.getToMove() == Constants.BLACK) {
                    current.getTiles()[pos2[0]][pos2[1] + 1].setPiece(null);
                }
            }
            current.getTiles()[pos1[0]][pos1[1]].getPiece().move(current.getTiles()[pos1[0]][pos1[1]], current.getTiles()[pos2[0]][pos2[1]]);
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
            if(current.getKings()[1 - current.getToMove()].isChecked(current, current.getKingTiles()[1 - current.getToMove()])) {
                System.out.println("King " + (1 - current.getToMove()) + " is in check");
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
    
    // dummy program
    public int getColour(String username) {
        if(username.equals("user")) {
            return 0;
        } else { return 1;}
    }

    //main method
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        String t1;
        String t2;
        int[] forPromotions;
        ChessGame g = new ChessGame();
        RandomBot bot = new RandomBot();
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
            g.move(t1, t2);
            //code for promoting pawns
            forPromotions = Constants.chessToCoord(t2);
            if(forPromotions[1] == 7*g.getCurrentPos().getToMove() && g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].getPiece().getName().equals("p")) {
                System.out.println("Promote pawn on " + t2 + " to what?");
                System.out.println("Q: Queen\nR: Rook\nB: Bishop\nN: Knight");
                do {
                    t1 = s.next();
                    s.nextLine();
                } while(!(t1.equals("Q") || t1.equals("R") || t1.equals("B") || t1.equals("N")));
                if(t1.equals("Q")) {
                    g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Queen(1 - g.getCurrentPos().getToMove()));
                }
                else if(t1.equals("R")) {
                    g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Rook(1 - g.getCurrentPos().getToMove()));
                }
                else if(t1.equals("B")) {
                    g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Bishop(1 - g.getCurrentPos().getToMove()));
                }
                else if(t1.equals("N")) {
                    g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Knight(1 - g.getCurrentPos().getToMove()));
                }
            }
            if(!g.getCurrentPos().ended()) {
                botMove = bot.nextMove(g.getCurrentPos());
                System.out.println("Bot moved " + botMove.substring(0, 2) + " to " + botMove.substring(2, 4));
                g.move(botMove.substring(0, 2), botMove.substring(2, 4));
                forPromotions = Constants.chessToCoord(botMove.substring(2, 4));
                if(forPromotions[1] == 7*g.getCurrentPos().getToMove() && g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].getPiece().getName().equals("p")) {
                    botMove = bot.promotePawn(g.getCurrentPos());
                    if(botMove.equals("Q")) {
                        g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Queen(1 - g.getCurrentPos().getToMove()));
                    }
                    else if(botMove.equals("R")) {
                        g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Rook(1 - g.getCurrentPos().getToMove()));
                    }
                    else if(botMove.equals("B")) {
                        g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Bishop(1 - g.getCurrentPos().getToMove()));
                    }
                    else if(botMove.equals("N")) {
                        g.getCurrentPos().getTiles()[forPromotions[0]][forPromotions[1]].setPiece(new Knight(1 - g.getCurrentPos().getToMove()));
                    }
                }
            }
        }
        s.close();
    }
}