package test;

import generalManagers.DeleteManager;
import managers.ChatManager;
import managers.CycleManager;
import models.Chat;
import models.Cycle;
import models.Message;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import java.util.Date;

public class ChatAndMessageTests {

    //Needed to run them together, because the ID-s are automatically generated
    @Test
    public void chatAndMessageTest(){
        DeleteManager.emptyBase("messages");
        DeleteManager.emptyBase("chats");
        CycleManager.addCycleToDB(new Cycle(1));
        CycleManager.addCycleToDB(new Cycle(2));
        Chat ch1 = new Chat(1, new Cycle(1));
        Chat ch2 = new Chat(2, new Cycle(2));
        assertEquals(ChatManager.addChatToDB(ch1), true);
        assertEquals(ChatManager.addChatToDB(ch2), true);
        assertEquals(ChatManager.getChatByCycleID(1).getChatID(), ch1.getChatID());
        assertEquals(ChatManager.getChatByCycleID(2).getChatID(), ch2.getChatID());

        Message msg1 = new Message(1, ch1.getChatID(), "first message", new Timestamp(new Date().getTime()));
        Message msg2 = new Message(2, ch1.getChatID(), "second message", new Timestamp(new Date().getTime()));
        Message msg3 = new Message(3, ch2.getChatID(), "third message", new Timestamp(new Date().getTime()));
        assertEquals(ChatManager.addMessageToDB(msg1), true);
        assertEquals(ChatManager.addMessageToDB(msg2), true);
        assertEquals(ChatManager.addMessageToDB(msg3), true);
        assertEquals(ChatManager.getChatByID(ch1.getChatID()).getMessageAmount(), 2);
        assertEquals(ChatManager.getChatByID(ch2.getChatID()).getMessageAmount(), 1);

        assertTrue(ChatManager.removeMessageFromDB(msg1.getMessageID()));
        assertEquals(ChatManager.getChatByID(ch1.getChatID()).getMessageAmount(), 1);
        assertTrue(ChatManager.removeMessageFromDB(msg2.getMessageID()));
        assertTrue(ChatManager.removeMessageFromDB(msg3.getMessageID()));
        assertTrue(ChatManager.removeChatFromDB(ch1.getChatID()));
        assertTrue(ChatManager.removeChatFromDB(ch2.getChatID()));
    }

}
