
package test;

import generalManagers.DeleteManager;

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

}
