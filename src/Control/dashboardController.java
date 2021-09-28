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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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



    public void addAppointment(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/appointment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void updateAppointment(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/appointment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void deleteAppointment(ActionEvent event) {
        // Remove from schedule
        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
        Main.schedule.removeAppointment(selectedAppointment);

        // Remove from DB
        Main.loader.removeAppointment(selectedAppointment);

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

    public void switchView(ActionEvent event) {
        System.out.println("View switched!");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appt_id"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        descrCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("cust_id"));

        appointmentTable.setItems(Main.schedule.getAppointments());

    }
}

