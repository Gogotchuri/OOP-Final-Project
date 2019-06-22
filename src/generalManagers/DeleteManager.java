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
    public static void delete(String table, String columnName, Object obj) {
        String query = "DELETE FROM " + table + " WHERE " + columnName + " = ?;";
        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            if(obj instanceof String ) st.setString(1,(String) obj);
            else st.setInt(1,(Integer) obj);

            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}