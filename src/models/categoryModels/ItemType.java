package models.categoryModels;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

/**
 * Class representing a type of a category
 */

@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemType extends ItemParameter implements Comparable <ItemType>{

    /**
     * @param id ID of a type
     * @param name Name of a type
     */
    public ItemType(int id, String name) {
        super(id, name);
    }

    /**
     * Alternate constructor
     * @param name Name of a type
     */
    public ItemType(String name) {
        super(name);
    }

    @Override
    /**
     * Compares a parameter to another one with strings
     */
    public int compareTo(ItemType o) {
        return getName().compareTo(o.getName());
    }
}
