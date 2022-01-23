package chesslogic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

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
    
    public ArrayList<String> captureMoves(Board b)  {
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
                        if(b.getTile(sTemp.substring(2, 4)).getPiece() != null 
                               || (b.getTile(sTemp.substring(2, 4)).getPiece() == null && b.getTile(sTemp.substring(0, 2)).getPiece().getName().equals("p") && b.getTiles()[b.getTile(sTemp.substring(2, 4)).getX()][b.getTile(sTemp.substring(2, 4)).getY() + 1 - 2*b.getTile(sTemp.substring(0, 2)).getPiece().getColour()].getPiece() != null && b.getTiles()[b.getTile(sTemp.substring(2, 4)).getX()][b.getTile(sTemp.substring(2, 4)).getY() + 1 - 2*b.getTile(sTemp.substring(0, 2)).getPiece().getColour()].getPiece().getName().equals("p"))) {
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
        }
        return output;
    }
    
    public abstract String nextMove(ChessGame g) ;
    
}