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

    public int getDiv_id() {
        return div_id;
    }

    public void setDiv_id(int div_id) {
        this.div_id = div_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }
}
