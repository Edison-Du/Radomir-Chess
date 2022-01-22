package server;

import java.util.*;

import config.Consts;
import config.PathConsts;

import java.io.*;



public class Database {
    private HashMap<String, User> storage = new HashMap<String, User>();
    private File data;
    
    public Database() {
        try {
            int[] settingsPreferences = new int[Consts.NUM_SETTINGS];
            data = new File(PathConsts.USERS);
            Scanner in = new Scanner(data);
            while (in.hasNextLine()) {
                String username = in.next();
                String password = in.next();

                for (int i = 0; i < Consts.NUM_SETTINGS; i++) {
                    settingsPreferences[i] = Integer.parseInt(in.next());
                }

                storage.put(username, new User(username, password, settingsPreferences));
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Database not found");
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, User user){
        if (this.storage.containsKey(username)) return false;
        else {
            this.storage.put(username, user);
            return true;
        }
    }

    public boolean validateUser(String username, String password){
        if (!this.storage.containsKey(username)) return false;
        else if (this.storage.get(username).getPassword().equals(password)) return true;
        System.out.println("Started from the bottom now im here");
        return false;
    }

    public User getUser(String username) {
        return this.storage.get(username);
    }
}
