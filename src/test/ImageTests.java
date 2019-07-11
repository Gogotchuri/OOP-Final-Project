package test;

import generalManagers.DeleteManager;
import generalManagers.UpdateManager;
import managers.CategoryManager;
import managers.ImagesManager;
import managers.ItemManager;
import managers.UserManager;
import models.*;
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


public class ImageTests {
    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * For testing purposes : id ----> image_category :
     * 1 ----> profile
     * 2 ----> featured
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */


    private static final Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"mudamtqveny", "password", "levan", "gelashvili", "lgela17", "123", null, null, null, time, time);
    private static User u2 = new User(2,"king", "heat","lebron","james","ljame03", "6", null, null, null, time, time);
    private static final ItemCategory dummy = new ItemCategory(1, new ItemSerie("a"), new ItemType("b"), new ItemBrand("c"));

    private static Item it1 = new Item(1, u1.getUserID(), dummy, null, "Samsung A20", "", time, time);
    private static Item it2 = new Item(2, u1.getUserID(), dummy, null, "Hp laptop", "",time, time);
    private static Item it3 = new Item(3, u2.getUserID(), dummy, null, "2016 finals trophy", "", time, time);

    private static final Image prof1 = new Image(u1.getUserID(),"levanisProfili");
    private static final Image prof2 = new Image(u2.getUserID(),"lebronisProfili");

    private static final Image itemProf1 = new ItemImage("samsungProfile", it1.getOwnerID(), it1.getItemID(), ImageCategories.ImageCategory.PROFILE);
    private static final Image itemProf2 = new ItemImage("trophyProfile", it3.getOwnerID(), it3.getItemID(), ImageCategories.ImageCategory.PROFILE);

    private static final Image itemImg1 = new ItemImage("samsungFeatured1", it1.getOwnerID(), it1.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static final Image itemImg2 = new ItemImage("samsungFeatured2", it1.getOwnerID(), it1.getItemID(), ImageCategories.ImageCategory.FEATURED);
    private static final Image itemImg3 = new ItemImage("hpFeatured", it2.getOwnerID(), it2.getItemID(), ImageCategories.ImageCategory.FEATURED);

    private void emptyBase() {
        DeleteManager.delete("profile_images", "1", 1);
        DeleteManager.delete("item_images", "1", 1);
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
        UpdateManager.reseedTable("profile_images", 1);
        UpdateManager.reseedTable("item_images", 1);
    }

    @Test
    public void insertIntoBase() {
        emptyBase();
        assertTrue(CategoryManager.insertCategory(dummy));
        assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));
        assertTrue(ItemManager.addItemToDB(it1));
        assertTrue(ItemManager.addItemToDB(it2));
        assertTrue(ItemManager.addItemToDB(it3));

        assertTrue(ImagesManager.addImage(prof1));
        assertTrue(ImagesManager.addImage(prof2));

        assertTrue(ImagesManager.addImage(itemProf1));
        assertTrue(ImagesManager.addImage(itemProf2));

        assertTrue(ImagesManager.addImage(itemImg1));
        assertTrue(ImagesManager.addImage(itemImg2));
        assertTrue(ImagesManager.addImage(itemImg3));
    }

    @Test
    public void checkUserProfilePictures() {
        assertEquals(ImagesManager.getUserProfileImage(u1.getUserID()), prof1);
        assertEquals(ImagesManager.getUserProfileImage(u2.getUserID()), prof2);
        assertNotEquals(ImagesManager.getUserProfileImage(u2.getUserID()), prof1);
    }

    @Test
    public void checkUserItems() {
        equalLists(ImagesManager.getItemImagesByUserID(u1.getUserID()), Arrays.asList(itemProf1,itemImg1,itemImg2,itemImg3));
        equalLists(ImagesManager.getItemImagesByUserID(u2.getUserID()), Arrays.asList(itemProf2));
        equalLists(ImagesManager.getItemImagesByUserID(55555), Arrays.asList());
        unEqualLists(ImagesManager.getItemImagesByUserID(u1.getUserID()), Arrays.asList(itemProf1));
        unEqualLists(ImagesManager.getItemImagesByUserID(u1.getUserID()), Arrays.asList(itemProf2,itemImg3));
    }

    @Test
    public void checkItemImages() {
        equalLists(ImagesManager.getItemImagesByItemID(it1.getItemID()), Arrays.asList(itemProf1, itemImg1, itemImg2));
        equalLists(ImagesManager.getItemImagesByItemID(it2.getItemID()), Arrays.asList(itemImg3));
        equalLists(ImagesManager.getItemImagesByItemID(it3.getItemID()), Arrays.asList(itemProf2));
        equalLists(ImagesManager.getItemImagesByItemID(21345), Arrays.asList());
        unEqualLists(ImagesManager.getItemImagesByItemID(it1.getItemID()), Arrays.asList(itemProf2));
        unEqualLists(ImagesManager.getItemImagesByItemID(it2.getItemID()), Arrays.asList(itemProf2, itemImg3));
    }

    @Test
    public void takeByID() {
        assertEquals(ImagesManager.getItemImageByID(1), itemProf1);
        assertEquals(ImagesManager.getItemImageByID(3), itemImg1);
        assertEquals(ImagesManager.getItemImageByID(4), itemImg2);
    }

    private void equalLists(List<ItemImage> a, List<Image> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertEquals(a,b);
    }

    private void unEqualLists(List<ItemImage> a, List<Image> b) {
        Collections.sort(a);
        Collections.sort(b);
        assertNotEquals(a,b);
    }
}
