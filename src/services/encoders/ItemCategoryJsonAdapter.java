package services.encoders;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.categoryModels.ItemCategory;

import java.io.IOException;

public class ItemCategoryJsonAdapter extends TypeAdapter<ItemCategory> {
    @Override
    public void write(JsonWriter jsonWriter, ItemCategory category) throws IOException {
        Gson gson = new Gson();
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(category.getId());
        jsonWriter.name("model");
        jsonWriter.value(gson.toJson(category.getSeries()));
        jsonWriter.name("manufacturer");
        jsonWriter.value(gson.toJson(category.getBrand()));
        jsonWriter.name("type");
        jsonWriter.value(gson.toJson(category.getType()));
        jsonWriter.endObject();
    }

    @Override
    public ItemCategory read(JsonReader jsonReader) throws IOException {
        return null;
    }

}
