package models;

import managers.ItemManager;

import java.sql.Timestamp;
import java.util.List;

public class Deal {
    private int id;
    private int user_id;
    private int status_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    public Deal(int id, int user_id, int status_id, Timestamp created_at, Timestamp updated_at){
        this.id = id;
        this.user_id = user_id;
        this.status_id = status_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    //public List<Item> getOwnedItems(){return ItemManager.getOwnedItems(id);}

    public List<Category> getWantedItemCategories(){return ItemManager.getWantedItemCategories(id);}

    public int getId(){return id;}

    public int getUser_id(){return user_id;}

    public int getStatus_id(){return status_id;}

    public Timestamp getCreated_at(){return created_at;}

    public Timestamp getUpdated_at() {
        return updated_at;
    }
}
