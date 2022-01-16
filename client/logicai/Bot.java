package logicai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

public abstract class Bot {
    
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
    
    public abstract String nextMove(ChessGame g) ;
    
}