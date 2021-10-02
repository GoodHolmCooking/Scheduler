package Model;

import Control.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class CustHandler {
    public void loadDashboard(ActionEvent event, Stage stage, Scene scene) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private String formatAddress(String streetName, String streetNum, String city, int country_id, String division) {
        String address = null;


        if (country_id == 2) { // If the address is in the UK
            address = String.format("%s %s, %s, %s", streetNum, streetName, city, division);
        }
        else {
            address = String.format("%s %s, %s", streetNum, streetName, city);
        }

        return address;
    }

    public void saveCustomer(ActionEvent event, TextField idField, TextField nameField,
                             TextField postalField,
                             TextField phoneField, ComboBox<String> countryBox, ComboBox<String> divBox, Stage stage,
                             Scene scene, TextField streetNameField, TextField streetNumField, TextField cityField
                             ) throws Exception {

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText().strip();

        String postal = postalField.getText().strip();
        String phone = phoneField.getText().strip();

        String countryName = countryBox.getSelectionModel().getSelectedItem();
        int country_id = Main.countryList.getId(countryName);

        String divName = divBox.getSelectionModel().getSelectedItem();
        int div_id = Main.divisionList.getID(divName);

        String streetName = streetNameField.getText().strip();
        String streetNum = streetNumField.getText().strip();
        String city = cityField.getText().strip();
        String address = formatAddress(streetName, streetNum, city, country_id, divName);

        String creator = Main.authenticator.getCurrentUser();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        String formatedTime = currentTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Customer customer = new Customer(id, name, address, postal, phone, formatedTime, creator, div_id, divName,
                country_id, countryName);

        if (Main.customerList.customerExists(id)) {
            // Update Customer List
            Main.customerList.updateCustomer(customer, formatedTime);

            // Update DB
            Main.DBHandler.updateCustomer(customer, formatedTime);
        }
        else {

            // Save to Customer List
            Main.customerList.addCustomer(customer);

            // Save to DB
            Main.DBHandler.addCustomer(customer);
        }

        loadDashboard(event, stage, scene);
    }

    public void deleteCustomer(Customer selectedCustomer) {

        // Remove from Customer List
        Main.customerList.removeCustomer(selectedCustomer);

        // Remove from DB
        Main.DBHandler.removeCustomer(selectedCustomer);

    }
}
