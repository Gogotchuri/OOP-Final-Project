
package test;

import generalManagers.DeleteManager;

public class Tester {

    /**
     * Truncates all data in all tables in Data Base
     */
    public static void emptyDataBase() {
        DeleteManager.emptyBase("profile_images");
        DeleteManager.emptyBase("item_images");
        DeleteManager.emptyBase("image_categories");
        DeleteManager.emptyBase("messages");
        DeleteManager.emptyBase("chats");
        DeleteManager.emptyBase("offered_cycles");
        DeleteManager.emptyBase("cycles");
        DeleteManager.emptyBase("wanted_items");
        DeleteManager.emptyBase("owned_items");
        DeleteManager.emptyBase("items");
        DeleteManager.emptyBase("item_categories");
        DeleteManager.emptyBase("item_brands");
        DeleteManager.emptyBase("item_types");
        DeleteManager.emptyBase("deals");
        DeleteManager.emptyBase("process_statuses");
        DeleteManager.emptyBase("users");
    }

}
