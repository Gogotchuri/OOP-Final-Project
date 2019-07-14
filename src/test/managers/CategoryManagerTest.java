
package test.managers;

import managers.CategoryManager;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSeries;
import models.categoryModels.ItemType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.Tester;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryManagerTest extends Tester {

    private static final ItemCategory cat1 = new ItemCategory(1,"S3", "Phone", "Samsung");
    private static final ItemCategory cat2 = new ItemCategory(2,"S2", "Phone", "Samsung");
    private static final ItemCategory cat3 = new ItemCategory(3,"Iphone 6", "Phone", "Apple");
    private static final ItemCategory cat4 = new ItemCategory(4,"Iphone 7", "Phone", "Apple");
    private static final ItemCategory cat5 = new ItemCategory(5,"iMac", "Computer", "Apple");

    /**
     * For my reference :
     * Type : Phone -> 1, Computer -> 2
     * Brand : Samsung -> 1, Apple -> 2
     */

    @Before
    public void setUp() throws Exception {
        emptyDataBase();
        insertCategory();
    }

    @Test
    public void insertCategory() {
        assertEquals(CategoryManager.insertCategory(cat1), cat1.getId());
        assertEquals(CategoryManager.insertCategory(cat2), cat2.getId());
        assertEquals(CategoryManager.insertCategory(cat3), cat3.getId());
        assertEquals(CategoryManager.insertCategory(cat4), cat4.getId());
        assertEquals(CategoryManager.insertCategory(cat5), cat5.getId());

        assertEquals(CategoryManager.insertCategory(cat1), cat1.getId());
        assertEquals(CategoryManager.insertCategory(cat4), cat4.getId());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getCategoryByID() {
        assertEquals(CategoryManager.getCategoryByID(1), cat1);
        assertEquals(CategoryManager.getCategoryByID(3), cat3);
        assertEquals(CategoryManager.getCategoryByID(55555), null);
    }

    @Test
    public void getItemCategoriesByItemCategoryIDs() {
        Tester.equalLists(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(1,2,3)), Arrays.asList(cat1,cat2,cat3));
        Tester.equalLists(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(4,5)), Arrays.asList(cat4,cat5));
        assertEquals(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(3,55555)), null);
        assertEquals(CategoryManager.getItemCategoriesByItemCategoryIDs(Arrays.asList(55555)), null);
    }

    @Test
    public void listsEqualsIgnoreOrder() {
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

    @Test
    public void getCategoriesWithBrandAndType() {
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 1), Arrays.asList(cat1, cat2));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 2), Arrays.asList(cat3, cat4));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(2, 2), Arrays.asList(cat5));
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(2, 1), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(5555, 1), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(1, 5555), Arrays.asList());
        assertEquals(CategoryManager.getCategoriesWithBrandAndType(5555, 5555), Arrays.asList());
    }

    @Test
    public void getCategoriesWithBrand() {
        equalLists(CategoryManager.getCategoriesWithBrand(1), Arrays.asList(cat1, cat2));
        equalLists(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat3, cat4, cat5));
        equalLists(CategoryManager.getCategoriesWithBrand(5555), Arrays.asList());
        assertNotEquals(CategoryManager.getCategoriesWithBrand(1), Arrays.asList(cat1));
        assertNotEquals(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat1, cat2));
        assertNotEquals(CategoryManager.getCategoriesWithBrand(2), Arrays.asList(cat3, cat5));
    }

    @Test
    public void getCategoriesWithType() {
        equalLists(CategoryManager.getCategoriesWithType(1), Arrays.asList(cat1, cat2, cat4, cat3));
        equalLists(CategoryManager.getCategoriesWithType(2), Arrays.asList(cat5));
        assertEquals(CategoryManager.getCategoriesWithType(55555), Arrays.asList());
        assertNotEquals(CategoryManager.getCategoriesWithType(1), Arrays.asList(cat1, cat2, cat3));
        assertNotEquals(CategoryManager.getCategoriesWithType(2), Arrays.asList(cat5, cat4));
        assertNotEquals(CategoryManager.getCategoriesWithType(2), Arrays.asList());
    }

    @Test
    public void getSeriesWithBrandAndType() {
        assertEquals(CategoryManager.getSeriesWithBrandAndType("Phone", "Samsung"), Arrays.asList(cat1.getSeries(), cat2.getSeries()));
        assertEquals(CategoryManager.getSeriesWithBrandAndType("Computer", "Apple"), Arrays.asList(cat5.getSeries()));
        assertEquals(CategoryManager.getSeriesWithBrandAndType("Computer", "Samsung"), Arrays.asList());
    }

    @Test
    public void getAllBrands() {
        equalLists(CategoryManager.getAllBrands(), Arrays.asList(new ItemBrand("Samsung"), new ItemBrand("Apple")));
    }

    @Test
    public void getAllTypes() {
        equalLists(CategoryManager.getAllTypes(), Arrays.asList(new ItemType("Phone"), new ItemType("Computer")));
    }

}
