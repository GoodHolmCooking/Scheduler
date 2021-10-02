package Model;

import java.util.Arrays;
import java.util.HashMap;

public class Customer {
    private int id;
    private String name;
    private String address;
    private String postal;
    private String phone;
    private String created;
    private String creator;
    private String updated;
    private String updater;
    private int div_id;
    private String country;
    private int country_id;
    private String division;

    public Customer(int id, String name, String address, String postal, String phone, String created,
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
        this.updated = null;
        this.updater = null;
    }

    private int findFirstLetterIndex(String searchString) {
        int splitIndex = 0;
        int loopIndex = 0;
        for (char character : searchString.toCharArray()) {
            try {
                Integer.parseInt(String.valueOf(character));
                loopIndex += 1;
            }
            catch (Exception e) { // Tries to convert. It can't. This is where the split should start.
                splitIndex = loopIndex;
            }
        }

        return splitIndex;
    }

    private boolean checkForCity(String searchString) {
        boolean cityFound = false;

        for (char character : searchString.toCharArray()) {
            if (character == ',') {
                cityFound = true;
                break;
            }
        }

        return cityFound;
    }

    public HashMap<String, String> getAddressMap() {
        HashMap<String, String> addressMap = new HashMap<String, String>();
        String[] splitAddress = this.address.split(",");
        String numName = splitAddress[0];

        System.out.println(numName);

        int splitIndex = findFirstLetterIndex(numName);

        String streetName = numName.substring(splitIndex);
        String streetNum = numName.substring(0, splitIndex);

        boolean cityFound = checkForCity(numName);
        if (cityFound) {
            String city = splitAddress[1];
            addressMap.put("city", city);
        }

        addressMap.put("name", streetName);
        addressMap.put("number", streetNum);

        return addressMap;
    }

    public String getCity() {
        String city = "";

        String[] splitAddress = this.address.split(",");

        int addressLen = (int) Arrays.stream(splitAddress).count();
        if (addressLen > 1) {
            city = splitAddress[1];
        }

        return city;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

    public int getDiv_id() {
        return div_id;
    }

    public void setDiv_id(int div_id) {
        this.div_id = div_id;
    }
}
