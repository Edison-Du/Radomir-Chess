package chesslogic;

/**
 * [Move.java]
 * encapsulated class for a move and an approximation of how good it is
 * @author Peter Gu
 * @version 1.0 Jan 24, 2022
 */

class Move implements Comparable<Move> {

    // Class Variables
    String move;
    int score;

    /**
     * Intialize a move object
     */
    public Move(String move, int score){
        this.move = move;
        this.score = score;
    }

    /**
     * Compares two objects
     * @param int the integer value of the comparison
     */
    @Override
    public int compareTo(Move a)
    {
        if (a.score > score) return 1;
        else if (a.score == score) return 0;
        else return -1;
    }
}