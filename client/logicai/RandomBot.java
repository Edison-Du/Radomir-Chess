package logicai;

import java.util.ArrayList;
import java.util.Random;

public class RandomBot extends Bot {
    
    @Override
    public String nextMove(ChessGame g)  {
        ArrayList<String> temp = this.legalMoves(g.getCurrentPos());
        Random r = new Random();
        int num;
        num = r.nextInt(temp.size());
        return temp.get(num);
    }
    
    public String promotePawn(ChessGame g) {
        Random r = new Random();
        int num = r.nextInt(4);
        if(num == 0) {
            return "Q";
        }
        else if(num == 1) {
            return "R";
        }
        else if(num == 2) {
            return "B";
        }
        return "N";
    }
    
}