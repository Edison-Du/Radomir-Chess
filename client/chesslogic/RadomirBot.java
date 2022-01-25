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
}