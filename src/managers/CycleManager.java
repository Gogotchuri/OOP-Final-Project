
package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
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
            if(i != null)
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

        //Freshly created cycle should be waiting for acceptance
        statement.setInt(1, ProcessStatus.Status.WAITING.getId());

        if (statement.executeUpdate() == 0)
            throw new SQLException("Creating Cycle failed, no rows affected.");

        Cycle insertedCycle;
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                insertedCycle = new Cycle (
                        generatedKeys.getInt(1),
                        ProcessStatus.Status.WAITING,
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
                "INSERT INTO offered_cycles (status_id, deal_id, cycle_id) VALUES (?, ?, ?);"
            );


        statement.setInt(1, ProcessStatus.Status.WAITING.getId());
        statement.setInt(2, cycleDealID);
        statement.setInt(3, cycleID);

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
                           "   SET status_id = " + ProcessStatus.Status.COMPLETED.getId() + " \n" +
                           " WHERE cycle_id = " + cycleID + " \n" +
                           "   AND deal_id = " + dealID + ";"
            );
            if (statement.executeUpdate() == 0)
                return false;

            if (allAccepted(cycleID)) {
                return updateCycleStatus(cycleID, ProcessStatus.Status.ONGOING.getId()) &&
                        ChatManager.addChatToDB(new Chat(new Cycle(cycleID)));
            }

            return true;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates cycle's status in database
     * @param cycleID
     * @param statusID
     * @return true if successful
     */
    private static boolean updateCycleStatus(int cycleID, int statusID){
        UpdateForm uf = new UpdateForm("cycles", cycleID);
        uf.addUpdate("status_id", statusID);
        return UpdateManager.update(uf);
    }


    /**
     * @param cycleID - ID of Cycle in DB
     * @return Whether All of Users accepted Cycle or not
     */
    private static boolean allAccepted(int cycleID) {
        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                    "SELECT 1 \n" +
                           "  FROM offered_cycles \n" +
                           " WHERE cycle_id = " + cycleID + " \n" +
                           "   AND status_id = " + ProcessStatus.Status.WAITING.getId() + ";"
                );
            return !statement.executeQuery().next();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Deletes a Cycle from DB
     * @param cycleID - ID of Cycle in DB
     */
    public static boolean deleteCycle(int cycleID){
        return DeleteManager.delete("cycles", "id", cycleID);
    }

    public static boolean userParticipatesInCycle(int user_id, int cycle_id){
        String stmtString = "SELECT count(oc.id) as num_id \n" +
                    "FROM offered_cycles oc \n" +
                    "WHERE oc.user_id = " + user_id + " AND \n" +
                    "WHERE oc.cycle_id = " + cycle_id +";";
        try {
            PreparedStatement statement =
                    DAO.getPreparedStatement (stmtString);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt("num_id") > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Returns all cycles for the user with given id
     * @param userID User id for whom to get cycles
     * @return returns List of cycle if everything went well, if query crashed
     *          prints stack trace and returns null
     */
    public static List<Cycle> getUserCycles(int userID) {
        List<Cycle> cycles = new ArrayList<>();
        try {
            PreparedStatement statement =
                    DAO.getPreparedStatement (
                            "SELECT oc.cycle_id \n" +
                                    "FROM offered_cycles oc \n" +
                                    "WHERE oc.user_id = " + userID + ";"
                    );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                cycles.add (
                        getCycleByCycleID(resultSet.getInt("cycle_id"))
                );
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return cycles;
    }
}
