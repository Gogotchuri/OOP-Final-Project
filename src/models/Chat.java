package models;

import java.util.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Vector;

/**
 * Class containing sequence of messages
 */
public class Chat {
    private int chatID;
    private int cycleID;
    private int messageAmount;
    private Timestamp lastUpdateDate; //Need timestamp for exact time
    private Vector<Message> messages; //Not sure needed

    /**
     * @param chatID
     * @param cycleID
     * @param updateDate
     */
    public Chat(int chatID, int cycleID, Date updateDate){
        messages = new Vector<>(); //Should be fetched from db, not explicitly created
        this.chatID = chatID;
        this.cycleID = cycleID;
        this.messageAmount = 0;
        this.lastUpdateDate = (updateDate != null) ? new Timestamp(updateDate.getTime())
            : new Timestamp((new Date()).getTime());
    }

    /**
     * Add a new message in the sequence.
     *
     * @param msg  message to be added in the sequence
     */
    public void addMessage(Message msg){
        messages.add(msg);
        messageAmount++;
    }

    /**
     * @return  iterator containing every message in the chat
     */
    public Iterator getMessages(){
        Iterator it = messages.iterator();
        return it;
    }

    /**
     * @param messages
     */
    public void setMessages(Vector<Message> messages){
        this.messages = messages;
    }

    /**
     * @return id of this chat
     */
    public int getChatID() {
        return chatID;
    }

    /**
     * @return last time this chat was updated
     */
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param date
     */
    public void setUpdateDate(Date date){
        lastUpdateDate.setTime(date.getTime());
    }

    /**
     * @param id
     */
    public void setChatID(int id){
        chatID = id;
    }

    /**
     * @param id
     */
    public void setCycleID(int id){
        cycleID = id;
    }

    /**
     * @return ID of cycle which this chat is associated with
     */
    public int getCycleID() {
        return cycleID;
    }

    /**
     * @return amount of messages in chat
     */
    public int getMessageAmount() {
        return messageAmount;
    }

    /**
     * @param o Passed object
     * @return Whether two chats are equal or not
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Chat)) return false;
        return chatID == ((Chat) o).getChatID();
    }
}
