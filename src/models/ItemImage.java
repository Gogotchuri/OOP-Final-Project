package models;
import java.sql.Timestamp;

/**
 * Item image, class extending Image
 * Added features are id of an item and category of an image
 * Since all images in Image are profile images, they don't require additional category
 */
public class ItemImage extends Image {

    private int itemId;
    private ImageCategories.ImageCategory category;

    public ItemImage(int id, String url, int userID, int itemId, ImageCategories.ImageCategory category, Timestamp createdAt) {
        super(id, userID, url, createdAt);
        this.itemId = itemId;
        this.category = category;
    }

    public ItemImage(String url, int userID, int itemID, ImageCategories.ImageCategory category) {
        this(0, url, userID, itemID, category, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * @return ID of an item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @return Category of an image
     */
    public ImageCategories.ImageCategory getImageCategory() {
        return category;
    }
}
