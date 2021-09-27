package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    private int appt_id;
    private Date start;
    private Date end;
    private String title;
    private String type;
    private String description;
    private String location;
    private int cust_id;

    public Appointment(int appt_id, Date start, Date end, String title, String type, String description, String location, int cust_id) {
        this.appt_id = appt_id;
        this.start = start;
        this.end = end;
        this.title = title;
        this.type = type;
        this.description = description;
        this.location = location;
        this.cust_id = cust_id;
    }

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
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
}


