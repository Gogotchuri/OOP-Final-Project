package models;

/**
 * Class encapsulating a single category
 */
public class Category {

    private int id;
    private String name;

    /**
     *
     * @param id
     * @param name
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return Id of the category
     */
    public int getId() {
        return id;
    }

    /**
     * @return Name of the category
     */
    public String getName() {
        return name;
    }
}
