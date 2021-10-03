package Model;

import Control.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Schedule {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public boolean doesCustHaveAppt(Customer customer) {
        boolean hasAppt = false;

        for (Appointment appointment : appointments) {
            if (appointment.getCust_id() == customer.getId()) {
                hasAppt = true;
                break;
            }
        }

        return hasAppt;
    }

    public void updateAppointment(Appointment apptUpdate, String updater, String updated) {
        int updateId = apptUpdate.getAppt_id();
        String updateStart = apptUpdate.getStart();
        String updateEnd = apptUpdate.getEnd();
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

    public boolean doesApptExist(int testId) {
        boolean apptExists = false;

        for (Appointment appointment : appointments) {
            if (testId == appointment.getAppt_id()) {
                apptExists = true;
                break;
            }
        }

        return apptExists;
    }

    public boolean doesApptOverlap(String startDate, String endDate, int cust_id) {
        boolean apptOverlaps = false;
        Timestamp newStart = Timestamp.valueOf(startDate);
        Timestamp newEnd = Timestamp.valueOf(endDate);

        for (Appointment appointment : appointments) {
            if (appointment.getCust_id() == cust_id) {
                Timestamp start = Timestamp.valueOf(appointment.getStart());
                Timestamp end = Timestamp.valueOf(appointment.getEnd());

                if ((newStart.compareTo(start) > 0) && (newStart.compareTo(end) < 0)) {
                    // Begins during another appointment.
                    apptOverlaps = true;
                    break;
                } else if ((newEnd.compareTo(start) > 0) && (newEnd.compareTo(end) < 0)) {
                    // Ends during another appointment.
                    apptOverlaps = true;
                    break;
                }
            }
        }

        return apptOverlaps;
    }

    public boolean apptIn15() {
        boolean apptIn15 = false;
        LocalDateTime currentLdt = LocalDateTime.now();
        int curDay = currentLdt.getDayOfYear();
        int curHour = currentLdt.getHour();
        int curMin = currentLdt.getMinute();

        int user = Main.authenticator.getCurrentId();

        for (Appointment appointment : appointments) {
            if (appointment.getUser_id() == user) {
                Timestamp start = Timestamp.valueOf(appointment.getStart());
                LocalDateTime startLdt = start.toLocalDateTime();
                int startDay = startLdt.getDayOfYear();
                int startHour = startLdt.getHour();
                int startMinute = startLdt.getMinute();

                if (curDay == startDay) {

                    if (startHour > curHour) {
                        // start hour is more than the current hour
                        if ((startHour - curHour) == 1) {
                            // start hour is exactly one hour greater than current time.
                            if (curMin >= 45) {
                                // check minute of next hour
                                int timeUntilAppt = 60 - curMin + startMinute;
                                if (timeUntilAppt <= 15) {
                                    apptIn15 = true;
                                }
                            }
                        } else if (startHour == curHour) {
                            // start hour is the same
                            int timeUntilAppt = startMinute - curMin;
                            if ((timeUntilAppt <= 15) && (timeUntilAppt > 0)) {
                                apptIn15 = true;
                            }
                        }
                    }

                }

            }

        }
        return apptIn15;
    }

    private HashMap<String, Integer> updateMap(HashMap<String, Integer> map, String type) {
        if (!map.containsKey(type)) { // No key has been found. Assign  it and set count to 1.
            map.put(type, 1);
        }
        else { // if a key is found, increase the count by 1.
            int oldValue = map.get(type);
            map.put(type, oldValue + 1);
        }

        return map;
    }

    private String unloadMonthMap(HashMap<String, Integer> map, String month, String report) {
        if (!map.isEmpty()) {
            report += String.format("%s:\n", month);
            for (String type : map.keySet()) {
                int amt = map.get(type);
                report += String.format("%s: %d \n", type, amt);
            }
        }

        report += "\n";

        return report;
    }

    public String totalByType() {

        // HashMap per month
        HashMap<String, Integer> janMap = new HashMap<String, Integer>();
        HashMap<String, Integer> febMap = new HashMap<String, Integer>();
        HashMap<String, Integer> marchMap = new HashMap<String, Integer>();
        HashMap<String, Integer> aprilMap = new HashMap<String, Integer>();
        HashMap<String, Integer> mayMap = new HashMap<String, Integer>();
        HashMap<String, Integer> juneMap = new HashMap<String, Integer>();
        HashMap<String, Integer> julyMap = new HashMap<String, Integer>();
        HashMap<String, Integer> augMap = new HashMap<String, Integer>();
        HashMap<String, Integer> septMap = new HashMap<String, Integer>();
        HashMap<String, Integer> octMap = new HashMap<String, Integer>();
        HashMap<String, Integer> novMap = new HashMap<String, Integer>();
        HashMap<String, Integer> decMap = new HashMap<String, Integer>();

        for (Appointment appointment : appointments) {
            Timestamp start = Timestamp.valueOf(appointment.getStart());
            int month = start.toLocalDateTime().getMonthValue();
            String apptType = appointment.getType();


            switch (month) {
                case 1: // January
                    janMap = updateMap(janMap, apptType);
                    break;
                case 2: // February
                    febMap = updateMap(febMap, apptType);
                    break;
                case 3: // March
                    marchMap = updateMap(marchMap, apptType);
                    break;
                case 4: // April
                    aprilMap = updateMap(aprilMap, apptType);
                    break;
                case 5: // May
                    mayMap = updateMap(mayMap, apptType);
                    break;
                case 6: // June
                    juneMap = updateMap(juneMap, apptType);
                    break;
                case 7: // July
                    julyMap = updateMap(julyMap, apptType);
                    break;
                case 8: // August
                    augMap = updateMap(augMap, apptType);
                    break;
                case 9: // September
                    septMap = updateMap(septMap, apptType);
                    break;
                case 10: // October
                    octMap = updateMap(octMap, apptType);
                    break;
                case 11: // November
                    novMap = updateMap(novMap, apptType);
                    break;
                case 12: // December
                    decMap = updateMap(decMap, apptType);
                    break;

                default:
                    break;
            }
        }

        String report = "";

        if (!janMap.isEmpty()) {
            report = unloadMonthMap(janMap, "January", report);
        }

        if (!febMap.isEmpty()) {
            report = unloadMonthMap(febMap, "February", report);
        }

        if (!marchMap.isEmpty()) {
            report = unloadMonthMap(marchMap, "March", report);
        }

        if (!aprilMap.isEmpty()) {
            report = unloadMonthMap(aprilMap, "April", report);

        }

        if (!mayMap.isEmpty()) {
            report = unloadMonthMap(mayMap, "May", report);
        }

        if (!juneMap.isEmpty()) {
            report = unloadMonthMap(juneMap, "June", report);
        }

        if (!julyMap.isEmpty()) {
            report = unloadMonthMap(julyMap, "July", report);
        }

        if (!augMap.isEmpty()) {
            report = unloadMonthMap(augMap, "August", report);
        }

        if (!septMap.isEmpty()) {
            report = unloadMonthMap(septMap, "September", report);
        }

        if (!octMap.isEmpty()) {
            report = unloadMonthMap(octMap, "October", report);
        }

        if (!novMap.isEmpty()) {
            report = unloadMonthMap(novMap, "November", report);

        }

        if (!decMap.isEmpty()) {
            report = unloadMonthMap(decMap, "December", report);
        }

        return report;

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
