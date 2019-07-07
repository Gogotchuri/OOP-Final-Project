
package managers;

import database.DatabaseAccessObject;
import models.Image;
import models.Item;
import models.User;
import models.categoryModels.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {


    private static final String INSERT_ITEM_QUERY = "INSERT INTO items (user_id, item_category_id, description, " +
            "created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?);";
    private static final String INSERT_WANTED_ITEM_QUERY = "INSERT INTO wanted_items (deal_id, item_category_id, " +
            "created_at, updated_at) VALUES(?, ?, ?, ?);";
    private static final String INSERT_OWNED_ITEM_QUERY = "INSERT INTO owned_items (deal_id, item_id, " +
            "created_at, updated_at)  VALUES(?, ?, ?, ?);";
    private static final String SELECT_DEAL_OWNED_ITEMS = "SELECT item_id from owned_items where deal_id = ?";
    private static final String SELECT_DEAL_WANTED_ITEMS = "SELECT item_category_id from wanted_items where deal_id = ?";
    private static final String SELECT_BY = "SELECT * FROM ? WHERE ? = ? ;";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();


    /**
     * @param itemID - ID of Deal in DB
     * @return Fully Filled Item object
     *         Or null if Item with such ID does not exists
     */
    public static Item getItemByID(int itemID){

        User owner = getItemOwnerByDealID(itemID);
        ItemCategory category = getItemCategoryByItemID(itemID);
        List<Image> images = getImagesByItemID(itemID);
        String name = getItemNameByItemID(itemID),
                description = getItemDescriptionByItemID(itemID);

        return (owner == null ||
                 category == null ||
                  images == null ||
                   name == null ||
                    description == null)
                ?
                null : new Item(itemID, owner, category, images, name, description);
    }


    /**
     * TODO: Lasha
     * @param itemID - ID of Deal in DB
     * @return Owner (User) of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static User getItemOwnerByDealID(int itemID) {

        /*
         სელექთი, რომელიც დააბრუნებს
         itemID-ს მქონე Item-ის მფლობელის ID-ს.
         თუ itemID არ არსებობს ბაზაში, დააბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        int ownerID = 0; // აქ ჩაწერე შედეგი, თუ არაცარიელი ცხრილი დაბრუნდა

        return ownerID == 0 ? null : UserManager.getUserByID(ownerID);
    }


    /**
     * TODO: Lasha
     * @param itemID - ID of Deal in DB
     * @return Category of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static ItemCategory getItemCategoryByItemID(int itemID) {

        /*
         სელექთი, რომელიც დააბრუნებს
         itemID-ს მქონე Item-ის კატეგორიის ID-ს.
         თუ itemID არ არსებობს ბაზაში, დააბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        int categoryID = 0; // აქ ჩაწერე შედეგი, თუ არაცარიელი ცხრილი დაბრუნდა

        return categoryID == 0 ? null : CategoryManager.getCategoryByID(categoryID);
    }


    /**
     * TODO: Lasha
     * @param itemID - ID of Deal in DB
     * @return List of Images which Item with itemID have.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static List<Image> getImagesByItemID(int itemID) {

        /*
         სელექთი, რომელიც დააბრუნებს
         itemID-ის Item-ის შესაბამის Image-ების ID-ებს.
         თუ itemID არ არსებობს ბაზაში, დააბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        List<Integer> imageIDs = null; // აქ გადაწერე შედეგად მიღებული ცხრილი მონაცემები

        List<Image> images = new ArrayList<>(imageIDs.size());
        for (Integer imageID : imageIDs)
            images.add(ImagesManager.getImageByID(imageID));

        return images;
    }


    /**
     * TODO: Lasha
     * @param itemID - ID of Deal in DB
     * @return Name of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static String getItemNameByItemID(int itemID) {

        /*
         სელექთი, რომელიც დააბრუნებს
         itemID-ს მქონე Item-ის სახელს.
         თუ itemID არ არსებობს ბაზაში, დააბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        /*
         თუ ცარიელი ცხრილი დაბრუნდა
            return null
         თუ არადა Item-ის სახელი
         */
        return null;
    }


    /**
     * TODO: Lasha
     * @param itemID - ID of Deal in DB
     * @return Description of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static String getItemDescriptionByItemID(int itemID) {

        /*
         სელექთი, რომელიც დააბრუნებს
         itemID-ს მქონე Item-ის აღწერას.
         თუ itemID არ არსებობს ბაზაში, დააბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        /*
         თუ ცარიელი ცხრილი დაბრუნდა
            return null
         თუ არადა Item-ის სახელი
         */
        return null;
    }


    /**
     * @param item Item, which needs to added to database
     */
    public static boolean addItemToDB(Item item) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_ITEM_QUERY);
            st.setInt(1,item.getOwner().getId());
            st.setInt(2,item.getCategory().getId());
            st.setString(3,item.getDescription());
            st.setString(4,item.getName());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            st.setTimestamp(5, t);
            st.setTimestamp(6, t);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param table Name of table
     * @param column Name of column
     * @param value Passed value
     * @return All items from given table matching given criteria
     */
    public static List<Item> getItemsByColumn(String table, String column, String value) {
        List<Item> list = new ArrayList<>();
        try {
            String statement = "SELECT * FROM " + table + " WHERE " + column + " = " + value + ";";

            PreparedStatement st = DBO.getPreparedStatement(statement);

            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                list.add(parseItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return list;
    }

    /**
     *
     * @param userId Id of a user
     * @return List of the items of one user
     */
    public static List<Item> getUserItems(int userId) {
        return getItemsByColumn("items","user_id", userId + "");
    }


    /**
     *
     * @param dealId Id of the deal
     * @return List of items
     */
    public static List<Item> getOwnedItems(int dealId) {
        String query = "SELECT * FROM ? JOIN owned_items on items.id = owned_items.item_id WHERE ? = ?;";
        return getItemsByColumn("items", "owned_items.deal_id", dealId + "");
    }

    /**
     * @param rs ResultSet
     * @return Item, parsed from ResultSet
     * @throws SQLException
     */
    private static Item parseItem(ResultSet rs) throws SQLException {
        /*return new Item(rs.getBigDecimal("id").intValue(),
                        rs.getBigDecimal("user_id").intValue(),
                        rs.getString("name"),
                        rs.getString("description"),
                        parseCategory(rs),
                        rs.getDate("created_at")
        );*/
        return null;
    }

    /**
     *
     * @param rs ResultSet
     * @return Category of an item, created from ResultSet, if doesn't exist, returns null
     */
    private static ItemCategory parseCategory(ResultSet rs) throws SQLException {
        int categoryId = rs.getInt("item_category_id");

        String statement = "SELECT * FROM item_categories WHERE id = " + categoryId + ";";
        PreparedStatement st = DBO.getPreparedStatement(statement);
        ResultSet set = st.executeQuery();

        if(set.next()) {
            // return new ItemCategory(set.getInt("id"), set.getString("name"));
        }

        return null;
    }


    /**
     *adds wanted item in the database
     *
     * @param deal_id
     * @param item_category_id
     * @return boolean
     * @throws SQLException
     */
    public static boolean insertWantedItem(int deal_id, int item_category_id){
        return insert(deal_id, item_category_id, INSERT_WANTED_ITEM_QUERY);
    }


    /**
     *adds wanted item in the database
     *
     * @param deal_id
     * @param item_id
     * @return boolean
     * @throws SQLException
     */
    public static boolean insertOwnedItem(int deal_id, int item_id){
        return insert(deal_id, item_id, INSERT_OWNED_ITEM_QUERY);
    }

    /**
     *
     * @param deal_id ID of the deal
     * @param id Item_id or item_category_id
     * @param query Query string which needs to be executed
     * @return
     */
    private static boolean insert(int deal_id, int id, String query){
        try {
            PreparedStatement st = DBO.getPreparedStatement(query);
            st.setInt(1,deal_id);
            st.setInt(2, id);
            Timestamp t = new Timestamp(System.currentTimeMillis());
            st.setTimestamp(3, t);
            st.setTimestamp(4, t);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * @param dealID
     * @param query
     * @return Item categories
     */
    private static List <ItemCategory> getItemCategories(int dealID, String query){
        List<ItemCategory> list = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(query);
            st.setInt(1,dealID);
            ResultSet set = st.executeQuery();

            while(set.next()) {
                list.add(parseCategory(set));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    /**
     *
     * @param dealID Id of the deal
     * @return List of categories wanted in one deal
     */
    public static List <ItemCategory> getWantedItemCategories(int dealID){
        return getItemCategories(dealID, SELECT_DEAL_WANTED_ITEMS);
    }

    /**
     *
     * @param dealID Id of the deal
     * @return List of categories owned in one deal
     */
    public static List <ItemCategory> getOwnedItemCategories(int dealID){
        return getItemCategories(dealID, SELECT_DEAL_OWNED_ITEMS);
    }
}