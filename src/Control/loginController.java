package Control;

import Model.Appointment;
import Model.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    private Parent root;

    @FXML
    private TextField nameField, pwordField;

    @FXML
    private Label locField, nameLabel, pwordLabel;

    private String error;

    @FXML
    private Button loginBtn;

    /**
     * checks the username and password. Logs in if correct. This contains the second lambda expression. The lambda
     * checks the username and password based on what has been stored from the inital database load. This is run as a
     * lambda because this occurs only once at the top of the program instead of a repeated function.
     * @param event the event of the login button.
     * @throws Exception required to make function work.
     */
    public void onLogin(ActionEvent event) throws Exception {
        String username = nameField.getText();
        String password = pwordField.getText();


        Validation validation = (suppliedUser, suppliedPassword) -> {
            boolean passwordCorrect = false;
            HashMap<String,String> passwords = Main.authenticator.getPasswords();

            if (passwords.containsKey(suppliedUser)) {
                String correctPassword = passwords.get(suppliedUser);
                if (suppliedPassword.equals(correctPassword)) {
                    passwordCorrect = true;
                }
            }

            return passwordCorrect;
        };

        if (validation.checkPassword(username, password)) {
            Main.authenticator.setCurrentUser(username);
            Main.authenticator.logAttempt(username, true);
            Appointment foundAppointment = Main.schedule.apptIn15();

            if (foundAppointment != null) {
                int id = foundAppointment.getAppt_id();
                String startTime = foundAppointment.getStart();

                String warning =
                        String.format("You have an upcoming appointment.\nAppointment ID: %d\nApointment begins at " +
                                "%s", id, startTime);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText(warning);
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("No upcoming appointments.");
                alert.showAndWait();
            }

            Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Dashboard");
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            Main.authenticator.logAttempt(username, false);

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

