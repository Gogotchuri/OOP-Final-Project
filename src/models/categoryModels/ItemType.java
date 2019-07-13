package models.categoryModels;

import com.google.gson.annotations.JsonAdapter;
import services.encoders.ItemParameterJsonAdapter;

//Class representing a type of a category
@JsonAdapter(ItemParameterJsonAdapter.class)
public class ItemType extends ItemParameter {

    public ItemType(int id, String name) {
        super(id, name);
    }

    public ItemType(String name) {
        super(name);
    }
}
