
package managers;

import database.DatabaseAccessObject;
import models.Cycle;
import models.Deal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

public class CycleManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();
    private static final String GET_LAST_ID_CYCLE = "SELECT MAX(id) FROM cycles;";
    private static final String GET_LAST_ID_OFFERED = "SELECT MAX(id) FROM offered_cycles;";
    private static final String INSERT_CYCLE_QUERY = "INSERT INTO cycles (id, status_id, created_at, updated_at) VALUES (?, ?, ?, ?);";
    private static final String INSERT_DEAL_TO_OFFERED = "INSERT INTO offered_cycles (id, deal_id, cycle_id) VALUES (?, ?, ?);";

    /**
     TODO: P.S. Should be as fast as possible
     Returns true iff:
     Data Base contains such cycle.
     */
    public static boolean containsDB(Cycle cycle) {
        return false;
    }

    /**
     TODO: P.S. Should be as fast as possible
     Inserts new cycle into the Data Base.
     */
    public static void addCycleToDB(Cycle cycle) {
        insertCycle(cycle);

        Iterator<Map.Entry<Integer, Deal>> it = cycle.getCycleInfo();
        while(it.hasNext()) {
            Deal cur = it.next().getValue();
            insertDealToOffered(cur, cycle.getId());
        }
    }

    /**
     * @param cur Deal to add in offered_cycles
     * @param cycleID ID of a cycle, that deal belongs to
     */
    private static void insertDealToOffered(Deal cur, int cycleID) {
        try {
            PreparedStatement st = DAO.getPreparedStatement(INSERT_DEAL_TO_OFFERED);

            st.setInt(1, generateID(GET_LAST_ID_OFFERED));
            st.setInt(2, cur.getId());
            st.setInt(3, cycleID);

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cycle passed cycle, to be inserted in database
     */
    private static void insertCycle(Cycle cycle) {
        try {
            PreparedStatement st = DAO.getPreparedStatement(INSERT_CYCLE_QUERY);

            st.setInt(1, generateID(GET_LAST_ID_CYCLE));
            st.setInt(2, 1); //TODO : STATUS ID ARIS ES, ROMELIC DEFAULT 1ia mara gavitanot sadme
            st.setTimestamp(3, cycle.getCreated_at());
            st.setTimestamp(4,cycle.getUpdated_at());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return GeneratedID, taken from database
     * @throws SQLException
     */
    private static int generateID(String query) throws SQLException {
        PreparedStatement st = DAO.getPreparedStatement(query);
        ResultSet set = st.executeQuery();
        return (set.next()) ? 1 + set.getInt(1) : 1;
    }
}
