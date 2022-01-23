/*
 * idea: check each branch
 * create a queue of moves & feed the moves into different threads, use alpha-beta pruning on each branch
 */

//class ThreadBotP1 {//extends Bot {
    /*private int depth;
    private int side;
    private int countUndos;
    private int countMoves;

    private int[][] attackPoints;
    private int[][] placementPoints;
    private int[] directionXOne, directionYOne, directionXTwo, directionYTwo;
    private String move;
    
    private int threads;

    
    public ThreadBotP1(int depth, int side, int threads) {
        this.depth = depth;
        this.side = side;
        placementPoints = new int[8][8];
        attackPoints = new int[8][8];
        directionXOne = new int[] {-1, -1, 1, 1, 0, 0, -1, 1};
        directionYOne = new int[] {1, -1, -1, 1, -1, 1, 0, 0};
        directionXTwo = new int[] {-2, 2, -2, 2, 0, 0, -2, 2, -1, 1, -2, 2, -1, 1, -2, 2};
        directionYTwo = new int[] {0, 0, -2, 2, -2, 2, 2, -2, 2, 2, -1, -1, -2, -2, 1, 1};
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
            int temp;
            ArrayList<Move> possibleMoves = sortMoves(g.getCurrentPos(), legalMoves(g.getCurrentPos()));
            for(int i = 0; i < possibleMoves.size(); i++) {
                String curMove = possibleMoves.get(i).move;
                g.move(curMove.substring(0, 2), curMove.substring(2, 4), curMove.substring(4, 5));
                temp = -search(g, depth - 1, -beta, -alpha, cnt+1);
                g.undo();
                if (temp >= beta){
                    return beta;
                }
                if (temp > alpha){
                    alpha = temp;
                    if (cnt == 0){
                        this.move = curMove;
                    }
                }
            }
        }
        return alpha;
    }

    public ArrayList<Move> sortMoves(Board b, ArrayList<String> temp){
        ArrayList<Move> sortedMoves = new ArrayList<>();
        for (String move : temp){
            int[] curPos = Constants.chessToCoord(move.substring(0, 2));
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

    public String nextMove(ChessGame g){
        this.search(g, this.depth, -999999, 999999, 0);
        return this.move;
    }
    
    private class SearchThread extends Thread {
        
    }
    */
    
//}