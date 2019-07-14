package models;

import managers.ChatManager;
import managers.CycleManager;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.*;

/**
 * Class containing sequence of messages
 */
public class Chat implements Comparable<Chat> {

    private int chatID;               // -1
    private Cycle cycle;              // null
    private Timestamp lastUpdateDate; // null
    private Vector<Message> messages; // should be empty vector


    /**
     * Constructor for creating object of chat with already existing messages in it
     * @param chatID the id of the chat in DB
     * @param cycle cycle which the chat is for
     * @param updateDate time of last update
     * @param messages collection of messages
     */
    public Chat(int chatID, Cycle cycle, Timestamp updateDate, Vector<Message> messages){
        this.messages = messages;
        this.chatID = chatID;
        this.cycle = cycle;
        this.lastUpdateDate = (updateDate != null) ? new Timestamp(updateDate.getTime())
                : new Timestamp((new Date()).getTime());
    }


    /**
     * Constructor with only chat id and cycle
     * @param chatID the id of the chat in DB
     * @param cycle cycle which the chat is for
     */
    public Chat(int chatID, Cycle cycle){
        this(chatID, cycle, null, new Vector<>());
    }


    /**
     * Constructor with only cycle
     * @param cycle cycle which the chat is for
     */
    public Chat(Cycle cycle) {
        this(-1, cycle);
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
    public List<Message> getMessages(){
        return messages;
    }

    /**
     * @param messages Vector of messages to set
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
     * @param date last time this chat was updated
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
     * @param cycle Passed cycle to set
     */
    public void setCycle(Cycle cycle){
        this.cycle = cycle;
    }

    /**
     * @return cycle which this chat is associated with
     */
    public Cycle getCycle() {
        return cycle;
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
        return CycleManager.userParticipatesInCycle(user_id, cycle.getCycleID());
    }

    /**
     * Returns list of users participating in this chat (cycle in general)
     * */
    public List<User> getParticipants(){
        return ChatManager.getChatParticipants(chatID);
    }

    /**
     * Returns list of user names participating in this chat (cycle in general)
     * */
    public List<String> getParticipantNames(){
        return ChatManager.getChatUserNames(chatID);
    }

    /**
     * @param chat - Passed Cycle
     * @return Comparison of Cycles
     */
    @Override public int compareTo(Chat chat) {
        if (chat.chatID > this.chatID)
            return -1;
        if (chat.chatID < this.chatID)
            return 1;
        return 0;
    }

}
