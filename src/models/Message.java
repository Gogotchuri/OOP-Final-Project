package models;

import com.google.gson.JsonObject;
import com.google.gson.annotations.JsonAdapter;
import services.encoders.MessageJsonAdapter;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Simple message class based on the "messages" table in the database
 */
@JsonAdapter(MessageJsonAdapter.class)
public class Message {
    private int chatID;
    private int messageID;
    private String body;
    private int userId;
    private Timestamp date;

    /**
     * @param chatID  chat id
     * @param body  body of the message
     * @param date  send date passed as utils Timestamp class
     */
    public Message(int messageID, int chatID, int user_id, String body, Timestamp date){
        this.messageID = messageID;
        this.chatID = chatID;
        this.userId = user_id;
        this.body = body;
        this.date = date;
    }

    /**
     * Alternate constructor
     * @param chatID ID of a chad
     * @param user_id ID of a user
     * @param body Body of a message
     */
    public Message(int chatID, int user_id, String body){
        this.messageID = -1;//Not really important
        this.chatID = chatID;
        this.userId = user_id;
        this.body = body;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    /**
     * @return  chat id
     */
    public int getChatID() {
        return chatID;
    }

    /**
     * @param id
     */
    public void setChatID(int id){
        chatID = id;
    }


    /**
     * @return  message id
     */
    public int getMessageID() {
        return messageID;
    }

    /**
     * @param id
     */
    public void setMessageID(int id){
        messageID = id;
    }

    /**
     * @return body of the message
     */
    public String getBody() {
        return body;
    }

    /**
     * @return Id of a user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param body
     */
    public void setBody(String body){
        this.body = body;
    }

    /**
     * @return send date
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(Date date){
        this.date.setTime(date.getTime());
    }

    /**
     * @param o Passed message
     * @return Whether two messages are equal or not
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Message)) return false;
        return messageID == ((Message) o).getMessageID();
    }

    /**
     * @return JsonObject of a message
     */
    public JsonObject toJsonObject() {
        JsonObject jo = new JsonObject();
        jo.addProperty("userId", ""+this.userId);
        jo.addProperty("chat_id", ""+this.chatID);
        jo.addProperty("body", this.body);
        jo.addProperty("date", this.date.toString());
        return jo;
    }
}


