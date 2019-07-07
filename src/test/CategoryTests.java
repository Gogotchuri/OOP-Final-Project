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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {

    private static final String BRAND_TABLE = "item_brands";
    private static final String SERIE_TABLE = "item_categories";
    private static final String TYPE_TABLE = "item_types";
    private static final String OTHER = "other";

    private static final ItemCategory cat1 = makeCategory("S3", "ტელეფონი", "Samsung");
    private static final ItemCategory cat2 = makeCategory("S2", "ტელეფონი", "Samsung");
    private static final ItemCategory cat3 = makeCategory("iMac", "კომპიუტერი", "Apple");
    private static final ItemCategory cat4 = makeCategory("Iphone", "ტელეფონი", "Apple");
    private static final ItemCategory cat5 = makeCategory("E40", "მაცივარი", "Samsung");

    private static final ItemCategory oCat1 = makeCategory("ტელეფონი", "other", "Samsung");
    private static final ItemCategory oCat2 = makeCategory("Samsung", "other", "other");
    private static final ItemCategory oCat3 = makeCategory("Iphone", "other", "other");
    private static final ItemCategory oCat4 = makeCategory("S3", "other", "Samsung");

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

        assertTrue(CategoryManager.insertCategory(oCat1));
        assertTrue(CategoryManager.insertCategory(oCat2));
        assertTrue(CategoryManager.insertCategory(oCat3));
        assertTrue(CategoryManager.insertCategory(oCat4));

        assertFalse(CategoryManager.insertCategory(cat1));
        assertFalse(CategoryManager.insertCategory(oCat2));
        assertFalse(CategoryManager.insertCategory(oCat4));
    }

    @Test
    public void selectFromDB() {
        //Simple
        equalLists(CategoryManager.getCategories(cat1), Arrays.asList(cat1));
        equalLists(CategoryManager.getCategories(cat3), Arrays.asList(cat3));
        equalLists(CategoryManager.getCategories(cat5), Arrays.asList(cat5));


        //Edge, with other inputs
        equalLists(CategoryManager.getCategories(oCat1), Arrays.asList(cat1,cat2));
        equalLists(CategoryManager.getCategories(oCat2), Arrays.asList(cat1,cat2,cat5,oCat1,oCat2,oCat4));
        equalLists(CategoryManager.getCategories(makeCategory("ტელეფონი",OTHER,OTHER)), Arrays.asList(cat1,cat2,cat4,oCat1));
        equalLists(CategoryManager.getCategories(oCat3), Arrays.asList(oCat3, cat4));
        equalLists(CategoryManager.getCategories(oCat4), Arrays.asList(oCat4, cat1));
        equalLists(CategoryManager.getCategories(makeCategory("Apple",OTHER, OTHER)), Arrays.asList(cat3,cat4));
        equalLists(CategoryManager.getCategories(makeCategory("კომპიუტერი",OTHER,OTHER)), Arrays.asList(cat3));
        equalLists(CategoryManager.getCategories(makeCategory("კომპიუტერი","Samsung", OTHER)), Arrays.asList());

        //Just for branching
        equalLists(CategoryManager.getCategories(makeCategory("E40","მაცივარი", OTHER)), Arrays.asList(cat5));
        equalLists(CategoryManager.getCategories(makeCategory("E40",OTHER, "Samsung")), Arrays.asList(cat5));
        equalLists(CategoryManager.getCategories(makeCategory("E40",OTHER, OTHER)), Arrays.asList(cat5));
    }

    private static ItemCategory makeCategory(String serie, String type, String brand) {
        return new ItemCategory(new ItemSerie(serie), new ItemType(type), new ItemBrand(brand));
    }

    private void equalLists(List<ItemCategory> l1, List<ItemCategory> l2) {
        Collections.sort(l1);
        Collections.sort(l2);
    }
}
