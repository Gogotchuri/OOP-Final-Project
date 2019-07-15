package models;
import managers.ImagesManager;

import java.sql.Timestamp;

/**
 * Class representing image
 * This is a default class, used for saving user profile images
 * Parent class for itemImages
 */
public class Image implements Comparable <Image> {

    private int id;
    private int userID;
    private String url;
    private Timestamp createdAt;

    /**
     * @param id Id of an image
     * @param userID Id of image's user
     * @param url Url of an image
     * @param createdAt Date when this image was created at
     */
    public Image(int id, int userID, String url, Timestamp createdAt) {
        this.id = id;
        this.userID = userID;
        this.url = url;
        this.createdAt = createdAt;
    }

    /**
     * Alternate constructor
     * @param userID Id of image's user
     * @param url Url of an image
     */
    public Image(int userID, String url) {
        this(0, userID, url, new Timestamp(System.currentTimeMillis()));
    }

    /**
     * @return ID of an image
     */
    public int getId () { return id; }

    /**
     * @return user's ID
     */
    public int getUserId () { return userID; }

    /**
     * @return url of an image
     */
    public String getUrl () { return url; }

    /**
     * @return Date when created
     */
    public Timestamp getCreatedDate () { return createdAt; }

    @Override
    /**
     * @param o Passed image
     * @return Checks whether two images are same or not
     */
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Image)) return false;
        return url.equals(((Image) o).getUrl());
    }

    @Override
    /**
     * @param other passed image
     * @return compares two images
     */
    public int compareTo(Image other) { return url.compareTo(other.getUrl()); }

    @Override
    /**
     * String representation of an image
     */
    public String toString() {
        return id + " : " + url;
    }
}