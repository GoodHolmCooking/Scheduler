package Model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class Appointment {
    private int appt_id;
    private String start;
    private String end;
    private String title;
    private String type;
    private String description;
    private String location;
    private int cust_id;
    private Contact contact;
    private int user_id;
    private String display;
    private String created;
    private String creator;
    private String updated;
    private String updater;


    public Appointment(int appt_id, String start, String end, String title, String type, String description,
                       String location, int cust_id, Contact contact, int user_id, String created, String creator) {
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
        this.created = created;
        this.creator = creator;
        this.updated = null;
        this.updater = null;
    }

    /**
     * @param ts the Timestamp to parse.
     * @return A HashMap of the Year, Month, Day, Hour, Minute, and Second.
     */
    private HashMap<String, Integer> parseDate(String ts) {
        // Initial string example: "2021-09-10 13:14:10"

        String[] dateStrings = ts.split(" ");
        // After split: {"2021-09-10", "13:14:10"}

        String[] yearMonthDay = dateStrings[0].split("-");
        // After split: {"2021", "09", "10"}
        int year = Integer.parseInt(yearMonthDay[0]);
        int month = Integer.parseInt(yearMonthDay[1]);
        int day = Integer.parseInt(yearMonthDay[2]);

        String[] hourMinSec = dateStrings[1].split(":");
        int hour = Integer.parseInt(hourMinSec[0]);
        int minute = Integer.parseInt(hourMinSec[1]);
        int second = Integer.parseInt(hourMinSec[2]);

        HashMap<String, Integer> dateMap = new HashMap<String, Integer>();
        dateMap.put("year", year);
        dateMap.put("month", month);
        dateMap.put("day", day);
        dateMap.put("hour", hour);
        dateMap.put("minute", minute);
        dateMap.put("second", second);

        return dateMap;
    }

    /**
     *
     * @return the appointment's start date as a LocalDate.
     */
    public LocalDate getStartDate(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");
        return LocalDate.of(year, month, day);
    }

    /**
     *
     * @return the starting hour of the appointment.
     */
    public int getStartHr(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        return dateMap.get("hour");
    }


    /**
     *
     * @return the starting minutes of the appointment.
     */
    public int getStartMin(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        return dateMap.get("minute");
    }

    /**
     *
     * @return the appointment's end date returned as a LocalDate.
     */
    public LocalDate getEndDate(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");
        return LocalDate.of(year, month, day);
    }

    /**
     *
     * @return the appointment's ending hour.
     */
    public int getEndHr(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        return dateMap.get("hour");
    }

    /**
     *
     * @return the appointment's ending minute.
     */
    public int getEndMin(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        return dateMap.get("minute");
    }

    /**
     * this sets the appointment's display method to show the month as a string.
     */
    public void displayMonth() {
        Calendar cal = Calendar.getInstance();
        int id = this.appt_id;

        HashMap<String, Integer> dateMap = parseDate(this.start);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");

        Instant localDate = LocalDate.of(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC);
        Date date = Date.from(localDate);


        cal.setTime(date);

        int calMonth = cal.get(Calendar.MONTH);
        this.display = new DateFormatSymbols().getMonths()[month-1];
    }

    /**
     * sets the appointment's display method to a week number.
     */
    public void displayWeek() {
        Calendar cal = Calendar.getInstance();
        int id = this.appt_id;

        HashMap<String, Integer> dateMap = parseDate(this.start);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");

        Instant localDate = LocalDate.of(year, month, day).atStartOfDay().toInstant(ZoneOffset.UTC);
        Date date = Date.from(localDate);

        cal.setTime(date);

        int week = cal.get(Calendar.WEEK_OF_YEAR);
        this.display = String.valueOf(week);
    }

    /**
     *
     * @return the appointment's id.
     */
    public int getAppt_id() {
        return appt_id;
    }

    /**
     *
     * @param appt_id the id to set the appointment id to.
     */
    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    /**
     *
     * @return the appointment's start time as a string.
     */
    public String getStart() {
        return start;
    }

    /**
     *
     * @param start the appointment's start time.
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     *
     * @return the appointment's end time.
     */
    public String getEnd() {
        return end;
    }

    /**
     *
     * @param end the end time to set the appointment's end time to.
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     *
     * @return the appointment's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title the title to set the apointment to.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return the appointment's type.
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type the appointment's type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return the appointment's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description the appointment's description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return the appointment's location.
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location the appointment's location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return the customer id associated with the appointment.
     */
    public int getCust_id() {
        return cust_id;
    }

    /**
     *
     * @param cust_id the customer id assoicated with the appointment.
     */
    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    /**
     *
     * @return the contact associated with the appointment.
     */
    public Contact getContact() {
        return contact;
    }

    /**
     *
     * @param contact the contact associated with the appointment.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     *
     * @return the user id associated with the appointment.
     */
    public int getUser_id() {
        return user_id;
    }

    /**
     *
     * @param user_id the user id associated with the appointment.
     */
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    /**
     *
     * @return either the month as a string or the week as a number.
     */
    public String getDisplay() {
        return display;
    }

    /**
     *
     * @param display the month as a string or the week as a number to display.
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     *
     * @return the time this appointment was created.
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @return the user that created this appointment.
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @param updated the most recent time this appointment has been updated.
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     *
     * @param updater the user that most recently updated this appointment.
     */
    public void setUpdater(String updater) {
        this.updater = updater;
    }
}


