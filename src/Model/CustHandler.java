package Model;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustHandler {
    public void loadDashboard(ActionEvent event, Stage stage, Scene scene) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void saveCustomer() {

    }

}
