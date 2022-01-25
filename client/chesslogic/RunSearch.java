package chesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.InterruptedException;

/**
 * [RunSearch.java]
 * Search involved with Radomir Bot
 * @author Peter Gu
 * @author Leo Guan
 * @version 1.0 Jan 24, 2022
 * Each thread added increases the speed marginally. Tested with intel i3-8130U, 12 GB RAM (not sure what type, too lazy to find out)
 */

private class RunSearch implements Runnable {

    // Class Variables
    
    private ChessGame game;
    private int depth;
    private ArrayList<String> check;
    
    private String[] myMoves;
    private int[] myScores;
    private int mySection;
    
    private int[][] attackPoints;
    private int[][] placementPoints;
    private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;

    /**
     * Initializes a thread to search
     */
    
    RunSearch(ChessGame g, int depth, ArrayList<String> toCheck, String[] myMoves, int[] myScores, int mySection, int[] directionXOne, int[] directionYOne, int[] directionXTwo, int[] directionYTwo) {
        this.depth = depth;
        this.game = g;
        this.check = toCheck;
        this.myMoves = myMoves;
        this.myScores = myScores;
        this.mySection = mySection;
        placementPoints = new int[8][8];
        attackPoints = new int[8][8];
        this.directionXOne = directionXOne;
        this.directionYOne = directionYOne;
        this.directionXTwo = directionXTwo;
        this.directionYTwo = directionYTwo;
        resetPlacementPoints();
    }
    
    /**
     * Checks a x position and a y position
     * @param x a integer x
     * @param y a integer y
     * @return a boolean value
     */
    public boolean check(int x, int y){
        if (x >= 0 && x < 8 && y >= 0 && y < 8) return true;
        return false;
    }
    
