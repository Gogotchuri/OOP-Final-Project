package services.encoders;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import managers.UserManager;
import models.Message;
import models.User;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>{
    @Override
    public void init(EndpointConfig endpointConfig) {}

    @Override
    public void destroy() {}

    @Override
    public String encode(Message message) throws EncodeException {
        JsonObject jo = message.toJsonObject();
        User user = UserManager.getUserById(message.getUserId());
        if(user == null) throw new EncodeException(message, "Username couldn't be determined!");
        jo.addProperty("username", user.getUsername());
        Gson gson = new Gson();
        return gson.toJson(jo);
    }
}
