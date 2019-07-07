
package managers;

import database.DatabaseAccessObject;
import models.Image;
import models.Item;
import models.ItemImage;
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

    //private static final String SELECT_DEAL_OWNED_ITEMS = "SELECT item_id from owned_items where deal_id = ?";

    private static final String JOIN_CATEGORIES = "SELECT * from item_categories s JOIN item_types t on s.type_id = t.id" +
            " JOIN item_brands b on s.brand_id = b.id ";
    private static final String GET_ITEM_INFO_BY_ITEM_ID = "SELECT * FROM items WHERE id = ?;";
    private static final String GET_IMAGE_IDS_BY_ITEM_ID = "SELECT id FROM item_images WHERE item_id = ?;";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();


    /**
     * @param itemID - ID of Deal in DB
     * @return Fully Filled Item object
     *         Or null if Item with such ID does not exists
     */
    public static Item getItemByID(int itemID){

        User owner = getOwnerByItemID(itemID);
        ItemCategory category = getItemCategoryByItemID(itemID);
        List<ItemImage> images = ImagesManager.getItemImagesByItemID(itemID);
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
     * @param itemID - ID of Deal in DB
     * @return Owner (User) of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static User getOwnerByItemID(int itemID) {
        int ownerID = -1;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_ITEM_INFO_BY_ITEM_ID);
            st.setInt(1, itemID);
            ResultSet set = st.executeQuery();
            if(set.getFetchSize() != 0) {
                set.next();
                ownerID = set.getBigDecimal("user_id").intValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ownerID == -1 ? null : UserManager.getUserByID(ownerID);
    }


    /**
     * @param itemID - ID of Deal in DB
     * @return Category of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static ItemCategory getItemCategoryByItemID(int itemID) {
        int categoryID = -1;
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_ITEM_INFO_BY_ITEM_ID);
            st.setInt(1, itemID);
            ResultSet set = st.executeQuery();
            if (set.getFetchSize() != 0) {

                set.next();
                categoryID = set.getBigDecimal("item_category_id").intValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryID == -1 ? null : CategoryManager.getCategoryByID(categoryID);
    }


    /**
     * @param itemID - ID of Deal in DB
     * @return Name of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static String getItemNameByItemID(int itemID) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_ITEM_INFO_BY_ITEM_ID);
            st.setInt(1, itemID);
            ResultSet set = st.executeQuery();
            if(set.getFetchSize() != 0) {
                set.next();
                return set.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param itemID - ID of Deal in DB
     * @return Description of Item with ID = itemID.
     *         If such itemID does not exists in DB
     *         returns null.
     */
    private static String getItemDescriptionByItemID(int itemID) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(GET_ITEM_INFO_BY_ITEM_ID);
            st.setInt(1, itemID);
            ResultSet set = st.executeQuery();
            if(set.getFetchSize() != 0) {
                set.next();
                return set.getString("description");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param item Item, which needs to added to database
     */
    public static boolean addItemToDB(Item item) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_ITEM_QUERY);
            st.setInt(1,item.getOwner().getUserID());
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
        return getItemsByColumn("items", "owned_items.deal_id", dealId + "");
    }

    /**
     * @param rs ResultSet
     * @return Item, parsed from ResultSet
     * @throws SQLException
     */
    private static Item parseItem(ResultSet rs) throws SQLException {
        return new Item(rs.getBigDecimal("id").intValue(),
                        UserManager.getUserByID(rs.getInt("user_id")),
                        CategoryManager.getCategoryByID(rs.getInt("item_category_id")),
                        ImagesManager.getItemImagesByItemID(rs.getInt("id")),
                        rs.getString("name"),
                        rs.getString("description"));
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
                list.add(ItemCategory.parseCategory(set));
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
    public static List <ItemCategory> getWantedItemCategories(int dealID) {
        String query = JOIN_CATEGORIES + " JOIN wanted_items s.id = on wanted_items.deal_id" +
                " WHERE wanted_items.deal_id = ?;";
        return getItemCategories(dealID, query);
    }

    /**
     *
     * @param dealID Id of the deal
     * @return List of categories owned in one deal
     */
    //TODO : გვჭირდება ეს საერთოდ?
    public static List <ItemCategory> getOwnedItemCategories(int dealID) {
        return null;
    }
}