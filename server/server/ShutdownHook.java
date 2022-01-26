package server;

import java.io.FileWriter;
import config.PathConsts;
import java.util.Map;
import java.util.HashMap;

public class ShutdownHook extends Thread {

    private HashMap<String, User> database;
    private FileWriter fileWriter;

    public ShutdownHook(Server server) {
        this.database = server.getDatabase().getUsers();
    }

    @Override
    public void run() {
        // Saves user data before shutting down
        try {
            fileWriter = new FileWriter(PathConsts.USERS);
            synchronized(database) {
                for (Map.Entry<String, User> user : database.entrySet()) {
                    fileWriter.write(user.getValue().toString());
                }
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}