package models.categoryModels;
import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

/**
 * Class encapsulating an item brand
 */

@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemBrand extends ItemParameter implements Comparable <ItemBrand>{

    /**
     * @param id ID of brand
     * @param name Name of brand
     */
    public ItemBrand(int id, String name) {
        super(id, name);
    }

    /**
     * Alternate constructor
     * @param name Name of brand
     */
    public ItemBrand(String name) {
        super(name);
    }

    @Override
    /**
     * Compares a parameter to another one with strings
     */
    public int compareTo(ItemBrand o) {
        return getName().compareTo(o.getName());
    }
}
