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
import java.util.ResourceBundle;

public class custUpdateController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField postalField;
    @FXML private TextField phoneField;
    @FXML private ComboBox<String> countryBox;
    @FXML private ComboBox<String> divBox;

    public void onSave(ActionEvent event) throws Exception {
        Main.custHandler.saveCustomer(event, idField, nameField, addressField, postalField, phoneField, countryBox,
                divBox, stage, scene);
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

        // Set address
        addressField.setText(customer.getAddress());

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

