package server;

import java.io.FileWriter;
import config.PathConsts;
import java.util.Map;
import java.util.HashMap;

public class ShutdownHook extends Thread {

    Server server;
    HashMap<String, User> database;
    FileWriter fileWriter;

    public ShutdownHook(Server server) {
        this.server = server;
        this.database = server.getDatabase().getData();
    }

    @Override
    public void run() {
        // Saves user data before shutting down
        try {
            synchronized(database) {
                fileWriter = new FileWriter(PathConsts.USERS);
                for (Map.Entry<String, User> user : this.database.entrySet()) {
                    fileWriter.write(user.getValue().toString());
                }
                fileWriter.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}