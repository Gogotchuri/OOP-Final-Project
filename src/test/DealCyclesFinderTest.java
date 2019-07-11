package test;

import database.DatabaseAccessObject;
import events.DealCyclesFinder;
import generalManagers.DeleteManager;
import managers.CategoryManager;
import managers.DealsManager;
import managers.ItemManager;
import managers.UserManager;
import models.Deal;
import models.Item;
import models.User;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DealCyclesFinderTest {

    private static Timestamp time = new Timestamp(System.currentTimeMillis());

    private static User u1 = new User(1,"a","a","a","a","a","a,",null,null,null,time,time);
    private static User u2 = new User(2,"b","b","b","b","b","b,",null,null,null,time,time);
    private static User u3 = new User(3,"c","c","c","c","c","c,",null,null,null,time,time);

    private static ItemCategory cat1 = new ItemCategory(1, new ItemSerie("S3"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory cat2 = new ItemCategory(2, new ItemSerie("S2"), new ItemType("Phone"), new ItemBrand("Samsung"));
    private static ItemCategory cat3 = new ItemCategory(3, new ItemSerie("Iphone"), new ItemType("Phone"), new ItemBrand("Apple"));

    private static Item it1 = new Item(1, u1.getUserID(), cat1, null, "Samsung galaxy s3","", time, time);
    private static Item it2 = new Item(2, u1.getUserID(), cat2, null, "Samsung galaxy s2","", time, time);
    private static Item it3 = new Item(3, u2.getUserID(), cat3, null, "Iphone 6s","", time, time);

    private static Deal d1 = new Deal(1, u1.getUserID(), Arrays.asList(it1), Arrays.asList(it3.getCategory()), null, "Viyidi aifons", time);
    private static Deal d2 = new Deal(2, u2.getUserID(), Arrays.asList(it3), Arrays.asList(it1.getCategory()), null, "Viyidi samsungs", time);

    private void emptyBase() {
        DeleteManager.deleteAndReseed("deals");
        DeleteManager.deleteAndReseed("items");
        DeleteManager.deleteAndReseed("item_categories");
        DeleteManager.deleteAndReseed("item_brands");
        DeleteManager.deleteAndReseed("item_types");
        DeleteManager.deleteAndReseed("users");
        DeleteManager.deleteAndReseed("cycles");
    }

    @Test
    public void insert(List<Deal> deals) {
        emptyBase();

        CategoryManager.insertCategory(cat1);
        CategoryManager.insertCategory(cat2);
        CategoryManager.insertCategory(cat3);

        UserManager.storeUser(u1);
        UserManager.storeUser(u2);

        ItemManager.addItemToDB(it1);
        ItemManager.addItemToDB(it2);
        ItemManager.addItemToDB(it3);

        for (int i=0; i<deals.size()-1; i++) {
            DealsManager.storeDeal(deals.get(i));
        }
    }

    @Test
    public void test1() {
        dealsMakeCycleInOrder(Arrays.asList(d1, d2));
    }

    private void dealsMakeCycleInOrder(List<Deal> deals) {
        insert(deals);
        new DealCyclesFinder(deals.get(deals.size()-1)).run();
        assertEquals(getCycleSize(),1);
    }

    private int getCycleSize() {
        try {
            PreparedStatement st = DatabaseAccessObject.getInstance().getPreparedStatement("SELECT COUNT(*) FROM cycles;");
            ResultSet set = st.executeQuery();
            if(set.next()) return set.getInt(1);
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
