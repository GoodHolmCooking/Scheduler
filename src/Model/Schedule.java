package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

public class Schedule {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) { appointments.add(appointment); }

    public void updateAppointment(Appointment apptUpdate, String updater, Timestamp updated) {
        int updateId = apptUpdate.getAppt_id();
        Timestamp updateStart = apptUpdate.getStart();
        Timestamp updateEnd = apptUpdate.getEnd();
        String updateTitle = apptUpdate.getTitle();
        String updateType = apptUpdate.getType();
        String updateDescr = apptUpdate.getDescription();
        String updateLoc = apptUpdate.getLocation();
        int updateCustId = apptUpdate.getCust_id();
        Contact updateContact = apptUpdate.getContact();
        int updateUserId = apptUpdate.getUser_id();

        Appointment foundAppointment = null;

        for (Appointment appointment : appointments) {
            if (appointment.getAppt_id() == updateId) {
                foundAppointment = appointment;
                break;
            }
        }

        foundAppointment.setStart(updateStart);
        foundAppointment.setEnd(updateEnd);
        foundAppointment.setTitle(updateTitle);
        foundAppointment.setType(updateType);
        foundAppointment.setDescription(updateDescr);
        foundAppointment.setLocation(updateLoc);
        foundAppointment.setCust_id(updateCustId);
        foundAppointment.setContact(updateContact);
        foundAppointment.setUser_id(updateUserId);
        foundAppointment.setUpdated(updated);
        foundAppointment.setUpdater(updater);
    }

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

    public Appointment getAppointment(int desiredId) {
        Appointment foundAppointment = null;
        for (Appointment appointment : appointments) {
            int currentId = appointment.getAppt_id();
            if (currentId == desiredId) {
                foundAppointment = appointment;
                break;
            }
        }

        return foundAppointment;
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

    public boolean doesApptExist(Appointment testAppt) {
        int id = testAppt.getAppt_id();
        boolean apptExists = false;

        for (Appointment appointment : appointments) {
            if (id == appointment.getAppt_id()) {
                apptExists = true;
                break;
            }
        }

        return apptExists;
    }

    public void switchToWeekView() {
        for (Appointment appointment : appointments) {
            appointment.displayWeek();
        }
    }

    public void switchToMonthView() {
        for (Appointment appointment : appointments) {
            appointment.displayMonth();
        }
    }
}
