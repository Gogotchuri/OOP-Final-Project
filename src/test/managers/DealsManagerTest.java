
package test.managers;

import controllers.front.DealsController;
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
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DealsManagerTest extends Tester { // TODO: Krawa

    private static ItemCategory cat1 = new ItemCategory(1, new ItemSeries("item1"), new ItemType("car"), new ItemBrand("toyota"));
    private static ItemCategory cat2 = new ItemCategory(2, new ItemSeries("item2"), new ItemType("fridge"), new ItemBrand("samsung"));
    private static ItemCategory cat3 = new ItemCategory(3, new ItemSeries("item3"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
    private static ItemCategory cat4 = new ItemCategory(4, new ItemSeries("item4"), new ItemType("fridge"), new ItemBrand("samsung"));
    private static ItemCategory cat5 = new ItemCategory(5, new ItemSeries("item5"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
    private static ItemCategory cat6 = new ItemCategory(6, new ItemSeries("item6"), new ItemType("lolipop"), new ItemBrand("chupa-chups"));
    private static User user1 = new User("one", "1", "o",
            "ne", "onemail", "111");
    private static User user2 = new User("two", "2", "t",
            "wo", "twomail", "222");
    private static User user3 = new User("three", "3", "th",
            "ree", "threemail", "333");
    private static Item item1 = new Item(1, 1, cat1, null, "sams s3",
            "qaia", null, null);
    private static Item item2 = new Item(2, 2, cat2, null, "sams s2",
            "qaia", null, null);
    private static Item item3 = new Item(3, 1, cat3, null, "macomputer",
            "qaia", null, null);
    private static Item item4 = new Item(4, 1, cat4, null, "blia",
            "qaia", null, null);
    private static Item item5 = new Item(5, 2, cat5, null, "blous",
            "qaia", null, null);
    private static Item item6 = new Item(6, 3, cat6, null, "blous",
            "qaia", null, null);


    private static ItemImage image1 = new ItemImage("https://media.istockphoto.com/photos/green-apple-picture-id475190419?k=6&m=475190419&s=612x612&w=0&h=G01aHVafnPd01ugi6dmJKtNHS-nz0GrQAbpzDcjuXI0=",
            1, item1.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static ItemImage image2 = new ItemImage("https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/FraiseFruitPhoto.jpg/220px-FraiseFruitPhoto.jpg",
            2, item2.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static ItemImage image3 = new ItemImage("https://cdn1.medicalnewstoday.com/content/images/articles/318/318601/avocado-sliced.jpg",
            1, item3.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static ItemImage image4 = new ItemImage("https://images.pexels.com/photos/1108099/pexels-photo-1108099.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500",
            1, item4.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static ItemImage image5 = new ItemImage("https://cdn.pixabay.com/photo/2018/05/07/10/48/husky-3380548__340.jpg",
            2, item5.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static ItemImage image6 = new ItemImage("https://boygeniusreport.files.wordpress.com/2016/11/puppy-dog.jpg?quality=98&strip=all&w=782",
            3, item6.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static Deal deal1 = new Deal(1, new ArrayList<>(Arrays.asList(item1, item2)),
            new ArrayList<>(Arrays.asList(cat3)), "deal1", "desc1");
    private static Deal deal2 = new Deal(2, new ArrayList<>(Arrays.asList(item3)),
            new ArrayList<>(Arrays.asList(cat2, cat1)), "deal2", "desc2");
    private static Deal deal3 = new Deal(1, new ArrayList<>(Arrays.asList(item4)),
            new ArrayList<>(Arrays.asList(cat5)), "deal3", "desc3");
    private static Deal deal4 = new Deal(2, new ArrayList<>(Arrays.asList(item5)),
            new ArrayList<>(Arrays.asList(cat6)), "deal4", "desc4");
    private static Deal deal5 = new Deal(3, new ArrayList<>(Arrays.asList(item6)),
            new ArrayList<>(Arrays.asList(cat4)), "deal5", "desc5");
    private static Deal deal6 = new Deal(2, new ArrayList<>(Arrays.asList(item3)),
            new ArrayList<>(Arrays.asList(cat4)), "deal6", "desc6");
    private static Cycle cycle1 = new Cycle(Arrays.asList(deal1, deal2));
    private static Cycle cycle2 = new Cycle(Arrays.asList(deal3, deal4, deal5));

    @Before
    public void setUp() throws Exception {
        emptyDataBase();

        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
        UserManager.storeUser(user3);
        CategoryManager.insertCategory(cat1);
        CategoryManager.insertCategory(cat2);
        CategoryManager.insertCategory(cat3);
        CategoryManager.insertCategory(cat4);
        CategoryManager.insertCategory(cat5);
        CategoryManager.insertCategory(cat6);
        ItemManager.addItemToDB(item1);
        ItemManager.addItemToDB(item2);
        ItemManager.addItemToDB(item3);
        ItemManager.addItemToDB(item4);
        ItemManager.addItemToDB(item5);
        ItemManager.addItemToDB(item6);
        ImagesManager.addImage(image1);
        ImagesManager.addImage(image2);
        ImagesManager.addImage(image3);
        ImagesManager.addImage(image4);
        ImagesManager.addImage(image5);
        ImagesManager.addImage(image6);

        assertNotEquals(DealsManager.storeDeal(deal1), 0);
        assertNotEquals(DealsManager.storeDeal(deal2), 0);
        assertNotEquals(DealsManager.storeDeal(deal3), 0);
        assertNotEquals(DealsManager.storeDeal(deal4), 0);
        assertNotEquals(DealsManager.storeDeal(deal5), 0);
        assertNotEquals(DealsManager.storeDeal(deal6), 0);
        CycleManager.addCycleToDB(cycle1);
        CycleManager.addCycleToDB(cycle2);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getDealByDealID() {
        Deal deal1 = DealsManager.getDealByDealID(1);
        assertEquals(deal1.getOwnerID(), 1);
        assertEquals(deal1.getOwnedItems().size(), 2);
        assertEquals(deal1.getWantedCategories().size(), 1);
        assertEquals(DealsManager.getDealByDealID(2).getOwnerID(), 2);
        assertEquals(DealsManager.getDealByDealID(3).getOwnerID(), 1);
        assertEquals(DealsManager.getDealByDealID(4).getOwnerID(), 2);
        assertEquals(DealsManager.getDealByDealID(5).getOwnerID(), 3);
    }

    @Test
    public void getDealsByUserID() {
        List<Deal> deals = DealsManager.getDealsByUserID(3);
        assertEquals(deals.size(), 1);
        assertEquals(deals.get(0).getDealID(), 5);
        assertEquals(deals.get(0).getDescription(), "desc5");
        assertEquals(deals.get(0).getTitle(), "deal5");
        assertEquals(deals.get(0).getStatus(), ProcessStatus.Status.WAITING);
        assertNotEquals(deals.get(0).getCreateDate(), null);
        deals = DealsManager.getDealsByUserID(2);
        assertEquals(deals.size(), 3);
        deals = DealsManager.getDealsByUserID(1);
        assertEquals(deals.size(), 2);

    }

    @Test
    public void getDealsByCycleID() {
        List<Deal> deals = DealsManager.getDealsByCycleID(1);
        assertEquals(deals.size(), 2);
        Tester.equalLists(Arrays.asList(deals.get(0).getDealID(),deals.get(1).getDealID()), Arrays.asList(1, 2));
        deals = DealsManager.getDealsByCycleID(2);
        assertEquals(deals.size(), 3);
        Tester.equalLists(Arrays.asList(deals.get(0).getDealID(),deals.get(1).getDealID(),
                deals.get(2).getDealID()), Arrays.asList(3, 4, 5));

    }

    @Test
    public void getDealsBySearchCriteria() {
        DealsController.SearchCriteria sc1 = new DealsController.SearchCriteria();
        sc1.addCriteria(DealsController.SearchCriteria.Criteria.USER_NAME, "two");
        DealsController.SearchCriteria sc2 = new DealsController.SearchCriteria();
        sc2.addCriteria(DealsController.SearchCriteria.Criteria.CATEGORY_NAME, cat4.getSeries().getName());
        assertEquals(DealsManager.getDealsBySearchCriteria(sc1).size(), 3);
        assertEquals(DealsManager.getDealsBySearchCriteria(sc2).size(), 2);
    }

    @Test
    public void getClients() {
        assertEquals(DealsManager.getClients(deal1.getDealID()).size(), 1);
        assertEquals(DealsManager.getClients(deal2.getDealID()).size(), 1);
        assertEquals(DealsManager.getClients(deal3.getDealID()).size(), 2);
    }

    @Test
    public void deleteDeal() {
        assertTrue(DealsManager.deleteDeal(deal1.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal2.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal3.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal4.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal5.getDealID()));
        assertTrue(DealsManager.deleteDeal(deal6.getDealID()));
    }

    @Test
    public void getUsersDealsByCycleId(){
        List<Deal> deals = DealsManager.getUsersDealsByCycleId(1, 1);
        assertEquals(deals.size(), 1);
        assertEquals(deals.get(0).getDealID(), 1);
        deals = DealsManager.getUsersDealsByCycleId(3, 2);
        assertEquals(deals.size(), 1);
        assertEquals(deals.get(0).getDealID(), 5);
        deals = DealsManager.getUsersDealsByCycleId(3, 3);
        assertEquals(deals.size(), 0);
        deals = DealsManager.getUsersDealsByCycleId(4, 2);
        assertEquals(deals.size(), 0);
    }
}
