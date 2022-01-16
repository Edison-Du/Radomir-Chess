package logicai;

import java.util.ArrayList;
import java.io.IOException;

class DepthSearchBotP1 extends Bot {
    
    private int depth;
    private int side;
    private int countOperations;
    
    public DepthSearchBotP1(int depth, int side) throws IOException {
        this.depth = depth;
        this.side = side;
        this.countOperations = 0;
    }
    
    private int score(Board b) throws IOException {
        if(b.ended()) {
            if(b.getKings()[Constants.WHITE].isChecked(b, b.getKingTiles()[Constants.WHITE])) {
                return -60;
            }
            else if(b.getKings()[Constants.BLACK].isChecked(b, b.getKingTiles()[Constants.BLACK])) {
                return 60;
            }
            else {
                return -1;
            }
        }
        else {
            int out = 0;
            for(int i = 0; i < 64; i++) {
                if(b.getTiles()[i/8][i%8].getPiece() != null) {
                    out += b.getTiles()[i/8][i%8].getPiece().getPoints()*(1 - 2*this.side);
                }
            }
            return out;
        }
    }
    
    @Override
    public String nextMove(ChessGame g) throws IOException {
        ArrayList<String> possibleMoves = legalMoves(g.getCurrentPos());
        int[] bestScore;
        int[] temp;
        String bestMove;
        bestMove = possibleMoves.get(0);
        g.move(possibleMoves.get(0).substring(0, 2), possibleMoves.get(0).substring(2, 4), possibleMoves.get(0).substring(4, 5));
        bestScore = average(g);
        g.undo();
        for(int i = 1; i < possibleMoves.size(); i++) {
            g.move(possibleMoves.get(0).substring(0, 2), possibleMoves.get(0).substring(2, 4), possibleMoves.get(i).substring(4, 5));
            temp = average(g);
            if(temp[0]*bestScore[1] > temp[1]*bestScore[0]) {
                bestScore = temp;
                bestMove = possibleMoves.get(i);
            }
        }
        System.out.println("Called average " + this.countOperations + " times");
        this.countOperations = 0;
        return bestMove;
    }
    
    private int[] average(ChessGame g, int tail) throws IOException {
        this.countOperations++;
        if(tail == 0) {
            int[] out = new int[2];
            out[0] = score(g.getCurrentPos());
            out[1] = 1;
            return out;
        }
        else if(g.getCurrentPos().ended()) {
            int[] out = new int[2];
            out[0] = score(g.getCurrentPos());
            out[1] = 1;
            return out;
        }
        else {
            ArrayList<String> possibleMoves = legalMoves(g.getCurrentPos());
            int curScore = 0;
            int leafCount = 0;
            int[] temp;
            int[] out;
            for(int i = 0; i < possibleMoves.size(); i++) {
                g.move(possibleMoves.get(i).substring(0, 2), possibleMoves.get(i).substring(2, 4), possibleMoves.get(i).substring(4, 5));
                temp = average(g, tail - 1);
                curScore += temp[0];
                leafCount += temp[1];
                g.undo();
            }
            out = new int[2];
            out[0] = curScore;
            out[1] = leafCount;
            return out;
        }
    }
    
    private int[] average(ChessGame g) throws IOException {
        return average(g, depth);
    }
    
}