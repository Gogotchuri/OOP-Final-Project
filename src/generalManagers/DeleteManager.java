package generalManagers;

import database.DatabaseAccessObject;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteManager {

    private static final DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    /**
     * @param table Name of a table
     * @param columnName Name of a column
     * @param obj Object to compare to and then delete
     */
    public static boolean delete(String table, String columnName, Object obj) {
        String query = "DELETE FROM " + table + " WHERE " + columnName + " = ?;";
        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            if(obj instanceof String ) st.setString(1,(String) obj);
            else st.setInt(1,(Integer) obj);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Used to delete every entry from table
     * @param table name of table which needs to be emptied
     * @return true if operation successful
     */
    public static boolean emptyBase(String table) {
        try {
            PreparedStatement st = DatabaseAccessObject.getInstance()
                    .getPreparedStatement("delete from " + table + ";");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param table Passed table
     * @return Empties whole table and reseeds it too
     */
    public static boolean deleteAndReseed(String table) {
        return delete(table,"1",1) && UpdateManager.reseedTable(table,1);
    }
}