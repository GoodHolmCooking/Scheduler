package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;

public class Authenticator {
    private final HashMap<String, String> passwords = new HashMap<String, String>();
    private final HashMap<String, Integer> ids = new HashMap<String, Integer>();
    private String currentUser = null;

    public boolean authenticate(String suppliedUser, String suppliedPassword) {
        boolean passwordCorrect = false;
        if (passwords.containsKey(suppliedUser)) {
            String correctPassword = passwords.get(suppliedUser);
            if (suppliedPassword.equals(correctPassword)) {
                passwordCorrect = true;
                this.currentUser = suppliedUser;
            }
        }

        return passwordCorrect;
    }

    public void addUser(String user, String password) {
        passwords.put(user, password);
    }
    public void addId(String user, int id) { ids.put(user, id); }

    public String getCurrentUser() {
        return currentUser;
    }
    public int getCurrentId() {
        int id = getId(this.currentUser);
        return id;
    }
    public int getId(String user) { return ids.get(user); }

    public void logAttempt(String username, boolean success) {
        // Timestamp
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String result = "";

        if (success) {
            result = "Successful";
        }
        else {
            result = "Unsuccessful";
        }

        String timeString = currentTime.toString();
        String record = String.format("Username: %s; Result: %s; Time: %s \n", username, result, timeString);

        try {
            File logFile = new File("login_activity.txt");
        }
        catch (Exception e) {
            // File already exists
        }

        try {
            FileWriter writer = new FileWriter("login_activity.txt", true);
            writer.write(record);
            writer.close();
        }
        catch (IOException e) {
            // Necessary to run.
        }
    }
}
