
package test.managers;

import managers.*;
import models.*;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSeries;
import models.categoryModels.ItemType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.Tester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChatManagerTest extends Tester { // TODO: Lasha

    private static User user1 = new User("one", "1", "o",
            "ne", "onemail", "111");
    private static User user2 = new User("two", "2", "t",
            "wo", "twomail", "222");
    private static User user3 = new User("three", "3", "th",
            "ree", "threemail", "333");
    private static Cycle cycle1 = new Cycle(1);
    private static Cycle cycle2 = new Cycle(2);
    private static Cycle cycle3 = new Cycle(3);
    private static Chat ch1 = new Chat(1, cycle1);
    private static Chat ch2 = new Chat(2, cycle2);
    private static Chat ch3 = new Chat(3, cycle3);

    private static Message msg1 = new Message(ch1.getChatID(),1,"1 message");
    private static Message msg2 = new Message(ch1.getChatID(), 2,"2 message");
    private static Message msg3 = new Message(ch2.getChatID(), 1,"3 message");
    private static Message msg4 = new Message(ch2.getChatID(), 3,"4 message");
    private static Message msg5 = new Message(ch3.getChatID(), 3,"5 message");
    private static Message msg6 = new Message(ch3.getChatID(), 2,"6 message");
    private static Message msg7 = new Message(ch3.getChatID(), 1,"7 message");

    @Before
    public void setUp() throws Exception {
        emptyDataBase();
        insertSeeds();
    }

    private void insertSeeds(){
        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
        UserManager.storeUser(user3);
        CycleManager.addCycleToDB(cycle1);
        CycleManager.addCycleToDB(cycle2);
        CycleManager.addCycleToDB(cycle3);
        assertEquals(ChatManager.addChatToDB(ch1), true);
        assertEquals(ChatManager.addChatToDB(ch2), true);
        assertEquals(ChatManager.addChatToDB(ch3), true);
        assertEquals(ChatManager.addMessageToDB(msg1), true);
        assertEquals(ChatManager.addMessageToDB(msg2), true);
        assertEquals(ChatManager.addMessageToDB(msg3), true);
        assertEquals(ChatManager.addMessageToDB(msg4), true);
        assertEquals(ChatManager.addMessageToDB(msg5), true);
        assertEquals(ChatManager.addMessageToDB(msg6), true);
        assertEquals(ChatManager.addMessageToDB(msg7), true);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void removeMessageFromDB() {
        assertTrue(ChatManager.removeMessageFromDB(msg1.getMessageID()));
        assertTrue(ChatManager.removeMessageFromDB(msg3.getMessageID()));
        assertTrue(ChatManager.removeMessageFromDB(msg4.getMessageID()));
        assertTrue(ChatManager.removeMessageFromDB(msg7.getMessageID()));
    }

    @Test
    public void removeMessagesByChatID() {
        assertTrue(ChatManager.removeMessagesByChatID(ch2.getChatID()));
        assertEquals(ChatManager.getChatByID(ch2.getChatID()).getMessageAmount(), 0);
        assertTrue(ChatManager.removeMessagesByChatID(ch3.getChatID()));
        assertEquals(ChatManager.getChatByID(ch3.getChatID()).getMessageAmount(), 0);
    }

    @Test
    public void removeChatFromDB() {
        assertTrue(ChatManager.removeChatFromDB(ch1.getChatID()));
        assertTrue(ChatManager.removeChatFromDB(ch2.getChatID()));
        assertTrue(ChatManager.removeChatFromDB(ch3.getChatID()));
    }

    @Test
    public void getChatByCycleID() {
        assertEquals(ChatManager.getChatByCycleID(1).getChatID(), 1);
        assertEquals(ChatManager.getChatByCycleID(2).getChatID(), 2);
        assertEquals(ChatManager.getChatByCycleID(3).getChatID(), 3);
        assertEquals(ChatManager.getChatByCycleID(3).getMessageAmount(), 3);
    }

    private void addChatByCreatingCycle(){
        ItemCategory cat1 = new ItemCategory(1, new ItemSeries("item1"), new ItemType("car"), new ItemBrand("toyota"));
        ItemCategory cat2 = new ItemCategory(2, new ItemSeries("item2"), new ItemType("fridge"), new ItemBrand("samsung"));
        ItemCategory cat3 = new ItemCategory(3, new ItemSeries("item3"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
        CategoryManager.insertCategory(cat1);
        CategoryManager.insertCategory(cat2);
        CategoryManager.insertCategory(cat3);
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
    }

    @Test
    public void getUserChats() {
        addChatByCreatingCycle();
        assertEquals(ChatManager.getUserChats(user1.getUserID()).size(), 1);
        assertEquals(ChatManager.getUserChats(user2.getUserID()).size(), 1);

    }

    @Test
    public void getChatByID() {
        assertTrue(ChatManager.removeMessageFromDB(msg1.getMessageID()));
        assertEquals(ChatManager.getChatByID(ch1.getChatID()).getMessageAmount(), 1);
        assertTrue(ChatManager.removeMessageFromDB(msg3.getMessageID()));
        assertTrue(ChatManager.removeMessageFromDB(msg4.getMessageID()));
        assertEquals(ChatManager.getChatByID(ch2.getChatID()).getMessageAmount(), 0);
        assertTrue(ChatManager.removeMessageFromDB(msg7.getMessageID()));
        assertEquals(ChatManager.getChatByID(ch3.getChatID()).getMessageAmount(), 2);
    }

    @Test
    public void getChatParticipants() {
        addChatByCreatingCycle();
        List<User> ls = ChatManager.getChatParticipants(4);
        assertEquals(ls.size(), 2);
    }

    @Test
    public void getChatUserNames() {
        addChatByCreatingCycle();
        List<String> ls = ChatManager.getChatUserNames(4);
        Tester.equalLists(ls, Arrays.asList(user1.getUsername(), user2.getUsername()));
    }
}
