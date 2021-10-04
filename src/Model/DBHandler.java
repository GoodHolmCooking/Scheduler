package Model;

import Control.Main;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DBHandler {
//    private void loadUsers() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
//
//
//            while (resultSet.next()) {
//                String user = resultSet.getString("User_Name");
//                String password = resultSet.getString("Password");
//                int id = resultSet.getInt("User_ID");
//
//                Main.authenticator.addUser(user, password);
//                Main.authenticator.addId(user, id);
//            }
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


//    private void loadSchedule() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM appointments");
//
//
//            while (resultSet.next()) {
//                int appt_id = resultSet.getInt("Appointment_ID");
//                String start = resultSet.getString("Start");
//                String end = resultSet.getString("End");
//                String title = resultSet.getString("Title");
//                String type = resultSet.getString("Type");
//                String description = resultSet.getString("Description");
//                String location = resultSet.getString("Location");
//                int cust_id = resultSet.getInt("Customer_ID");
//                int contactId = resultSet.getInt("Contact_ID");
//                Contact contact = Main.contactList.getContact(contactId);
//                int user_id = resultSet.getInt("User_ID");
//                String creator = resultSet.getString("Created_By");
//                String created = resultSet.getString("Create_Date");
//                String updated = resultSet.getString("Last_Update");
//                String updater = resultSet.getString("Last_Updated_By");
//
//                // Start
//                LocalDateTime startLdt = Timestamp.valueOf(start).toLocalDateTime();
//                ZonedDateTime startZdt = ZonedDateTime.of(startLdt, ZoneId.of("UTC"));
//                ZonedDateTime startLocTime = startZdt.withZoneSameInstant(ZoneId.systemDefault());
//                String formattedStart = startLocTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//                // End
//                LocalDateTime endLdt = Timestamp.valueOf(end).toLocalDateTime();
//                ZonedDateTime endZdt = ZonedDateTime.of(endLdt, ZoneId.of("UTC"));
//                ZonedDateTime endLocTime = endZdt.withZoneSameInstant(ZoneId.systemDefault());
//                String formattedEnd = endLocTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//                Appointment appointment = new Appointment(appt_id, formattedStart, formattedEnd, title, type, description,
//                        location,
//                        cust_id, contact, user_id, created, creator);
//
//                // Manually added to not cause problems with constructor.
//                appointment.setUpdater(updater);
//                appointment.setUpdated(updated);
//
//                Main.schedule.addAppointment(appointment);
//
//            }
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void loadContactList() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM contacts");
//
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("Contact_ID");
//                String name = resultSet.getString("Contact_Name");
//                String email = resultSet.getString("Email");
//                Contact contact = new Contact(id, name, email);
//
//                Main.contactList.addContact(contact);
//            }
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void addAppointment(Appointment appointment) {
        int appt_id = appointment.getAppt_id();
        String start = appointment.getStart();
        String end = appointment.getEnd();
        String title = appointment.getTitle();
        String type = appointment.getType();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        int cust_id = appointment.getCust_id();
        int user_id = appointment.getUser_id();
        int contact_id = appointment.getContact().getId();
        String creator = appointment.getCreator();
        String created = appointment.getCreated();

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

            statement.setInt(1, appt_id); // Appointment ID
            statement.setString(2, start); // Start
            statement.setString(3, end); // End
            statement.setString(4, title); // Title
            statement.setString(5, type); // Type
            statement.setString(6, description); // Description
            statement.setString(7, location); // Location
            statement.setInt(8, cust_id); // Customer ID
            statement.setString(9, created); // Create Date
            statement.setString(10, creator); // Created By
            statement.setString(11, null); // Last Update
            statement.setString(12, null); // Last Updated By
            statement.setInt(13, user_id); // User ID
            statement.setInt(14, contact_id); // Contact ID

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment, String updater, String updated) {
        int appt_id = appointment.getAppt_id();
        String start = appointment.getStart();
        String end = appointment.getEnd();
        String title = appointment.getTitle();
        String type = appointment.getType();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        int cust_id = appointment.getCust_id();
        int user_id = appointment.getUser_id();
        int contact_id = appointment.getContact().getId();

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
            statement.setString(5, start); // Start
            statement.setString(6, end); // End
            statement.setString(7, updated); // Last Update
            statement.setString(8, updater); // Last Updated By
            statement.setInt(9, cust_id); // Customer ID
            statement.setInt(10, user_id); // User ID
            statement.setInt(11, contact_id); // Contact ID
            statement.setInt(12, appt_id); // Appointment ID

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

    public void addCustomer(Customer customer) {
        int id = customer.getId();
        String name = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostal();
        String phone = customer.getPhone();
        String created = customer.getCreated();
        String creator = customer.getCreator();
        int div_id = customer.getDiv_id();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO customers (" +
                            "Customer_ID, " +
                            "Customer_Name, " +
                            "Address, " +
                            "Postal_Code, " +
                            "Phone, " +
                            "Create_Date, " +
                            "Created_By, " +
                            "Last_Update," +
                            "Last_Updated_By," +
                            "Division_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            statement.setInt(1, id); // Customer ID
            statement.setString(2, name); // Customer Name
            statement.setString(3, address); // Address
            statement.setString(4, postal); // Postal Code
            statement.setString(5, phone); // Phone
            statement.setString(6, created); // Create Date
            statement.setString(7, creator); // Created By
            statement.setTimestamp(8, null); // Last Update
            statement.setString(9, null); // Last Updated By
            statement.setInt(10, div_id); // Division ID

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer, String updated) {
        int id = customer.getId();
        String name = customer.getName();
        String address = customer.getAddress();
        String postal = customer.getPostal();
        String phone = customer.getPhone();
        String updater = Main.authenticator.getCurrentUser();
        int div_id = customer.getDiv_id();
        String country = customer.getCountry();
        int country_id = customer.getCountry_id();
        String division = customer.getDivision();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE customers " +
                        "SET " +
                        "Customer_Name = ?," +
                        "Address = ?," +
                        "Postal_Code = ?," +
                        "Phone = ?," +
                        "Last_Update = ?," +
                        "Last_Updated_By = ?," +
                        "Division_ID = ? WHERE " +
                        "Customer_ID = ?");

            statement.setString(1, name);
            statement.setString(2, address);
            statement.setString(3, postal);
            statement.setString(4, phone);
            statement.setString(5, updated);
            statement.setString(6, updater);
            statement.setInt(7, div_id);
            statement.setInt(8, id);

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeCustomer(Customer customer) {
        int id = customer.getId();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
            PreparedStatement statement = connection.prepareStatement("DELETE FROM customers WHERE Customer_ID " +
                    "= ?");

            statement.setInt(1, id);

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void loadCustomers() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT DISTINCT " +
//                            "customers.*, " +
//                            "first_level_divisions.Division, " +
//                            "first_level_divisions.Division_ID, " +
//                            "countries.Country_ID, " +
//                            "countries.Country " +
//                        "FROM customers " +
//                        "JOIN first_level_divisions " +
//                        "ON customers.Division_ID = first_level_divisions.Division_ID " +
//                        "JOIN countries " +
//                        "ON first_level_divisions.Country_ID = countries.Country_ID"
//            );
//
//
//            while (resultSet.next()) {
//                int id = resultSet.getInt("Customer_ID");
//                String name = resultSet.getString("Customer_Name");
//                String address = resultSet.getString("Address");
//                String postal = resultSet.getString("Postal_Code");
//                String phone = resultSet.getString("Phone");
//                String created = resultSet.getString("Create_Date");
//                String creator = resultSet.getString("Created_By");
//                String updated = resultSet.getString("Last_Update");
//                String updater = resultSet.getString("Last_Updated_By");
//                int div_id = resultSet.getInt("Division_ID");
//                String division = resultSet.getString("Division");
//                if (division.equals("QuÃ©bec")) {
//                    division = "Québec";
//                }
//
//                String country = resultSet.getString("Country");
//                int country_id = resultSet.getInt("Country_ID");
//
//                Customer customer = new Customer(id, name, address, postal, phone, created, creator, div_id, division
//                        , country_id, country);
//
//                // Manually added to not affect constructor.
//                customer.setUpdated(updated);
//                customer.setUpdater(updater);
//
//                Main.customerList.addCustomer(customer);
//            }
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void loadDivisions() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT Division_ID, Division, Country_ID " +
//                        "FROM first_level_divisions"
//            );
//
//
//            while (resultSet.next()) {
//                int div_id = resultSet.getInt("Division_ID");
//                String divName = resultSet.getString("Division");
//                if (divName.equals("QuÃ©bec")) {
//                    divName = "Québec";
//                }
//                int country_id = resultSet.getInt("Country_ID");
//
//                Division division = new Division(div_id, divName, country_id);
//                Main.divisionList.addDivision(division);
//
//            }
//            connection.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    private void loadCountries() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/project", "root", "root");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(
//                    "SELECT Country_ID, Country " +
//                        "FROM countries"
//            );
//
//            while (resultSet.next()) {
//                String countryName = resultSet.getString("Country");
//                int country_id = resultSet.getInt("Country_ID");
//
//                Country country = new Country(countryName, country_id);
//
//                Main.countryList.addCountry(country);
//            }
//
//            connection.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void loadUtilityData() {
//        loadCountries();
//        loadDivisions();
//    }
//
//    public void initializeData() {
//        loadContactList();
//        loadSchedule();
//        loadUtilityData();
//        loadCustomers();
//        loadUsers();
//    }
}
