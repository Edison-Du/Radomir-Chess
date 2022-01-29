package chesslogic;

/**
 * [RadomirBot.java]
 * Multithreaded Chess engine
 * Chess engine uses depth first search on a min-max tree to calculate the next best move
 * Evaluation consists of heat maps, counting pieces and considering offensive positions
 * Search is optimized by alpha beta pruning as well as sorting moves based on initial evaluation
 * Time complexity is predicted to be O(b^(d/2)) where b is the number of branches and d is the depth
 * The search is also divided into some x amount of threads to run branches asynchronously
 * @author Peter Gu
 * @author Leo Guan
 * @version 1.0 Jan 24, 2022
 * Each thread added increases the speed marginally. Tested with intel i3-8130U, 12 GB RAM
 */

import java.util.ArrayList;
import java.util.Collections;
import java.lang.InterruptedException;
import java.time.Instant;
import java.time.Duration;

public class RadomirBot extends Bot {
    
    // Class Variables
    private int depth;
    private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;
    private RunSearch[] runnables;
    private Thread[] threads;
    private String[] myMoves;
    private int[] myScores;
    private int numThreads;
    
    private OpeningTree opening;
    private boolean isOpening;

    /**
     * Initializes the chess engine 
     * @param int the depth of the engine
     * @param int the number of threads
     */
    public RadomirBot(int depth, int threads) {
        opening = new OpeningTree();
        isOpening = true;
        this.depth = depth;
        directionXOne = new int[] {-1, -1, 1, 1, 0, 0, -1, 1};
        directionYOne = new int[] {1, -1, -1, 1, -1, 1, 0, 0};
        directionXTwo = new int[] {-2, 2, -2, 2, 0, 0, -2, 2, -1, 1, -2, 2, -1, 1, -2, 2};
        directionYTwo = new int[] {0, 0, -2, 2, -2, 2, 2, -2, 2, 2, -1, -1, -2, -2, 1, 1};
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
     * Returns the next move for the bot
     * @param g the current game
     * @return the string move
     */
    public String nextMove(ChessGame g) {
        if(isOpening) {
            if(g.getStringMoves().isEmpty()) { //occurs only if bot is white
                return opening.getRandomMove();
            }
            else {
                String prevMove = g.getStringMoves().peek().substring(0, 4) + " ";
                if(!opening.hasNext()) {
                    isOpening = false;
                    opening.reset();
                }
                else if(!opening.hasNext(prevMove)) {
                    isOpening = false;
                    opening.reset();
                }
                else {
                    opening.nextMove(prevMove);
                    if(opening.hasNext()) {
                        return opening.getRandomMove();
                    }
                    else {
                        isOpening = false;
                        opening.reset();
                    }
                }
            }
        }
        
        ArrayList<String> moves = legalMoves(g.getCurrentPos());
        ArrayList<ArrayList<String>> partition = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < this.numThreads && i < moves.size(); i++) {
            partition.add(new ArrayList<String>());
        }
        for(int i = 0; i < moves.size(); i++) {
            partition.get(i%numThreads).add(moves.get(i));
        }

        for(int i = 0; i < numThreads && i < moves.size(); i++) {
            runnables[i] = new RunSearch(g.copy(), this.depth, partition.get(i), this.myMoves, this.myScores, i, this.directionXOne, this.directionYOne, this.directionXTwo, this.directionYTwo);
            
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
    
    // All calculations are done in separate threads
    private class RunSearch implements Runnable {

        final long maxSearch = 4000;

        // Class variables
        private ChessGame game;
        private int depth;
        private ArrayList<String> check;
        
        private String[] myMoves;
        private int[] myScores;
        private int mySection;

        private boolean searchEnd;
        private Instant startTime;

        private int curScore;
        private String ourMove;
        
        private int[][] attackPoints;
        private int[][] placementPoints;
        private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;

        /**
         * Intializes a thread for searching the tree
         */
        RunSearch(ChessGame g, int depth, ArrayList<String> toCheck, String[] myMoves, int[] myScores, int mySection, int[] directionXOne, int[] directionYOne, int[] directionXTwo, int[] directionYTwo) {
            this.depth = depth;
            this.game = g;
            this.check = toCheck;
            this.myMoves = myMoves;
            this.myScores = myScores;
            this.mySection = mySection;
            placementPoints = new int[ChessConsts.BOARD_LENGTH][ChessConsts.BOARD_LENGTH];
            attackPoints = new int[ChessConsts.BOARD_LENGTH][ChessConsts.BOARD_LENGTH];
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
            for (int i = 1; i < ChessConsts.BOARD_LENGTH-1; i++) {
                attackPoints[i][1] = ChessConsts.INNER_POINTS;
                attackPoints[i][6] = ChessConsts.INNER_POINTS;
                attackPoints[1][i] = ChessConsts.INNER_POINTS;
                attackPoints[6][i] = ChessConsts.INNER_POINTS;
            }
            attackPoints[x][y] += ChessConsts.CENTER_POINTS;
            for (int i = 0; i < ChessConsts.BOARD_LENGTH*2; i++){
                if (check(x + directionXTwo[i], y + directionYTwo[i])){
                    attackPoints[x + directionXTwo[i]][y + directionYTwo[i]] += ChessConsts.INNER_POINTS;
                }
            }
            for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
                if (check(x + directionXOne[i], y + directionYOne[i])){
                    attackPoints[x + directionXOne[i]][y + directionYOne[i]] += ChessConsts.OUTER_POINTS;
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
            for (int i = 1; i < ChessConsts.BOARD_LENGTH-1; i++) {
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
        private int score(Board b, boolean onlyCaptured)  {
            int out = 0;
            // Evaluating Checkmate
            if(b.ended()) {
                if(b.getKings()[ChessConsts.WHITE].isChecked(b, b.getKingTiles()[ChessConsts.WHITE])) {
                    out += ChessConsts.CHECKMATE_POINTS * -1;
                }
                else if(b.getKings()[ChessConsts.BLACK].isChecked(b, b.getKingTiles()[ChessConsts.BLACK])) {
                    out += ChessConsts.CHECKMATE_POINTS;
                }
            }

            // Evaluating the number of pieces on either side
            for(int i = 0; i < b.getPieces().get(ChessConsts.WHITE).size(); i++) {
                if (b.getPieces().get(ChessConsts.WHITE).size() < 6 && 
                b.getPieces().get(ChessConsts.WHITE).get(i).getPiece() instanceof Pawn ||
                b.getPieces().get(ChessConsts.WHITE).get(i).getPiece() instanceof Bishop){
                    out += b.getPieces().get(ChessConsts.WHITE).get(i).getPiece().adjustPoints();
                }
                else out += b.getPieces().get(ChessConsts.WHITE).get(i).getPiece().getPoints();
            }
            for(int i = 0; i < b.getPieces().get(ChessConsts.BLACK).size(); i++) {
                if (b.getPieces().get(ChessConsts.BLACK).size() < 6 && 
                b.getPieces().get(ChessConsts.BLACK).get(i).getPiece() instanceof Pawn ||
                b.getPieces().get(ChessConsts.BLACK).get(i).getPiece() instanceof Bishop){
                    out -= b.getPieces().get(ChessConsts.BLACK).get(i).getPiece().adjustPoints();
                }
                else out -= b.getPieces().get(ChessConsts.BLACK).get(i).getPiece().getPoints();
            }

            if (!onlyCaptured){
                // Evaluating the position of each piece
                for (int i = 0; i < ChessConsts.BOARD_LENGTH; i++){
                    for (int j = 0; j < ChessConsts.BOARD_LENGTH; j++){
                        if (b.getTiles()[i][j].getPiece() != null){
                            if (b.getTiles()[i][j].getPiece().getColour() == ChessConsts.WHITE) out += placementPoints[i][j];
                            else out -= placementPoints[i][j];
                        }
                    }
                }

                // Evaluating the attack tiles of all pieces for white
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

                // Evaluating the attack tiles of all pieces for black
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
            }

            // If the score is negative it is in favour of black, if positive, it is in favour of white
            return out * (b.getToMove() == ChessConsts.BLACK ? -1 : 1);
        }

        private boolean checkTime(){
            Instant here = Instant.now();
            Duration timeElapsed = Duration.between(this.startTime, here);
            return timeElapsed.toMillis() > maxSearch;
        }
        
        /**
        * Searches the chess position for an optimal move recursively using a min max tree
        * Find the optimal move for black, then recurses to find an optimal move for white
        * Returns the score of the best postion after recursing some x depth
        * @param g the game
        * @param depth the depth of the search
        * @param alpha the alpha value of the search, i.e. the max
        * @param beta the beta value of the search, i.e. the min
        * @param cnt how deep the search has traversed
        * @return an integer value for the position
        */
        public int search(ChessGame g, int depth, int alpha, int beta, int cnt) {
            if (checkTime() && !searchEnd) this.searchEnd = true;
            if (searchEnd) return 0;
            if(g.getCurrentPos().ended()) {
                // Return the score if the position has no moves
                return score(g.getCurrentPos(), false);
            }
            else if(depth == 0) {
                // Return the score once the depth has reached 0
                return QuiescenceSearch(g, alpha, beta);
            }
            else {
                int temp;
                // Arraylist of possible moves 
                ArrayList<Move> possibleMoves;
                if(cnt == 0) {
                    possibleMoves = sortMoves(game.getCurrentPos(), check); // If inital branch, proceed with earlier moves 
                }
                else {
                    possibleMoves = sortMoves(g.getCurrentPos(), legalMoves(g.getCurrentPos()));
                }
                for(int i = 0; i < possibleMoves.size(); i++) {
                    String curMove = possibleMoves.get(i).move;
                    // Make a move on the board then undo it

                    g.move(curMove.substring(0, 2), curMove.substring(2, 4), curMove.substring(4, 5));
                    temp = -search(g, depth - 1, -beta, -alpha, cnt+1);

                    g.undo();
                    // If our value is larger than beta, we have achieved a best possible position for the opposition
                    // Here we can snip the rest of the branch because we are sure now that we will not make the parent move
                    if (temp >= beta){
                        return beta;
                    }

                    // If our value is larger than alpha, we have achieved a best possible position for ourselves
                    if (temp > alpha){
                        alpha = temp;
                        if (cnt == 0){
                            // Storing the values in the respective threads
                            this.ourMove = curMove;
                            this.curScore = alpha;
                        }
                    }
                }
            }
            return alpha;
        }

        /**
         * Searches the position for captures until there are none left
         * This helps the bot reduce the chances of making bad moves on low depth
         * @return an integer value of the position
         */
        int QuiescenceSearch (ChessGame g, int alpha, int beta) {
			int eval = score(g.getCurrentPos(), true);
			if (eval >= beta) {
				return beta;
			}
			if (eval > alpha) {
				alpha = eval;
			}
			ArrayList<Move> possibleMoves = sortMoves(g.getCurrentPos(), legalMoves(g.getCurrentPos()));
			for (int i = 0; i < possibleMoves.size(); i++) {
                String curMove = possibleMoves.get(i).move;

                g.move(curMove.substring(0, 2), curMove.substring(2, 4), curMove.substring(4, 5));

                eval = -QuiescenceSearch(g, -beta, -alpha);

                g.undo();

				if (eval >= beta) {
					return beta;
				}
				if (eval > alpha) {
					alpha = eval;
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
                    if (promotion.equals(ChessConsts.QUEEN)) score += ChessConsts.QUEEN_POINTS;
                    if (promotion.equals(ChessConsts.ROOK)) score += ChessConsts.ROOK_POINTS;
                    if (promotion.equals(ChessConsts.BISHOP)) score += ChessConsts.BISHOP_POINTS;
                    if (promotion.equals(ChessConsts.KNIGHT)) score += ChessConsts.KNIGHT_POINTS;
                }

                sortedMoves.add(new Move(move, score));
            }
            Collections.sort(sortedMoves); // Sort the moves
            return sortedMoves;
        }
        
        /**
         * Runs the thread
         */
        @Override
        public void run() {
            for (int i = 1; i <= this.depth; i++){
                this.searchEnd = false;
                this.startTime = Instant.now();
                search(game, i, -99999, 99999, 0);
                if (this.searchEnd) break;
                this.myMoves[mySection] = this.ourMove;
                this.myScores[mySection] = this.curScore;
            }
        }
    }   
}