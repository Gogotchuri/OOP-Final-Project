package test;

import generalManagers.DeleteManager;
import managers.CategoryManager;
import models.categoryModels.ItemCategory;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTests {

    private static final String BRAND_TABLE = "item_brands";
    private static final String SERIE_TABLE = "item_categories";
    private static final String TYPE_TABLE = "item_types";

    private static final ItemCategory cat1 = new ItemCategory(1,"S3", "Phone", "Samsung");
    private static final ItemCategory cat2 = new ItemCategory(2,"S2", "Phone", "Samsung");
    private static final ItemCategory cat3 = new ItemCategory(3,"Iphone 6", "Phone", "Apple");
    private static final ItemCategory cat4 = new ItemCategory(4,"Iphone 7", "Phone", "Apple");
    private static final ItemCategory cat5 = new ItemCategory(5,"iMac", "Computer", "Apple");


    private void resetBase() {
        DeleteManager.deleteAndReseed(SERIE_TABLE);
        DeleteManager.deleteAndReseed(BRAND_TABLE);
        DeleteManager.deleteAndReseed(TYPE_TABLE);
    }

    @Test
    public void insertCategory() {
        resetBase();
        assertTrue(CategoryManager.insertCategory(cat1) > 0);
        assertTrue(CategoryManager.insertCategory(cat2) > 0);
        assertTrue(CategoryManager.insertCategory(cat3) > 0);
        assertTrue(CategoryManager.insertCategory(cat4) > 0);
        assertTrue(CategoryManager.insertCategory(cat5) > 0);

        assertFalse(CategoryManager.insertCategory(cat1) > 0);
        assertFalse(CategoryManager.insertCategory(cat4) > 0);
    }

    @Test
    public void checkIDs() {
        equalLists(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(1,2,3)), Arrays.asList(cat1,cat2,cat3));
        equalLists(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(4,5)), Arrays.asList(cat4,cat5));
        assertEquals(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(3,55555)), null);
        assertEquals(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(55555)), null);

        assertEquals(CategoryManager.getCategoryByID(1), cat1);
        assertEquals(CategoryManager.getCategoryByID(3), cat3);
        assertEquals(CategoryManager.getCategoryByID(55555), null);
    }

    @Test
    public void checkBrand() {
        equalLists(CategoryManager.getCategoriesWithBrand(1), Arrays.asList(cat1, cat2));
        equalLists(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat3, cat4, cat5));
        equalLists(CategoryManager.getCategoriesWithBrand(5555), Arrays.asList());
        assertNotEquals(CategoryManager.getCategoriesWithBrand(1), Arrays.asList(cat1));
        assertNotEquals(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat1, cat2));
        assertNotEquals(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat3, cat5));
    }

    @Test
    public void checkType() {
        equalLists(CategoryManager.getCategoriesWithType(1), Arrays.asList(cat1, cat2, cat4, cat3));
        equalLists(CategoryManager.getCategoriesWithType(2), Arrays.asList(cat5));
        assertEquals(CategoryManager.getCategoriesWithType(55555), Arrays.asList());
        assertNotEquals(CategoryManager.getCategoriesWithType(1), Arrays.asList(cat1, cat2, cat3));
        assertNotEquals(CategoryManager.getCategoriesWithType(2), Arrays.asList(cat5, cat4));
        assertNotEquals(CategoryManager.getCategoriesWithType(2), Arrays.asList());
    }

    @Test
    public void checkBrandAndType() {
        //Type : Phone -> 1, Computer -> 2
        //Brand : Samsung -> 1, Apple -> 2
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 1), Arrays.asList(cat1, cat2));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 2), Arrays.asList(cat3, cat4));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(2, 2), Arrays.asList(cat5));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(2, 1), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(5555, 1), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 5555), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(5555, 5555), Arrays.asList());
    }

    @Test
    public void equalsWithoutOrder() {
        assertTrue(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(), Arrays.asList()));
        assertFalse(CategoryManager.listsEqualsIgnoreOrder(null, Arrays.asList()));
        assertTrue(CategoryManager.listsEqualsIgnoreOrder(null, null));
        assertTrue(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat1, cat2, cat4), Arrays.asList(cat4, cat1, cat2)));
        assertTrue(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat3, cat2), Arrays.asList(cat2, cat3)));
        assertTrue(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat1, cat2, cat3, cat4, cat5), Arrays.asList(cat4, cat3, cat1, cat5, cat2)));
        assertFalse(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat1), Arrays.asList(cat4)));
        assertFalse(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat1, cat3), Arrays.asList(cat1, cat2)));
        assertFalse(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat2, cat3, cat4, cat5), Arrays.asList(cat1, cat2, cat3, cat4)));
        assertFalse(CategoryManager.listsEqualsIgnoreOrder(Arrays.asList(cat2, cat3, cat4, cat5), Arrays.asList(cat1, cat2, cat3)));
    }

    private void equalLists(List<ItemCategory> l1, List<ItemCategory> l2) {
        Collections.sort(l1);
        Collections.sort(l2);
        assertEquals(l1,l2);
    }
}
