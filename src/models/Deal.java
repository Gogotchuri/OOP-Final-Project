package models;

import managers.ItemManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Deal {
    private int id;
    private int user_id;
    private int status_id;
    private List<Integer> owned_ids;
    private List<Integer> wanted_ids;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Deal(int id, int user_id, int status_id, Timestamp created_at, Timestamp updated_at){
        this.id = id;
        this.user_id = user_id;
        this.status_id = status_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Deal(int user_id, List<Integer> owned_item_ids, List<Integer> wanted_item_ids){
        this(0, user_id, ProcessStatus.Status.ONGOING.getId(),
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()));
        this.owned_ids = owned_item_ids;
        this.wanted_ids = wanted_item_ids;
    }

    /**
     * Initialize Deal by copying another
     * @param toCopy another object of the same type
     */
    public Deal(Deal toCopy) {
        this.id = toCopy.getId();
        this.user_id = toCopy.getUser_id();
        this.status_id = toCopy.getStatus_id();
        this.created_at = new Timestamp(toCopy.getCreated_at().getTime());
        this.updated_at = new Timestamp(toCopy.getUpdated_at().getTime());
        this.owned_ids = new ArrayList<>(toCopy.getOwned_ids());
        this.wanted_ids = new ArrayList<>(toCopy.getWanted_ids());
    }

    public List<Item> getOwnedItems(){ return ItemManager.getOwnedItems(id); }

    /**
     * @return List of every category of wanted items
     */
    public List<Category> getWantedItemCategories(){return ItemManager.getWantedItemCategories(id);}

    /**
     * @return List of every category of owned items
     */
    public List<Category> getOwnedItemCategories(){return ItemManager.getOwnedItemCategories(id);}

    public int getId(){return id;}

    public int getUser_id(){return user_id;}

    public int getStatus_id(){return status_id;}

    public List<Integer> getOwned_ids() {
        return owned_ids;
    }

    public List<Integer> getWanted_ids() {
        return wanted_ids;
    }

    public Timestamp getCreated_at(){return created_at;}

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setId(int id){
        this.id = id;
    }

    /**
     * Checks if the passed object is the same as this
     * @param other another object
     * @return
     */
    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null || this.getClass() != other.getClass()) return false;
        return this.id == ((Deal) other).getId();
    }

}
