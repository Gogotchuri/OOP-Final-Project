
package models;

import models.categoryModels.ItemCategory;
import java.util.Iterator;
import java.util.List;

public class Item {


    /* Comments means default values
     * if user does not initializes it. */

    private int itemID;               // 0
    private User owner;               // null
    private ItemCategory category;    // null
    private List<Image> images;       // null
    private String name, description; // null


    /**
     * Main Constructor
     * @param itemID - ID of Item in DB
     * @param owner - Owner of the Item
     * @param category - Category of the Item
     * @param images - List of Images of the Item
     * @param name - Name of the Item
     * @param description - Description of the Item
     */
    public Item (int itemID,
                  User owner,
                   ItemCategory category,
                    List<Image> images,
                     String name,
                      String description)
    {
        this.itemID = itemID;
        this.owner = owner;
        this.category = category;
        this.images = images;
        this.name = name;
        this.description = description;
    }


    /**
     * @return ID of Item in DB
     */
    public int getItemID() { return itemID; }


    /**
     * @return Owner of the Item
     */
    public User getOwner() { return owner; }


    /**
     * @return Category of the Item
     */
    public ItemCategory getCategory() { return category; }


    /**
     * @return List of Images of the Item
     */
    public List<Image> getImages() { return images; }


    /**
     * @return Iterator of the images
     */
    public Iterator<Image> getImagesIterator() {
        return images.iterator();
    }


    /**
     * @return Name of the Item
     */
    public String getName() { return name; }


    /**
     * @return Description of the Item
     */
    public String getDescription() { return description; }


    /**
     * !!! Item ID must be Initialized for correct comparing !!!
     * @param other Passed item
     * @return Whether two items are equal or not
     */
    public boolean equals(Object other) {

        if (this == other) return true;
        if (!(other instanceof Item)) return false;

        Item otherItem = (Item) other;

        return itemID == otherItem.itemID;
    }

}
