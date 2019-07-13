
package test.events;

import database.DatabaseAccessObject;
import events.DealCyclesFinder;
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
import org.junit.jupiter.api.BeforeAll;
import test.Tester;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.Assert.*;

public class DealCyclesFinderTest extends Tester {

    private static Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"u1","u1","u1","u1","u1","u1",null,null,null, time, time);
    private static User u2 = new User(2,"u2","u2","u2","u2","u2","u2",null,null,null, time, time);
    private static User u3 = new User(3,"u3","u3","u3","u3","u3","u3",null,null,null, time, time);

    private static ItemCategory a = new ItemCategory(1, new ItemSeries("a"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory b = new ItemCategory(2, new ItemSeries("b"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory c = new ItemCategory(3, new ItemSeries("c"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory d = new ItemCategory(4, new ItemSeries("d"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory e = new ItemCategory(5, new ItemSeries("e"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory f = new ItemCategory(6, new ItemSeries("f"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory g = new ItemCategory(7, new ItemSeries("g"), new ItemType("t"), new ItemBrand("b"));
    private static ItemCategory h = new ItemCategory(8, new ItemSeries("h"), new ItemType("t"), new ItemBrand("b"));

    private void setUp() {
        assertTrue(emptyDataBase());

        assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));
        assertTrue(UserManager.storeUser(u3));

        assertEquals(CategoryManager.insertCategory(a), 1);
        assertEquals(CategoryManager.insertCategory(b), 2);
        assertEquals(CategoryManager.insertCategory(c), 3);
        assertEquals(CategoryManager.insertCategory(d), 4);
        assertEquals(CategoryManager.insertCategory(e), 5);
        assertEquals(CategoryManager.insertCategory(f), 6);
        assertEquals(CategoryManager.insertCategory(g), 7);
        assertEquals(CategoryManager.insertCategory(h), 8);
    }

    @After public void tearDown() {
        assertTrue(emptyDataBase());
    }

    /****** run() tests ******/


    /* Test1 Models */


    private static Item t1_item1 = new Item(1, u1.getUserID(), a, null, a.getSeries().getName(),"", time, time);
    private static Item t1_item2 = new Item(2, u1.getUserID(), b, null, b.getSeries().getName(),"", time, time);
    private static Item t1_item3 = new Item(3, u2.getUserID(), c, null, c.getSeries().getName(),"", time, time);

    private static Deal t1_deal1 = new Deal(1, u1.getUserID(), Arrays.asList(t1_item1), Arrays.asList(t1_item2.getCategory()), null, "", time);
    private static Deal t1_deal2 = new Deal(2, u2.getUserID(), Arrays.asList(t1_item2), Arrays.asList(t1_item1.getCategory()), null, "", time);


    @Test public void test1() { setUp();

        ItemManager.addItemToDB(t1_item1);
        ItemManager.addItemToDB(t1_item2);
        ItemManager.addItemToDB(t1_item3);

        DealsManager.storeDeal(t1_deal1);
        DealsManager.storeDeal(t1_deal2);

        assertEquals(0, countCycles());
        checkCycle(t1_deal1, 1);
        checkCycle(t1_deal1, 1);
        checkCycle(t1_deal2, 1);
    }


    /* Test2 Models */


    private static Item a1 = new Item(1, 1, a, null, "a","", time, time);
    private static Item b2 = new Item(2, 2, b, null, "b","", time, time);
    private static Item c2 = new Item(3, 2, c, null, "c","", time, time);
    private static Item d2 = new Item(4, 2, d, null, "d","", time, time);
    private static Item e3 = new Item(5, 3, e, null, "e","", time, time);
    private static Item f3 = new Item(6, 3, f, null, "f","", time, time);
    private static Item g2 = new Item(7, 3, g, null, "g","", time, time);
    private static Item h2 = new Item(8, 3, h, null, "h","", time, time);

    private static Deal t2_deal1 = new Deal(1, u1.getUserID(), Arrays.asList(a1), Arrays.asList(b, c, d), null, "", time);
    private static Deal t2_deal2 = new Deal(2, u2.getUserID(), Arrays.asList(b2, c2, d2), Arrays.asList(e, f), null, "", time);
    private static Deal t2_deal3 = new Deal(3, u3.getUserID(), Arrays.asList(e3, f3), Arrays.asList(g, h), null, "", time);
    private static Deal t2_deal4 = new Deal(4, u2.getUserID(), Arrays.asList(g2, h2), Arrays.asList(a), null, "", time);

    @Test public void test2() { setUp();

        ItemManager.addItemToDB(a1);
        ItemManager.addItemToDB(b2);
        ItemManager.addItemToDB(c2);
        ItemManager.addItemToDB(d2);
        ItemManager.addItemToDB(e3);
        ItemManager.addItemToDB(f3);
        ItemManager.addItemToDB(g2);
        ItemManager.addItemToDB(h2);

        DealsManager.storeDeal(t2_deal1);
        DealsManager.storeDeal(t2_deal2);
        DealsManager.storeDeal(t2_deal3);
        DealsManager.storeDeal(t2_deal4);

        assertEquals(0, countCycles());
        checkCycle(t2_deal1, 1);
        checkCycle(t2_deal1, 1);
        checkCycle(t2_deal2, 1);
        checkCycle(t2_deal3, 1);
        checkCycle(t2_deal4, 1);
    }


    /*** Helpers ****/


    private void checkCycle(Deal deal, int expected) {
        new DealCyclesFinder(deal).run();
        assertEquals(expected, countCycles());
    }


    private int countCycles() {
        try {
            PreparedStatement statement =
                DatabaseAccessObject.getInstance().getPreparedStatement(
                    "SELECT COUNT(*) FROM cycles;"
                );
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
