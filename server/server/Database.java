package server;

import java.util.*;

import config.Consts;
import config.PathConsts;

import java.io.*;


public class Database {
    private final int SETTINGS_INDEX = 2;
    private HashMap<String, User> users;
    private File data;
    
    public Database() {
        int[] settingsPreferences = new int[Consts.NUM_SETTINGS];

        users = new HashMap<String, User>();
        data = new File(PathConsts.USERS);

        try {
            Scanner in = new Scanner(data);
            while (in.hasNextLine()) {
                String[] line = in.nextLine().split(" ");
                String username = line[0];
                String password = line[1];

                for (int i = 0; i < Consts.NUM_SETTINGS; i++) {
                    settingsPreferences[i] = Integer.parseInt(line[i + SETTINGS_INDEX]);
                }

                users.put(username, new User(username, password, settingsPreferences));
            }
            in.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Database file not found");
            e.printStackTrace();
        }
    }

    public boolean addUser(String username, User user) {
        if (this.users.containsKey(username)) return false;
        else {
            this.users.put(username, user);
            return true;
        }
    }

    public boolean validateUser(String username, String password){
        if (!this.users.containsKey(username)) return false;
        else if (this.users.get(username).getPassword().equals(password)) return true;
        System.out.println("Started from the bottom now im here");
        return false;
    }

    public User getUser(String username) {
        return this.users.get(username);
    }

    public void updatePreferences(String username, int[] settings) {
        User updatedUser = new User(username, this.users.get(username).getPassword(), settings);
        this.users.replace(username, updatedUser);
    }

    public HashMap<String, User> getUsers() {
        return this.users;
    }
}