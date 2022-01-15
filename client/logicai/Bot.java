package logicai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

abstract class Bot {
    
    public ArrayList<String> legalMoves(Board b) throws IOException {
        ArrayList<String> output = new ArrayList<String>();
        HashSet<String> tempSet;
        Iterator<String> tempIt;
        Tile tempTile;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                tempTile = b.getTiles()[i][j];
                tempSet = b.legalMoves(tempTile);
                if(tempSet != null) {
                    tempIt = tempSet.iterator();
                    while(tempIt.hasNext()) {
                        output.add(tempTile.toString() + tempIt.next());
                    }
                }
            }
        }
        return output;
    }
    
    abstract String nextMove(Board b) throws IOException;
    
    abstract String promotePawn(Board b);
    
}