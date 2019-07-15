package models.categoryModels;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

/**
 * Class representing a single serie of an item
 */

@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemSeries extends ItemParameter {

    /**
     * @param id ID of a serie
     * @param name Name of a serie
     */
    public ItemSeries(int id, String name) { super(id, name); }

    /**
     * Alternate constructor
     * @param name Name of a serie
     */
    public ItemSeries(String name) {
        super(name);
    }
}
