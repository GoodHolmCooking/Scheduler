package Model;

import java.sql.Timestamp;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private Timestamp created;
    private String creator;
    private Timestamp updated;
    private String updater;
    private int div_id;
    private String country;
    private int country_id;
    private String division;

    public Customer(int id, String name, String address, String postal, String phone, Timestamp created,
                    String creator, int div_id, String division, int country_id, String country) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postal = postal;
        this.phone = phone;
        this.created = created;
        this.creator = creator;
        this.div_id = div_id;
        this.country = country;
        this.division = division;
        this.country_id = country_id;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public int getDiv_id() {
        return div_id;
    }

    public void setDiv_id(int div_id) {
        this.div_id = div_id;
    }
}
