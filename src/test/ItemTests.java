package test;

import managers.ItemManager;
import models.Category;
import models.Item;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTests {

    private Category c1 = new Category(1, "Telephone");
    private Category c2 = new Category(2, "Tablet");
    private Category c3 = new Category(3, "Laptop");
    private Item item1 = new Item(1, 5, "item1", "qaia", c1, new Date(System.currentTimeMillis()));
    private Item item2 = new Item(2, 6, "item2", "ar varga", c2, new Date(System.currentTimeMillis()));
    private Item item3 = new Item(3, 6, "item3", "qaia", c3, new Date(System.currentTimeMillis()));


    @Test
    public void addItem(){
        assertTrue(ItemManager.addItemToDB(item1));
        assertTrue(ItemManager.addItemToDB(item2));
        assertTrue(ItemManager.addItemToDB(item3));
    }

    @Test
    public void testGetItemById(){
        assertEquals(ItemManager.getItemByID(1),item1);
        assertEquals(ItemManager.getItemByID(2),item2);
        assertEquals(ItemManager.getItemByID(3),item3);
    }

    @Test
    public void testGetItemByColumn(){
        List<Item> res = ItemManager.getItemsByColumn("items", "description", "'qaia'");
        assertTrue(res.contains(item1));
        assertFalse(res.contains(item2));
        assertTrue(res.contains(item3));
    }

    @Test
    public  void testGetUserItems(){
        List<Item> res = ItemManager.getUserItems(6);
        assertTrue(res.size() == 2);
        assertTrue(res.contains(item2));
        assertTrue(res.contains(item3));
    }

    @Test
    public void testInsertOwnedItems(){
        assertTrue(ItemManager.insertOwnedItem(1, 3));
        assertTrue(ItemManager.insertOwnedItem(1, 2));
    }

    @Test
    public void testInsertWantedItems(){
        assertTrue(ItemManager.insertWantedItem(1, 1));
    }

    @Test
    public void TestGetWantedItemCat(){
        List<Category> res = ItemManager.getWantedItemCategories(1);
        assertTrue(res.size() == 1);
        assertTrue(res.contains(c1));
        assertFalse(res.contains(c2));
        assertFalse(res.contains(c3));
    }


}
