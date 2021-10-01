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

    public LocalDate getStartDate(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");
        return LocalDate.of(year, month, day);
    }

    public int getStartHr(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        return dateMap.get("hour");
    }

    public int getStartMin(){
        HashMap<String, Integer> dateMap = parseDate(this.start);
        return dateMap.get("minute");
    }

    public LocalDate getEndDate(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        int year = dateMap.get("year");
        int month = dateMap.get("month");
        int day = dateMap.get("day");
        return LocalDate.of(year, month, day);
    }

    public int getEndHr(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        return dateMap.get("hour");
    }

    public int getEndMin(){
        HashMap<String, Integer> dateMap = parseDate(this.end);
        return dateMap.get("minute");
    }

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

    public int getAppt_id() {
        return appt_id;
    }

    public void setAppt_id(int appt_id) {
        this.appt_id = appt_id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
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

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getCreated() {
        return created;
    }

    public String getCreator() {
        return creator;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }
}


