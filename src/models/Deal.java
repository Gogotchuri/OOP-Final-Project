
package models;

import models.categoryModels.ItemCategory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Deal {


    /* Comments means default values
     * if user does not initializes it. */

    private int dealID;                          // 0
    private User owner;                          // null
    private List<Item> ownedItems;               // null
    private List<ItemCategory> wantedCategories; // null
    private ProcessStatus.Status status;         // null
    private Timestamp createDate;                // null


    /**
     * Main Constructor Of Deal
     * @param dealID - ID of Deal
     * @param owner - Owner of Deal
     * @param ownedItems - Owned Items of Deal
     * @param wantedCategories - Wanted Categories of Deal
     * @param status - Deal Status
     * @param createDate - Deal's Create Date
     */
    public Deal(int dealID,
                User owner,
                List<Item> ownedItems,
                List<ItemCategory> wantedCategories,
                ProcessStatus.Status status,
                Timestamp createDate)
    {
        this.dealID = dealID;
        this.owner = owner;
        this.ownedItems = ownedItems;
        this.wantedCategories = wantedCategories;
        this.status = status;
        this.createDate = createDate;
    }


    /**
     * Constructor.
     * @param owner - User which has this Deal
     * @param ownedItems - Owned Items of Deal
     * @param wantedCategories - Wanted Categories of Deal
     */
    public Deal(User owner,
                List<Item> ownedItems,
                List<ItemCategory> wantedCategories) {
        this(0, owner, ownedItems, wantedCategories, null, null);
    }


    /**
     * @return ID of the Deal.
     *         If returned -1 that means that
     *         Deal's ID is not initialized yet.
     */
    public int getDealID() { return dealID; }


    /**
     * @return Owner of the Deal.
     *         If returned null that means that
     *         Deal's Owner is not initialized yet.
     */
    public User getOwner() { return owner; }


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