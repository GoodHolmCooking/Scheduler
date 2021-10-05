package Model;

public class Division {
    private int div_id;
    private String name;
    private int country_id;

    public Division(int div_id, String name, int country_id) {
        this.div_id = div_id;
        this.name = name;
        this.country_id = country_id;
    }

    /**
     *
     * @return the id of the division.
     */
    public int getDiv_id() {
        return div_id;
    }

    /**
     *
     * @return the name of the division.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name update the customer's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the country's id.
     */
    public int getCountry_id() {
        return country_id;
    }
}
