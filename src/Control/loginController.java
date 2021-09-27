package Control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField nameField, pwordField;

    @FXML
    private Label locField, nameLabel, pwordLabel;

    private String correctUsername = "test";
    private String correctPassword = "test";

    private String error;

    @FXML
    private Button loginBtn;

    private boolean checkLogin() {
        String username = nameField.getText();
        String pword = pwordField.getText();
        boolean evaluation;

        if((username.equals(correctUsername)) && (pword.equals(correctPassword))) {
            evaluation = true;
        }
        else {
            evaluation = false;
        }

        return evaluation;
    }

    public void onLogin(ActionEvent event) throws Exception {
        if (checkLogin()) {
            Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String myLocation;

        // Sets the language based on the local settings.
        Locale myLocale = Locale.getDefault();
        String myLanguage = myLocale.getLanguage();
        String loadString = "Model/TextBundle_" + myLanguage;
        ResourceBundle textBundle = ResourceBundle.getBundle(loadString, myLocale);

        nameLabel.setText(textBundle.getString("username"));
        pwordLabel.setText(textBundle.getString("password"));
        loginBtn.setText(textBundle.getString("login"));
        error = textBundle.getString("error");


        // Sets the zone Id based on the local settings. Location is displayed based on available offices. If no
        // office is found, display is based of of Zone Id.
        if (myLocale.getLanguage().equals("fr")) {
            myLocation = "Montréal, Canada";
        }
        else {
            String myId = ZoneId.systemDefault().toString();
                switch (myId) {
                    case "America/Denver":
                        myLocation = "Phoenix, Arizona";
                        break;
                    case "America/New_York":
                        myLocation = "White Plains, New York";
                        break;
                    case "Europe/London":
                        myLocation = "London, England";
                        break;
                    default:
                        myLocation = myId;
                        break;
            }
        }

        locField.setText(myLocation);

    }
}

