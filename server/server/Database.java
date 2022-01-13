package server;

import java.util.*;
import java.io.*;

public class Database {
    private HashMap<String, String> storage = new HashMap<String, String>();
    File data;
    
    public Database() {
        // try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("../database/users.txt");
            // String data = readFromInputStream(inputStream);

            InputStreamReader in = new InputStreamReader(inputStream);
            // System.out.println(in.rea);
            // data = new File("../database/users.txt");
            // data =  new File(getClass().getResource("../database/users.txt").getFile());
            // Scanner in = new Scanner(data);
            // while (in.hasNext()){
            //     String username = in.next();
            //     String password = in.nextLine();
            //     storage.put(username, password);
            // }
            // in.close();
        // } catch (FileNotFoundException e) {
        //     System.out.println("Database not found");
        //     e.printStackTrace();
        // }
    }

    public boolean registerUser(String username, String password){
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
}
