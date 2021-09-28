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
    public static Loader loader = new Loader();
    public static Storage storage = new Storage();
    public static ApptHandler apptHandler = new ApptHandler();

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader.loadContactList();
        loader.loadSchedule();

        Parent root = FXMLLoader.load(getClass().getResource("../View/dashboard.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
