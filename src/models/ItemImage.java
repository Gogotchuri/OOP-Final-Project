package models;
import models.categoryModels.ItemCategory;

import java.sql.Timestamp;

/**
 * Item image, class extending Image
 * Added features are id of an item and category of an image
 * Since all images in Image are profile images, they don't require additional category
 */
public class ItemImage extends Image {

    private Item item;
    private ImageCategories.ImageCategory category;

    public ItemImage(int id, String url, Item item, ImageCategories.ImageCategory category, Timestamp createdAt) {
        super(id, item.getOwner(), url, createdAt);
        this.item = item;
        this.category = category;
    }

    public ItemImage(String url, Item item, ImageCategories.ImageCategory category) {
        this(0,url,item,category, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * @return ID of an item
     */
    public Item getItem() { return item; }

    /**
     * @return Category of an image
     */
    public ImageCategories.ImageCategory getImageCategory() { return category; }
}
