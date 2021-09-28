package Model;

import Control.Main;

import java.sql.*;

public class Loader {
    public void loadSchedule() {
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
                Appointment appointment = new Appointment(appt_id, start, end, title, type, description, location,
                        cust_id, contact, user_id);
                Main.schedule.addAppointment(appointment);

            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadContactList() {
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

    public void saveAppointment(Appointment appointment) {
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
            statement.setTimestamp(2, start); // Start
            statement.setTimestamp(3, end); // End
            statement.setString(4, title); // Title
            statement.setString(5, type); // Type
            statement.setString(6, description); // Description
            statement.setString(7, location); // Location
            statement.setInt(8, cust_id); // Customer ID
            statement.setTimestamp(9, Timestamp.valueOf("2021-09-10 13:14:10")); // Create Date
            statement.setString(10, "TestBot"); // Created By
            statement.setTimestamp(11, Timestamp.valueOf("2021-09-10 13:14:10")); // Last Update
            statement.setString(12, "TestBot"); // Last Updated By
            statement.setInt(13, user_id); // User ID
            statement.setInt(14, contact_id); // Contact ID

            statement.executeUpdate();

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment) {
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
            statement.setTimestamp(7, Timestamp.valueOf("2021-09-10 13:14:10")); // Create Date
            statement.setString(8, "TestBot"); // Created By
            statement.setTimestamp(9, Timestamp.valueOf("2021-09-10 13:14:10")); // Last Update
            statement.setString(10, "TestBot"); // Last Updated By
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

}
