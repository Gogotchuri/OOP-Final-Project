package test;

import controllers.front.DealsController;
import generalManagers.DeleteManager;
import managers.CycleManager;
import managers.DealsManager;
import managers.ItemManager;
import managers.UserManager;
import models.Cycle;
import models.Deal;
import models.Item;
import models.User;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DealTests {

    private static final ItemCategory cat1 = makeCategory("S3", "Phone", "Samsung");
    private static final ItemCategory cat2 = makeCategory("S2", "Phone", "Samsung");
    private static final ItemCategory cat3 = makeCategory("iMac", "Computer", "Apple");
    private static final User user1 = new User("one", "1", "o",
            "ne", "onemail", "111");
    private static final  User user2 = new User("two", "2", "t",
            "wo", "twomail", "222");
    private static final Item item1 = new Item(1, user1, cat1, null, "sams s3",
            "qaia", null, null);
    private static final Item item2 = new Item(2, user2, cat2, null, "sams s2",
            "qaia", null, null);
    private static final Item item3 = new Item(3, user1, cat3, null, "macomputer",
            "qaia", null, null);
    private Deal deal1, deal2;

    @BeforeClass
    public static void setup(){
        DeleteManager.emptyBase("deals");
        DeleteManager.emptyBase("users");
        DeleteManager.emptyBase("items");
        DeleteManager.emptyBase("cycles");
        ItemManager.addItemToDB(item1);
        ItemManager.addItemToDB(item2);
        ItemManager.addItemToDB(item3);
        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
    }

    @Test
    public void addDealTest(){
        deal1 = new Deal(user1, new ArrayList<>(Arrays.asList(item1, item2)),
                new ArrayList<>(Arrays.asList(cat3)));
        deal2 = new Deal(user2, new ArrayList<>(Arrays.asList(item3)),
                new ArrayList<>(Arrays.asList(cat2, cat3)));
        assertNotEquals(DealsManager.storeDeal(deal1), 0);
        assertNotEquals(DealsManager.storeDeal(deal2), 0);
    }

    @Test
    public void getDealByIDTest(){
        assertNotEquals(DealsManager.getDealByDealID(deal1.getDealID()), null);
        assertNotEquals(DealsManager.getDealByDealID(deal2.getDealID()), null);
    }

    @Test
    public void getDealsByUserTest(){
        assertEquals(DealsManager.getDealsByUserID(user1.getUserID()).get(0).getDealID(), deal1.getDealID());
        assertEquals(DealsManager.getDealsByUserID(user2.getUserID()).get(0).getDealID(), deal2.getDealID());
    }

    @Test
    public void getDealsByCycleIDTest(){
        Cycle cycle = new Cycle(new ArrayList<>(Arrays.asList(deal1, deal2)));
        CycleManager.addCycleToDB(cycle);
        assertEquals(DealsManager.getDealsByCycleID(cycle.getCycleID()).size(), 2);
    }

    @Test
    public void getDealsBySearchCriteriaTest(){
        DealsController.SearchCriteria sc1 = new DealsController.SearchCriteria();
        sc1.addCriteria(DealsController.SearchCriteria.Criteria.USER_NAME, "one");
        DealsController.SearchCriteria sc2 = new DealsController.SearchCriteria();
        sc2.addCriteria(DealsController.SearchCriteria.Criteria.CATEGORY_NAME, "S2");
        assertEquals(DealsManager.getDealsBySearchCriteria(sc1).size(), 1);
        assertEquals(DealsManager.getDealsBySearchCriteria(sc2).size(), 1);
    }

    @Test
    public void getClientsTest(){
        assertEquals(DealsManager.getClients(deal1.getDealID()).size(), 1);
        assertEquals(DealsManager.getClients(deal2.getDealID()).size(), 1);
    }

    @Test
    public void deleteDealTest(){
        assertTrue(DealsManager.deleteDeal(deal1.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal2.getDealID()));
    }


    private static ItemCategory makeCategory(String serie, String type, String brand) {
        return new ItemCategory(new ItemSerie(serie), new ItemType(type), new ItemBrand(brand));
    }
}
