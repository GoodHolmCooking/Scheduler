package Model;

import java.sql.Date;
import java.sql.Timestamp;

public class Appointment {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public Appointment(String type) {
        this.type = type;
    }
}


