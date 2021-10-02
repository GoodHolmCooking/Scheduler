package Model;

import Control.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ApptHandler {

    private void loadDashboard(ActionEvent event, Stage stage, Scene scene) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkTime(String num, String type, boolean inputValid, ArrayList<String> errors) {

        if (num.length() > 2) { // Do we have more than two characters?
            inputValid = false;
            String warningString = "Time fields must not have more than two characters";
            if (!errors.contains(warningString)) {
                errors.add(warningString);
            }
        }
        else { // So, we have 2 or less characters. Is every character a number?
            for (char character : num.toCharArray()) {
                try {
                    Integer.parseInt(String.valueOf(character));
                } catch (Exception e) {
                    inputValid = false; // Found a character that can't be converted to a number.
                    String warningString = "Time and ID fields must only contain numbers.";
                    if (!errors.contains(warningString)) {
                        errors.add(warningString);
                    }
                    break; // One is enough to invalidate, no need to check the rest.
                }
            }

            if (inputValid) { // Since every character is a number continue.
                int numValue = Integer.parseInt(num);
                if (type.equals("hr")) { // If we're checking the hour, more than 24 hours?
                    if (numValue > 24) {
                        inputValid = false;
                        String warningString = "Hour fields must not be greater than 24.";
                        if (!errors.contains(warningString)) {
                            errors.add(warningString);
                        }
                    }
                }
                else {
                    if (numValue > 60) { // If we're checking the minute, more than 60 minutes?
                        inputValid = false;
                        String warningString = "Minute fields must not be greater than 60.";
                        if (!errors.contains(warningString)) {
                            errors.add(warningString);
                        }
                    }
                }
            }
        }

        return inputValid;
    }

    private boolean checkNum(String num, boolean inputValid, ArrayList<String> errors) {
        // Is every character here a number?
        for (char character : num.toCharArray()) {
            try {
                Integer.parseInt(String.valueOf(character));
            } catch (Exception e) {
                inputValid = false;
                String warningString = "All ID and time fields must contain only numbers.";
                if (!errors.contains(warningString)) {
                    errors.add(warningString);
                }
            }
        }

        return inputValid;
    }

    private boolean containsData(String field, boolean inputValid, ArrayList<String> errors) {
        if (inputValid) { // Are we valid so far? If not, we're skipping.

            if (field.isBlank()) { // Is the field blank? Let's invalidate and add a warning.
                inputValid = false;
                String warningString = "All fields must be filled out.";
                if (!errors.contains(warningString)) { // If the warning is already there, let's not duplicate.
                    errors.add(warningString);
                }
            }
        }

        return inputValid;
    }

    private boolean validateInput(String title, String description, String location,
                                  String type, LocalDate startDate, String startHr, String startMin, LocalDate endDate,
                                  String endHr, String endMin,
                                  String user_id, ArrayList<String> errors){

        // Check title
        boolean inputValid = true;
         inputValid = containsData(title, inputValid, errors);

        // Check description
        inputValid = containsData(description, inputValid, errors);

        // Check location
        inputValid = containsData(location, inputValid, errors);

        // Check type
        inputValid = containsData(type, inputValid, errors);

        // Check Start Date
        if (inputValid) {
            if (startDate == null) {
                inputValid = false;
                errors.add("Please select a start date.");
            }
        }

        // Check start hour
        if (inputValid) {
            inputValid = containsData(startHr, inputValid, errors);
            if (inputValid) {
                inputValid = checkTime(startHr, "hr", inputValid, errors);
            }
        }

        // Check start minute
        if (inputValid) {
            inputValid = containsData(startMin, inputValid, errors);
            if (inputValid) {
                inputValid = checkTime(startMin, "min", inputValid, errors);
            }
        }

        // Check end date
        if (inputValid) {
            if (endDate == null) {
                inputValid = false;
                errors.add("Please select an ending date.");
            }
        }

        // Check end hour
        if (inputValid) {
            inputValid = containsData(endHr, inputValid, errors);
            if (inputValid) {
                inputValid = checkTime(endHr, "hr", inputValid, errors);
            }
        }

        // check end min
        if (inputValid) {
            inputValid = containsData(endMin, inputValid, errors);
            if (inputValid) {
                inputValid = checkTime(endMin, "min", inputValid, errors);
            }
        }

        // check user id
        if (inputValid) {
            inputValid = containsData(user_id, inputValid, errors);
            if (inputValid) {
                inputValid = checkNum(user_id, inputValid, errors);
            }
        }

        return inputValid;
    }

    private String formatTimestamp(LocalDate pickedDate, String hr, String min) {
        // Check hr
        if (hr.length() == 1) {
            hr = "0" + hr;
        }

        // Check min
        if (min.length() == 1) {
            min = "0" + min;
        }

        String formattedStartDate = pickedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String dateString = formattedStartDate + " " + hr + ":" + min + ":00";
        return dateString;
    }


    public void onSave(
            ActionEvent event,
            TextField apptField,
            TextField titleField,
            TextField descrField,
            TextField locField,
            ComboBox<String> contactBox,
            TextField typeField,
            DatePicker startDateField,
            TextField startHrField,
            TextField startMinField,
            DatePicker endDateField,
            TextField endHrField,
            TextField endMinField,
            ComboBox<String> customerBox,
            TextField userField,
            Stage stage,
            Scene scene
    ) throws Exception {
        ArrayList<String> errors = new ArrayList<String>();

        int appt_id = Integer.parseInt(apptField.getText());
        String title = titleField.getText().strip();
        String description = descrField.getText().strip();
        String location = locField.getText().strip();
        String contactName = (String) contactBox.getSelectionModel().getSelectedItem();
        String type = typeField.getText().strip();
        LocalDate pickedStartDate = startDateField.getValue();
        String startHr = startHrField.getText().strip();
        String startMin = startMinField.getText().strip();
        LocalDate pickedEndDate = endDateField.getValue();
        String endHr = endHrField.getText().strip();
        String endMin = endMinField.getText().strip();
        String customerName = customerBox.getSelectionModel().getSelectedItem();
        int cust_id = Main.customerList.getCustomerId(customerName);
        String user_id = userField.getText();
        String startDate = null;
        String endDate = null;

        boolean inputValid = validateInput(title, description, location, type, pickedStartDate, startHr, startMin,
                pickedEndDate,
                endHr, endMin,
                user_id, errors);
        if (inputValid) {
            // Format Timestamp takes the three inputs and combines them into one Timestamp value.
            startDate = formatTimestamp(pickedStartDate, startHr, startMin);
            endDate = formatTimestamp(pickedEndDate, endHr, endMin);

            if (endDate.compareTo(startDate) < 0) {
                inputValid = false;
                String warningString = "Start date must not be later than end date.";
                errors.add(warningString);
            }
        }

        if (inputValid) {
            int user_id_int = Integer.parseInt(user_id);
            Contact contact = Main.contactList.getContact(contactName);

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            String formatedTime = currentTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String creator = Main.authenticator.getCurrentUser();

            Appointment appointment = new Appointment(appt_id, startDate, endDate, title, type, description, location
                    , cust_id, contact, user_id_int, formatedTime, creator);

            boolean apptExists = Main.schedule.doesApptExist(appointment);

            if (apptExists) { // An appointment of that ID was found, so let's update the appointment on file.
                String updater = Main.authenticator.getCurrentUser();
                Main.schedule.updateAppointment(appointment, updater, formatedTime);
                Main.DBHandler.updateAppointment(appointment, updater, formatedTime);
            }
            else { // No appointment of that ID exists, so let's create a new one.
                // Add this new appointment to the backend of the program.
                Main.schedule.addAppointment(appointment);

                // Add this appointment to the persistent storage in the database.
                Main.DBHandler.addAppointment(appointment);
            }
            // Close the window and loadup the dashboard.
            loadDashboard(event, stage, scene);
        }
        else {
            // At some point, a validation error triggered. Don't save and check input.
            String warning = "";
            for (String error : errors) {
                warning += error + "\n";
            }

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(warning);
            alert.showAndWait();
        }
    }

    public void onCancel(ActionEvent event, Stage stage, Scene scene) throws Exception {
        loadDashboard(event, stage, scene);
    }
}
