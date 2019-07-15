package services.encoders;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.categoryModels.ItemParameter;

import java.io.IOException;

public class ItemParameterJsonAdapter extends TypeAdapter<ItemParameter> {
    @Override
    public void write(JsonWriter jsonWriter, ItemParameter itemParameter) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(itemParameter.getId());
        jsonWriter.name("name");
        jsonWriter.value(itemParameter.getName());
        jsonWriter.endObject();
    }

    @Override
    public ItemParameter read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
