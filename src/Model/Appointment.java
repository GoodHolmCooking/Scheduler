package Model;

import java.sql.Time;
import java.sql.Timestamp;

public class Appointment {
    private int appt_id;
    private Timestamp start;
    private Timestamp end;
    private String title;
    private String type;
    private String description;
    private String location;
    private int cust_id;
    private Contact contact;
    private int user_id;


    public Appointment(int appt_id, Timestamp start, Timestamp end, String title, String type, String description,
                       String location, int cust_id, Contact contact, int user_id) {
        this.appt_id = appt_id;
        this.start = start;
        this.end = end;
        this.title = title;
        this.type = type;
        this.description = description;
        this.location = location;
        this.cust_id = cust_id;
        this.contact = contact;
        this.user_id = user_id;
    }

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}


