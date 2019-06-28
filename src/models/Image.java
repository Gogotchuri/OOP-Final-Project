package models;
import managers.ImagesManager;

import java.sql.Timestamp;

public class Image {
    private int id;
    private int category_id;
    private String url;
    private int user_id;
    private int item_id;
    private Timestamp created_at;
    
    /**
     * create Image object according to its id
     *
     * @param id
     */

    public Image(int id) {
        Image i = ImagesManager.getImage(id);
        this.id = i.id;
        this.category_id = i.category_id;
        this.url = i.url;
        this.user_id = i.user_id;
        this.item_id = i.item_id;
        this.created_at = i.created_at;
    }

    /**
     *
     * @param id ID of an image
     * @param category_id id of image_category
     * @param url url of an image
     * @param user_id id of a user
     * @param item_id id of an item
     */
    public Image(int id, int category_id, String url, int user_id, int item_id, Timestamp created_at) {
        this.id = id;
        this.category_id = category_id;
        this.url = url;
        this.user_id = user_id;
        this.item_id = item_id;
        this.created_at = created_at;
    }

    /**
     * Alternate constructor, without id
     * @param category_id id of image_category
     * @param url url of an image
     * @param user_id id of a user
     * @param item_id id of an item
     */
    public Image(int category_id, String url, int user_id, int item_id, Timestamp created_at) {
        this.category_id = category_id;
        this.url = url;
        this.user_id = user_id;
        this.item_id = item_id;
        this.created_at = created_at;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public int getCategoryId(){
        return category_id;
    }

    public String getUrl(){
        return url;
    }

    public int getUserId(){
        return user_id;
    }

    public int getItemId(){
        return item_id;
    }

    public Timestamp getCreatedDate(){
        return created_at;
    }

    /**
     * @param o Passed image
     * @return Checks whether two images are same or not
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Image)) return false;
        return url.equals(((Image) o).getUrl());
    }
}