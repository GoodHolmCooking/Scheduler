package Control;

import Model.Appointment;
import Model.ContactList;
import javafx.event.ActionEvent;
import javafx.event.Event;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Time;
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

    public void onSave(ActionEvent event) throws Exception {
        int appt_id = Integer.parseInt(apptField.getText());
        String title = titleField.getText();
        String description = descrField.getText();
        String location = locField.getText();
        String contact = contactBox.getSelectionModel().getSelectedItem();
        String type = typeField.getText();
        LocalDate pickedStartDate = startDateField.getValue();
        String formattedStartDate = pickedStartDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startHr = startHrField.getText();
        String startMin = startMinField.getText();
        String startDateString = formattedStartDate + " " + startHr + ":" + startMin + ":00";
        Timestamp startDate = Timestamp.valueOf(startDateString);

//        LocalDate pickedEndDate = endDateField.getValue();
//        String formattedEndDate = pickedEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        String endHr = endHrField.getText();
//        String endMin = endMinField.getText();
        String endDateString = "2021-09-12 13:14:10";
        Timestamp endDate = Timestamp.valueOf(endDateString);

        int cust_id = Integer.parseInt(customerField.getText());
        int user_id = Integer.parseInt(userField.getText());

        Appointment appointment = new Appointment(appt_id, startDate, endDate, title, type, description, location, cust_id);
        Main.schedule.addAppointment(appointment);
        Main.loader.saveAppointment(appointment);

        loadDashboard(event);
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

