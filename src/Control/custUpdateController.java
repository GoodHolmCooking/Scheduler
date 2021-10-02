package Control;

import Model.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class custUpdateController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField streetNameField;
    @FXML private TextField streetNumField;
    @FXML private TextField cityField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> countryBox;
    @FXML private ComboBox<String> divBox;

    public void onSave(ActionEvent event) throws Exception {
        Main.custHandler.saveCustomer(event, idField, nameField, postalField, phoneField, countryBox,
                divBox, stage, scene, streetNameField, streetNumField, cityField);
    }

    public void onCancel(ActionEvent event) throws Exception {
        Main.custHandler.loadDashboard(event, stage, scene);
    }

    public void onCountryChanged(ActionEvent event) {
        String countryName = countryBox.getSelectionModel().getSelectedItem();
        int id = Main.countryList.getId(countryName);
        divBox.setItems(Main.divisionList.getDivisionNames(id));
        divBox.getSelectionModel().select(0);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int id = Main.storage.getCustomerId();

        Customer customer = Main.customerList.getCustomer(id);

        // Set id
        idField.setText(String.valueOf(id));

        // Set name
        nameField.setText(customer.getName());

        HashMap<String, String> addressMap = customer.getAddressMap();
        String streetName = addressMap.get("name");

        String streetNum = addressMap.get("number");

        String city = customer.getCity();

        // Set street name
        streetNameField.setText(streetName.strip());

        // Set street number
        streetNumField.setText(streetNum.strip());

//        System.out.println(city);
        // Set city
        if (!city.isBlank()) {
            cityField.setText(city.strip());
        }

        // Set postal
        postalField.setText(customer.getPostal());

        // Set phone
        phoneField.setText(customer.getPhone());

        // Set country
        int country_id = customer.getCountry_id();
        countryBox.setItems(Main.countryList.getCountryList());
        countryBox.getSelectionModel().select(Main.countryList.getIndex(country_id));

        // Set division
        divBox.setItems(Main.divisionList.getDivisionNames(country_id));
        int div_id = customer.getDiv_id();
        divBox.getSelectionModel().select(Main.divisionList.getIndex(div_id));
    }
}

