package services.encoders;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.Item;
import models.ItemImage;

import java.io.IOException;
import java.util.List;

public class ItemJsonAdapter extends TypeAdapter<Item> {
    @Override
    public void write(JsonWriter jw, Item item) throws IOException {
        Gson gson = new Gson();
        jw.beginObject();
        jw.name("id");
        jw.value(item.getItemID());
        jw.name("name");
        jw.value(item.getName());
        jw.name("description");
        jw.value(item.getDescription());
        jw.name("category");
        jw.value(gson.toJson(item.getCategory()));
        jw.name("image_url");
        List<ItemImage> itemImages = item.getImages();
        if(itemImages != null && !itemImages.isEmpty())
            jw.value(itemImages.get(0).getUrl());
        else
            jw.value("http://bandtanimalkingdom.weebly.com/uploads/3/9/9/4/39943199/s320981046200626688_p7_i1_w500.jpeg");
        jw.endObject();
    }

    @Override
    public Item read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
