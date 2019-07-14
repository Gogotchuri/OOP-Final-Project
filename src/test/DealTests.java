package test;

import controllers.front.DealsController;
import generalManagers.DeleteManager;
import managers.*;
import models.*;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSeries;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class DealTests {

    @Test
    public void setup(){

        DeleteManager.emptyBase("offered_cycles");
        DeleteManager.emptyBase("cycles");
        DeleteManager.emptyBase("deals");
        DeleteManager.emptyBase("users");
        DeleteManager.emptyBase("items");

        ItemCategory cat1 = new ItemCategory(1, new ItemSeries("item1"), new ItemType("car"), new ItemBrand("toyota"));
        ItemCategory cat2 = new ItemCategory(2, new ItemSeries("item2"), new ItemType("fridge"), new ItemBrand("samsung"));
        ItemCategory cat3 = new ItemCategory(3, new ItemSeries("item3"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
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

        ItemImage image1 = new ItemImage("https://media.istockphoto.com/photos/green-apple-picture-id475190419?k=6&m=475190419&s=612x612&w=0&h=G01aHVafnPd01ugi6dmJKtNHS-nz0GrQAbpzDcjuXI0=",
                user1.getUserID(), item1.getItemID(), ImageCategories.ImageCategory.FEATURED);
        ItemImage image2 = new ItemImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/FraiseFruitPhoto.jpg/220px-FraiseFruitPhoto.jpg",
                user1.getUserID(), item2.getItemID(), ImageCategories.ImageCategory.FEATURED);
        ItemImage image3 = new ItemImage("https://cdn1.medicalnewstoday.com/content/images/articles/318/318601/avocado-sliced.jpg",
                user2.getUserID(), item3.getItemID(), ImageCategories.ImageCategory.FEATURED);
        ImagesManager.addImage(image1);
        ImagesManager.addImage(image2);
        ImagesManager.addImage(image3);
        Deal deal1 = new Deal(user1.getUserID(), new ArrayList<>(Arrays.asList(item1, item2)),
                new ArrayList<>(Arrays.asList(cat3)), "deal1");
        Deal deal2 = new Deal(user2.getUserID(), new ArrayList<>(Arrays.asList(item3)),
                new ArrayList<>(Arrays.asList(cat2, cat1)), "deal2");
        assertNotEquals(DealsManager.storeDeal(deal1), 0);
        assertNotEquals(DealsManager.storeDeal(deal2), 0);

        assertNotEquals(DealsManager.getDealByDealID(deal1.getDealID()), null);
        assertNotEquals(DealsManager.getDealByDealID(deal2.getDealID()), null);

        assertEquals(DealsManager.getDealsByUserID(user1.getUserID()).get(0).getDealID(), deal1.getDealID());
        assertEquals(DealsManager.getDealsByUserID(user2.getUserID()).get(0).getDealID(), deal2.getDealID());

        Cycle cycle = new Cycle(new ArrayList<>(Arrays.asList(deal1, deal2)));
        CycleManager.addCycleToDB(cycle);
        assertEquals(DealsManager.getDealsByCycleID(cycle.getCycleID()).size(), 2);

        DealsController.SearchCriteria sc1 = new DealsController.SearchCriteria();
        sc1.addCriteria(DealsController.SearchCriteria.Criteria.USER_NAME, "one");
        DealsController.SearchCriteria sc2 = new DealsController.SearchCriteria();
        sc2.addCriteria(DealsController.SearchCriteria.Criteria.CATEGORY_NAME, cat1.getSeries().getName());
        assertEquals(DealsManager.getDealsBySearchCriteria(sc1).size(), 1);
        assertEquals(DealsManager.getDealsBySearchCriteria(sc2).size(), 1);

        assertEquals(DealsManager.getClients(deal1.getDealID()).size(), 1);
        assertEquals(DealsManager.getClients(deal2.getDealID()).size(), 1);

        //assertTrue(DealsManager.deleteDeal(deal1.getDealID()));
        //assertTrue(DealsManager.deleteDeal(deal2.getDealID()));
    }


    private static ItemCategory makeCategory(String serie, String type, String brand) {
        return new ItemCategory(new ItemSeries(serie), new ItemType(type), new ItemBrand(brand));
    }
}
