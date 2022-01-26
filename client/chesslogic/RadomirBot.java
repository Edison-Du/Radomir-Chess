package chesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.InterruptedException;

/**
 * [RadomirBot.java]
 * Multithreaded Chess engine
 * Chess engine uses depth first search on a min-max tree to calculate the next best move
 * Evaluation consists of heat maps, counting pieces and considering offensive positions
 * Search is optimized by alpha beta pruning as well as sorting moves based on initial evaluation
 * Time complexity is predicted to be O(b^(d/2)) where b is the number of branches and d is the depth
 * @author Peter Gu
 * @author Leo Guan
 * @version 1.0 Jan 24, 2022
 * Each thread added increases the speed marginally. Tested with intel i3-8130U, 12 GB RAM (not sure what type, too lazy to find out)
 */

public class RadomirBot extends Bot {

    // Class Variables
    private OpeningTree opening;
    private boolean isOpening;
    
    private int depth;

    private int[][] attackPoints;
    private int[][] placementPoints;
    private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;
    private String move;

    public RadomirBot(int depth, int side){
        opening = new OpeningTree();
        this.depth = depth;
        placementPoints = new int[ChessConsts.BOARD_LENGTH][ChessConsts.BOARD_LENGTH];
        attackPoints = new int[ChessConsts.BOARD_LENGTH][ChessConsts.BOARD_LENGTH];
        directionXOne = new int[] {-1, -1, 1, 1, 0, 0, -1, 1};
        directionYOne = new int[] {1, -1, -1, 1, -1, 1, 0, 0};
        directionXTwo = new int[] {-2, 2, -2, 2, 0, 0, -2, 2, -1, 1, -2, 2, -1, 1, -2, 2};
        directionYTwo = new int[] {0, 0, -2, 2, -2, 2, 2, -2, 2, 2, -1, -1, -2, -2, 1, 1};
        resetPlacementPoints();
    }

    /**
     * Gets the depth of the engine
     * @param int the depth
     */
    public int getDepth(){
        return this.depth;
    }

    /**
    * Checks a x position and a y position
    * @param x a integer x
    * @param y a integer y
    * @return a boolean value
    */
    public boolean check(int x, int y){
        if (x >= 0 && x < ChessConsts.BOARD_LENGTH && y >= 0 && y < ChessConsts.BOARD_LENGTH) return true;
        return false;
    }

