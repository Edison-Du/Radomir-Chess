package server;

import config.Consts;
import config.PathConsts;
import java.io.FileWriter;
import java.util.Map;

/**
 * [DatabaseUpdater.java]
 * A thread that saves the database to a file periodically
 * @author Edison Du
 * @author Jeffrey Xu
 * @version 1.0 Jan 24, 2022
 */
public class DatabaseUpdater extends Thread {
    
    private boolean isActive;
    private FileWriter fileWriter;
    private Database database;

    /**
     * DatabaseUpdater
     * Constructs the thread with the database to save
     * @param database the database to save
     */
    public DatabaseUpdater(Database database) {
        this.isActive = true;
        this.database = database;
    }

    /**
     * run
     * Continuouosly write the database to a file
     */
    @Override
    public void run() {
        try {
            while (isActive) {
                Thread.sleep(Consts.UPDATE_DATABASE_INTERVAL);
                fileWriter = new FileWriter(PathConsts.USERS);
                synchronized(database) {
                    for (Map.Entry<String, User> user : database.getUsers().entrySet()) {
                        fileWriter.write(user.getValue().toString());
                    }
                    fileWriter.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}