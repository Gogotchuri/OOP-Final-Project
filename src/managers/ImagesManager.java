package managers;

import database.DatabaseAccessObject;
import models.Image;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ImagesManager {
    private static final String GET_USER_IMAGE_QUERY = "SELECT id, image_category_id, url," +
            " item_id, created_at FROM images WHERE id = ?;";
    private static final String GET_IMAGE_QUERY = "SELECT image_category_id, url, user_id," +
            " item_id, created_at FROM images WHERE id = ?;";
    //aq image_category_is shesacvlelia!!!!!!!!!!
    private static final String GET_USER_PROFILE_IMAGE = "SELECT * FROM images WHERE user_id = ? and image_category_id = 1";
    private static final String INSERT_IMAGE_QUERY = "INSERT INTO images VALUES(?, ?, ?, ?, ?);";
    private static final String INSERT_IMAGE_CATEGORY_QUERY = "INSERT INTO image_categories VALUES(?, ?);";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();

/*    public ImagesManager(){
        DBO = DatabaseAccessObject.getInstance();
    }
*/
    /**
     * get Image object according to its id
     *
     * @param image_id
     * @return image from the database with given id
     */
    public static Image getImage(int image_id){
        Image img = null;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_IMAGE_QUERY);
            st.setInt(1, image_id);
            ResultSet set = st.executeQuery();
            img = new Image(image_id, set.getBigDecimal("image_category_id").intValue(),
                    set.getString("url"), set.getBigDecimal("user_id").intValue(),
                    set.getBigDecimal("item_id").intValue(),
                    new Timestamp(set.getDate("created_at").getTime()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Adds image into database
     * @param img
     * @return false if insertion fails
     */
    public static boolean addImage(Image img){
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_IMAGE_QUERY);
            st.setInt(1, img.getCategoryId());
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
     * get All images of given user
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
            img = new Image(set.getBigDecimal("image_id").intValue(), set.getBigDecimal("image_category_id").intValue(),
                    set.getString("url"), set.getBigDecimal("user_id").intValue(),
                    set.getBigDecimal("item_id").intValue(),
                    new Timestamp(set.getDate("created_at").getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * get All images of given user
     *
     * @param user_id
     * @return list of all images
     */
    public static List<Image> getUserImages(int user_id){
        List<Image> images = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_USER_IMAGE_QUERY);
            st.setInt(1, user_id);
            ResultSet set = st.executeQuery();

            while (set.next()) {
                Image img = new Image(set.getBigDecimal("image_id").intValue(),
                        set.getBigDecimal("image_catergory_id").intValue(),
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

    /**
     * Adds a type of image category in database
     * @param categoryID
     * @param name
     * @return false if insertion fails
     */
    public static boolean addImageCategory(int categoryID, String name){
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_IMAGE_QUERY);
            st.setInt(1, categoryID);
            st.setString(2, name);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
