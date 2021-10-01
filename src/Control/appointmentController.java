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
        apptField.setText(String.valueOf(Main.schedule.getCurrentId()));
        contactBox.setItems(Main.contactList.getContacts());
        contactBox.getSelectionModel().select(0);
        customerBox.setItems(Main.customerList.getCustomerNames());
        customerBox.getSelectionModel().select(0);
        String userName = Main.authenticator.getCurrentUser();
        int userId = Main.authenticator.getId(userName);
        userField.setText(String.valueOf(userId));
    }
}

