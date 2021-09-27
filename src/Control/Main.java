package Control;

import Model.ContactList;
import Model.Loader;
import Model.Schedule;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Schedule schedule = new Schedule();
    public static ContactList contactList = new ContactList();
    public static Loader loader = new Loader();

    @Override
    public void start(Stage primaryStage) throws Exception{
        loader.loadSchedule();
        loader.loadContactList();

        Parent root = FXMLLoader.load(getClass().getResource("../View/appointment.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
