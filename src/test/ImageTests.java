package test;

import generalManagers.DeleteManager;
import generalManagers.UpdateManager;
import managers.CategoryManager;
import managers.ItemManager;
import managers.UserManager;
import models.*;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertTrue;


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

    private static User u1 = new User(1,"mudamtqveny", "password", "levan", "gelashvili", "lgela17", "123", null, null, null);
    private static User u2 = new User(2,"king", "heat","lebron","james","ljame03", "6", null, null, null);
    private static final ItemCategory dummy = new ItemCategory(1, new ItemSerie("a"), new ItemType("b"), new ItemBrand("c"));

    private static Item it1 = new Item(1, u1, dummy, null, "Samsung A20", "");
    private static Item it2 = new Item(2, u1, dummy, null, "Hp laptop", "");
    private static Item it3 = new Item(3, u2, dummy, null, "2016 finals trophy", "");

    private static final Image prof1 = new Image(u1,"levanisProfili");
    private static final Image prof2 = new Image(u2, "lebronisProfili");

    private static final Image itemProf1 = new ItemImage("samsungProfile", it1, ImageCategories.ImageCategory.PROFILE);
    private static final Image itemProf2 = new ItemImage("trophyProfile", it3, ImageCategories.ImageCategory.PROFILE);

    private static final Image itemImg1 = new ItemImage("samsungFeatured1", it1, ImageCategories.ImageCategory.FEATURED);
    private static final Image itemImg2 = new ItemImage("samsungFeatured2", it1, ImageCategories.ImageCategory.FEATURED);
    private static final Image itemImg3 = new ItemImage("hpFeatured", it2, ImageCategories.ImageCategory.FEATURED);

    private void emptyBase() {
        DeleteManager.delete("item_categories", "1", 1);
        DeleteManager.delete("item_brands", "1", 1);
        DeleteManager.delete("item_types", "1", 1);
        DeleteManager.delete("profile_images", "1", 1);
        DeleteManager.delete("item_images", "1", 1);
        DeleteManager.delete("items", "1", 1);
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
        /*assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));
        assertTrue(ItemManager.addItemToDB(it1));
        assertTrue(ItemManager.addItemToDB(it2));
        assertTrue(ItemManager.addItemToDB(it3));*/
    }
}
