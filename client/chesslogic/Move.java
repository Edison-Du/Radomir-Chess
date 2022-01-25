package chesslogic;

/**
 * [Move.java]
 * encapsulated class for a move and an approximation of how good it is
 * @author 
 * @version 1.0 Jan 24, 2022
 */

class Move implements Comparable<Move> {

    String move;
    int score;

    public Move(String move, int score){
        this.move = move;
        this.score = score;
    }

    // Sort
    public int compareTo(Move a)
    {
        if (a.score > score) return 1;
        else if (a.score == score) return 0;
        else return -1;
    }
}