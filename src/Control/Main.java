package Control;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    public static Schedule schedule = new Schedule();
    public static ContactList contactList = new ContactList();
    public static DBHandler DBHandler = new DBHandler();
    public static Storage storage = new Storage();
    public static ApptHandler apptHandler = new ApptHandler();
    public static Authenticator authenticator = new Authenticator();
    public static CustHandler custHandler = new CustHandler();
    public static CustomerList customerList = new CustomerList();
    public static DivisionList divisionList = new DivisionList();
    public static CountryList countryList = new CountryList();

    @Override
    public void start(Stage primaryStage) throws Exception{
//        DBHandler.initializeData();

        Loader loader = () -> {
            String user = "root";
            String password = "root";
            String url = "jdbc:mysql://127.0.0.1:3306/project";

            // loadContactList();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");


                while (resultSet.next()) {
                    int id = resultSet.getInt("Contact_ID");
                    String name = resultSet.getString("Contact_Name");
                    String email = resultSet.getString("Email");
                    Contact contact = new Contact(id, name, email);

                    Main.contactList.addContact(contact);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // loadSchedule();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");


                while (resultSet.next()) {
                    int appt_id = resultSet.getInt("Appointment_ID");
                    String start = resultSet.getString("Start");
                    String end = resultSet.getString("End");
                    String title = resultSet.getString("Title");
                    String type = resultSet.getString("Type");
                    String description = resultSet.getString("Description");
                    String location = resultSet.getString("Location");
                    int cust_id = resultSet.getInt("Customer_ID");
                    int contactId = resultSet.getInt("Contact_ID");
                    Contact contact = Main.contactList.getContact(contactId);
                    int user_id = resultSet.getInt("User_ID");
                    String creator = resultSet.getString("Created_By");
                    String created = resultSet.getString("Create_Date");
                    String updated = resultSet.getString("Last_Update");
                    String updater = resultSet.getString("Last_Updated_By");

                    // Start
                    LocalDateTime startLdt = Timestamp.valueOf(start).toLocalDateTime();
                    ZonedDateTime startZdt = ZonedDateTime.of(startLdt, ZoneId.of("UTC"));
                    ZonedDateTime startLocTime = startZdt.withZoneSameInstant(ZoneId.systemDefault());
                    String formattedStart = startLocTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    // End
                    LocalDateTime endLdt = Timestamp.valueOf(end).toLocalDateTime();
                    ZonedDateTime endZdt = ZonedDateTime.of(endLdt, ZoneId.of("UTC"));
                    ZonedDateTime endLocTime = endZdt.withZoneSameInstant(ZoneId.systemDefault());
                    String formattedEnd = endLocTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                    Appointment appointment = new Appointment(appt_id, formattedStart, formattedEnd, title, type, description,
                            location,
                            cust_id, contact, user_id, created, creator);

                    // Manually added to not cause problems with constructor.
                    appointment.setUpdater(updater);
                    appointment.setUpdated(updated);

                    Main.schedule.addAppointment(appointment);

                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // loadCountries();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT Country_ID, Country " +
                                "FROM countries"
                );

                while (resultSet.next()) {
                    String countryName = resultSet.getString("Country");
                    int country_id = resultSet.getInt("Country_ID");

                    Country country = new Country(countryName, country_id);

                    Main.countryList.addCountry(country);
                }

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // loadDivisions();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT Division_ID, Division, Country_ID " +
                                "FROM first_level_divisions"
                );


                while (resultSet.next()) {
                    int div_id = resultSet.getInt("Division_ID");
                    String divName = resultSet.getString("Division");
                    if (divName.equals("QuÃ©bec")) {
                        divName = "Québec";
                    }
                    int country_id = resultSet.getInt("Country_ID");

                    Division division = new Division(div_id, divName, country_id);
                    Main.divisionList.addDivision(division);

                }
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            // loadCustomers();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(
                        "SELECT DISTINCT " +
                                "customers.*, " +
                                "first_level_divisions.Division, " +
                                "first_level_divisions.Division_ID, " +
                                "countries.Country_ID, " +
                                "countries.Country " +
                                "FROM customers " +
                                "JOIN first_level_divisions " +
                                "ON customers.Division_ID = first_level_divisions.Division_ID " +
                                "JOIN countries " +
                                "ON first_level_divisions.Country_ID = countries.Country_ID"
                );


                while (resultSet.next()) {
                    int id = resultSet.getInt("Customer_ID");
                    String name = resultSet.getString("Customer_Name");
                    String address = resultSet.getString("Address");
                    String postal = resultSet.getString("Postal_Code");
                    String phone = resultSet.getString("Phone");
                    String created = resultSet.getString("Create_Date");
                    String creator = resultSet.getString("Created_By");
                    String updated = resultSet.getString("Last_Update");
                    String updater = resultSet.getString("Last_Updated_By");
                    int div_id = resultSet.getInt("Division_ID");
                    String division = resultSet.getString("Division");
                    if (division.equals("QuÃ©bec")) {
                        division = "Québec";
                    }

                    String country = resultSet.getString("Country");
                    int country_id = resultSet.getInt("Country_ID");

                    Customer customer = new Customer(id, name, address, postal, phone, created, creator, div_id, division
                            , country_id, country);

                    // Manually added to not affect constructor.
                    customer.setUpdated(updated);
                    customer.setUpdater(updater);

                    Main.customerList.addCustomer(customer);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            // loadUsers();
            try {
                Connection connection = DriverManager.getConnection(url, user, password);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users");


                while (resultSet.next()) {
                    String userName = resultSet.getString("User_Name");
                    String userPassword = resultSet.getString("Password");
                    int id = resultSet.getInt("User_ID");

                    Main.authenticator.addUser(userName, userPassword);
                    Main.authenticator.addId(userName, id);
                }
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        loader.load();

        Parent root = FXMLLoader.load(getClass().getResource("../View/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
