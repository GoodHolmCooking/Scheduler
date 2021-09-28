package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Schedule {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }

    public void removeAppointment(Appointment selectedAppointment) {
        int id = selectedAppointment.getAppt_id();
        int loopIndex = 0;
        int selectedApptIndex = 0;
        for (Appointment appointment : appointments) {
            if (appointment.getAppt_id() == id) {
                selectedApptIndex = loopIndex;
                break;
            }

            loopIndex += 1;
        }

        appointments.remove(selectedApptIndex);
    }

    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    public int getCurrentId() {
        int highest_id = 0;
        for (Appointment appointment : appointments) {
            int loop_id = appointment.getAppt_id();
            if (loop_id > highest_id) {
                highest_id = loop_id;
            }
        }

        int current_id = highest_id + 1;
        return current_id;
    }
}
