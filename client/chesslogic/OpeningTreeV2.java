package chesslogic;

import java.util.HashMap;
import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

import config.PathsConsts;

public class OpeningTreeV2 {
    
    Node head;
    Node current;
    Random r;
    
    public OpeningTreeV2() {
        this.head = new Node(null, null);
        this.current = this.head;
        r = new Random();
        try {
            Scanner in = new Scanner(new File(PathsConsts.OPENING_FILE_TWO));
            String curLine;
            while(in.hasNext()) {
                curLine = in.nextLine();
                if(curLine.substring(0, 1).equals("{")) {
                    this.current.push(curLine.substring(1, 6));
                    this.nextMove(curLine.substring(1, 6));
                    if(curLine.length() > 6 && curLine.substring(6, 7).equals("}")) {
                        prevMove();
                    }
                }
                else if(curLine.substring(0, 1).equals("}")) {
                    prevMove();
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public boolean nextMove(String next) {
        if(this.current.contains(next)) {
            this.current = this.current.getNext(next);
            return true;
        }
        return false;
    }
    
    public boolean hasNext() {
        return !this.current.isLeaf();
    }
    
    public boolean hasNext(String next) {
        return this.current.contains(next);
    }
    
    public boolean prevMove() {
        if(this.current != this.head) {
            this.current = this.current.getPrev();
            return true;
        }
        return false;
    }
    
    public String getData() {
        return this.current.getData();
    }
    
    public ArrayList<String> getNextMoves() {
        return this.current.getNextMoves();
    }
    
    public String getRandomMove() {
        int x = r.nextInt(this.getNextMoves().size());
        String out = getNextMoves().get(x);
        this.nextMove(out);
        return out;
    }
    
    public void reset() {
        this.current = this.head;
    }
    
    private class Node {
        
        private String data;
        private HashMap<String, Node> map;
        private ArrayList<String> nextMoves;
        private int mapSize;
        private Node prev;
        
        Node(String move, Node prev) {
            this.data = move;
            this.map = new HashMap<String, Node>();
            this.nextMoves = new ArrayList<String>();
            this.mapSize = 0;
            this.prev = prev;
        }
        
        public boolean contains(String move) {
            return map.containsKey(move);
        }
        
        public void push(String nextMove) {
            map.put(nextMove, new Node(nextMove, this));
            nextMoves.add(nextMove);
            this.mapSize++;
        }
        
        public boolean isLeaf() {
            return this.mapSize == 0;
        }
        
        public Node getPrev() {
            return this.prev;
        }
        
        public Node getNext(String move) {
            return map.get(move);
        }
        
        public String getData() {
            return this.data;
        }
        
        public ArrayList<String> getNextMoves() {
            return this.nextMoves;
        }
        
    }
    
}