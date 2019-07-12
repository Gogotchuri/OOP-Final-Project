
package models;

import com.google.gson.annotations.JsonAdapter;
import models.categoryModels.ItemCategory;
import services.encoders.ItemJsonAdapter;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@JsonAdapter(ItemJsonAdapter.class)
public class Item implements Comparable<Item> {


    /* Comments means default values
     * if user does not initializes it. */

    private int itemID;               // 0
    private int ownerID;              // 0
    private ItemCategory category;    // null
    private List<ItemImage> images;   // null
    private String name, description; // null
    private Timestamp createDate;     // null
    private Timestamp updateDate;     // null


    /**
     * Main Constructor
     * @param itemID - ID of Item in DB
     * @param ownerID - Owner ID of the Item
     * @param category - Category of the Item
     * @param images - List of Images of the Item
     * @param name - Name of the Item
     * @param description - Description of the Item
     */
    public Item (int itemID,
                  int ownerID,
                   ItemCategory category,
                    List<ItemImage> images,
                     String name,
                      String description,
                       Timestamp createDate,
                        Timestamp updateDate)
    {
        this.itemID = itemID;
        this.ownerID = ownerID;
        this.category = category;
        this.images = images;
        this.name = name;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }


    /**
     * @return ID of Item in DB
     */
    public int getItemID() { return itemID; }

    /**
     * Updates item's id
     */
    public void setItemID(int id) { this.itemID = id; }


    /**
     * @return Owner's ID of the Item
     */
    public int getOwnerID() { return ownerID; }


    /**
     * @return Category of the Item
     */
    public ItemCategory getCategory() { return category; }


    /**
     * @return List of Images of the Item
     */
    public List<ItemImage> getImages() { return images; }


    /**
     * @return Iterator of the images
     */
    public Iterator<ItemImage> getImagesIterator() {
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
     * @return Deal's Create Date
     */
    public Timestamp getCreateDate() { return createDate; }


    /**
     * @return Deal's Update Date
     */
    public Timestamp getUpdateDate() { return updateDate; }


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

    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
