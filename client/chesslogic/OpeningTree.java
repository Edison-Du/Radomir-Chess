package chesslogic;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;
import java.util.Scanner;

import config.PathConsts;

import java.util.Random;

/**
 * [OpeningTree.java]
 * 
 * @author Leo Guan
 * @author Peter Gu
 * @version 1.0 Jan 24, 2022
 */
public class OpeningTree {

    HashMap<String, ArrayList<String>> tree;
    File input;
    
    public OpeningTree(){
        tree = new HashMap<>();
        try{ 
            input = new File(PathConsts.OPENING_FILE);
            Scanner in = new Scanner(input);
            while (in.hasNextLine()){
                String line = in.nextLine();
                String key = line.substring(0, line.indexOf(" "));
                tree.put(key, new ArrayList<>());
                line = line.substring(line.indexOf(" ")+1);
                while (!line.equals("")){
                    String value = line.substring(0, line.indexOf(" "));
                    tree.get(key).add(value);
                    line = line.substring(line.indexOf(" ")+1);
                }
            }
            in.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean checkMove(String move){
        return this.tree.containsKey(move);
    }

    public String getMove(String move){
        Random r = new Random();
        int num = r.nextInt(this.tree.get(move).size());
        return this.tree.get(move).get(num);
    }
    
}