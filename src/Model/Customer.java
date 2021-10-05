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

    /**
     * finds the index of the first letter of the string.
     * @param searchString the string to search.
     * @return returns the index of the first letter.
     */
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
                break;
            }
        }

        return splitIndex;
    }

    /**
     * Looks at an address and sees if there is an address attached to it.
     * @param searchString the string to search.
     * @return a boolean stating if there was a city found in this string or not.
     */
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

    /**
     *
     * @return a HashMap storing the street name and the street number.
     */
    public HashMap<String, String> getAddressMap() {
        HashMap<String, String> addressMap = new HashMap<String, String>();
        String[] splitAddress = this.address.split(",");
        String numName = splitAddress[0];

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

    /**
     *
     * @return returns the city from the customer's address.
     */
    public String getCity() {
        String city = "";

        String[] splitAddress = this.address.split(",");

        int addressLen = (int) Arrays.stream(splitAddress).count();
        if (addressLen > 1) {
            city = splitAddress[1];
        }

        return city;
    }


    /**
     *
     * @return the customer's country id.
     */
    public int getCountry_id() {
        return country_id;
    }

    /**
     *
     * @param country_id the country id to set.
     */
    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    /**
     *
     * @return the customer's country.
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country the country to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     *
     * @return the division the customer lives in.
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division the country to division to set.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     *
     * @return the customer's id.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the customer's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address the address to set.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return the customer's postal code.
     */
    public String getPostal() {
        return postal;
    }

    /**
     *
     * @param postal the postal code to set.
     */
    public void setPostal(String postal) {
        this.postal = postal;
    }

    /**
     *
     * @return the customer's phone number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone the phone number to set.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return the time the customer was created.
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @return the user that created this customer.
     */
    public String getCreator() {
        return creator;
    }

    /**
     *
     * @param updated the last time this customer was updated.
     */
    public void setUpdated(String updated) {
        this.updated = updated;
    }

    /**
     *
     * @param updater the user that last updated this customer.
     */
    public void setUpdater(String updater) {
        this.updater = updater;
    }

    /**
     *
     * @return the division id of this customer.
     */
    public int getDiv_id() {
        return div_id;
    }

    /**
     *
     * @param div_id the division id to set.
     */
    public void setDiv_id(int div_id) {
        this.div_id = div_id;
    }
}
