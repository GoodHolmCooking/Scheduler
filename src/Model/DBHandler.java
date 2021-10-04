package Model;

import Control.Main;
import java.sql.*;

public class DBHandler {

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
}
