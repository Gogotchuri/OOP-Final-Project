package models;

import java.util.Collections;
import java.util.List;

/**
 * Class encapsulating a single category
 */
public class Category implements Comparable <Category>{

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

    /**
     * @param other Passed object
     * @return Are two categories equal or not
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Category)) return false;
        return name.equals(((Category) other).getName());
    }

    /**
     * Compares two lists of categories
     * @param l1
     * @param l2
     * @return Whether they are equal or not
     */
    public static boolean listsEqualsIgnoreOrder(List<Category> l1, List<Category> l2) {
        if(l1 == null && l2 == null) return true;
        if(l1 == null ^ l2 == null) return false;
        if(l1.size() != l2.size()) return false;

        Collections.sort(l1);
        Collections.sort(l2);
        return l1.equals(l2);
    }

    @Override
    /**
     * Compares two categories by their name, since they are unique
     */
    public int compareTo(Category other) {
        return name.compareTo(other.getName());
    }
}
