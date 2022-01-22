package chesslogic;

import java.util.ArrayList;

public class DepthSearchBotP2 extends Bot {
    
    private int depth;
    private int side;
    private int countUndos;
    private int countMoves;

    private int[][] attackPoints;
    private int[][] placementPoints;
    private int[] directionX;
    private int[] directionY;
    private String move;

    
    public DepthSearchBotP2(int depth, int side) {
        this.depth = depth;
        this.side = side;
        placementPoints = new int[8][8];
        attackPoints = new int[8][8];
        directionX = new int[] {-2, 2, -2, 2, 0, 0, -2, 2, -1, 1, -2, 2, -1, 1, -2, 2};
        directionY = new int[] {0, 0, -2, 2, -2, 2, 2, -2, 2, 2, -1, -1, -2, -2, 1, 1};
        resetPlacementPoints();
    }

    public int getDepth(){
        return this.depth;
    }

    public boolean check(int x, int y){
        if (x >= 0 && x < 8 && y >= 0 && y < 8) return true;
        return false;
    }

    public void resetAttackPoints(int x, int y){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                attackPoints[i][j] = 0;
            }
        }
        for (int i = 0; i < 16; i++){
            if (check(x + directionX[i], y + directionY[i])){
                attackPoints[x + directionX[i]][y + directionY[i]] += 2;
            }
        }
    }

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
    
    public int search(ChessGame g, int depth, int alpha, int beta, int cnt) {
        if(g.getCurrentPos().ended()) {
            return score(g.getCurrentPos());
        }
        else if(depth == 0) {
            return score(g.getCurrentPos());
        }
        else {
            // int out = -99999;
            int temp;
            ArrayList<String> possibleMoves = legalMoves(g.getCurrentPos());
            for(int i = 0; i < possibleMoves.size(); i++) {
                g.move(possibleMoves.get(i).substring(0, 2), possibleMoves.get(i).substring(2, 4), possibleMoves.get(i).substring(4, 5));
                temp = -search(g, depth - 1, -beta, -alpha, cnt+1);
                // if (temp > 100) System.out.println("move: " + possibleMoves.get(i) + " score: " + temp);
                g.undo();
                // if(temp*(1 - 2*this.side) > out*(1 - 2*this.side) && g.getCurrentPos().getToMove() == this.side) {
                //     out = temp;
                // }
                // else if(temp*(1 - 2*this.side) < out*(1 - 2*this.side) && g.getCurrentPos().getToMove() != this.side) {
                //     out = temp;
                // }
                if (temp >= beta){
                    return beta;
                }
                if (temp > alpha){
                    alpha = temp;
                    if (cnt == 0){
                        this.move = possibleMoves.get(i);
                    }
                }
            }
        }
        return alpha;
    }

    public String getMove(){
        return this.move;
    }
    
    public String nextMove(ChessGame g) {
        return "hey how you doing baby";
        // countMoves = 0;
        // countUndos = 0;
        // ArrayList<String> possibleMoves = legalMoves(g.getCurrentPos());
        // int curScore;
        // String out;
        // int temp;
        // if(possibleMoves.size() > 0) {
        //     out = possibleMoves.get(0);
        //     g.move(out.substring(0, 2), out.substring(2, 4), out.substring(4, 5));
        //     curScore = search(g, this.depth, 0, 0);
        //     g.undo();
        //     for(int i = 1; i < possibleMoves.size(); i++) {
        //         g.move(possibleMoves.get(i).substring(0, 2), possibleMoves.get(i).substring(2, 4), possibleMoves.get(i).substring(4, 5));
        //         temp = search(g, this.depth, -99999, 99999);
        //         if (temp > curScore){
        //             curScore = temp;
        //             out = possibleMoves.get(i);
        //         }
        //         // if(temp*(1 - 2*this.side) > curScore*(1 - 2*this.side)) {
        //         //     System.out.println(possibleMoves.get(i) + " is a better move than " + out + " with a score of " + temp + ", compared to " + curScore);
        //         //     out = possibleMoves.get(i);
        //         //     curScore = temp;
        //         // }
        //         g.undo();
        //     }
        //     System.out.println(curScore);
        //     return out;
        // }
        // return null;
    }
    
}