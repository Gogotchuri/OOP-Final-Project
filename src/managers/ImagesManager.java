package managers;

import database.DatabaseAccessObject;
import models.Image;
import models.ItemImage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImagesManager {
    private static final String GET_USER_IMAGE_QUERY = "SELECT id, image_category_id, url," +
            " item_id, created_at FROM item_images WHERE user_id = ?;";
    private static final String GET_IMAGE_QUERY = "SELECT image_category_id, url, user_id," +
            " item_id, created_at FROM item_images WHERE id = ?;";

    private static final String GET_LAST_ID_ITEM = "SELECT MAX(id) FROM item_images;";
    private static final String GET_LAST_ID_PROFILE = "SELECT MAX(id) FROM profile_images;";

    private static final String GET_USER_PROFILE_IMAGE = "SELECT * FROM profile_images WHERE user_id = ?";

    private static final String INSERT_ITEM_IMAGE_QUERY = "INSERT INTO item_images (image_category_id, url, user_id, item_id," +
            " created_at) VALUES(?, ?, ?, ?, ?);";
    private static final String INSERT_PROFILE_IMAGE_QUERY = "INSERT INTO profile_images (url, user_id," +
            " created_at) VALUES(?, ?, ?);";

    private static final String INSERT_IMAGE_CATEGORY_QUERY = "INSERT INTO image_categories VALUES(?, ?);";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();

    /**
     * TODO: Levan
     * @param imageID - ID of Image in DB
     * @return Fully Filled Image object.
     *         Or null if Image with such ID does not exists.
     */
    public static Image getImageByID(int imageID) {
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

            st.setInt(1, img.getImageCategory());
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
            img = new Image(set.getBigDecimal("id").intValue(),
                    set.getBigDecimal("user_id").intValue(),
                    set.getString("url"),
                    new Timestamp(set.getDate("created_at").getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * get All item images of given user
     *
     * @param user_id
     * @return list of all images
     */

    public static List<ItemImage> getUserImages(int user_id){
        List<ItemImage> images = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_USER_IMAGE_QUERY);
            st.setInt(1, user_id);
            ResultSet set = st.executeQuery();

            while (set.next()) {
                ItemImage img = new ItemImage(set.getBigDecimal("id").intValue(),
                        set.getBigDecimal("image_category_id").intValue(),
                        set.getString("url"), user_id,
                        set.getBigDecimal("item_id").intValue(),
                        new Timestamp(set.getDate("created_at").getTime()));
                images.add(img);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return images;
    }
}
