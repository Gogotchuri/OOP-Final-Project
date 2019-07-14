
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

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CycleManagerTest extends Tester {


    private static Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"u1","u1","u1","u1","u1","u1",null,null,null, time, time);
    private static User u2 = new User(2,"u2","u2","u2","u2","u2","u2",null,null,null, time, time);

    private static ItemCategory ic1 = new ItemCategory(1, new ItemSeries("is1"), new ItemType("t1"), new ItemBrand("b1"));
    private static ItemCategory ic2 = new ItemCategory(2, new ItemSeries("is2"), new ItemType("t1"), new ItemBrand("b2"));

    private static Item i1 = new Item(1, u1.getUserID(), ic1, null, ic1.getSeries().getName(),"", time, time);
    private static Item i2 = new Item(2, u1.getUserID(), ic2, null, ic2.getSeries().getName(),"", time, time);

    private static Deal d1 = new Deal(1, u1.getUserID(), Arrays.asList(i1), Arrays.asList(i2.getCategory()), null, "", "", time);
    private static Deal d2 = new Deal(2, u2.getUserID(), Arrays.asList(i2), Arrays.asList(i1.getCategory()), null, "", "", time);

    private static Cycle c = new Cycle(1, ProcessStatus.Status.WAITING, Arrays.asList(d1, d2));


    @Before public void setUp() {
        emptyDataBase();

        assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));

        assertEquals(CategoryManager.insertCategory(ic1), 1);
        assertEquals(CategoryManager.insertCategory(ic2), 2);

        assertTrue(ItemManager.addItemToDB(i1));
        assertTrue(ItemManager.addItemToDB(i2));

        assertEquals(DealsManager.storeDeal(d1), 1);
        assertEquals(DealsManager.storeDeal(d2), 2);

        assertTrue(CycleManager.addCycleToDB(c)); // Test addCycleToDB()
    }


    @After public void tearDown() {
        assertTrue(CycleManager.deleteCycle(c.getCycleID()));
        emptyDataBase();
    }


    @Test public void getCycleByCycleIDTest() {
        assertNotNull(CycleManager.getCycleByCycleID(1));
        assertNull(CycleManager.getCycleByCycleID(2));
    }


    @Test public void containsDBTest() {
        try { assertTrue(CycleManager.containsDB(c)); }
        catch (SQLException e) {
            e.printStackTrace();
            fail();
        }
    }


    @Test public void acceptCycleTest() {
        Cycle temp = CycleManager.getCycleByCycleID(c.getCycleID());
        assertNotNull(temp);

        assertEquals(temp.getCycleStatus(), ProcessStatus.Status.WAITING);

        assertTrue(CycleManager.acceptCycle(c.getCycleID(), d1.getDealID()));
        assertTrue(CycleManager.acceptCycle(c.getCycleID(), d2.getDealID()));

        temp = CycleManager.getCycleByCycleID(c.getCycleID());
        assertNotNull(temp);

        assertEquals(temp.getCycleStatus(), ProcessStatus.Status.ONGOING);
    }


    @Test public void userParticipatesInCycleTest() {

        assertTrue(CycleManager.userParticipatesInCycle(u1.getUserID(), c.getCycleID()));
        assertTrue(CycleManager.userParticipatesInCycle(u2.getUserID(), c.getCycleID()));

        assertFalse(CycleManager.userParticipatesInCycle(3, c.getCycleID()));
        assertFalse(CycleManager.userParticipatesInCycle(u1.getUserID(), 2));
    }


    @Test public void getUserCyclesTest() {

        checkCycleListSize(CycleManager.getUserCycles(u1.getUserID()), 1);
        checkCycleListSize(CycleManager.getUserCycles(u2.getUserID()), 1);
        checkCycleListSize(CycleManager.getUserCycles(3), 0);
    }


    @Test public void getCyclesByDealIDTest() {

        checkCycleListSize(CycleManager.getCyclesByDealID(d1.getDealID()), 1);
        checkCycleListSize(CycleManager.getCyclesByDealID(d2.getDealID()), 1);
        checkCycleListSize(CycleManager.getCyclesByDealID(3), 0);

    }


    private static void checkCycleListSize(List<Cycle> cycleList, int expectedSize) {
        assertNotNull(cycleList);
        assertEquals(cycleList.size(), expectedSize);
    }

    @Test public void isOfferedCycleAcceptedTest() {
        assertFalse(CycleManager.isOfferedCycleAccepted(c.getCycleID(), d1.getOwnerID()));
        CycleManager.acceptCycle(c.getCycleID(), d1.getDealID());
        assertTrue(CycleManager.isOfferedCycleAccepted(c.getCycleID(), d1.getOwnerID()));
    }
}
