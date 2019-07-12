package test;

import database.DatabaseAccessObject;
import events.DealCyclesFinder;
import generalManagers.DeleteManager;
import managers.CategoryManager;
import managers.DealsManager;
import managers.ItemManager;
import managers.UserManager;
import models.Deal;
import models.Item;
import models.User;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealCyclesFinderTest {

    private static Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"a","a","a","a","a","a,",null,null,null,time,time);
    private static User u2 = new User(2,"b","b","b","b","b","b,",null,null,null,time,time);
    private static User u3 = new User(3,"c","c","c","c","c","c,",null,null,null,time,time);

    private static ItemCategory cat1 = new ItemCategory(1, new ItemSerie("S3"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory cat2 = new ItemCategory(2, new ItemSerie("S2"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory cat3 = new ItemCategory(3, new ItemSerie("Iphone"), new ItemType("Phone"), new ItemBrand("Apple"));

    private static Item it1 = new Item(1, u1.getUserID(), cat1, null, "Samsung galaxy s3","", time, time);
    private static Item it2 = new Item(2, u1.getUserID(), cat2, null, "Samsung galaxy s2","", time, time);
    private static Item it3 = new Item(3, u2.getUserID(), cat3, null, "Iphone 6s","", time, time);

    private static Deal d1 = new Deal(1, u1.getUserID(), Arrays.asList(it1), Arrays.asList(it3.getCategory()), null, "Viyidi aifons", time);
    private static Deal d22 = new Deal(2, u2.getUserID(), Arrays.asList(it3), Arrays.asList(it1.getCategory()), null, "Viyidi samsungs", time);

    private void emptyBase() {
        DeleteManager.deleteAndReseed("deals");
        DeleteManager.deleteAndReseed("items");
        DeleteManager.deleteAndReseed("item_categories");
        DeleteManager.deleteAndReseed("item_brands");
        DeleteManager.deleteAndReseed("item_types");
        DeleteManager.deleteAndReseed("users");
        DeleteManager.deleteAndReseed("cycles");
    }

    public void insert() {
        emptyBase();

        CategoryManager.insertCategory(cat1);
        CategoryManager.insertCategory(cat2);
        CategoryManager.insertCategory(cat3);

        UserManager.storeUser(u1);
        UserManager.storeUser(u2);

        ItemManager.addItemToDB(it1);
        ItemManager.addItemToDB(it2);
        ItemManager.addItemToDB(it3);

        DealsManager.storeDeal(d1);
        DealsManager.storeDeal(d22);
    }

    @Test
    public void test1() {
        insert();
        dealsMakeCycleInOrder(d1, 1);
        dealsMakeCycleInOrder(d1, 1);
        dealsMakeCycleInOrder(d22, 1);
    }


    private static ItemCategory a = new ItemCategory(1, new ItemSerie("a"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory b = new ItemCategory(2, new ItemSerie("b"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory c = new ItemCategory(3, new ItemSerie("c"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory d = new ItemCategory(4, new ItemSerie("d"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory e = new ItemCategory(5, new ItemSerie("e"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory f = new ItemCategory(6, new ItemSerie("f"), new ItemType("Phone"), new ItemBrand("Samsung"));

    private static Item a1 = new Item(1, 1, a, null, "Samsung galaxy a","", time, time);
    private static Item b2 = new Item(2, 2, b, null, "Samsung galaxy b","", time, time);
    private static Item c2 = new Item(3, 2, c, null, "Samsung galaxy c","", time, time);
    private static Item d2 = new Item(4, 2, d, null, "Samsung galaxy d","", time, time);
    private static Item e3 = new Item(5, 3, e, null, "Samsung galaxy e","", time, time);
    private static Item f3 = new Item(6, 3, f, null, "Samsung galaxy f","", time, time);

    private static Deal deal1 = new Deal(1, 1, Arrays.asList(a1), Arrays.asList(b, c, d), null, "", time);
    private static Deal deal2 = new Deal(2, 2, Arrays.asList(b2, c2, d2), Arrays.asList(e, f), null, "", time);
    private static Deal deal3 = new Deal(3, 3, Arrays.asList(e3, f3), Arrays.asList(a), null, "", time);

    @Test
    public void test2() {

        emptyBase();

        CategoryManager.insertCategory(a);
        CategoryManager.insertCategory(b);
        CategoryManager.insertCategory(c);
        CategoryManager.insertCategory(d);
        CategoryManager.insertCategory(e);
        CategoryManager.insertCategory(f);

        UserManager.storeUser(u1);
        UserManager.storeUser(u2);
        UserManager.storeUser(u3);

        ItemManager.addItemToDB(a1);
        ItemManager.addItemToDB(b2);
        ItemManager.addItemToDB(c2);
        ItemManager.addItemToDB(d2);
        ItemManager.addItemToDB(e3);
        ItemManager.addItemToDB(f3);

        DealsManager.storeDeal(deal1);
        DealsManager.storeDeal(deal2);
        DealsManager.storeDeal(deal3);

        assertEquals(0, getCycleSize());
        dealsMakeCycleInOrder(deal1, 1);
        dealsMakeCycleInOrder(deal1, 1);
        dealsMakeCycleInOrder(deal2, 1);
        dealsMakeCycleInOrder(deal3, 1);
    }


    private void dealsMakeCycleInOrder(Deal deal, int expected) {
        new DealCyclesFinder(deal).run();
        assertEquals(expected, getCycleSize());
    }

    private int getCycleSize() {
        try {
            PreparedStatement st = DatabaseAccessObject.getInstance().getPreparedStatement("SELECT COUNT(*) FROM cycles;");
            ResultSet set = st.executeQuery();
            if(set.next()) return set.getInt(1);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}