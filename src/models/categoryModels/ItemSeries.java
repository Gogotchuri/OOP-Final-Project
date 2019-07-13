package models.categoryModels;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

//Class representing a serie of a category
@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemSeries extends ItemParameter {

    public ItemSeries(int id, String name) { super(id, name); }

    public ItemSeries(String name) {
        super(name);
    }
}
