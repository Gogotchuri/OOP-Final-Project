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
        //TODO implement me
        jw.name("id");
//        jw.value(deal.getDealID());
//        jw.name(name);
//        jw.value();
//        jw.name();
//        jw.value();
//        jw.name();
//        jw.value();
//        jw.name();
//        jw.value();
        jw.endObject();
    }

    @Override
    public Deal read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
