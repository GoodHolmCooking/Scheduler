package Control;

import Model.Appointment;
import Model.Contact;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class apptUpdateController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML private TextField apptField;
    @FXML private TextField titleField;
    @FXML private TextField descrField;
    @FXML private TextField locField;
    @FXML private ComboBox<String> contactBox;
    @FXML private TextField typeField;
    @FXML private ComboBox<String> customerBox;
    @FXML private TextField userField;
    @FXML private DatePicker startDateField;
    @FXML private TextField startHrField;
    @FXML private TextField startMinField;
    @FXML private TextField endHrField;
    @FXML private TextField endMinField;
    @FXML private DatePicker endDateField;

    public void onSave(ActionEvent event) throws Exception {
        Main.apptHandler.onSave(
                event,
                apptField,
                titleField,
                descrField,
                locField,
                contactBox,
                typeField,
                startDateField,
                startHrField,
                startMinField,
                endDateField,
                endHrField,
                endMinField,
                customerBox,
                userField,
                stage,
                scene
        );
    }

    public void onCancel(ActionEvent event) throws Exception {
        Main.apptHandler.onCancel(event, stage, scene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int id = Main.storage.getAppointmentId();
        Appointment selectedAppointment = Main.schedule.getAppointment(id);

//      Load ID
        apptField.setText(String.valueOf(id));

//        Load title
        titleField.setText(selectedAppointment.getTitle());

//        Load description
        descrField.setText(selectedAppointment.getDescription());

//        Load location
        locField.setText(selectedAppointment.getLocation());

//        Load contact
        contactBox.setItems(Main.contactList.getContacts());
        Contact contact = selectedAppointment.getContact();
        int index = Main.contactList.getIndex(contact);
        contactBox.getSelectionModel().select(index);

//        Load type
        typeField.setText(selectedAppointment.getType());

//        Load start date
        startDateField.setValue(selectedAppointment.getStartDate());

//        Load start hr
        startHrField.setText(String.valueOf(selectedAppointment.getStartHr()));

//        Load start min
        startMinField.setText(String.valueOf(selectedAppointment.getStartMin()));

//        Load end date
        endDateField.setValue(selectedAppointment.getEndDate());

//        Load end hr
        endHrField.setText(String.valueOf(selectedAppointment.getEndHr()));

//        Load end min
        endMinField.setText(String.valueOf(selectedAppointment.getEndMin()));

//        Load customer id
        int customer_id = selectedAppointment.getCust_id();
        customerBox.setItems(Main.customerList.getCustomerNames());
        customerBox.getSelectionModel().select(Main.customerList.getIndex(customer_id));

//        Load user id
        userField.setText(String.valueOf(selectedAppointment.getUser_id()));
    }
}

