package Control;

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

public class customerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;


    @FXML private TextField idField;
    @FXML private ComboBox<String> countryBox;
    @FXML private ComboBox<String> divBox;


    public void onSave(ActionEvent event) throws Exception {
        Main.custHandler.saveCustomer();
    }

    public void onCancel(ActionEvent event) throws Exception {
        Main.custHandler.loadDashboard(event, stage, scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idField.setText(String.valueOf(Main.customerList.getCurrentId()));
        countryBox.setItems(Main.countryList.getCountryList());
        countryBox.getSelectionModel().select(0);
        divBox.setItems(Main.divisionList.getDivisionNames());
        divBox.getSelectionModel().select(0);
    }
}

