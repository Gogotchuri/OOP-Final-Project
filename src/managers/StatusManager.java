
package managers;

import database.DatabaseAccessObject;
import models.ProcessStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusManager {


    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();


    /**
     * @param tableName - Name Of table
     * @param ID - ID of Some Object in DB
     * @return ProcessStatus of Object with given ID.
     *         If such object does not exists in table returns null.
     */
    public static ProcessStatus.Status getStatusIDByID(String tableName, int ID) {

        int statusID = 0;

        try {

            PreparedStatement statement =
                    DAO.getPreparedStatement (
                            "SELECT status_id FROM " + tableName + " WHERE id = " + ID + ";"
                    );

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                statusID = resultSet.getBigDecimal("status_id").intValue();

        } catch (SQLException e) { e.printStackTrace(); }

        return statusID == 0 ? null : ProcessStatus.getStatusByID(statusID);
    }

}
