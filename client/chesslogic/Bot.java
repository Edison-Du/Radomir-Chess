package chesslogic;

/**
 * Class for a bot
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public abstract class Bot {
    
    /**
     * return all legal moves of a board
     * @param b the board
     * @return all legal moves
     */
    public ArrayList<String> legalMoves(Board b)  {
        ArrayList<String> output = new ArrayList<String>();
        HashSet<String> tempSet;
        Iterator<String> tempIt;
        String sTemp;
        Tile tempTile;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                tempTile = b.getTiles()[i][j];
                tempSet = b.legalMoves(tempTile);
                if(tempSet != null) {
                    tempIt = tempSet.iterator();
                    while(tempIt.hasNext()) {
                        sTemp = tempIt.next();
                        if(b.promotingMove(tempTile.toString(), sTemp)) {
                            output.add(tempTile.toString() + sTemp + "Q");
                            output.add(tempTile.toString() + sTemp + "R");
                            output.add(tempTile.toString() + sTemp + "B");
                            output.add(tempTile.toString() + sTemp + "N");
                        }
                        else {
                            output.add(tempTile.toString() + sTemp + " ");
                        }
                    }
                }
            }
        }
        return output;
    }
    
    /**
     * get the next move of this bot
     * @param g the game
     * @return the next move (e.g. e2ee)
     */
    public abstract String nextMove(ChessGame g);
}