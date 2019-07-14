
package managers;

import database.DatabaseAccessObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DateManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    /**
     * finds time when given row was created
     * @param tableName - Name Of table
     * @param ID - ID of Some Object in DB
     * @return Creation Date of Object with given ID.
     *         If such object does not exists in table returns null.
     */
    public static Timestamp getCreateDateByID(String tableName, int ID) {
        return getDateByID(tableName, ID, "created_at");
    }


    /**
     * finds time when given row was last updated
     * @param tableName - Name Of table
     * @param ID - ID of Some Object in DB
     * @return Update Date of Object with given ID.
     *         If such object does not exists in table returns null.
     */
    public static Timestamp getUpdateDateByID(String tableName, int ID) {
        return getDateByID(tableName, ID, "updated_at");
    }


    /**
     * Helper Method for getCreateDateByID and getUpdateDateByID
     * @param tableName - Name Of table
     * @param ID - ID of Some Object in DB
     * @param dateName - Name of Date Column in DB
     * @return Some Date of Object with given ID.
     *         If such object does not exists in table returns null.
     */
    private  static Timestamp getDateByID(String tableName, int ID, String dateName) {
        Timestamp date = null;
        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                        "SELECT " + dateName + '\n' +
                               "  FROM " + tableName + '\n' +
                               " WHERE id = " + ID + ";"
                );
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                date = resultSet.getTimestamp(dateName);
        } catch (SQLException e) { e.printStackTrace(); }
        return date;
    }

}
