package services.encoders;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.Deal;

import java.io.IOException;

public class DealJsonAdapter extends TypeAdapter<Deal> {
    @Override
    public void write(JsonWriter jw, Deal deal) throws IOException {
        Gson gson = new Gson();
        jw.beginObject();
        jw.name("id");
        jw.value(deal.getDealID());
        jw.name("name");
        jw.value(deal.getTitle());
        jw.name("description");
        jw.value(deal.getDescription());
        jw.name("author_id");
        jw.value(deal.getOwnerID());
        jw.name("date");
        jw.value(deal.getCreateDate().toString());
        jw.name("wanted-items");
        jw.value(gson.toJson(deal.getWantedCategories()));
        jw.name("owned-items");
        jw.value(gson.toJson(deal.getOwnedItems()));
        jw.endObject();
    }

    @Override
    public Deal read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
