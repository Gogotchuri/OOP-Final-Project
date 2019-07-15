package services.encoders;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import models.User;

import java.io.IOException;

public class UserJsonAdapter extends TypeAdapter<User> {
    @Override
    public void write(JsonWriter jw, User user) throws IOException {
        jw.beginObject();
        jw.name("id");
        jw.value(user.getUserID());
        jw.name("first_name");
        jw.value(user.getFirstName());
        jw.name("last_name");
        jw.value(user.getLastName());
        jw.name("username");
        jw.value(user.getUsername());
        jw.name("email");
        jw.value(user.getEmail());
        jw.name("phone_number");
        jw.value(user.getPhoneNumber());
        jw.endObject();
    }

    @Override
    public User read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
