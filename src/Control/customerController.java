package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class customerController implements Initializable {
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
        idField.setText(String.valueOf(Main.customerList.getCurrentId()));
        countryBox.setItems(Main.countryList.getCountryList());
        countryBox.getSelectionModel().select(0);
        divBox.setItems(Main.divisionList.getDivisionNames(1));
        divBox.getSelectionModel().select(0);
    }
}

