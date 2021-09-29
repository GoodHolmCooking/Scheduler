package Control;

import Model.Appointment;
import Model.Schedule;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TableView<Appointment> appointmentTable;



    @FXML private TableColumn<Appointment, String> typeCol;

    @FXML private TableColumn<Appointment, Integer> apptIdCol;

    @FXML private TableColumn<Appointment, Timestamp> startCol;

    @FXML private TableColumn<Appointment, Timestamp> endCol;

    @FXML private TableColumn<Appointment, String> titleCol;

    @FXML private TableColumn<Appointment, String> descrCol;

    @FXML private TableColumn<Appointment, String> locCol;

    @FXML private TableColumn<Appointment, Integer> custIdCol;

    @FXML private TableColumn<Appointment, String> displayCol;

    @FXML private RadioButton monthRadio, weekRadio;

    private void noSelectionWarning(String type) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(String.format("Please select an item to %s.", type));
        alert.showAndWait();
    }

    public void sortByMonth(ActionEvent event) {
        System.out.println("Sort by month run.");
        weekRadio.setSelected(false);
        weekRadio.setDisable(false);
        monthRadio.setDisable(true);
        Main.schedule.switchToMonthView();
        displayCol.setText("Month");
        appointmentTable.refresh();
    }

    public void sortByWeek(ActionEvent event) {
        System.out.println("Sort by week run.");
        monthRadio.setSelected(false);
        monthRadio.setDisable(false);
        weekRadio.setDisable(true);
        Main.schedule.switchToWeekView();
        displayCol.setText("Week");
        appointmentTable.refresh();
    }



    public void addAppointment(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/appointment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAppointment(ActionEvent event) throws Exception {
        try {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            // Storage class holds the appointment ID while we switch between windows.
            Main.storage.setAppointmentId(selectedAppointment.getAppt_id());

            Parent root = FXMLLoader.load(getClass().getResource("../View/apptUpdate.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            noSelectionWarning("update");
        }
    }

    public void deleteAppointment(ActionEvent event) {
        try {
            // Remove from schedule
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            Main.schedule.removeAppointment(selectedAppointment);

            // Remove from DB
            Main.loader.removeAppointment(selectedAppointment);
        }
        catch (Exception e) {
            noSelectionWarning("delete");
        }

    }

    public void addCustomer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/customer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateCustomer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/customer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteCustomer(ActionEvent event) {
        System.out.println("Customer deleted!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.schedule.switchToMonthView();

        apptIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appt_id"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        descrCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("cust_id"));
        displayCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("display"));

        appointmentTable.setItems(Main.schedule.getAppointments());

    }
}

