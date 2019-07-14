
package test.managers;

import generalManagers.DeleteManager;
import jdk.jfr.Category;
import managers.CategoryManager;
import managers.ImagesManager;
import managers.ItemManager;
import managers.UserManager;
import models.*;
import models.categoryModels.ItemCategory;
import org.junit.*;
import test.Tester;

import java.util.List;

import static org.junit.Assert.*;

public class ImagesManagerTest extends Tester {

    private static int user1ID;
    private static int user2ID;

    private static Image profile1;
    private static Image profile2;

    private static ItemImage itemImage1;
    private static ItemImage itemImage2;
    private static ItemImage itemImage3;


    @Before
    public void setUp() throws Exception {
        Tester.emptyDataBase();

        User user1 = new User("user1", "123456", "", "", "email1", "1");
        User user2 = new User("user2", "123456", "", "", "email2", "2");
        //User user3 = new User("user3", "123456", "", "", "", "3");
        //User user4 = new User("user4", "123456", "", "", "", "4");

        UserManager.storeUser(user1);
        UserManager.storeUser(user2);
        //UserManager.storeUser(user3);
        //UserManager.storeUser(user4);

        user1ID = UserManager.getUserByUsername("user1").getUserID();
        user2ID = UserManager.getUserByUsername("user2").getUserID();

        profile1 = new Image(user1ID, "image1");
        profile2 = new Image(user2ID, "image2");
        //Image profile3 = new Image(UserManager.getUserByUsername("user3").getUserID(), "image1");
        //Image profile4 = new Image(UserManager.getUserByUsername("user4").getUserID(), "image1");

        //item1 --> user1
        //item2 --> user1
        //item3 --> user2

        ItemCategory cat = new ItemCategory(1, "", "", "");
        CategoryManager.insertCategory(cat);

        Item item1 = new Item(1, user1ID, cat, null, "", "", null, null);
        Item item2 = new Item(2, user1ID, cat, null, "", "", null, null);
        Item item3 = new Item(3, user2ID, cat, null, "", "", null, null);

        ItemManager.addItemToDB(item1);
        ItemManager.addItemToDB(item2);
        ItemManager.addItemToDB(item3);

        itemImage1 = new ItemImage("image3", user1ID, 1, ImageCategories.ImageCategory.FEATURED);
        itemImage2 = new ItemImage("image4", user1ID, 2, ImageCategories.ImageCategory.FEATURED);
        itemImage3 = new ItemImage("image5", user2ID, 3, ImageCategories.ImageCategory.FEATURED);

        assertTrue(ImagesManager.addImage(profile1));
        assertTrue(ImagesManager.addImage(profile2));
        assertTrue(ImagesManager.addImage(itemImage1));
        assertTrue(ImagesManager.addImage(itemImage2));
        assertTrue(ImagesManager.addImage(itemImage3));
    }

    @After
    public void tearDown() throws Exception {

    }



    //tested before
    @Test
    public void addImage() {
    }

    @Test
    public void getItemImageByID() {
        assertEquals(ImagesManager.getItemImageByID(1), itemImage1);
        assertEquals(ImagesManager.getItemImageByID(2), itemImage2);
        assertEquals(ImagesManager.getItemImageByID(3), itemImage3);
        assertEquals(ImagesManager.getItemImageByID(11), null);
    }

    @Test
    public void getUserProfileImage() {
        assertEquals(ImagesManager.getUserProfileImage(1), profile1);
        assertEquals(ImagesManager.getUserProfileImage(2), profile2);
        assertEquals(ImagesManager.getUserProfileImage(10), null);
    }

    @Test
    public void getItemImagesByUserID() {
        List<ItemImage> userImages1 = ImagesManager.getItemImagesByUserID(1);
        List<ItemImage> userImages2 = ImagesManager.getItemImagesByUserID(2);
        List<ItemImage> userImages3 = ImagesManager.getItemImagesByUserID(11);
        assertTrue(userImages1.contains(itemImage1));
        assertTrue(userImages1.contains(itemImage2));
        assertFalse(userImages1.contains(itemImage3));
        assertFalse(userImages2.contains(itemImage1));
        assertFalse(userImages2.contains(itemImage2));
        assertTrue(userImages2.contains(itemImage3));
        assertTrue(userImages3.size() == 0);
    }

    @Test
    public void getItemImagesByItemID() {
        List<ItemImage> itemImages1 = ImagesManager.getItemImagesByItemID(1);
        List<ItemImage> itemImages2 = ImagesManager.getItemImagesByItemID(2);
        List<ItemImage> itemImages3 = ImagesManager.getItemImagesByItemID(3);
        List<ItemImage> itemImages4 = ImagesManager.getItemImagesByItemID(19);
        assertTrue(itemImages1.contains(itemImage1));
        assertFalse(itemImages1.contains(itemImage2));
        assertTrue(itemImages2.contains(itemImage2));
        assertTrue(itemImages3.contains(itemImage3));
        assertTrue(itemImages4.size()==0);
    }
}
