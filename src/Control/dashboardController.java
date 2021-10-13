package Control;

import Model.Appointment;
import Model.Customer;
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
    @FXML private TableColumn<Appointment, String> userCol;

    @FXML private RadioButton monthRadio, weekRadio;
    @FXML private TextArea reportArea;

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, Integer> mainCustIdCol;
    @FXML private TableColumn<Customer, String> nameCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> postalCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> countryCol;
    @FXML private TableColumn<Customer, String> divCol;

    /**
     * generates a warning when nothing is selected.
     * @param type specifies if the user is trying to update or delete.
     */
    private void noSelectionWarning(String type) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(String.format("Please select an item to %s.", type));
        alert.showAndWait();
    }

    /**
     * sets the appointment table to display appointments by month.
     */
    public void sortByMonth() {
        weekRadio.setSelected(false);
        weekRadio.setDisable(false);
        monthRadio.setDisable(true);
        Main.schedule.switchToMonthView();
        displayCol.setText("Month");
        appointmentTable.refresh();
    }

    /**
     * sets the appointment table to display appointments by week.
     */
    public void sortByWeek() {
        monthRadio.setSelected(false);
        monthRadio.setDisable(false);
        weekRadio.setDisable(true);
        Main.schedule.switchToWeekView();
        displayCol.setText("Week");
        appointmentTable.refresh();
    }

    /**
     * runs a report displaying the total appointment type by month.
     */
    public void totalReport() {
        reportArea.setText(Main.schedule.totalByType());
    }

    /**
     * runs a report displaying the schedules for each contact.
     */
    public void contactReport() {
        reportArea.setText(Main.schedule.appointmentByContact());
    }

    /**
     * runs a report showing appointments that have passed the current time.
     */
    public void expiredReport() {
        reportArea.setText(Main.schedule.getExpiredAppointments());
    }

    /**
     * clears the report area.
     */
    public void clearReportArea() {
        reportArea.setText("");
    }

    /**
     * opens up the appointment form to add a new appointment.
     * @param event the ActionEvent of the button clicked.
     * @throws Exception required for function to run.
     */
    public void addAppointment(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/appointment.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Manage Appointments");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * opens the appointment form to update a selected appointment.
     * @param event the ActionEvent of the button clicked.
     */
    public void updateAppointment(ActionEvent event) {
        try {
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            // Storage class holds the appointment ID while we switch between windows.
            Main.storage.setAppointmentId(selectedAppointment.getAppt_id());

            Parent root = FXMLLoader.load(getClass().getResource("../View/apptUpdate.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Manage Appointments");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            noSelectionWarning("update");
        }
    }

    /**
     * deletes an appointment.
     */
    public void deleteAppointment(ActionEvent event) {
        try {
            // Remove from schedule
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();
            Main.schedule.removeAppointment(selectedAppointment);

            // Remove from DB
            Main.DBHandler.removeAppointment(selectedAppointment);

            int id = selectedAppointment.getAppt_id();
            String type = selectedAppointment.getType();

            String report = String.format("Appointment ID: %d (Type: %s) has been canceled.", id, type);
            reportArea.setText(report);
        }
        catch (Exception e) {
            noSelectionWarning("delete");
        }

    }

    /**
     * launches the customer form to add a new customer.
     * @param event the ActionEvent of the button clicked.
     * @throws Exception required for function to run.
     */
    public void addCustomer(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/customer.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Manage Customers");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * updates an already existing customer.
     * @param event the ActionEvent of the clicked button.
     */
    public void updateCustomer(ActionEvent event) {
        try {
            // Select the customer from the table. Then store the id in an object to hold between windows.
            Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            Main.storage.setCustomerId(selectedCustomer.getId());

            Parent root = FXMLLoader.load(getClass().getResource("../View/custUpdate.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Manage Customers");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
            noSelectionWarning("update");
        }
    }

    /**
     * deletes a selected customer.
     */
    public void deleteCustomer() {
        try {
            Customer customer = customerTable.getSelectionModel().getSelectedItem();
            boolean hasAppt = Main.schedule.doesCustHaveAppt(customer);
            if (!hasAppt) {
                Main.custHandler.deleteCustomer(customer);
                String name = customer.getName();
                String report = String.format("%s has been deleted.", name);
                reportArea.setText(report);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText(String.format("Please remove associated appointments before removing customer."));
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            noSelectionWarning("delete");
        }
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
        userCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("user_id"));
        appointmentTable.setItems(Main.schedule.getAppointments());
        appointmentTable.getSortOrder().add(displayCol);

        mainCustIdCol.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        postalCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("postal"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("phone"));
        countryCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("country"));
        divCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("division"));
        customerTable.setItems(Main.customerList.getCustomers());
        customerTable.getSortOrder().add(mainCustIdCol);
    }
}

