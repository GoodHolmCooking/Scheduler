package Model;

import Control.Main;

import java.sql.*;

public class DBHandler {
    private void loadUsers() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");


            while (resultSet.next()) {
                String user = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");

                Main.authenticator.addUser(user, password);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void loadSchedule() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");


            while (resultSet.next()) {
                int appt_id = resultSet.getInt("Appointment_ID");
                Timestamp start = resultSet.getTimestamp("Start");
                Timestamp end = resultSet.getTimestamp("End");
                String title = resultSet.getString("Title");
                String type = resultSet.getString("Type");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                int cust_id = resultSet.getInt("Customer_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Contact contact = Main.contactList.getContact(contactId);
                int user_id = resultSet.getInt("User_ID");
                String creator = resultSet.getString("Created_By");
                Timestamp created = resultSet.getTimestamp("Create_Date");
                Timestamp updated = resultSet.getTimestamp("Last_Update");
                String updater = resultSet.getString("Last_Updated_By");

                Appointment appointment = new Appointment(appt_id, start, end, title, type, description, location,
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
    }

    private void loadContactList() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
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
    }

    public void addAppointment(Appointment appointment) {
        int appt_id = appointment.getAppt_id();
        Timestamp start = appointment.getStart();
        Timestamp end = appointment.getEnd();
        String title = appointment.getTitle();
        String type = appointment.getType();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        int cust_id = appointment.getCust_id();
        int user_id = appointment.getUser_id();
        int contact_id = appointment.getContact().getId();
        String creator = appointment.getCreator();
        Timestamp created = appointment.getCreated();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
            "INSERT INTO appointments (" +
                "Appointment_ID, " +
                "Start, " +
                "End, " +
                "Title, " +
                "Type, " +
                "Description, " +
                "Location, " +
                "Customer_ID," +
                    "Create_Date," +
                    "Created_By," +
                    "Last_Update," +
                    "Last_Updated_By," +
                    "User_ID," +
                    "Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            statement.setInt(1, appt_id); // Appointment ID
            statement.setTimestamp(2, start); // Start
            statement.setTimestamp(3, end); // End
            statement.setString(4, title); // Title
            statement.setString(5, type); // Type
            statement.setString(6, description); // Description
            statement.setString(7, location); // Location
            statement.setInt(8, cust_id); // Customer ID
            statement.setTimestamp(9, created); // Create Date
            statement.setString(10, creator); // Created By
            statement.setTimestamp(11, null); // Last Update
            statement.setString(12, null); // Last Updated By
            statement.setInt(13, user_id); // User ID
            statement.setInt(14, contact_id); // Contact ID

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment, String updater, Timestamp updated) {
        int appt_id = appointment.getAppt_id();
        Timestamp start = appointment.getStart();
        Timestamp end = appointment.getEnd();
        String title = appointment.getTitle();
        String type = appointment.getType();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        int cust_id = appointment.getCust_id();
        int user_id = appointment.getUser_id();
        int contact_id = appointment.getContact().getId();
        Timestamp created = appointment.getCreated();
        String creator = appointment.getCreator();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE appointments " +
                        "SET " +
                        "Title = ?," +
                        "Description = ?," +
                        "Location = ?," +
                        "Type = ?," +
                        "Start = ?," +
                        "End = ?," +
                        "Create_Date = ?," +
                        "Created_By = ?," +
                        "Last_Update = ?," +
                        "Last_Updated_By = ?," +
                        "Customer_ID = ?," +
                        "User_ID = ?," +
                        "Contact_ID = ? WHERE " +
                        "Appointment_ID = ?");

            statement.setString(1, title); // Title
            statement.setString(2, description); // Description
            statement.setString(3, location); // Location
            statement.setString(4, type); // Type
            statement.setTimestamp(5, start); // Start
            statement.setTimestamp(6, end); // End
            statement.setTimestamp(7, created); // Create Date
            statement.setString(8, creator); // Created By
            statement.setTimestamp(9, updated); // Last Update
            statement.setString(10, updater); // Last Updated By
            statement.setInt(11, cust_id); // Customer ID
            statement.setInt(12, user_id); // User ID
            statement.setInt(13, contact_id); // Contact ID
            statement.setInt(14, appt_id); // Appointment ID

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeAppointment(Appointment appointment) {
        int appt_id = appointment.getAppt_id();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments WHERE Appointment_ID " +
                    "= ?");

            statement.setInt(1, appt_id);

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCustomers() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
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
                Timestamp created = resultSet.getTimestamp("Create_Date");
                String creator = resultSet.getString("Created_By");
                Timestamp updated = resultSet.getTimestamp("Last_Update");
                String updater = resultSet.getString("Last_Updated_By");
                int div_id = resultSet.getInt("Division_ID");
                String division = resultSet.getString("Division");
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
    }

    private void loadDivisions() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
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
    }

    private void loadCountries() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
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
    }

    private void loadUtilityData() {
        loadCountries();
        loadDivisions();
    }

    public void initializeData() {
        loadContactList();
        loadSchedule();
        loadUtilityData();
        loadCustomers();
        loadUsers();
    }
}
