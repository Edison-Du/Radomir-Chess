package server;

import config.Consts;
import config.PathConsts;
import java.io.FileWriter;
import java.util.Map;

public class DatabaseUpdater extends Thread {
    
    private boolean isActive;
    private FileWriter fileWriter;
    private Database database;

    public DatabaseUpdater(Database database) {
        this.isActive = true;
        this.database = database;
    }

    @Override
    public void run() {
        try {
            while (isActive) {
                Thread.sleep(Consts.UPDATE_DATABASE_INTERVAL);
                fileWriter = new FileWriter(PathConsts.USERS);
                int numAccounts = database.getData().size();
                synchronized(database) {
                    for (Map.Entry<String, User> user : database.getData().entrySet()) {
                        if (numAccounts == 0) {
                            fileWriter.write(user.getValue().toString().trim());
                        } else {
                            fileWriter.write(user.getValue().toString());
                        }
                        numAccounts--;
                    }
                    fileWriter.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}