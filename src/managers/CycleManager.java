
package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.Cycle;
import models.Deal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CycleManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();
    private static final String INSERT_CYCLE_QUERY = "INSERT INTO cycles (status_id, " +
            "created_at, updated_at) VALUES (?, ?, ?);";
    private static final String INSERT_DEAL_TO_OFFERED = "INSERT INTO offered_cycles (deal_id, " +
            "cycle_id) VALUES (?, ?);";
    private static final String GET_CYCLE_QUERY = "SELECT * FROM cycles WHERE cycle_id = ?;";
    private static final String GET_CYCLE_BY_DEAL_QUERY = "SELECT c.id, c.status_id, c.created_at, c.updated_at" +
            " FROM cycles c JOIN offered_cycles oc ON c.id = oc.cycle_id WHERE oc.deal_id = ?";

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
     * @param cycleID
     * @return cycle with the passed id
     */
    public static Cycle getCycleByID(int cycleID){
        Cycle res = null;
        try {
            PreparedStatement st = DAO.getPreparedStatement(GET_CYCLE_QUERY);
            st.setInt(1, cycleID);
            ResultSet set = st.executeQuery();
            res = new Cycle(cycleID, set.getBigDecimal("status_id").intValue(),
                    new Timestamp(set.getDate("created_at").getTime()),
                    new Timestamp(set.getDate("updated_at").getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  res;
    }

    /**
     *
     * @param dealID
     * @return
     */
    public static List<Cycle> getCyclesByDealID(int dealID){
        List<Cycle> list = new ArrayList<>();
        try {
            PreparedStatement st = DAO.getPreparedStatement(GET_CYCLE_BY_DEAL_QUERY);
            st.setInt(1, dealID);
            ResultSet set = st.executeQuery();
            while(set.next()) {
                list.add(
                        new Cycle(set.getBigDecimal("status_id").intValue(),
                                set.getBigDecimal("status_id").intValue(),
                                new Timestamp(set.getDate("created_at").getTime()),
                                new Timestamp(set.getDate("updated_at").getTime())));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  list;
    }

    /**
     * @param cur Deal to add in offered_cycles
     * @param cycleID ID of a cycle, that deal belongs to
     */
    private static void insertDealToOffered(Deal cur, int cycleID) {
        try {
            PreparedStatement st = DAO.getPreparedStatement(INSERT_DEAL_TO_OFFERED);

            st.setInt(1, cur.getId());
            st.setInt(2, cycleID);

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

            st.setInt(1, 1); //TODO : STATUS ID ARIS ES, ROMELIC DEFAULT 1ia mara gavitanot sadme
            st.setTimestamp(2, cycle.getCreated_at());
            st.setTimestamp(3,cycle.getUpdated_at());

            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes offered cycles associated with passed cycle id
     * @param cycleID
     */
    private static void deleteOfferedCycles(int cycleID){
        DeleteManager.delete("offered_cycles", "cycle_id", cycleID);
    }


    /**
     * Deletes a cycle from database
     * @param cycleID id of the cycle to be deleted
     */
    public static void deleteCycle(int cycleID){
        deleteOfferedCycles(cycleID);
        DeleteManager.delete("cycles", "id", cycleID);
    }
}
