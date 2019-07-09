package managers;

import database.DatabaseAccessObject;
import models.Image;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSerie;
import models.categoryModels.ItemType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();
    private static final String BRAND_TABLE = "item_brands";
    private static final String SERIE_TABLE = "item_categories";
    private static final String TYPE_TABLE = "item_types";
    private static final String OTHER = "other";
    private static final String JOIN_QUERY = "SELECT * from item_categories s JOIN item_types t on s.type_id = t.id" +
            " JOIN item_brands b on s.brand_id = b.id ";
    private static final String GET_WANTED_CATEGORIES_BY_DEAL_QUERY = "SELECT i.item_category_id FROM items i " +
            "JOIN wanted_items wi ON i.item_category_id = wi.item_category_id " +
            "WHERE wi.deal_id = ?;";


    /**
     * @param categoryID - ID of Deal in DB
     * @return Fully Filled Category object
     *         Or null if Category with such ID does not exists
     */
    //TODO: Levan daamtavre es
    public static ItemCategory getCategoryByID(int categoryID) {
        ItemCategory category = null;

        String query = JOIN_QUERY + " WHERE s.id = " + categoryID + ";";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();

            if(set.next()) return ItemCategory.parseCategory(set);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param itemCategoryIDs - List of IDs of ItemCategory in DB
     * @return List of Fully Filled ItemCategory objects
     *         Or null if some error happens
     */
    public static List<ItemCategory> getItemCategoriesByItemCategoryIDs(List<Integer> itemCategoryIDs) {
        List<ItemCategory> itemCategories = new ArrayList<>(itemCategoryIDs.size());
        for (Integer itemCategoryID : itemCategoryIDs) {
            ItemCategory itemCategory = getCategoryByID(itemCategoryID);
            if (itemCategory == null)
                return null;
            itemCategories.add(itemCategory);
        }
        return itemCategories;
    }


    /**
     * @param dealID - ID of Deal in DB
     * @return List of Categories which Deal with dealID wants to get
     *         If such dealID does not exists in DB
     *         returns null
     */
    public static List<ItemCategory> getWantedCategoriesByDealID(int dealID) {
        List<Integer> categoryIDs = new ArrayList<>();
        boolean flag = false;

        try {
            PreparedStatement st = DAO.getPreparedStatement(GET_WANTED_CATEGORIES_BY_DEAL_QUERY);
            st.setInt(1, dealID);
            ResultSet set = st.executeQuery();

            while (set.next()) {
                flag = true;
                categoryIDs.add(set.getBigDecimal("item_category_id").intValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!flag) return null;

        List<ItemCategory> categories = new ArrayList<>();
        for (Integer categoryID : categoryIDs)
            categories.add(CategoryManager.getCategoryByID(categoryID));

        return categories;
    }


    /**
     * Compares two lists of categories
     * @param l1 - ItemCategory list 1
     * @param l2 - ItemCategory list 2
     * @return Whether they are equal or not
     */
    public static boolean listsEqualsIgnoreOrder(List<ItemCategory> l1, List<ItemCategory> l2) {
        if(l1 == null && l2 == null) return true;
        if(l1 == null ^ l2 == null) return false;
        if(l1.size() != l2.size()) return false;

        Collections.sort(l1);
        Collections.sort(l2);
        return l1.equals(l2);
    }


    /**
     * @param cat Passed category
     * @return Returns all categories from database matching given criteria
     */
    public static List<ItemCategory> getCategories(ItemCategory cat) {

        List<ItemCategory> list = new ArrayList<>();
        String serie = cat.getSerie().getName(), type = cat.getType().getName(), brand = cat.getBrand().getName();

        String query = JOIN_QUERY + " WHERE (s.name LIKE '%" + serie + "%' OR t.name LIKE'%" +
                                      serie + "%' OR b.name LIKE '%" + serie + "%') ";

        if(cat.getType().getName() != OTHER)
            query += "AND t.name LIKE '%" + type + "%' ";
        if(cat.getBrand().getName() != OTHER)
            query += "AND b.name LIKE '%" + brand + "%'";
        query += ';';

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
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
     * @param cat Insert passed category into database
     * @return Success of insertion
     */
    public static boolean insertCategory(ItemCategory cat) {

        //No completely duplicate entries
        if(baseContainsRow(cat.getSerie().getName(), cat.getType().getName(), cat.getBrand().getName())) return false;

        //If both parameters are other, we invoke special method, which adds all others to the base
        if (cat.getType().getName().equals(OTHER) && cat.getBrand().getName().equals(OTHER)) {
            return addEverything(cat);
        }

        //Else, we insert info in a normal manner

        int typeID = checkAndReturnID(TYPE_TABLE, cat.getType().getName());
        int brandID = checkAndReturnID(BRAND_TABLE, cat.getBrand().getName());

        return insertIntoParentTable(SERIE_TABLE, cat.getSerie().getName(), typeID, brandID);
    }

    /**
     * Edge case method for categories
     * If user passed only serie and left other fields empty, we invoke this only in that case
     * @param cat Passed category
     */
    private static boolean addEverything(ItemCategory cat) {
        return insertIntoParentTable
                (SERIE_TABLE,
                        cat.getSerie().getName(),
                        insertIntoAndReturnID(TYPE_TABLE, cat.getType().getName()),
                        insertIntoAndReturnID(BRAND_TABLE, cat.getBrand().getName()));
    }

    /**
     * @param tableName Name of table
     * @param name String value to compare to
     * @return ID of a row, newly inserted or already existing
     */
    private static int checkAndReturnID(String tableName, String name) {

        //If name equals other, we insert it into a new field
        if(!name.equals(OTHER) && baseContains(tableName, name))
            return getIdByName(tableName, name);
        return insertIntoAndReturnID(tableName, name);
    }

    /**
     * @param table Name of table
     * @param str String value to compare to
     * @return ID of that row
     */
    private static int getIdByName(String table, String str) {
        return ((Long) getValueBy(table, "id", "name", str)).intValue();
    }

    /**
     * @param tableName Name of table
     * @param returnColumn Column values of which we need
     * @param compareColumn Column values of which we compare to passed object
     * @param obj Passed object
     * @return Value by passed criteria
     */
    private static Object getValueBy(String tableName, String returnColumn, String compareColumn, Object obj) {

        String query = "SELECT " + returnColumn + " FROM " + tableName + " WHERE " + compareColumn + " = ?;";
        Object res = null;

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            if(obj instanceof String) st.setString(1, (String) obj);
            else st.setInt(1, ((Long) obj).intValue());

            ResultSet set = st.executeQuery();
            if(set.next()) res = set.getObject(1);
            else return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * @param tableName Name of passed table
     * @param str       String value
     * @return id that it was inserted in
     */
    private static int insertIntoAndReturnID(String tableName, String str) {

        String query = "INSERT INTO " + tableName + " (name) VALUES (?);";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, str);
            st.executeUpdate();

            //Asks for id of an insertion and returns it if available
            ResultSet set = st.getGeneratedKeys();
            return (set.next()) ? set.getInt(1) : -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param tableName Name of the table
     * @param name Name of string
     * @param typeID typeID
     * @param brandID brandID
     * @return Success of insertion
     */
    private static boolean insertIntoParentTable(String tableName, String name, int typeID, int brandID) {
        String query = "INSERT INTO " + tableName + " (name, type_id, brand_id) VALUES (?, ?, ?);";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            st.setString(1, name);
            st.setInt(2, typeID);
            st.setInt(3, brandID);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param tableName Name of passed table
     * @param str       String to compare values to
     * @return Whether database contains such string query in given table
     */
    private static boolean baseContains(String tableName, String str) {

        String query = "SELECT id FROM " + tableName + " WHERE name = '" + str + "';";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            return st.executeQuery().next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static boolean baseContainsRow(String serie, String type, String brand) {

        String query = JOIN_QUERY + " WHERE s.name LIKE '%" + serie +
                "%' AND t.name LIKE '%" + type + "%' AND b.name LIKE '%" + brand + "%';";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            return st.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
