package test;

import generalManagers.DeleteManager;
import managers.ImagesManager;
import managers.UserManager;
import models.*;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ImageTests {
    /**
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     *
     * For testing purposes : id ----> image_category :
     * 1 ----> profile
     * 2 ----> featured
     * 3 ----> cover
     *
     * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

    //TODO : NEEDS TO BE TESTED OVER BECAUSE OF MASSIVE CHANGES

    /*
    private static final Timestamp time = new Timestamp(System.currentTimeMillis());

    private static final User u1 = new User(1,"LG","password","levan","gelashvili", "lgela17", "555");
    private static final User u2 = new User("KING","heat","Lebron","James","ljame03","23");
    private static final ItemCategory cat = new ItemCategory(1, new ItemSerie("a"), new ItemType("b"), new ItemBrand("c"));

    private static final Item it1 = new Item(1,1,cat,null time);
    private static final Item it2 = new Item(2,2,cat,null, time);

    private static final Image im1 = new ItemImage(1,1,"chemi_cover",1,3, time);
    private static final Image im2 = new ItemImage(2,1,"chemi_featured",1,2, time);
    private static final Image im3 = new ItemImage(3,1,"chemi_meore_featured",1,2, time);
    private static final Image im4 = new ItemImage(4,1,"chemi_nivtis_profili",1,1,time);
    private static final Image im5 = new ItemImage(5,2,"meore_profili",2,1,time);
    private static final Image im6 = new ItemImage(6,2,"meore_featured",2,2,time);

    private static final Image im7 = new Image(7,1,"avatar1",time);
    private static final Image im8 = new Image(8,2,"avatar2",time);


    @Test
    public void prepareForForeignKeys() {
        new UserTests().emptyBase();
        UserManager.storeUser(u1);
        UserManager.storeUser(u2);


//            ეს ჩავამატე ჩემ ბაზაში, რადგან itemManager მზად არ იყო
//
//            insert into items(id,user_id,item_category_id,description,name)
//            values
//            (1,1,1,'magaria','nivti'),
//            (2,2,1,'magaria2','nivti2');
    }

    @Test
    public void addImages() {
        DeleteManager.emptyBase("item_images");
        DeleteManager.emptyBase("profile_images");
        assertTrue(ImagesManager.addImage(im1));
        assertTrue(ImagesManager.addImage(im2));
        assertTrue(ImagesManager.addImage(im3));
        assertTrue(ImagesManager.addImage(im4));
        assertTrue(ImagesManager.addImage(im5));
        assertTrue(ImagesManager.addImage(im6));
        assertTrue(ImagesManager.addImage(im7));
        assertTrue(ImagesManager.addImage(im8));
    }

    @Test
    public void testGetUserImages() {
        List<ItemImage> list1 = ImagesManager.getUserImages(1);
        List<ItemImage> list2 = ImagesManager.getUserImages(2);
        Collections.sort(list1);
        Collections.sort(list2);
        assertEquals(list1, Arrays.asList(im1,im2,im3,im4));
        assertEquals(list2, Arrays.asList(im5,im6));
    }

    @Test
    public void testGetProfileImages() {
        assertEquals(ImagesManager.getUserProfileImage(1),im7);
        assertEquals(ImagesManager.getUserProfileImage(2),im8);
        assertEquals(ImagesManager.getUserProfileImage(3),null);
    }

     */
}
