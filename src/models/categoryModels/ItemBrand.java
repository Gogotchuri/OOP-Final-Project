package models.categoryModels;
import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

//Class representing a brand of a category

@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemBrand extends ItemParameter implements Comparable <ItemBrand>{


    public ItemBrand(int id, String name) {
        super(id, name);
    }

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
