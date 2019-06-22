package test;

import managers.ChatManager;
import models.Chat;
import models.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;

public class ChatAndCycleTests {

    /*
     * TODO: write cycle test, use the cycles in chat tests
     * since chats requires cycles, we might as well write them in one
     * class. we can split them if needed.
     */

    @Test
    public void addChatTest(){
        Chat ch1 = new Chat(1, 1, null);
        Chat ch2 = new Chat(2, 2, null);
        assertEquals(ChatManager.addChatToDB(ch1), true);
        assertEquals(ChatManager.addChatToDB(ch2), true);
        assertEquals(ChatManager.getChatByCycleID(1).getChatID(), ch1.getChatID());
        assertEquals(ChatManager.getChatByCycleID(2).getChatID(), ch2.getChatID());
    }

    @Test
    public void addMessageTest(){
        Message msg1 = new Message(1, 1, "first message", new Timestamp(new Date().getTime()));
        Message msg2 = new Message(2, 1, "second message", new Timestamp(new Date().getTime()));
        Message msg3 = new Message(3, 2, "third message", new Timestamp(new Date().getTime()));
        assertEquals(ChatManager.addMessageToDB(msg1), true);
        assertEquals(ChatManager.addMessageToDB(msg2), true);
        assertEquals(ChatManager.addMessageToDB(msg3), true);
        assertEquals(ChatManager.getChatByID(1).getMessageAmount(), 2);
        assertEquals(ChatManager.getChatByID(2).getMessageAmount(), 1);

    }

    @Test
    public void removeTest(){
        assertTrue(ChatManager.removeMessageFromDB(1));
        assertEquals(ChatManager.getChatByID(1).getMessageAmount(), 1);
        assertTrue(ChatManager.removeMessageFromDB(2));
        assertTrue(ChatManager.removeMessageFromDB(3));
        assertTrue(ChatManager.removeChatFromDB(1));
    }
}
