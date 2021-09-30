package Control;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Schedule schedule = new Schedule();
    public static ContactList contactList = new ContactList();
    public static DBHandler DBHandler = new DBHandler();
    public static Storage storage = new Storage();
    public static ApptHandler apptHandler = new ApptHandler();
    public static Authenticator authenticator = new Authenticator();
    public static CustHandler custHandler = new CustHandler();
    public static CustomerList customerList = new CustomerList();

    @Override
    public void start(Stage primaryStage) throws Exception{
        DBHandler.loadContactList();
        DBHandler.loadSchedule();
        DBHandler.loadCustomers();
        authenticator.addUser("test", "test");

        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
