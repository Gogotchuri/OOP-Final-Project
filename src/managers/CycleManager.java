
package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.Chat;
import models.Cycle;
import models.Deal;
import models.ProcessStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CycleManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    /**
     * @param cycleID - ID of Cycle in DB
     * @return Fully Filled Cycle object which's ID = cycleID
     *         Or null if Cycle with such ID does not exists
     */
    public static Cycle getCycleByCycleID(int cycleID) {

        ProcessStatus.Status cycleStatus =
            StatusManager.getStatusIDByID("cycles", cycleID);

        List<Deal> cycleDeals =
            DealsManager.getDealsByCycleID(cycleID);

        return (cycleStatus == null || cycleDeals == null)
                ?
                null : new Cycle(cycleID, cycleStatus, cycleDeals);
    }


    /**
     * @param dealID - ID of Cycle in DB
     * @return Fully Filled List of Cycle objects of Deal
     *         Which out site has offered to User
     *         Or null if some error happens
     */
    public static List<Cycle> getCyclesByDealID(int dealID) {
        List<Cycle> cycles = new ArrayList<>();
        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                    "SELECT cycle_id \n" +
                           "  FROM offered_cycles \n" +
                           " WHERE deal_id = " + dealID + ";"
                );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                cycles.add (
                    getCycleByCycleID(resultSet.getBigDecimal("cycle_id").intValue())
                );
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return cycles;
    }


    /**
     Returns true iff:
     Data Base contains such cycle
     @param cycle - Cycle (at least) initialized with only Set of Deals
     */
    public static boolean containsDB(Cycle cycle) throws SQLException {

        StringBuilder queryBuilder = new StringBuilder (
            "SELECT 1 \n" +
            "  FROM ( \n" +
            "      SELECT COUNT(oc.deal_id) AS all_deals_num, \n" +
            "             cd.cycle_deals_num AS cycle_deals_num \n" +
            "        FROM offered_cycles oc \n" +
            "        JOIN (SELECT ioc.cycle_id AS cycle_id, \n" +
            "                     COUNT(ioc.deal_id) AS cycle_deals_num \n" +
            "                FROM offered_cycles ioc \n" +
            "               WHERE "
        );

        Iterator<Deal> i = cycle.getDealsIterator();

        /* As we know that, Cycle contains at least two Deal's */
        queryBuilder.append("iod.deal_id = ").append(i.next().getDealID()).append(" ");
        while (i.hasNext())
            queryBuilder.append("OR iod.deal_id = ").append(i.next().getDealID()).append(" ");

        queryBuilder.append('\n');

        queryBuilder.append (
            "               GROUP BY ioc.cycle_id) cd ON (oc.cycle_id = cd.cycle_id) \n" +
            "       GROUP BY oc.cycle_id \n" +
            ") result \n" +
            "WHERE result.all_deals_num = result.cycle_deals_num;"
        );


        String query = queryBuilder.toString();

        PreparedStatement statement = DAO.getPreparedStatement(query);
        ResultSet resultSet = statement.executeQuery();

        return resultSet.next();
    }


    /**
     * Returns true iff:
     * Data Base contains such cycle.
     * @param cycle - Cycle (at least) initialized with only Set of Deals
     */
    public static boolean addCycleToDB(Cycle cycle) {
        try {
            Cycle insertedCycle = insertCycle(cycle);

            Iterator<Deal> i = cycle.getDealsIterator();
            while (i.hasNext())
                insertCycleToOffered(i.next().getDealID(), insertedCycle.getCycleID());

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * Updates passed cycle object's ID
     * @param cycle - Passed Cycle, to be inserted in DB
     */
    private static Cycle insertCycle(Cycle cycle) throws SQLException {

        PreparedStatement statement =
            DAO.getPreparedStatement (
                "INSERT INTO cycles (status_id) VALUES (?);",
                Statement.RETURN_GENERATED_KEYS
            );

        statement.setInt(1, ProcessStatus.Status.ONGOING.getId());

        if (statement.executeUpdate() == 0)
            throw new SQLException("Creating Cycle failed, no rows affected.");

        Cycle insertedCycle;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                insertedCycle = new Cycle (
                        generatedKeys.getInt(1),
                        ProcessStatus.Status.ONGOING,
                        cycle.getDeals()
                );
                cycle.setCycleID(insertedCycle.getCycleID());
            } else
                throw new SQLException("Creating Cycle failed, no ID obtained.");
        }

        return insertedCycle;
    }


    /**
     * @param cycleDealID ID of Deal to add in offered_cycles
     * @param cycleID ID of a cycle, that deal belongs to
     */
    private static void insertCycleToOffered(int cycleDealID, int cycleID)
        throws SQLException {

        PreparedStatement statement =
            DAO.getPreparedStatement (
                "INSERT INTO offered_cycles (deal_id, cycle_id) VALUES (?, ?);"
            );

        statement.setInt(1, cycleDealID);
        statement.setInt(2, cycleID);

        if (statement.executeUpdate() == 0)
            throw new SQLException("Creating Offered Cycle failed, no rows affected.");
    }


    /**
     * Deletes offered Cycles associated with passed Cycle id.
     * @param cycleID - ID of Cycle in DB
     */
    private static void deleteOfferedCycles(int cycleID){
        DeleteManager.delete("offered_cycles", "cycle_id", cycleID);
    }


    /**
     * @param cycleID - ID of Cycle in DB
     * @param dealID - ID of Deal in DB
     * @return Whether Offered Cycle accepted or not
     */
    public static boolean acceptCycle(int cycleID, int dealID) {
        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                    "UPDATE offered_cycles \n" +
                           "   SET status_id = " + ProcessStatus.Status.COMPLETED + " \n" +
                           " WHERE cycle_id = " + cycleID + " \n" +
                           "   AND deal_id = " + dealID + ";"
            );
            if (statement.executeUpdate() == 0)
                return false;

            if (allAccepted(cycleID))
                return ChatManager.addChatToDB(new Chat(new Cycle(cycleID)));

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * TODO
     * @param cycleID - ID of Cycle in DB
     * @return Whether Cycle accepted or not
     */
    private static boolean allAccepted(int cycleID) {
        return false;
    }


    /**
     * Deletes a Cycle from DB
     * @param cycleID - ID of Cycle in DB
     */
    public static boolean deleteCycle(int cycleID){
        deleteOfferedCycles(cycleID);
        return DeleteManager.delete("cycles", "id", cycleID);
    }

}
