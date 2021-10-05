package Model;

public class Country {
    private String name;
    private int id;

    /**
     *
     * @param name the country name.
     * @param id the country id.
     */
    public Country(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     *
     * @return the country name.
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the country name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the country id.
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the country id to set.
     */
    public void setId(int id) {
        this.id = id;
    }
}
