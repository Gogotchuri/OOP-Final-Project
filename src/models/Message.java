package models;

import java.sql.Date;

/**
 * Simple message class based on the "messages" table in the database
 */
public class Message {
    private int chatID;
    private String body;
    private Date date;

    /**
     * @param chatID  chat id
     * @param body  body of the message
     * @param date  send date
     */
    public Message(int chatID, String body, Date date){
        this.chatID = chatID;
        this.body = body;
        this.date = date;
    }

    /**
     * @return  chat id
     */
    public int getChatID() {
        return chatID;
    }

    /**
     * @return body of the message
     */
    public String getBody() {
        return body;
    }

    /**
     * @return send date
     */
    public Date getDate() {
        return date;
    }
}
