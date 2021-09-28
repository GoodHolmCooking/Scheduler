package Control;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField apptField;
    @FXML private TextField titleField;
    @FXML private TextField descrField;
    @FXML private TextField locField;
    @FXML private ComboBox<String> contactBox;
    @FXML private TextField typeField;
    @FXML private TextField customerField;
    @FXML private TextField userField;
    @FXML private DatePicker startDateField;
    @FXML private TextField startHrField;
    @FXML private TextField startMinField;
    @FXML private TextField endHrField;
    @FXML private TextField endMinField;
    @FXML private DatePicker endDateField;

    private void loadDashboard(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private boolean checkTime(String num, String type) {
        boolean inputValid = true; // Initializing value

        if (!num.isBlank()) { // If we have data, proceed.
            if (num.length() > 2) { // Do we have more than two characters?
                inputValid = false;
            }
            else { // So, we have 2 or less characters. Is every character a number?
                for (char character : num.toCharArray()) {
                    try {
                        Integer.parseInt(String.valueOf(character));
                    } catch (Exception e) {
                        inputValid = false; // Found a character that can't be converted to a number.
                        break; // One is enough to invalidate, no need to check the rest.
                    }
                }

                if (inputValid) { // Since every character is a number continue.
                    int numValue = Integer.parseInt(num);
                    if (type.equals("hr")) { // If we're checking the hour, more than 24 hours?
                        if (numValue > 24) {
                            inputValid = false;
                        }
                    }
                    else {
                        if (numValue > 60) { // If we're checking the minute, more than 60 minutes?
                            inputValid = false;
                        }
                    }
                }
            }
        }
        else { // There's actually no data here so invalidate.
            inputValid = false;
        }
        return inputValid;
    }

    private boolean checkNum(String num) {
        boolean inputValid = true;

        if (!num.isBlank()) { // Is every character here a number?
            for (char character : num.toCharArray()) {
                try {
                    Integer.parseInt(String.valueOf(character));
                } catch (Exception e) {
                    inputValid = false;
                }
            }
        }
        else { // If not, invalidate.
            inputValid = false;
        }
        return inputValid;
    }

    private boolean validateInput(String title, String description, String location,
                                  String type, LocalDate startDate, String startHr, String startMin, LocalDate endDate,
                                  String endHr, String endMin, String cust_id,
                                  String user_id){

        // Check title
        boolean inputValid = !title.isBlank();

        // Check description
        if (inputValid) {
            if (description.isBlank()) {
                inputValid = false;
            }
        }

        // Check location
        if (inputValid) {
            if (location.isBlank()) {
                inputValid = false;
            }
        }

        // Check type
        if (inputValid) {
            if (type.isBlank()) {
                inputValid = false;
            }
        }

        // Check Start Date
        if (inputValid) {
            if (startDate == null) {
                inputValid = false;
            }
        }

        // Check start hr
        if (inputValid) {
            inputValid = checkTime(startHr, "hr");
        }

        // If the last check passed, check the start minute.
        if (inputValid) {
            inputValid = checkTime(startMin, "min");
        }

        if (inputValid) {
            if (endDate == null) {
                inputValid = false;
            }
            // Does the meeting end after it begins? Otherwise, that's a time paradox.
            else if (endDate.compareTo(startDate) < 0){
                inputValid = false;
            }
        }

        if (inputValid) {
            inputValid = checkTime(endHr, "hr");
        }

        // check end min
        if (inputValid) {
            inputValid = checkTime(endMin, "min");
        }

        // check customer id
        if (inputValid) {
            inputValid = checkNum(cust_id);
        }

        // check user id
        if (inputValid) {
            inputValid = checkNum(user_id);
        }

        return inputValid;
    }

    private Timestamp formatTimestamp(LocalDate pickedDate, String hr, String min) {
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
        return Timestamp.valueOf(dateString);
    }


    public void onSave(ActionEvent event) throws Exception {
        int appt_id = Integer.parseInt(apptField.getText());
        String title = titleField.getText();
        String description = descrField.getText();
        String location = locField.getText();
        String contact = contactBox.getSelectionModel().getSelectedItem();
        String type = typeField.getText();
        LocalDate pickedStartDate = startDateField.getValue();
        String startHr = startHrField.getText();
        String startMin = startMinField.getText();
        LocalDate pickedEndDate = endDateField.getValue();
        String endHr = endHrField.getText();
        String endMin = endMinField.getText();
        String cust_id = customerField.getText();
        String user_id = userField.getText();

        boolean inputValid = validateInput(title, description, location, type, pickedStartDate, startHr, startMin,
                pickedEndDate,
                endHr, endMin,
                cust_id, user_id);

        if (inputValid) {

            // Format Timestamp takes the three inputs and combines them into one Timestamp value.
            Timestamp startDate = formatTimestamp(pickedStartDate, startHr, startMin);
            Timestamp endDate = formatTimestamp(pickedEndDate, startHr, startMin);

            int cust_id_int = Integer.parseInt(cust_id);
            Appointment appointment = new Appointment(appt_id, startDate, endDate, title, type, description, location
                    , cust_id_int);

            // Add this new appointment to the backend of the program.
            Main.schedule.addAppointment(appointment);

            // Add this appointment to the persistent storage in the database.
            Main.loader.saveAppointment(appointment);

            // Close the window and loadup the dashboard.
            loadDashboard(event);
        }
        else {
            // At some point, a validation error triggered. Don't save and check input.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Input is bad yo.");
            alert.showAndWait();
        }


    }

    public void onCancel(ActionEvent event) throws Exception {
        loadDashboard(event);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        apptField.setText(String.valueOf(Main.schedule.getCurrentId()));
        contactBox.setItems(Main.contactList.getContacts());
        contactBox.getSelectionModel().select(0);
    }
}

