package chesslogic;

import java.util.ArrayList;
import java.util.Collections;
import java.lang.InterruptedException;

/**
 * [RadomirBot.java]
 * Multithreaded Chess engine
 * Chess engine uses Depth first search on a min-max tree to calculate the next best move
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
    
    private int depth;
    private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;
    private RunSearch[] runnables;
    private Thread[] threads;
    private String[] myMoves;
    private int[] myScores;
    private int numThreads;
    
    private OpeningTreeV2 opening;
    private boolean isOpening;
    
    /**
     * Initializes a chess engine with a depth and number of threads
     */
    public RadomirBot(int depth, int threads) {
        opening = new OpeningTreeV2();
        isOpening = true;
        this.depth = depth;

        // Direction arrays for heatmaps
        directionXOne = new int[] {-1, -1, 1, 1, 0, 0, -1, 1};
        directionYOne = new int[] {1, -1, -1, 1, -1, 1, 0, 0};
        directionXTwo = new int[] {-2, 2, -2, 2, 0, 0, -2, 2, -1, 1, -2, 2, -1, 1, -2, 2};
        directionYTwo = new int[] {0, 0, -2, 2, -2, 2, 2, -2, 2, 2, -1, -1, -2, -2, 1, 1};

        // Threads
        runnables = new RunSearch[threads];
        this.threads = new Thread[threads];
        myMoves = new String[threads];
        myScores = new int[threads];
        this.numThreads = threads;
    }
    
    /**
     * Gets the depth of the engine
     * @param int the depth
     */
    public int getDepth(){
        return this.depth;
    }
    
    /**
     * Returns the next move from the engine
     * @param g a current chessGame
     * @return a string that represents the next move
     */
    public String nextMove(ChessGame g) {
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
        
        // Handling threads
        ArrayList<String> moves = legalMoves(g.getCurrentPos());
        ArrayList<ArrayList<String>> partition = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < this.numThreads && i < moves.size(); i++) {
            partition.add(new ArrayList<String>());
        }
        for(int i = 0; i < moves.size(); i++) {
            partition.get(i%numThreads).add(moves.get(i));
        }
        for(int i = 0; i < numThreads && i < moves.size(); i++) {
            runnables[i] = new RunSearch(this, g.copy(), this.depth, partition.get(i), this.myMoves, this.myScores, i, this.directionXOne, this.directionYOne, this.directionXTwo, this.directionYTwo);
            threads[i] = new Thread(runnables[i]);
            threads[i].start();
        }
        for(int i = 0; i < numThreads && i < moves.size(); i++) {
            try {
                threads[i].join();
            } catch(InterruptedException e) {e.printStackTrace(); }
        }
        int index = 0;
        int temp = myScores[0];
        for(int i = 1; i < numThreads && i < moves.size(); i++) {
            if(myScores[i] > temp) {
                temp = myScores[i];
                index = i;
            }
        }
        return myMoves[index];
    }

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
}