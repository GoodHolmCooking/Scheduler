package Model;

import Control.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private boolean validateCustomer(String name, String phone, String streetNum, String streetName, String city,
                                     String postal, ArrayList<String> errors) {
        boolean inputValid = true;
        String blankWarning = "All fields must contain data.";
        String numWarningString = "Postal and street number fields must only contain numbers.";

        // Check Name
        if (name.isBlank()) {
            inputValid = false;
            if (!errors.contains(blankWarning)) {
                errors.add(blankWarning);
            }
        }

        // Check Phone Number
        if (inputValid) {
            if (!phone.isBlank()) {
                // Do something
            }
            else {
                inputValid = false;
                if (!errors.contains(blankWarning)) {
                    errors.add(blankWarning);
                }
            }
        }

        // Check Street Number
        if (inputValid) {
            if (!streetNum.isBlank()) {
                for (char character : streetNum.toCharArray()) {
                    try {
                        Integer.parseInt(String.valueOf(character));
                    } catch (Exception e) {
                        inputValid = false;
                        if (!errors.contains(numWarningString)) {
                            errors.add(numWarningString);
                        }
                    }
                }
            }
            else {
                inputValid = false;
                if (!errors.contains(blankWarning)) {
                    errors.add(blankWarning);
                }
            }
        }

        // Check Street Name
        if (inputValid) {
            if (!streetName.isBlank()) {}
            else {
                inputValid = false;
                if (!errors.contains(blankWarning)) {
                    errors.add(blankWarning);
                }
            }
        }

        // Check City
        if (inputValid) {
            if (!city.isBlank()) {}
            else {
                inputValid = false;
                if (!errors.contains(blankWarning)) {
                    errors.add(blankWarning);
                }
            }
        }

        // Check Postal Code
        if (inputValid) {
            if (!postal.isBlank()) {
                for (char character : postal.toCharArray()) {
                    try {
                        Integer.parseInt(String.valueOf(character));
                    } catch (Exception e) {
                        inputValid = false;

                        if (!errors.contains(numWarningString)) {
                            errors.add(numWarningString);
                        }
                    }
                }
            }
            else {
                inputValid = false;
                if (!errors.contains(blankWarning)) {
                    errors.add(blankWarning);
                }
            }
        }


        return inputValid;
    }

    public void saveCustomer(ActionEvent event, TextField idField, TextField nameField,
                             TextField postalField,
                             TextField phoneField, ComboBox<String> countryBox, ComboBox<String> divBox, Stage stage,
                             Scene scene, TextField streetNameField, TextField streetNumField, TextField cityField
                             ) throws Exception {

        ArrayList<String> errors = new ArrayList<String>();
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

        boolean validCustomer = validateCustomer(name, phone, streetNum, streetName, city, postal, errors);
        if (validCustomer) {

            String address = formatAddress(streetName, streetNum, city, country_id, divName);

            String creator = Main.authenticator.getCurrentUser();
//            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            LocalDateTime ldt = LocalDateTime.now();
            ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
            ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
            String formattedTime = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            String formatedTime = currentTime.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            Customer customer = new Customer(id, name, address, postal, phone, formattedTime, creator, div_id, divName,
                    country_id, countryName);

            if (Main.customerList.customerExists(id)) {
                // Update Customer List
                Main.customerList.updateCustomer(customer, formattedTime);

                // Update DB
                Main.DBHandler.updateCustomer(customer, formattedTime);
            } else {

                // Save to Customer List
                Main.customerList.addCustomer(customer);

                // Save to DB
                Main.DBHandler.addCustomer(customer);
            }

            loadDashboard(event, stage, scene);
        }
        else {
            String errorString = "";
            for (String error : errors) {
                errorString += error + "\n";
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(errorString);
            alert.showAndWait();
        }
    }

    public void deleteCustomer(Customer selectedCustomer) {

        // Remove from Customer List
        Main.customerList.removeCustomer(selectedCustomer);

        // Remove from DB
        Main.DBHandler.removeCustomer(selectedCustomer);
    }
}
