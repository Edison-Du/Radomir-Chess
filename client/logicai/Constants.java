package logicai;

/**
 * Class for constants and specific maps
 * @author Leo Guan
 * @version 1.0, Jan. 2022
 */

class Constants {
    
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    
    /**
     * takes an integer array with two elements and converts it to chess coordinates
     * @param x horizontal coordinate from 0-7
     * @param y vertical coordinate from 0-7
     * @return the chess notation corresponding to the coordinate
     * Example: coordToChess(5, 0) returns "f1"
     */
    public static final String coordToChess(int x, int y) {
        String out = "";
        int j = x + 97;
        char n = (char) j;
        out = out + n;
        out = out + (y + 1);
        return out;
    }
    
    /**
     * takes a string in chess notation and converts it to an integer array with two elements
     * @param pos position in chess notation
     * @return coordinates in the form [x, y]
     */
    public static final int[] chessToCoord(String pos) {
        int[] out = new int[2];
        char n = pos.charAt(0);
        int x = (int) n;
        x = x - 97;
        n = pos.charAt(1);
        int y = (int) n;
        y = y - 49;
        out[0] = x;
        out[1] = y;
        return out;
    }
        
}