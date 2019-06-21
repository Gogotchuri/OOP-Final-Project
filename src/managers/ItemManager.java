package managers;

import database.DatabaseAccessObject;
import models.Category;
import models.Item;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ItemManager {

    private static final String INSERT_ITEM_QUERY = "INSERT INTO items VALUES(?, ?, ?, ?, ?, ?);";
    private static final String INSERT_WANTED_ITEM_QUERY = "INSERT INTO wanted_items VALUES(?, ?, ?, ?);";
    private static final String INSERT_OWNED_ITEM_QUERY = "INSERT INTO owned_items VALUES(?, ?, ?, ?);";
    private static final String SELECT_DEAL_OWNED_ITEMS = "SELECT item_id from owned_items where deal_id = ?";
    private static final String SELECT_DEAL_WANTED_ITEMS = "SELECT category_id from wanted_items where deal_id = ?";

    private static final String SELECT_ITEMS_BY = "SELECT * FROM items WHERE ? = ? ;";
    private static final String SELECT_ITEM_CATEGORIES = "SELECT * FROM item_categories WHERE ? = ? ;";
    private static DatabaseAccessObject DBO = DatabaseAccessObject.getInstance();

    /**
     * @param item Item, which needs to added to database
     */
    public static void addItemToDB(Item item) {
        try {
            PreparedStatement st = DBO.getPreparedStatement(INSERT_ITEM_QUERY);
            st.setInt(1,item.getItemId());
            st.setInt(2,item.getUserId());
            st.setInt(3,item.getCategory().getId());
            st.setString(4,item.getDescription());
            st.setString(5,item.getName());
            st.setTimestamp(6,item.getCreatedAt());
            st.setTimestamp(7,item.getUpdatedAt());

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param column Name of the column in database
     * @param value Value of given column
     * @return List of items matching given criteria
     */
    private static List<Item> getItemsByColumn(String column, String value) {
        List<Item> list = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(SELECT_ITEMS_BY);

            st.setString(1,column);
            st.setString(2,value);

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
        return getItemsByColumn("user_id", userId + "");
    }

    /**
     * @param itemId Id of an item
     * @return Item with that id, if doesn't exist, returns null
     */
    public static Item getItemById(int itemId){
        List<Item> items = getItemsByColumn("id", itemId + "");
        return (items.isEmpty()) ? null : items.get(0);
    }

    /**
     *
     * @param rs ResultSet
     * @return Item, parsed from ResultSet
     * @throws SQLException
     */
    private static Item parseItem(ResultSet rs) throws SQLException {
        return new Item(rs.getBigDecimal("id").intValue(),
                        rs.getBigDecimal("user_id").intValue(),
                        rs.getString("name"),
                        rs.getString("description"),
                        parseCategory(rs),
                        rs.getDate("created_at")
        );
    }

    /**
     *
     * @param rs ResultSet
     * @return Category of an item, created from ResultSet, if doesn't exist, returns null
     */
    private static Category parseCategory(ResultSet rs) throws SQLException {
        int categoryId = rs.getInt("item_category_id");

        PreparedStatement st = DBO.getPreparedStatement(SELECT_ITEM_CATEGORIES);
        st.setString(1,"id");
        st.setInt(2, categoryId);
        ResultSet set = st.executeQuery();

        while(set.next()) {
            return new Category(set.getInt("id"), set.getString("name"));
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
     *
     * @param deal_id Id of the deal
     * @return List of items from one deal
     */
    public static List <Item> getItemsFromDeal(int deal_id){
        List<Item> res = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(SELECT_DEAL_OWNED_ITEMS);
            st.setInt(1,deal_id);
            ResultSet set = st.executeQuery();
            while(set.next()){
                res.add(getItemById(set.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }

    /**
     *
     * @param dealID Id of the deal
     * @return List of categories wanted in one deal
     */
    public static List <Category> getWantedItemCategories(int dealID){
        List<Category> list = new ArrayList<>();
        try {
            PreparedStatement st = DBO.getPreparedStatement(SELECT_DEAL_WANTED_ITEMS);
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
}