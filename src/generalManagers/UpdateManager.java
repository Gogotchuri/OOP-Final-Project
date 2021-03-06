package generalManagers;
import database.DatabaseAccessObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

public class UpdateManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    /**
     * Updates data in database
     * @param form Takes and updateForm, depending oh which, changes data
     */
    public static boolean update(UpdateForm form) {
        String query = "UPDATE " + form.getTableName() + " SET ";
        Iterator<UpdateForm.Pair> it = form.getUpdates();

        while(it.hasNext()) {
            UpdateForm.Pair cur = it.next();

            query += cur.getColumnName() + " = ";
            if(cur.getNewValue() instanceof String) query += "'" + cur.getNewValue() + "'";
            else query += cur.getNewValue();
            query += ",";
        }
        query = query.substring(0, query.length() - 1); //To remove an extra ,
        query += " WHERE id = " + form.getId() + " ;";

        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param tableName Name of passed table
     * @param seedValue Value of seed, int to reset id to
     * @return Whether reseeding was successful
     */
    public static boolean reseedTable(String tableName, int seedValue) {
        String query = "ALTER TABLE " + tableName + " AUTO_INCREMENT = " + seedValue + ";";
        try {
            PreparedStatement st = DAO.getPreparedStatement(query);
            st.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
