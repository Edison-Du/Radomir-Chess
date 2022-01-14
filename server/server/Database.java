package server;

import java.util.*;

import config.PathConsts;

import java.io.*;



public class Database {
    private HashMap<String, String> storage = new HashMap<String, String>();
    private File data;
    
    public Database() {
        try {
            data = new File(PathConsts.USERS);
            Scanner in = new Scanner(data);
            while (in.hasNextLine()){
                String username = in.next();
                String password = in.next();
                storage.put(username, password);
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Database not found");
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, String password){
        if (!this.storage.containsKey(username)) return false;
        else {
            try {
                PrintWriter out = new PrintWriter(data);
                out.println(username + " " + password);
                this.storage.put(username, password);
                return true;
            } catch (FileNotFoundException e){
                System.out.println("Database not found");
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean validateUser(String username, String password){
        if (!this.storage.containsKey(username)) return false;
        else if (this.storage.get(username).equals(password)) return true;
        return false;
    }
}
