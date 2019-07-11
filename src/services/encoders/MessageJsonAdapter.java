package services.encoders;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import managers.UserManager;
import models.Message;
import models.User;

import java.io.IOException;

public class MessageJsonAdapter extends TypeAdapter<Message> {
    @Override
    public void write(JsonWriter jsonWriter, Message message) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("id");
        jsonWriter.value(message.getMessageID());

        jsonWriter.name("user_id");
        jsonWriter.value(message.getUserId());

        jsonWriter.name("body");
        jsonWriter.value(message.getBody());

        jsonWriter.name("date");
        jsonWriter.value(message.getDate().toString());

        User author = UserManager.getUserByID(message.getUserId());
        jsonWriter.name("username");
        if(author != null)
            jsonWriter.value(author.getUsername());
        else
            jsonWriter.value("undefined");
        jsonWriter.endObject();
    }

    @Override
    public Message read(JsonReader jsonReader) throws IOException {
        return null;
    }
}
