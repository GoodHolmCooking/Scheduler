package Model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;

public class Tracker {
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

        // Write all of this to a file.
        // Check for a file.
        // If a file does not exist, write to the file.

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
