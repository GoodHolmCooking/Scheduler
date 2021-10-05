package Model;

public class Contact {
    private int id;
    private String name;
    private String email;

    public Contact(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     *
     * @return the id of the contact.
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
     * @return the name of the contact.
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
}
