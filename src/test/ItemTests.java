package test;
import generalManagers.DeleteManager;
import generalManagers.UpdateManager;
import managers.CategoryManager;
import managers.ItemManager;
import managers.UserManager;
import models.Item;
import models.User;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
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

    private static final ItemCategory cat1 = new ItemCategory(1, new ItemSerie("S3"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static final ItemCategory cat2 = new ItemCategory(2, new ItemSerie("E40"), new ItemType("Fridge"), new ItemBrand("Samsung"));
    private static final ItemCategory cat3 = new ItemCategory(3, new ItemSerie("FinalsTrophy"), new ItemType("Trophy"), new ItemBrand("NBA"));

    private static final Item it1 = new Item(1, u1.getUserID(), cat1, null, "Samsung Galaxy s3", "Karg mdgomareobashi", time, time);
    private static final Item it2 = new Item(2, u1.getUserID(), cat2, null, "Macivari", "aciebs", time, time);
    private static final Item it3 = new Item(3, u2.getUserID(), cat3, null, "2016shi mogebuli finali", "kai iyo", time, time);
    private static final Item it4 = new Item(4, u1.getUserID(), cat1, null, "Meore teleponic mqonia", "esec karg", time, time);

    private void emptyBase() {
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
    }

    @Test
    public void itemsByID() {
        assertEquals(ItemManager.getItemByID(1), it1);
        assertEquals(ItemManager.getItemByID(3), it3);
        assertEquals(ItemManager.getItemByID(5555), null);
        assertNotEquals(ItemManager.getItemByID(1), it2);
    }

    @Test
    public void itemsOfUser() {
        equalLists(ItemManager.getUserItems(u1.getUserID()), Arrays.asList(it1,it2,it4));
        equalLists(ItemManager.getUserItems(u2.getUserID()), Arrays.asList(it3));
        equalLists(ItemManager.getUserItems(4444), Arrays.asList());
    }

    private void equalLists(List<Item> a, List<Item> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertEquals(a,b);
    }
}
