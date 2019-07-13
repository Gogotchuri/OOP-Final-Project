
package test;

import generalManagers.DeleteManager;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Tester {

    /**
     * Truncates all data in all tables in Data Base
     */
    public static boolean emptyDataBase() {
        return
        DeleteManager.deleteAndReseed("profile_images") &&
        DeleteManager.deleteAndReseed("item_images") &&
        DeleteManager.deleteAndReseed("image_categories") &&
        DeleteManager.deleteAndReseed("messages") &&
        DeleteManager.deleteAndReseed("chats") &&
        DeleteManager.deleteAndReseed("offered_cycles") &&
        DeleteManager.deleteAndReseed("cycles") &&
        DeleteManager.deleteAndReseed("wanted_items") &&
        DeleteManager.deleteAndReseed("owned_items") &&
        DeleteManager.deleteAndReseed("items") &&
        DeleteManager.deleteAndReseed("item_categories") &&
        DeleteManager.deleteAndReseed("item_brands") &&
        DeleteManager.deleteAndReseed("item_types") &&
        DeleteManager.deleteAndReseed("deals") &&
        DeleteManager.deleteAndReseed("process_statuses") &&
        DeleteManager.deleteAndReseed("users");
    }

    /**
     * Sorts two lists and compares them
     * Passed class must be comparable !
     * Asserts their equality
     * @param l1 First list
     * @param l2 Second list
     * @param <T> Generic java class symbol
     */
    public static <T extends Comparable<T>> void equalLists(List<T> l1, List<T> l2) {
        Collections.sort(l1);
        Collections.sort(l2);
        assertEquals(l1,l2);
    }

    /**
     * Sorts two lists and compares them
     * Passed class must be comparable !
     * Asserts their inequality
     * @param l1 First list
     * @param l2 Second list
     * @param <T> Generic java class symbol
     */
    public static <T extends Comparable<T>> void inequalLists(List<T> l1, List<T> l2) {
        Collections.sort(l1);
        Collections.sort(l2);
        assertNotEquals(l1,l2);
    }

}
