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
            fileWriter = new FileWriter(PathConsts.USERS);
            int numAccounts = database.size();
            synchronized(database) {
                for (Map.Entry<String, User> user : database.entrySet()) {
                    if (numAccounts == 0) {
                        fileWriter.write(user.getValue().toString().trim());
                    } else {
                        fileWriter.write(user.getValue().toString());
                    }
                    numAccounts--;
                }
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}