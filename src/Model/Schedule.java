package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }
}
