package chesslogic;

import java.util.Comparator;

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