    /**
    * Resets the attack heat map based on the position of enemy king
    * @param x an integer x
    * @param y an integer y
    */
    public void resetAttackPoints(int x, int y){
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                attackPoints[i][j] = ChessConsts.CENTER_POINTS;
            }
        }
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++) {
            attackPoints[i][0] = ChessConsts.OUTER_POINTS;
            attackPoints[i][7] = ChessConsts.OUTER_POINTS;
            attackPoints[0][i] = ChessConsts.OUTER_POINTS;
            attackPoints[7][i] = ChessConsts.OUTER_POINTS;
        }
        for (int i = 1; i < ChessConsts.CENTER_POINTS-1; i++) {
            attackPoints[i][1] = ChessConsts.INNER_POINTS;
            attackPoints[i][6] = ChessConsts.INNER_POINTS;
            attackPoints[1][i] = ChessConsts.INNER_POINTS;
            attackPoints[6][i] = ChessConsts.INNER_POINTS;
        }
        attackPoints[x][y] += 4;
        for (int i = 0; i < ChessConsts.BOARD_LENGTH*2; i++){
            if (check(x + directionXTwo[i], y + directionYTwo[i])){
                attackPoints[x + directionXTwo[i]][y + directionYTwo[i]] += ChessConsts.OUTER_POINTS;
            }
        }
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            if (check(x + directionXOne[i], y + directionYOne[i])){
                attackPoints[x + directionXOne[i]][y + directionYOne[i]] += ChessConsts.INNER_POINTS;
            }
        }
    }

    /**
    * Reset the placement heat map
    */
    public void resetPlacementPoints(){
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                placementPoints[i][j] = ChessConsts.CENTER_POINTS;
            }
        }
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++) {
            placementPoints[i][0] = ChessConsts.OUTER_POINTS;
            placementPoints[i][7] = ChessConsts.OUTER_POINTS;
            placementPoints[0][i] = ChessConsts.OUTER_POINTS;
            placementPoints[7][i] = ChessConsts.OUTER_POINTS;
        }
        for (int i = 1; i < ChessConsts.CENTER_POINTS-1; i++) {
            placementPoints[i][1] = ChessConsts.INNER_POINTS;
            placementPoints[i][6] = ChessConsts.INNER_POINTS;
            placementPoints[1][i] = ChessConsts.INNER_POINTS;
            placementPoints[6][i] = ChessConsts.INNER_POINTS;
        }
    }
    
    /**
    * Scores the board
    * @param b the board being scored
    * @return the integer score of the board
    */
    private int score(Board b)  {
        int out = 0;
        // Evaluating Checkmate
        if(b.ended()) {
            if(b.getKings()[ChessConsts.WHITE].isChecked(b, b.getKingTiles()[ChessConsts.WHITE])) {
                out = ChessConsts.CHECKMATE_POINTS * -1;
            }
            else if(b.getKings()[ChessConsts.BLACK].isChecked(b, b.getKingTiles()[ChessConsts.BLACK])) {
                out = ChessConsts.CHECKMATE_POINTS;
            }
        }
        // Evaluating the number of pieces on either side
        for(int i = 0; i < b.getPieces().get(ChessConsts.WHITE).size(); i++) {
            out = out + b.getPieces().get(ChessConsts.WHITE).get(i).getPiece().getPoints();
        }
        for(int i = 0; i < b.getPieces().get(ChessConsts.BLACK).size(); i++) {
            out = out - b.getPieces().get(ChessConsts.BLACK).get(i).getPiece().getPoints();
        }

        // Evaluating the position of each piece
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                if (b.getTiles()[i][j].getPiece() != null){
                    if (b.getTiles()[i][j].getPiece().getColour() == ChessConsts.WHITE) out += placementPoints[i][j];
                    else out -= placementPoints[i][j];
                }
            }
        }

        // Evaluating the attack tiles of all pieces for 
        resetAttackPoints(b.getKingTiles()[ChessConsts.BLACK].getX(), b.getKingTiles()[ChessConsts.BLACK].getY());
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                if (b.getTiles()[i][j].getPiece() != null && b.getTiles()[i][j].getPiece().getColour() == ChessConsts.WHITE){
                    for (Tile t : b.getTiles()[i][j].getPiece().range(b, b.getTiles()[i][j])){
                        out += attackPoints[t.getX()][t.getY()];
                    }
                }
            }
        }

        resetAttackPoints(b.getKingTiles()[ChessConsts.WHITE].getX(), b.getKingTiles()[ChessConsts.WHITE].getY());
        for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
            for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                if (b.getTiles()[i][j].getPiece() != null && b.getTiles()[i][j].getPiece().getColour() == ChessConsts.BLACK){
                    for (Tile t : b.getTiles()[i][j].getPiece().range(b, b.getTiles()[i][j])){
                        out -= attackPoints[t.getX()][t.getY()];
                    }
                }
            }
        }

        // If the score is negative it is in favour of black, if positive, it is in favour of white
        return out * (b.getToMove() == ChessConsts.BLACK ? -1 : 1);
    }
    
    /**
    * Searches the chess position for an optimal move recursively using a min max tree
    * Find the optimal move for black, then recurses to find an optimal move for white
    * Returns the score of the best postion after recursing some x depth
    * @param g the game
    * @param depth the depth of the search
    * @param alpha the alpha value of the search, i.e. the max
    * @param beta the beta value of the search, i.e. the min
    * @param cnt the cnt of the search
    * @return an integer value for the position
    */
    public int search(ChessGame g, int depth, int alpha, int beta, int cnt) {
        if(g.getCurrentPos().ended()) {
            return score(g.getCurrentPos());
        }
        else if(depth == 0) {
            // Return the score once the depth has reached 0
            return score(g.getCurrentPos());
        }
        else {
            int temp;
            ArrayList<Move> possibleMoves = sortMoves(g.getCurrentPos(), legalMoves(g.getCurrentPos()));
            for(int i = 0; i < possibleMoves.size(); i++) {
                String curMove = possibleMoves.get(i).move;
                g.move(curMove.substring(0, 2), curMove.substring(2, 4), curMove.substring(4, 5));
                temp = -search(g, depth - 1, -beta, -alpha, cnt+1);
                g.undo();
                if (temp >= beta){
                    return beta;
                }
                if (temp > alpha){
                    alpha = temp;
                    if (cnt == 0){
                        this.move = curMove;
                    }
                }
            }
        }
        return alpha;
    }

    /**
    * Sort an arraylist of moves based on their predicted evaluation
    * This immensely speeds up the search because branches that can be pruned will be theoretically pruned earlier
    * @param b the board
    * @param temp the arraylist of moves
    * @return the sorted arraylist of moves
    */
    public ArrayList<Move> sortMoves(Board b, ArrayList<String> temp){
        ArrayList<Move> sortedMoves = new ArrayList<>();
        for (String move : temp){
            // Evaluate the move
            int[] newPos = ChessConsts.chessToCoord(move.substring(2, 4));
            String promotion = move.substring(4, 5);
            int score = 0;

            if (b.getTiles()[newPos[0]][newPos[1]].getPiece() != null)
                score += (b.getTiles()[newPos[0]][newPos[1]].getPiece().getPoints());
            score += placementPoints[newPos[0]][newPos[1]];

            if (promotion != null){
                if (promotion.equals("Q")) score += ChessConsts.QUEEN_POINTS;
                if (promotion.equals("R")) score += ChessConsts.ROOK_POINTS;
                if (promotion.equals("B")) score += ChessConsts.BISHOP_POINTS;
                if (promotion.equals("N")) score += ChessConsts.KNIGHT_POINTS;
            }

            sortedMoves.add(new Move(move, score));
        }
        // Sort the moves
        Collections.sort(sortedMoves);
        return sortedMoves;
    }



    public String nextMove(ChessGame g){
        if(g.getStringMoves().isEmpty()) {
            isOpening = true;
        }
        else if(opening.depth() >= g.getCurrentPos().getTurn()) {
            for(int i = opening.depth(); i >= g.getCurrentPos().getTurn(); i--) {
                opening.prevMove();
            }
            if(opening.getData() != null) {
            }
            if(opening.hasNext(g.getStringMoves().peek().substring(0, 4) + " ")) {
                isOpening = true;
            }
            else {
                isOpening = false;
            }
        }
        
        if(isOpening) {
            if(g.getStringMoves().isEmpty()) { //occurs only if bot is white
                return opening.getRandomMove();
            }
            else {
                String prevMove = g.getStringMoves().peek().substring(0, 4) + " ";
                if(!opening.hasNext()) {
                    isOpening = false;
                }
                else if(!opening.hasNext(prevMove)) {
                    isOpening = false;
                }
                else {
                    opening.nextMove(prevMove);
                    if(opening.hasNext()) {
                        return opening.getRandomMove();
                    }
                    else {
                        isOpening = false;
                    }
                }
            }
        }

        this.search(g, this.depth, -999999, 999999, 0);
        return this.move;
    }
}
