package models.categoryModels;

//Class representing all three child models of an item category
public class ItemParameter{

    private int id;
    private String name;

    /**
     * Constructor of a parameter
     * @param name Name
     */
    public ItemParameter(String name) {
        this.name = name;
    }

    /**
     * Alternate Constructor
     * @param id ID
     * @param name Name
     */
    public ItemParameter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return id
     */
    public int getId() { return id; }

    /**
     * @return name
     */
    public String getName() { return name; }

    /**
     * @param other Passed object
     * @return Equality of two objects
     */
    public boolean equals(Object other) {
        if(other == this) return true;
        if(!(other instanceof ItemParameter)) return false;
        return name.equals(((ItemParameter) other).getName());
    }

    /**
     * @return String representation of an item parameter
     */
    public String toString() {
        return name;
    }
}