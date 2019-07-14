package managers;

import database.DatabaseAccessObject;
import models.Image;
import models.ImageCategories;
import models.ItemImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImagesManager {
    private static final String GET_USER_PROFILE_IMAGE = "SELECT * FROM profile_images WHERE user_id = ?;";
    private static final String GET_ITEM_IMAGE_BY_ID = "SELECT id, image_category_id, url, user_id," +
            " item_id, created_at FROM item_images WHERE id = ?;";
    private static final String GET_ITEM_IMAGE_BY_ITEM_ID = "SELECT id, image_category_id, url, user_id," +
            " item_id, created_at FROM item_images WHERE item_id = ?;";
    private static final String GET_ITEM_IMAGE_BY_USER_ID = "SELECT id, image_category_id, url, user_id," +
            " item_id, created_at FROM item_images WHERE user_id = ?;";

    private static final String INSERT_ITEM_IMAGE_QUERY = "INSERT INTO item_images (image_category_id, url, user_id, item_id," +
            " created_at) VALUES(?, ?, ?, ?, ?);";
    private static final String INSERT_PROFILE_IMAGE_QUERY = "INSERT INTO profile_images (url, user_id," +
            " created_at) VALUES(?, ?, ?);";

    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();

    /**
     * @param imageID - ID of Image in DB
     * @return Fully Filled Image object.
     *         Or null if Image with such ID does not exists.
     */
    public static ItemImage getItemImageByID(int imageID) {
        Image img = null;

        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_ITEM_IMAGE_BY_ID);
            st.setInt(1,imageID);
            ResultSet set = st.executeQuery();

            if(set.next())
                return parseItemImage(set);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Adds image into database, based on whether it's a profile image or item image
     * @param img
     * @return false if insertion fails
     */
    public static boolean addImage(Image img){
        return (img instanceof ItemImage) ? addItemImage((ItemImage) img) : addProfileImage(img);
    }

    /**
     * Adds image to itemImages
     * @param img
     * @return false if insertion fails
     */
    private static boolean addItemImage(ItemImage img) {

        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_ITEM_IMAGE_QUERY);

            st.setInt(1, img.getImageCategory().getId());
            st.setString(2, img.getUrl());
            st.setInt(3, img.getUserId());
            st.setInt(4, img.getItemId());
            st.setTimestamp(5, img.getCreatedDate());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Adds image to profile images
     * @param img
     * @returnfalse if insertion fails
     */
    private static boolean addProfileImage(Image img) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_PROFILE_IMAGE_QUERY);

            st.setString(1, img.getUrl());
            st.setInt(2, img.getUserId());
            st.setTimestamp(3, img.getCreatedDate());
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * get profile image of given user
     *
     * @param user_id
     * @return profile image
     */
    public static Image getUserProfileImage(int user_id){
        Image img = null;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_USER_PROFILE_IMAGE);
            st.setInt(1, user_id);
            ResultSet set = st.executeQuery();
            if(!set.next()){
                return null;
            }
            img = parseProfileImage(set, user_id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * @param userId Id of a user
     * @return All images of items that passed user owns
     */
    public static List<ItemImage> getItemImagesByUserID(int userId) {
        return getItemImages(userId, GET_ITEM_IMAGE_BY_USER_ID);
    }

    /**
     * @param itemId ID of an item
     * @return All images of a single item
     */
    public static List<ItemImage> getItemImagesByItemID(int itemId) {
        return getItemImages(itemId, GET_ITEM_IMAGE_BY_ITEM_ID);
    }

    /**
     * @param id Passed id, item or user id
     * @param query String query
     * @return Returns all images of a user or item
     */
    private static List<ItemImage> getItemImages(int id, String query){
        List<ItemImage> images = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(query);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();

            while (set.next()) {
                images.add(parseItemImage(set));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * @param set Passed resultSet
     * @param user_id Id of a user
     * @return Parsed profile image
     * @throws SQLException
     */
    private static Image parseProfileImage(ResultSet set, int user_id) throws SQLException {
        return new Image(set.getBigDecimal("id").intValue(),
                user_id,
                set.getString("url"),
                new Timestamp(set.getTimestamp("created_at").getTime()));
    }

    /**
     * @param set Passed resultSet
     * @return Parsed image
     * @throws SQLException
     */
    private static ItemImage parseItemImage(ResultSet set) throws SQLException {
        return new ItemImage(set.getInt("id"),
                set.getString("url"),
                set.getInt("user_id"),
                set.getInt("item_id"),
                ImageCategories.getCategoryByID(set.getInt("image_category_id")),
                new Timestamp(set.getTimestamp("created_at").getTime()));
    }
}
