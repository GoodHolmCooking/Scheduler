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

    public static Schedule schedule = new Schedule();

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
        System.out.println("Appointment deleted!");
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
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");


            while (resultSet.next()) {
                int appt_id = resultSet.getInt("Appointment_ID");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                int cust_id = resultSet.getInt("Customer_ID");
                Appointment appointment = new Appointment(appt_id, start, end, title, type, description, location, cust_id);
                schedule.addAppointment(appointment);

            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

//        For testing what is actually stored in the schedule.
//        for (Appointment appointment : schedule.getAppointments()) {
//            // Do something
//        }

        apptIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appt_id"));
        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, Timestamp>("end"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        descrCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("cust_id"));

        appointmentTable.setItems(schedule.getAppointments());

    }
}

