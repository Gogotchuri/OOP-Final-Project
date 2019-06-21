package models;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Simple message class based on the "messages" table in the database
 */
public class Message {
    private int chatID;
    private int messageID;
    private String body;
    //Changed to sql timestamp, since date can not contain time in it
    private Timestamp date;

    /**
     * @param chatID  chat id
     * @param body  body of the message
     * @param date  send date passed as utils date class
     */
    public Message(int messageID, int chatID, String body, Date date){
        this.messageID = messageID;
        this.chatID = chatID;
        this.body = body;
        this.date = new Timestamp(date.getTime());
    }

    public Message(int chatID, String body){
        this.messageID = -1;//Not really important
        this.chatID = chatID;
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
}


