package test;
import generalManagers.DeleteManager;
import generalManagers.UpdateManager;
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
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTests {

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

    private void emptyBase() {
        DeleteManager.delete("deals", "1", 1);
        DeleteManager.delete("items", "1", 1);
        DeleteManager.delete("item_categories", "1", 1);
        DeleteManager.delete("item_brands", "1", 1);
        DeleteManager.delete("item_types", "1", 1);
        DeleteManager.delete("users", "1", 1);

        UpdateManager.reseedTable("item_brands", 1);
        UpdateManager.reseedTable("item_types", 1);
        UpdateManager.reseedTable("item_categories", 1);
        UpdateManager.reseedTable("users", 1);
        UpdateManager.reseedTable("items", 1);
        UpdateManager.reseedTable("deals", 1);
    }

    @Test
    public void insertItems() {
        emptyBase();
        CategoryManager.insertCategory(cat1);
        CategoryManager.insertCategory(cat2);
        CategoryManager.insertCategory(cat3);

        UserManager.storeUser(u1);
        UserManager.storeUser(u2);

        ItemManager.addItemToDB(it1);
        ItemManager.addItemToDB(it2);
        ItemManager.addItemToDB(it3);
        ItemManager.addItemToDB(it4);

        DealsManager.storeDeal(d1);
        DealsManager.storeDeal(d2);
    }

    @Test
    public void itemsByID() {
        assertEquals(ItemManager.getItemByID(1), it1);
        assertEquals(ItemManager.getItemByID(3), it3);
        assertEquals(ItemManager.getItemByID(5555), null);
        assertNotEquals(ItemManager.getItemByID(1), it2);
        equalLists(ItemManager.getItemsByItemIDs(Arrays.asList(1,3)), Arrays.asList(it1, it3));
    }

    @Test
    public void itemsOfUser() {
        equalLists(ItemManager.getUserItems(u1.getUserID()), Arrays.asList(it1,it2,it4));
        equalLists(ItemManager.getUserItems(u2.getUserID()), Arrays.asList(it3));
        equalLists(ItemManager.getUserItems(4444), Arrays.asList());
    }

    @Test
    public void itemsFromDeal() {
        equalLists(ItemManager.getItemsByDealID(1), Arrays.asList(it1, it2, it4));
        unEqualLists(ItemManager.getItemsByDealID(1), Arrays.asList(it1, it2));
        unEqualLists(ItemManager.getItemsByDealID(1), Arrays.asList(it3));

        equalLists(ItemManager.getItemsByDealID(2), Arrays.asList(it3));
        unEqualLists(ItemManager.getItemsByDealID(2), Arrays.asList(it2));
    }

    @Test
    public void categoriesFromDeal() {
        equalListsCategories(ItemManager.getWantedItemCategories(1), Arrays.asList(cat3));
        equalListsCategories(ItemManager.getWantedItemCategories(2), Arrays.asList(cat2, cat1));
        unEqualListsCategories(ItemManager.getWantedItemCategories(1), Arrays.asList(cat1));
        unEqualListsCategories(ItemManager.getWantedItemCategories(2), Arrays.asList(cat1));
    }

    private void unEqualListsCategories(List<ItemCategory> a, List<ItemCategory> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertNotEquals(a,b);
    }

    private void equalListsCategories(List<ItemCategory> a, List<ItemCategory> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertEquals(a,b);
    }

    private void equalLists(List<Item> a, List<Item> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertEquals(a,b);
    }

    private void unEqualLists(List<Item> a, List<Item> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertNotEquals(a,b);
    }
}
