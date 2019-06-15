package models;

import java.util.Iterator;
import java.util.Vector;

/**
 * Class containing sequence of messages
 */
public class Chat {
    private Vector<Message> messages;

    public Chat(){
        messages = new Vector<>();
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
        Iterator it = messages.iterator();
        return it;
    }
}
