
package test.managers;

import managers.CategoryManager;
import managers.DealsManager;
import managers.ItemManager;
import managers.UserManager;
import models.Deal;
import models.Item;
import models.User;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSeries;
import models.categoryModels.ItemType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.Tester;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ItemManagerTest extends Tester {

    private static final Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"mudamtqveny", "password", "levan", "gelashvili", "lgela17", "123", null, null, null, time, time);
    private static User u2 = new User(2,"king", "heat","lebron","james","ljame03", "6", null, null, null, time, time);

    private static final ItemCategory cat1 = new ItemCategory(1, new ItemSeries("S3"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static final ItemCategory cat2 = new ItemCategory(2, new ItemSeries("E40"), new ItemType("Fridge"), new ItemBrand("Samsung"));
    private static final ItemCategory cat3 = new ItemCategory(3, new ItemSeries("FinalsTrophy"), new ItemType("Trophy"), new ItemBrand("NBA"));

    private static final Item it1 = new Item(1, u1.getUserID(), cat1, null, "Samsung Galaxy s3", "Karg mdgomareobashi", time, time);
    private static final Item it2 = new Item(2, u1.getUserID(), cat2, null, "Macivari", "aciebs", time, time);
    private static final Item it3 = new Item(3, u2.getUserID(), cat3, null, "2016shi mogebuli finali", "kai iyo", time, time);
    private static final Item it4 = new Item(4, u1.getUserID(), cat1, null, "Meore teleponic mqonia", "esec karg", time, time);

    private static final Deal d1 = new Deal(u1.getUserID(), Arrays.asList(it1, it2, it4), Arrays.asList(cat3));
    private static final Deal d2 = new Deal(u2.getUserID(), Arrays.asList(it3), Arrays.asList(cat1, cat2));

    @Before
    public void setUp() throws Exception {
        emptyDataBase();
        assertNotEquals(CategoryManager.insertCategory(cat1),-1);
        assertNotEquals(CategoryManager.insertCategory(cat2),-1);
        assertNotEquals(CategoryManager.insertCategory(cat3),-1);
        UserManager.storeUser(u1);
        UserManager.storeUser(u2);
        ItemManager.addItemToDB(it1);
        ItemManager.addItemToDB(it2);
        ItemManager.addItemToDB(it3);
        ItemManager.addItemToDB(it4);
        DealsManager.storeDeal(d1);
        DealsManager.storeDeal(d2);
    }

    @After
    public void tearDown() throws Exception {
        int oldSize = tableSize("items");
        ItemManager.deleteItemById(1);
        assertEquals(oldSize, tableSize("items") + 1);
    }

    @Test
    public void getItemByID() {
        assertEquals(ItemManager.getItemByID(1), it1);
        assertEquals(ItemManager.getItemByID(3), it3);
        assertEquals(ItemManager.getItemByID(5555), null);
        assertNotEquals(ItemManager.getItemByID(1), it2);
    }

    @Test
    public void getItemsByItemIDs() {
        equalLists(ItemManager.getItemsByItemIDs(Arrays.asList(1,3)), Arrays.asList(it1, it3));
        inEqualLists(ItemManager.getItemsByItemIDs(Arrays.asList(1,2)), Arrays.asList(it1, it3));
    }

    @Test
    public void getItemsByDealID() {
        equalLists(ItemManager.getItemsByDealID(1), Arrays.asList(it1, it2, it4));
        inEqualLists(ItemManager.getItemsByDealID(1), Arrays.asList(it1, it2));
        inEqualLists(ItemManager.getItemsByDealID(1), Arrays.asList(it3));
        equalLists(ItemManager.getItemsByDealID(2), Arrays.asList(it3));
        inEqualLists(ItemManager.getItemsByDealID(2), Arrays.asList(it2));
    }

    @Test
    public void addItemToDB() {
        //Tested in @before
    }

    @Test
    public void getUserItems() {
        equalLists(ItemManager.getUserItems(u1.getUserID()), Arrays.asList(it1,it2,it4));
        equalLists(ItemManager.getUserItems(u2.getUserID()), Arrays.asList(it3));
        equalLists(ItemManager.getUserItems(4444), Arrays.asList());
    }

    @Test
    public void getOwnedItems() {

    }

    @Test
    public void insertWantedItemCategory() {

    }

    @Test
    public void insertOwnedItem() {

    }

    @Test
    public void getWantedItemCategories() {
        equalLists(ItemManager.getWantedItemCategories(1), Arrays.asList(cat3));
        equalLists(ItemManager.getWantedItemCategories(2), Arrays.asList(cat2, cat1));
        inEqualLists(ItemManager.getWantedItemCategories(1), Arrays.asList(cat1));
        inEqualLists(ItemManager.getWantedItemCategories(2), Arrays.asList(cat1));
    }

    @Test
    public void deleteItemById() {
        //Tested in @after
    }
}
