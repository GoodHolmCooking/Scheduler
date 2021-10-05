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

    /**
     * @return the combination of usernames and passwords.
     */
    public HashMap<String, String> getPasswords() {
        return passwords;
    }

    /**
     * @param user the username to add.
     * @param password the password to add.
     */
    public void addUser(String user, String password) {
        passwords.put(user, password);
    }

    /**
     *
     * @param user the username associated with the id.
     * @param id the id to add.
     */
    public void addId(String user, int id) { ids.put(user, id); }

    /**
     *
     * @return the user currently logged in.
     */
    public String getCurrentUser() {
        return currentUser;
    }

    /**
     *
     * @return the id of the current user.
     */
    public int getCurrentId() {
        int id = getId(this.currentUser);
        return id;
    }

    /**
     *
     * @param user the user whose id you wish to grab.
     * @return the id of the specified user.
     */
    public int getId(String user) { return ids.get(user); }

    /**
     *
     * @param currentUser the username to set as current.
     */
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Update the login activity report.
     * @param username the username that attempted the login success.
     * @param success whether the login was a success or a failure.
     */
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
