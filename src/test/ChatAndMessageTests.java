package test;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import managers.*;
import models.*;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ChatAndMessageTests {

    //Needed to run them together, because the ID-s are automatically generated
    @Test
    public void chatAndMessageTest(){
        DeleteManager.emptyBase("messages");
        DeleteManager.emptyBase("chats");
        DeleteManager.emptyBase("offered_cycles");
        DeleteManager.emptyBase("deals");
        DeleteManager.emptyBase("cycles");
        DeleteManager.emptyBase("users");
        User user1 = new User("one", "1", "o",
                "ne", "onemail", "111");
        User user2 = new User("two", "2", "t",
                "wo", "twomail", "222");
        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
        Cycle cycle1 = new Cycle(1);
        Cycle cycle2 = new Cycle(2);
        CycleManager.addCycleToDB(cycle1);
        CycleManager.addCycleToDB(cycle2);
        Chat ch1 = new Chat(1, cycle1);
        Chat ch2 = new Chat(2, cycle2);
        assertEquals(ChatManager.addChatToDB(ch1), true);
        assertEquals(ChatManager.addChatToDB(ch2), true);
        assertEquals(ChatManager.getChatByCycleID(cycle1.getCycleID()).getChatID(), ch1.getChatID());
        assertEquals(ChatManager.getChatByCycleID(cycle2.getCycleID()).getChatID(), ch2.getChatID());

        Message msg1 = new Message(ch1.getChatID(), user1.getUserID(),"first message");
        Message msg2 = new Message(ch1.getChatID(), user2.getUserID(),"second message");
        Message msg3 = new Message(ch2.getChatID(), user1.getUserID(),"third message");
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

    @Test
    public void getUserChatsTest(){
        DeleteManager.emptyBase("offered_cycles");
        DeleteManager.emptyBase("cycles");
        DeleteManager.emptyBase("deals");
        DeleteManager.emptyBase("users");
        DeleteManager.emptyBase("items");

        ItemCategory cat1 = new ItemCategory(1, new ItemSerie("item1"), new ItemType("car"), new ItemBrand("toyota"));
        ItemCategory cat2 = new ItemCategory(2, new ItemSerie("item2"), new ItemType("fridge"), new ItemBrand("samsung"));
        ItemCategory cat3 = new ItemCategory(3, new ItemSerie("item3"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
        User user1 = new User("one", "1", "o",
                "ne", "onemail", "111");
        User user2 = new User("two", "2", "t",
                "wo", "twomail", "222");
        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
        Item item1 = new Item(1, user1.getUserID(), cat1, null, "sams s3",
                "qaia", null, null);
        Item item2 = new Item(2, user2.getUserID(), cat2, null, "sams s2",
                "qaia", null, null);
        Item item3 = new Item(3, user1.getUserID(), cat3, null, "macomputer",
                "qaia", null, null);

        ItemManager.addItemToDB(item1);
        ItemManager.addItemToDB(item2);
        ItemManager.addItemToDB(item3);
        Deal deal1 = new Deal(user1.getUserID(), new ArrayList<>(Arrays.asList(item1, item2)),
                new ArrayList<>(Arrays.asList(cat3)));
        Deal deal2 = new Deal(user2.getUserID(), new ArrayList<>(Arrays.asList(item3)),
                new ArrayList<>(Arrays.asList(cat2, cat1)));
        DealsManager.storeDeal(deal1);
        DealsManager.storeDeal(deal2);
        Cycle cycle = new Cycle(Arrays.asList(deal1, deal2));
        CycleManager.addCycleToDB(cycle);

        CycleManager.acceptCycle(cycle.getCycleID(), deal1.getDealID());
        CycleManager.acceptCycle(cycle.getCycleID(), deal2.getDealID());


        assertEquals(ChatManager.getUserChats(user1.getUserID()).size(), 1);
        assertEquals(ChatManager.getUserChats(user2.getUserID()).size(), 1);
    }

}