    /**
     * Resets the attack heat map based on the position of enemy king
     * @param x an integer x
     * @param y an integer y
     */
    public void resetAttackPoints(int x, int y){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                attackPoints[i][j] = 3;
            }
        }
        for (int i = 0; i < 8; i++) {
            attackPoints[i][0] = 1;
            attackPoints[i][7] = 1;
            attackPoints[0][i] = 1;
            attackPoints[7][i] = 1;
        }
        for (int i = 1; i < 7; i++) {
            attackPoints[i][1] = 2;
            attackPoints[i][6] = 2;
            attackPoints[1][i] = 2;
            attackPoints[6][i] = 2;
        }
        attackPoints[x][y] += 4;
        for (int i = 0; i < 16; i++){
            if (check(x + directionXTwo[i], y + directionYTwo[i])){
                attackPoints[x + directionXTwo[i]][y + directionYTwo[i]] += 2;
            }
        }
        for (int i = 0; i < 8; i++){
            if (check(x + directionXOne[i], y + directionYOne[i])){
                attackPoints[x + directionXOne[i]][y + directionYOne[i]] += 1;
            }
        }
    }
    
    /**
     * Reset the heat map
     */
    public void resetPlacementPoints(){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                placementPoints[i][j] = 3;
            }
        }
        for (int i = 0; i < 8; i++) {
            placementPoints[i][0] = 1;
            placementPoints[i][7] = 1;
            placementPoints[0][i] = 1;
            placementPoints[7][i] = 1;
        }
        for (int i = 1; i < 7; i++) {
            placementPoints[i][1] = 2;
            placementPoints[i][6] = 2;
            placementPoints[1][i] = 2;
            placementPoints[6][i] = 2;
        }
    }
    
    /**
     * Scores the board
     * @param b the board being scored
     * @return the integer score of the board
     */
    private int score(Board b)  {
        int out = 0;
        if(b.ended()) {
            if(b.getKings()[Constants.WHITE].isChecked(b, b.getKingTiles()[Constants.WHITE])) {
                out = -1000;
            }
            else if(b.getKings()[Constants.BLACK].isChecked(b, b.getKingTiles()[Constants.BLACK])) {
                out = 1000;
            }
            else out = 0;
        }
        for(int i = 0; i < b.getPieces().get(0).size(); i++) {
            out = out + b.getPieces().get(0).get(i).getPiece().getPoints();
        }
        for(int i = 0; i < b.getPieces().get(1).size(); i++) {
            out = out - b.getPieces().get(1).get(i).getPiece().getPoints();
        }
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (b.getTiles()[i][j].getPiece() != null){
                    if (b.getTiles()[i][j].getPiece().getColour() == 0) out += placementPoints[i][j];
                    else out -= placementPoints[i][j];
                }
            }
        }
        resetAttackPoints(b.getKingTiles()[1].getX(), b.getKingTiles()[1].getY());
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (b.getTiles()[i][j].getPiece() != null && b.getTiles()[i][j].getPiece().getColour() == 0){
                    for (Tile t : b.getTiles()[i][j].getPiece().range(b, b.getTiles()[i][j])){
                        out += attackPoints[t.getX()][t.getY()];
                    }
                }
            }
        }
        resetAttackPoints(b.getKingTiles()[0].getX(), b.getKingTiles()[0].getY());
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (b.getTiles()[i][j].getPiece() != null && b.getTiles()[i][j].getPiece().getColour() == 1){
                    for (Tile t : b.getTiles()[i][j].getPiece().range(b, b.getTiles()[i][j])){
                        out -= attackPoints[t.getX()][t.getY()];
                    }
                }
            }
        }
        return out * (b.getToMove() == 1 ? -1 : 1);
    }
    
    /**
     * Searches the chess position for an optimal move recursively using a min max tree
     * Find the optimal move for side x, then recurses to find an optimal move for side y
     * Returns the score of the best postion after recursing some z depth
     * @param g the game
     * @param depth the depth of the search
     * @param alpha the alpha value of the search, i.e. the max
     * @param beta the beta value of the search, i.e. the min
     * @param cnt the cnt of the search
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
            ArrayList<Move> possibleMoves; // All possible moves in the position
            if(cnt == 0) {
                possibleMoves = sortMoves(game.getCurrentPos(), check);
            }
            else {
                possibleMoves = sortMoves(g.getCurrentPos(), legalMoves(g.getCurrentPos()));
            }
            for(int i = 0; i < possibleMoves.size(); i++) {
                String curMove = possibleMoves.get(i).move;
                g.move(curMove.substring(0, 2), curMove.substring(2, 4), curMove.substring(4, 5));
                // The idea 
                temp = -search(g, depth - 1, -beta, -alpha, cnt+1);
                g.undo();
                if (temp >= beta){
                    return beta;
                }
                if (temp > alpha){
                    alpha = temp;
                    if (cnt == 0){
                        myMoves[mySection] = curMove;
                        myScores[mySection] = alpha;
                    }
                }
            }
        }
        return alpha;
    }
    
    public ArrayList<Move> sortMoves(Board b, ArrayList<String> temp){
        ArrayList<Move> sortedMoves = new ArrayList<>();
        for (String move : temp){
            int[] newPos = Constants.chessToCoord(move.substring(2, 4));
            String promotion = move.substring(4, 5);
            int score = 0;
            if (b.getTiles()[newPos[0]][newPos[1]].getPiece() != null)
                score += (b.getTiles()[newPos[0]][newPos[1]].getPiece().getPoints());
            score += placementPoints[newPos[0]][newPos[1]];
            if (promotion != null){
                if (promotion.equals("Q")) score += Constants.QUEEN_POINTS;
                if (promotion.equals("R")) score += Constants.ROOK_POINTS;
                if (promotion.equals("B")) score += Constants.BISHOP_POINTS;
                if (promotion.equals("N")) score += Constants.KNIGHT_POINTS;
            }
            sortedMoves.add(new Move(move, score));
        }
        Collections.sort(sortedMoves);
        return sortedMoves;
    }
    
    @Override
    public void run() {
        search(game, this.depth, -99999, 99999, 0);
    }
    
}