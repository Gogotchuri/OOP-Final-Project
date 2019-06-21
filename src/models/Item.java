package models;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Class representing a single item
 */
public class Item {

    private int userId, id;
    private Category category;
    private List<Image> images;
    private String name, description;
    private Timestamp createdAt, updatedAt;


    /**
     * Constructor of an item
     * @param createdAt Date, when item was created
     * @param name Name of the item
     * @param description Description of the item
     */
    public Item(int id, int userId, String name, String description, Category category, Date createdAt) {
        this.userId = userId;
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = new Timestamp(createdAt.getTime());
        this.category = category;
        images = new ArrayList<Image>();
    }

    /**
     * @return Name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * @return Description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Category of the item
     */
    public Category getCategory() { return category; }

    /**
     * @return Images of the item
     */
    public Iterator<Image> getImages() {
        return images.iterator();
    }

    /**
     * @return Id of the user
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @return Id of the item
     */
    public int getItemId() { return id; }

    /**
     * Adds an item to the list of items
     * @param image Image of an item
     */
    public void addImage(Image image) {
        images.add(image);
    }

    /**
     * Updates date of an item
     * @param updatedAt Date, when item was updated
     */
    public void updateDate(Date updatedAt) {
        this.updatedAt.setTime(updatedAt.getTime());
    }

    /**
     * @return Date, when item was created
     */
    public Timestamp getCreatedAt() { return createdAt; }

    /**
     * @return Date, when item was updated
     */
    public Timestamp getUpdatedAt() { return updatedAt; }
}