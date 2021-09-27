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

    public Schedule schedule = new Schedule();

    @FXML private TableView<Appointment> appointmentTable;

    @FXML private TableColumn<Appointment, String> typeCol;


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
            ResultSet resultSet = statement.executeQuery("SELECT type FROM appointments");


            while (resultSet.next()) {
                String type = resultSet.getString("Type");
                Appointment appointment = new Appointment(type);

                schedule.addAppointment(appointment);
            }
            connection.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (Appointment appointment : schedule.getAppointments()) {
            System.out.println(appointment.getType());
        }


        typeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));

        appointmentTable.setItems(schedule.getAppointments());

    }
}

