package Model;

import Control.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Schedule {
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**
     * adds an appointment to the list of appointments.
     * @param appointment the appointment to add.
     */
    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    /**
     * checks to see if a given customer has any appointments.
     * @param customer the customer to check.
     * @return a boolean stating if the customer has any appointments or not.
     */
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

    /**
     * updates an existing appointment.
     * @param apptUpdate the appointment object holding the updated information.
     * @param updater the user that issued the update.
     * @param updated the time this update was issued.
     */
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

    /**
     * removes an appointment from the schedule.
     * @param selectedAppointment the appointment to remove.
     */
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

    /**
     * returns an appointment based on a supplied id.
     * @param desiredId the id of the appointment to retrieve.
     * @return the appointment found based on the supplied id.
     */
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

    /**
     *
     * @return the list of appointments.
     */
    public ObservableList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * generates an id for new appointments.
     * @return the new id.
     */
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

    /**
     * Checks to see if an appointment already exists based on a supplied appointment id.
     * @param testId the id to check.
     * @return a boolean stating if the appointment exists or not.
     */
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

    /**
     * checks to see if a given customer has any overlapping appointments.
     * @param startDate the starting date of the appointment.
     * @param endDate the ending date of teh appointment.
     * @param cust_id the id of the customer associated with the appointment.
     * @return a boolean stating if an appointment overlaps with existing appointments or not.
     */
    public boolean doesApptOverlap(String startDate, String endDate, int cust_id, int appt_id) {
        boolean apptOverlaps = false;
        Timestamp newStart = Timestamp.valueOf(startDate);
        Timestamp newEnd = Timestamp.valueOf(endDate);

        for (Appointment appointment : appointments) {
            if (appt_id != appointment.getAppt_id()) {
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
        }

        return apptOverlaps;
    }

    /**
     * checks to see if there are any appointments within 15 minutes of the current time.
     * @return the appointment found to be within 15 minutes.
     */
    public Appointment apptIn15() {
        LocalDateTime currentLdt = LocalDateTime.now();
        int curDay = currentLdt.getDayOfYear();
        int curHour = currentLdt.getHour();
        int curMin = currentLdt.getMinute();
        int user = Main.authenticator.getCurrentId();


        Appointment foundAppointment = null;

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
                                    foundAppointment = appointment;
                                }
                            }
                        }
                    }
                    else if (startHour == curHour) {
                        // start hour is the same
                        int timeUntilAppt = startMinute - curMin;
                        if ((timeUntilAppt <= 15) && (timeUntilAppt > 0)) {
                            foundAppointment = appointment;
                        }
                    }
                }

            }

        }
        return foundAppointment;
    }

    /**
     * update a HashMap with new information.
     * @param map the HashMap to update.
     * @param type the typeString to add as a key.
     * @return the updated HashMap.
     */
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

    /**
     * Unpacks a HashMap into a formatted report string.
     * @param map the HashMap to unpack.
     * @param month the month string.
     * @param report the report string this information will be added to.
     * @return the updated report.
     */
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

    /**
     * generates a report of the total appointment types by month.
     * @return the generated report.
     */
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

    /**
     * generates a report of appointments whose start time are past the current time.
     * @return the generated report.
     */
    public String getExpiredAppointments() {
        // Current Time
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
        ZonedDateTime utc = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        String currentTime = utc.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Timestamp currentTs = Timestamp.valueOf(currentTime);

        ArrayList<Appointment> expiredAppointments = new ArrayList<Appointment>();
        for (Appointment appointment : appointments) {
            String start = appointment.getStart();
            Timestamp appointmentTs = Timestamp.valueOf(start);
            if (currentTs.compareTo(appointmentTs) > 0) {
                // If the current time is after the start date of an appointment. That appointment is expired.
                expiredAppointments.add(appointment);
            }
        }

        String report = "Expired Appointments:\n";
        for (Appointment appointment : expiredAppointments) {
            String title = appointment.getTitle();
            int id = appointment.getAppt_id();
            String start = appointment.getStart();
            String end = appointment.getEnd();
            report += String.format("\nID: %d\nTitle: %s\nStart Time: %s\nEnd Time: %s\n", id, title, start, end);
        }

        return report;
    }

    /**
     * generates a report of all the appointments associated with a contact.
     * @return the generated report.
     */
    public String appointmentByContact() {
        HashMap<Integer, ArrayList<Appointment>> contactAppointments = new HashMap<Integer, ArrayList<Appointment>>();

        for (Contact contact : Main.contactList.getContacts()) {
            ArrayList<Appointment> appointmentList = new ArrayList<>();
            int id = contact.getId();

            for (Appointment appointment : appointments) {
                int foundId = appointment.getContact().getId();
                if (id == foundId) { // appointment belongs to contact
                    appointmentList.add(appointment);
                }
            }

            if (!appointmentList.isEmpty()) {
                contactAppointments.put(id, appointmentList);
            }
        }

        String report = "";

        for (int id : contactAppointments.keySet()) {
            String contactName = Main.contactList.getContact(id).getName();
            report += String.format("%s:\n", contactName);

            ArrayList<Appointment> appointmentList = contactAppointments.get(id);
            for (Appointment appointment: appointmentList) {
                int appt_id = appointment.getAppt_id();
                String title = appointment.getTitle();
                String type = appointment.getType();
                String description = appointment.getDescription();
                String start = appointment.getStart();
                String end = appointment.getEnd();
                int cust_id = appointment.getCust_id();

                report += String.format("%s - %s\nAppointment ID: %d\n%s\n%s - %s\nCustomer ID: %d\n\n", title,
                        type, appt_id,
                        description, start,
                        end, cust_id);
            }

            report += "\n";
        }

        return report;
    }

    /**
     * sets the display of every appointment to the week view.
     */
    public void switchToWeekView() {
        for (Appointment appointment : appointments) {
            appointment.displayWeek();
        }
    }

    /**
     * sets the display of every appointment to the month view.
     */
    public void switchToMonthView() {
        for (Appointment appointment : appointments) {
            appointment.displayMonth();
        }
    }
}
