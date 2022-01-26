package server;

import java.io.FileWriter;

import java.util.Map;
import java.util.HashMap;

import config.PathConsts;

/**
 * [ShutdownHook.java]
 * Writes user information to a text file when the
 * server is shut down.
 * 
 * @author Edison Du
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class ShutdownHook extends Thread {

    private HashMap<String, User> database;
    private FileWriter fileWriter;

    /**
     * ShutdownHook
     * Initialize user database
     * @param server server object containing user database
     */
    public ShutdownHook(Server server) {
        this.database = server.getDatabase().getUsers();
    }

    /**
     * run
     * Writes all user information to a file
     */
    @Override
    public void run() {
        try {
            fileWriter = new FileWriter(PathConsts.USERS);
            synchronized(database) {
                for (Map.Entry<String, User> user : database.entrySet()) {
                    fileWriter.write(user.getValue().toString());
                }
                fileWriter.close();
            }

        } catch (Exception e) {
            System.out.println("Error writing to database.");
            e.printStackTrace();
        }
    }
}