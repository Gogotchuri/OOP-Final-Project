package managers;

import database.DatabaseAccessObject;
import models.categoryModels.ItemBrand;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemSeries;
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
    private static final String JOIN_QUERY = "SELECT * from item_categories s JOIN item_types t on s.type_id = t.id" +
            " JOIN item_brands b on s.brand_id = b.id ";
    private static final String GET_WANTED_CATEGORIES_BY_DEAL_QUERY = "SELECT wi.item_category_id FROM wanted_items wi " +
            "WHERE wi.deal_id = ?;";

    /**
     * @param categoryID - ID of Deal in DB
     * @return Fully Filled Category object
     *         Or null if Category with such ID does not exists
     */
    public static ItemCategory getCategoryByID(int categoryID) {

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
     */
    public static List<ItemCategory> getWantedCategoriesByDealID(int dealID) {
        List<Integer> categoryIDs = new ArrayList<>();

        try {
            PreparedStatement st = DAO.getPreparedStatement(GET_WANTED_CATEGORIES_BY_DEAL_QUERY);
            st.setInt(1, dealID);
            ResultSet set = st.executeQuery();

            while (set.next()) {
                categoryIDs.add(set.getBigDecimal("item_category_id").intValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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
     * @param brandID Brand id
     * @return All categories with given type
     */
    public static List<ItemCategory> getCategoriesWithBrand(int brandID) {
        return getCategoriesWithParameterID("b.id", brandID);
    }

    /**
     * @param typeID Type id
     * @return All categories with given type
     */
    public static List<ItemCategory> getCategoriesWithType(int typeID) {
        return getCategoriesWithParameterID("t.id", typeID);
    }

    /**
     * @param columnName Name of a column
     * @param id Id
     * @return List of categories matching passed criteria
     */
    private static List<ItemCategory> getCategoriesWithParameterID(String columnName, int id) {
        List<ItemCategory> list = new ArrayList<>();
        String query = JOIN_QUERY + " WHERE " + columnName + " = " + id + ";";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();

            while(set.next()) list.add(ItemCategory.parseCategory(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param typeID id of a type
     * @param brandID id of a brand
     * @return All categories matching type and brand ids
     */
    public static List<ItemCategory> getCategoriesWithBrandAndType(int typeID, int brandID) {
        List<ItemCategory> list = new ArrayList<>();
        String query = JOIN_QUERY + " WHERE t.id = " + typeID + " AND b.id = " + brandID + ";";
        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();

            while(set.next()) list.add(ItemCategory.parseCategory(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     * @param type id of a type
     * @param brand id of a brand
     * @return All categories matching type and brand ids
     */
    public static List<ItemSeries> getSeriesWithBrandAndType(String type, String brand) {
        List<ItemSeries> list = new ArrayList<>();
        String query = JOIN_QUERY + " WHERE t.name = '" + type + "' AND b.name = '" + brand + "';";
        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();
            while(set.next()) {
                ItemCategory ic = ItemCategory.parseCategory(set);
                list.add(ic.getSeries());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     * @return All item types from database
     */
    public static List<ItemType> getAllTypes() {
        List<ItemType> list = new ArrayList<>();

        try {
            PreparedStatement st = DAO.getPreparedStatement("SELECT * FROM item_types;");
            ResultSet set = st.executeQuery();
            while(set.next()) {
                list.add(new ItemType(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @return All item brands from database
     */
    public static List<ItemBrand> getAllBrands() {
        List<ItemBrand> list = new ArrayList<>();

        try {
            PreparedStatement st = DAO.getPreparedStatement("SELECT * FROM item_brands;");
            ResultSet set = st.executeQuery();
            while(set.next()) {
                list.add(new ItemBrand(set.getInt(1), set.getString(2)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * @param cat Insert passed category into database
     * @return Returns id of inserted category, or doesn't insert if already there
     */
    public static int insertCategory(ItemCategory cat) {

        //If this row is present with all fields, we don't add it at all
        int rowID = baseContainsRow(cat.getSeries().getName(), cat.getType().getName(), cat.getBrand().getName());
        if(rowID != -1) return rowID;

        //Else we get inserted ids of brand and type (of already present, just return ids)
        int typeID = checkAndReturnID(TYPE_TABLE, cat.getType().getName());
        int brandID = checkAndReturnID(BRAND_TABLE, cat.getBrand().getName());

        //And add new entry to the base
        return insertIntoParentTable(SERIE_TABLE, cat.getSeries().getName(), typeID, brandID);
    }

    /**
     * @param tableName Name of table
     * @param name String value to compare to
     * @return ID of a row, newly inserted or already existing
     */
    private static int checkAndReturnID(String tableName, String name) {
        int curID = baseContains(tableName, name);
        //If id is -1, this means we don't have it in our database and return newly inserted ID
        return (curID == -1) ? insertIntoAndReturnID(tableName, name) : curID;
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
     * @return Returns id of insertion, or -1 if unsuccessful
     */
    private static int insertIntoParentTable(String tableName, String name, int typeID, int brandID) {
        String query = "INSERT INTO " + tableName + " (name, type_id, brand_id) VALUES (?, ?, ?);";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query,  Statement.RETURN_GENERATED_KEYS);
            st.setString(1, name);
            st.setInt(2, typeID);
            st.setInt(3, brandID);
            st.executeUpdate();

            ResultSet set = st.getGeneratedKeys();
            if(set.next()) return set.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * @param tableName Name of passed table
     * @param str       String to compare values to
     * @return ID of the value we search for, returns -1 if doesn't exist
     */
    private static int baseContains(String tableName, String str) {

        String query = "SELECT id FROM " + tableName + " WHERE name = '" + str + "';";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();

            if(set.next()) return set.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * @param serie Serie name
     * @param type Type name
     * @param brand Brand name
     * @return If database contains row returns id, else -1
     */
    private static int baseContainsRow(String serie, String type, String brand) {

        String query = JOIN_QUERY + " WHERE s.name = '" + serie +
                "' AND t.name = '" + type + "' AND b.name = '" + brand + "';";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            ResultSet set = st.executeQuery();
            if(set.next()) return set.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
