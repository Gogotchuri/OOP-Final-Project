package models;
import java.sql.Timestamp;

/**
 * Item image, class extending Image
 * Added features are id of an item and category of an image
 * Since all images in Image are profile images, they don't require additional category
 */
public class ItemImage extends Image {

    private int itemId, imageCategory;

    public ItemImage(int id, int userId, String url, int itemId, int imageCategory, Timestamp createdAt) {
        super(id, userId, url, createdAt);
        this.itemId = itemId;
        this.imageCategory = imageCategory;
    }

    /**
     * @return ID of an item
     */
    public int getItemId() { return itemId; }

    /**
     * @return Category of an image
     */
    public int getImageCategory() { return imageCategory; }
}
