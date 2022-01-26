package chesslogic;

/**
 * Class for sorting moves
 * @author Peter Gu
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */
class Move implements Comparable<Move> {

    // Class Variables
    String move;
    int score;

    /**
     * Represents a move object with a move and score
     */
    public Move(String move, int score){
        this.move = move;
        this.score = score;
    }

    /**
     * Comparable method for objects
     */
    @Override
    public int compareTo(Move a)
    {
        if (a.score > score) return 1;
        else if (a.score == score) return 0;
        else return -1;
    }
}