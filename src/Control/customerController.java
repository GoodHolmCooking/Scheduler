package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void onSave(ActionEvent event) throws Exception {
        Main.custHandler.saveCustomer();
    }

    public void onCancel(ActionEvent event) throws Exception {
        Main.custHandler.loadDashboard(event, stage, scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Customer window initialized.");
    }
}

