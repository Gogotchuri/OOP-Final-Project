
package models;

import com.google.gson.annotations.JsonAdapter;
import models.categoryModels.ItemCategory;
import services.encoders.DealJsonAdapter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@JsonAdapter(DealJsonAdapter.class)
public class Deal {

    /* Comments means default values
     * if user does not initializes it. */

    private int dealID;                          // 0
    private int ownerID;                         // 0
    private String title;                        // null
    private String description;                  // null
    private List<Item> ownedItems;               // null
    private List<ItemCategory> wantedCategories; // null
    private ProcessStatus.Status status;         // null
    private Timestamp createDate;                // null


    /**
     * Main Constructor Of Deal
     * @param dealID - ID of Deal
     * @param ownerID - Owner ID of Deal
     * @param ownedItems - Owned Items of Deal
     * @param wantedCategories - Wanted Categories of Deal
     * @param status - Deal Status
     * @param createDate - Deal's Create Date
     */
    public Deal(int dealID,
                int ownerID,
                List<Item> ownedItems,
                List<ItemCategory> wantedCategories,
                ProcessStatus.Status status,
                String title,
                String description,
                Timestamp createDate)
    {
        this.dealID = dealID;
        this.ownerID = ownerID;
        this.ownedItems = ownedItems;
        this.wantedCategories = wantedCategories;
        this.status = status;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
    }


    /**
     * Constructor.
     * @param ownerID - User which has this Deal
     * @param ownedItems - Owned Items of Deal
     * @param wantedCategories - Wanted Categories of Deal
     */
    public Deal(int ownerID,
                List<Item> ownedItems,
                List<ItemCategory> wantedCategories) {
        this(0, ownerID, ownedItems, wantedCategories, ProcessStatus.Status.WAITING,
             null, null, null);
    }


    /**
     * Constructor.
     * @param ownerID - User which has this Deal
     * @param ownedItems - Owned Items of Deal
     * @param wantedCategories - Wanted Categories of Deal
     * @param title - title of the deal
     */
    public Deal(int ownerID,
                List<Item> ownedItems,
                List<ItemCategory> wantedCategories,
                String title) {
        this(0, ownerID, ownedItems, wantedCategories, ProcessStatus.Status.WAITING, title,
             null, new Timestamp(System.currentTimeMillis()));
    }


    /**
     * @return ID of the Deal.
     *         If returned 0 that means that
     *         Deal's ID is not initialized yet.
     */
    public int getDealID() { return dealID; }


    /**
     * @return Owner's ID of the Deal.
     *         If returned 0 that means that
     *         Deal's Owner is not initialized yet.
     */
    public int getOwnerID() { return ownerID; }


    /**
     * @return List of Owned Items of the Deal
     *         If returned null that means that
     *         Deal's Owned Item List is not initialized yet.
     */
    public List<Item> getOwnedItems() { return ownedItems; }


    /**
     * @return List of Owned Items of the Deal
     *         If returned null that means that
     *         Deal's Owned Item List is not initialized yet.
     */
    public List<ItemCategory> getWantedCategories() { return wantedCategories; }

    /**
     * @return Title of the deal
     *         If empty string is returned, means user did not name it
     */
    public String getTitle() { return title; }

    /**
     * @return Deal Status
     */
    public ProcessStatus.Status getStatus() { return status; }


    /**
     * @return Deal's Create Date
     */
    public Timestamp getCreateDate() { return createDate; }


    /**
     * @return List of every category of owned items
     * @throws NullPointerException - If owned items is not initialized
     */
    public List<ItemCategory> getOwnedItemCategories() {

        List<ItemCategory> ownedItemCategories = new ArrayList<>(ownedItems.size());

        for (Item item : ownedItems)
            ownedItemCategories.add(item.getCategory());

        return ownedItemCategories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets Deal ID
     * @param dealID - new Deal ID
     */
    public void setDealID(int dealID) { this.dealID = dealID; }


    /**
     * !!! Deal ID must be Initialized for correct comparing !!!
     * Checks if the passed object is the same as this
     * @param other - Passed Deal
     * @return Whether two Deals are equal or not
     */
    @Override public boolean equals(Object other) {

        if (other == this) return true;
        if (!(other instanceof Deal)) return false;

        Deal otherDeal = (Deal) other;

        return dealID == otherDeal.dealID;
    }

}