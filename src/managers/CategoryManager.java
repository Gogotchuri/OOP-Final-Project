package managers;

import database.DatabaseAccessObject;
import models.categoryModels.ItemCategory;
import models.categoryModels.ItemParameter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();
    private static final String BRAND_TABLE = "item_brands";
    private static final String SERIE_TABLE = "item_categories";
    private static final String TYPE_TABLE = "item_types";
    private static final ItemParameter OTHER = null;
    private static final int UNASSIGNED = -1;

    /**
     * @param cat Insert passed category into database
     * @return Success of insertion
     */
    public static boolean insertCategory(ItemCategory cat) {
        int typeID = UNASSIGNED, brandID = UNASSIGNED;
        if(cat.getType() != null) typeID = checkAndAdd(TYPE_TABLE, cat.getType().getName());
        if(cat.getBrand() != null) brandID = checkAndAdd(BRAND_TABLE, cat.getBrand().getName());
        return insertIntoParentTable(SERIE_TABLE, cat.getSerie().getName(), typeID, brandID);
    }

    /**
     * @param tableName Name of table
     * @param name String value to compare to
     * @return ID of a row, newly inserted or already existing
     */
    private static int checkAndAdd(String tableName, String name) {
        if(baseContains(tableName, name))
            return getIdByName(tableName,name);
        else
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
            PreparedStatement st = DAO.getPreparedStatement(query);
            st.setString(1, str);
            st.executeUpdate();

            st = DAO.getPreparedStatement("SELECT MAX(id) FROM " + tableName + ";");
            ResultSet set = st.executeQuery();
            return (set.next()) ? set.getInt(1) : -1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
}
