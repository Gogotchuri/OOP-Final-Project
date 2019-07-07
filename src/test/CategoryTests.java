package test;

import generalManagers.DeleteManager;
import generalManagers.UpdateManager;
import managers.CategoryManager;
import models.Category;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {

    private static final String BRAND_TABLE = "item_brands";
    private static final String SERIE_TABLE = "item_categories";
    private static final String TYPE_TABLE = "item_types";
    private static final String OTHER = "other";

    private static final ItemCategory cat1 = makeCategory("S3", "ტელეფონი", "Samsung");
    private static final ItemCategory cat2 = makeCategory("S2", "ტელეფონი", "Samsung");
    private static final ItemCategory cat3 = makeCategory("E40", "მაცივარი", "Samsung");
    private static final ItemCategory cat4 = makeCategory("Iphone 6", "ტელეფონი", "Apple");

    private static final ItemCategory cat5 = makeCategory("S3", OTHER, "Samsung");
    private static final ItemCategory cat6 = makeCategory("S3", "ტელეფონი", OTHER);
    private static final ItemCategory cat7 = makeCategory("S3", OTHER, OTHER);

    @Test
    public void resetBase() {

        // In a query this translates like 'WHERE 1 = 1' and deletes everything
        DeleteManager.delete(SERIE_TABLE, "1", 1);
        DeleteManager.delete(TYPE_TABLE, "1", 1);
        DeleteManager.delete(BRAND_TABLE, "1", 1);

        //Reseeds every table to 1
        UpdateManager.reseedTable(TYPE_TABLE, 1);
        UpdateManager.reseedTable(BRAND_TABLE, 1);
        UpdateManager.reseedTable(SERIE_TABLE, 1);
    }

    @Test
    public void insertCategory() {
        assertTrue(CategoryManager.insertCategory(cat1));
        assertTrue(CategoryManager.insertCategory(cat2));
        assertTrue(CategoryManager.insertCategory(cat3));
        assertTrue(CategoryManager.insertCategory(cat4));
        assertTrue(CategoryManager.insertCategory(cat5));
        assertTrue(CategoryManager.insertCategory(cat6));
        assertTrue(CategoryManager.insertCategory(cat7));

        assertFalse(CategoryManager.insertCategory(cat1));
        assertFalse(CategoryManager.insertCategory(cat6));
        assertFalse(CategoryManager.insertCategory(cat7));
    }

    private static ItemCategory makeCategory(String serie, String type, String brand) {
        return new ItemCategory(new ItemSerie(serie), new ItemType(type), new ItemBrand(brand));
    }
}
