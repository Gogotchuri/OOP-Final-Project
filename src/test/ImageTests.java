package test;

import database.DatabaseAccessObject;
import managers.ImagesManager;
import managers.ItemManager;
import managers.UserManager;
import models.Category;
import models.Image;
import models.Item;
import models.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ImageTests {
    /**
     * For testing purposes : id ----> image_category :
     * 1 ----> profile
     * 2 ----> featured
     * 3 ----> cover
     *
     * Some of the photos are user profile pictures, so they don't have an item it, which i set to 0 here
     */
    private static final Timestamp time = new Timestamp(System.currentTimeMillis());

    private static final User u1 = new User(1,"LG","password","levan","gelashvili", "lgela17", "555");
    private static final Category cat = new Category(1,"kategoria");

    private static final Item it1 = new Item(1,1,"nivti","magaria", cat, time);

    private static final Image im1 = new Image(1,1,"chemi_profili",1,0,time);
    private static final Image im2 = new Image(2,2,"chemi_featured",1,1,time);
    private static final Image im3 = new Image(3,2,"chemi_meore_featured",1,1,time);
    private static final Image im4 = new Image(4,1,"chemi_nivtis_profili",1,2,time);

    @Test
    public void emptyBase() {
        try {
            PreparedStatement st = DatabaseAccessObject.getInstance().getPreparedStatement("delete from images;");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void prepareForForeignKeys() {
        UserTests.emptyBase();
        UserManager.storeUser(u1);
        ItemManager.addItemToDB(it1);
    }

    @Test
    public void addImages() {
        assertTrue(ImagesManager.addImage(im1));
    }
}
