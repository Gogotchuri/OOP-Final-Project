
package models;

import com.google.gson.annotations.JsonAdapter;
import models.categoryModels.ItemCategory;
import services.encoders.ItemJsonAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
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
     * Constructor for freshly creating an Item instance
     * @param ownerID - User id of the owner
     * @param category - Category of the item
     * @param name - Name of the item
     * @param description - Description of the item
     */
    public Item (int ownerID, ItemCategory category, String name, String description){
        this(0, ownerID, category, new ArrayList<>(), name, description,
                new Timestamp(System.currentTimeMillis()),
                    new Timestamp(System.currentTimeMillis()));
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
     * This method adds image to item images
     * @param image image to add
     */
    public void addImage(ItemImage image){
        try {
            this.images.add(image);
        }catch (NullPointerException e){
            this.images = new ArrayList<>();
            this.images.add(image);
        }
    }
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
    /**
     * Compares two items with names
     */
    public int compareTo(Item o) {
        return name.compareTo(o.getName());
    }

    @Override
    /**
     * String representation of an item
     */
    public String toString() {
        return name;
    }
}
