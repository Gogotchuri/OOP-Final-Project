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
    private Timestamp lastUpdateDate; //Need timestamp for exact time
    private Vector<Message> messages; //Not sure needed

    /**
     * @param chatID
     * @param cycleID
     * @param updateDate
     */

    /**
     * Constructor for creating fresh object of chat
     * */
    public Chat(int chatID, int cycleID){
        messages = new Vector<>();
        this.chatID = chatID;
        this.cycleID = cycleID;
        this.lastUpdateDate = new Timestamp((new Date()).getTime());
    }

    /**
     * Constructor for creating object of chat with already existing messages in it
     * */
    public Chat(int chatID, int cycleID, Date updateDate, Vector<Message> messages){
        this.messages = messages;
        this.chatID = chatID;
        this.cycleID = cycleID;
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
    }

    /**
     * @return  iterator containing every message in the chat
     */
    public Iterator getMessages(){
        return messages.iterator();
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
        return messages.size();
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

    /**
     * Given a user id, returns true if user can participate in the chat
     * @param user_id user to check
     * @return true if user can participate in chat
     */
    public boolean isParticipant(int user_id){
       return true;
       //TODO implement me
    }
}
