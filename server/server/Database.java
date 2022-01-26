package server;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import config.Consts;
import config.PathConsts;

/**
 * [Database.java]
 * The database storing all user information
 * @author Peter Gu
 * @author Jeffrey Xu
 * @author Edison Du
 * @version 1.0 Jan 24, 2022
 */
public class Database {
    private final int SETTINGS_INDEX = 2;

    private HashMap<String, User> users;
    private File data;
    
    /**
     * Database
     * Creates a database object, reading users from a file and
     * storing the users in a hashmap with their username as the 
     * key and a user object as the value
     */
    public Database() {
        int[] settingsPreferences = new int[Consts.NUM_SETTINGS];

        users = new HashMap<String, User>();
        data = new File(PathConsts.USERS);

        try {
            // Read the user data from a file
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

    /**
     * addUser
     * Adds a user to the database
     * @param username the user's username
     * @param user the user object with associated user data
     * @return whether or not the user could be added
     */
    public boolean addUser(String username, User user) {
        if (this.users.containsKey(username)) {
            return false;
        } else {
            this.users.put(username, user);
            return true;
        }
    }

    /**
     * validateUser
     * Checks if user by the specified username and password exists
     * @param username the username of the user
     * @param password the password of the user
     * @return whether or not this user exists
     */
    public boolean validateUser(String username, String password){
        if (!this.users.containsKey(username)) {
            return false;

        } else if (this.users.get(username).getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * getUser
     * Gets a user by their username
     * @param username the username of the user
     * @return the user object with the user's data
     */
    public User getUser(String username) {
        return this.users.get(username);
    }

    /**
     * updatePreferences
     * Updates a user's settings
     * @param username the username of the user to update
     * @param settings the updated settings
     */
    public void updatePreferences(String username, int[] settings) {
        User updatedUser = new User(username, this.users.get(username).getPassword(), settings);
        this.users.replace(username, updatedUser);
    }

    /**
     * getUsers
     * Getter for the hashmap containing all users
     * @return the hashmap containing users
     */
    public HashMap<String, User> getUsers() {
        return this.users;
    }
}