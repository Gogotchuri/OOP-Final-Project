
package managers;

import controllers.front.DealsController.SearchCriteria;
import controllers.front.DealsController.SearchCriteria.Criteria;
import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.*;
import models.categoryModels.ItemCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DealsManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    /**
     * @param dealID - ID of Deal in DB
     * @return Fully Filled Deal object which's ID = dealID
     *         Or null if some error happens
     */
    public static Deal getDealByDealID(int dealID) {

        int ownerID = UserManager.getUserIDByDealID(dealID);
        List<Item> ownedItems = ItemManager.getItemsByDealID(dealID);
        List<ItemCategory> wantedCategories = CategoryManager.getWantedCategoriesByDealID(dealID);
        ProcessStatus.Status dealStatus = StatusManager.getStatusIDByID("deals", dealID);
        String title = getTitleByDealID(dealID);
        String description = getDescriptionByDealID(dealID);
        Timestamp dealCreateDate = DateManager.getCreateDateByID("deals", dealID);

        return (ownerID == 0 ||
                 ownedItems == null ||
                  wantedCategories == null ||
                   dealStatus == null ||
                    title == null ||
                     description == null ||
                      dealCreateDate == null)
                ?
                null : new Deal(dealID, ownerID, ownedItems, wantedCategories, dealStatus,
                                 title,  description, dealCreateDate);
    }


    /**
     * returns title of the given deal
     * @param dealID ID of Deal in DB
     * @return title of the deal, may return null, in that case, it's empty
     */
    private static String getTitleByDealID(int dealID) {
        String query = "SELECT title FROM deals where id = " + dealID + ";";
        return firstResultAsString(query);
    }


    /**
     * get description of given deal
     * @param dealID ID of Deal in DB
     * @return description of the deal, may return null, in that case, it's empty
     */
    private static String getDescriptionByDealID(int dealID) {
        String query = "SELECT description FROM deals where id = " + dealID + ";";
        return firstResultAsString(query);
    }


    /**
     * Helper Function
     * returns first row of given query
     * @param query - SQL query
     * @return First Result of the query as String
     */
    private static String firstResultAsString(String query) {
        try {
            PreparedStatement statement = DAO.getPreparedStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
                return resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get all deals of given user
     * @param userID - ID of User in DB
     * @return List of fully filled deals of User with ID = userID
     *         Or null if some error happens
     */
    public static List<Deal> getDealsByUserID(int userID) {
        List<Deal> deals = new ArrayList<>();
        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                    "SELECT id AS id_of_deal" +
                           "  FROM deals" +
                           " WHERE user_id = " + userID + ";"
                );
            queryDeals(deals, statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return deals;
    }


    /**
     * get all deals which participates in given cycle
     * @param cycleID - ID of Cycle in DB
     * @return List of fully filled deals of Cycle with ID = cycleID
     *         Or null if some error happens
     */
    public static List<Deal> getDealsByCycleID(int cycleID) {
        List<Deal> deals = new ArrayList<>();
        try {
            PreparedStatement statement =
                    DAO.getPreparedStatement (
                    "SELECT deal_id AS id_of_deal" +
                           "  FROM offered_cycles" +
                           " WHERE cycle_id = " + cycleID + ";"
                    );
            queryDeals(deals, statement);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return deals;
    }

    /**
     * gets deals of given user which are in given cycle
     * @param cycleID - ID of Cycle
     * @param userID - ID of user, whose deals are looked for
     * @return List of fully filled deals of Cycle with ID = cycleID
     *         and deal owner is user with userID
     */
    public static List<Deal> getUsersDealsByCycleId(int userID, int cycleID){
        List<Deal> result = new ArrayList<>();
        List<Deal> cycleDeals = getDealsByCycleID(cycleID);
        for (Deal d : cycleDeals)
            if (userID == d.getOwnerID())
                result.add(d);
        return result;
    }


    /**
     * Helper method, queries
     * SELECT deal_id !!! AS id_of_deal !!! FROM deals ...
     * Adds fully filled deals to the list
     * @param list - List, which we update with queried deals
     * @param statement - Passed preparedStatement
     * @throws SQLException - If some error happens
     */
    private static void queryDeals(List<Deal> list, PreparedStatement statement)
        throws SQLException {

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            int dealID = resultSet.getBigDecimal("id_of_deal").intValue();
            list.add (
                getDealByDealID(dealID)
            );
        }
    }


    /**
     * @param sc - SearchCriteria, based on which we query deals
     * @return List of deals based on given criteria
     *         Or null if some error happens
     */
    public static List<Deal> getDealsBySearchCriteria(SearchCriteria sc) {

        StringBuilder queryBuilder = new StringBuilder (
            "SELECT DISTINCT d.id AS id_of_deal \n" +
            "  FROM deals d \n" +
            "  JOIN users u ON (d.user_id = u.id) \n" +
            "  JOIN wanted_items wi ON (d.id = wi.deal_id) \n" +
            "  JOIN item_categories ic ON (wi.item_category_id = ic.id) \n" +
            " WHERE TRUE "
        );

        Iterator<Criteria> i = sc.getCriteriaIterator();

        while (i.hasNext()) {
            Criteria criteria = i.next();
            if (criteria == Criteria.USER_NAME) {
                String userName = sc.getCriteriaValue(Criteria.USER_NAME);
                queryBuilder.append(" AND u.user_name = \'").append(userName).append("\' \n");
            } else if (criteria == Criteria.TITLE) {
                String category = sc.getCriteriaValue(Criteria.TITLE);
                queryBuilder.append(" AND d.title LIKE \'%").append(category).append("%\' \n"); // TODO
            }
        }

        queryBuilder.append(';');

        return getDealList(queryBuilder.toString());
    }


    /**
     * Returns list of deals whose Wanted item categories are equal of
     * 'deal's Owned item categories Returns at least empty list
     * Or null if some error happens
     */
    public static List<Deal> getClients(int dealID) {

        String query =
            "SELECT d.id AS id_of_deal FROM deals d \n" +
            " WHERE d.id IN ( \n" +
            "    SELECT result.deal_id FROM \n" +
            "    (SELECT a.deal_id AS deal_id, \n" +
            "            COUNT(*) AS row_num FROM \n" +
            "        (SELECT deal_id, \n" +
            "                item_category_id, \n" +
            "                COUNT(*) AS freq_item_category \n" +
            "           FROM wanted_items \n" +
            "          GROUP BY deal_id, item_category_id) a \n" +
            "        JOIN \n" +
            "        (SELECT i.item_category_id AS item_category_id, \n" +
            "                COUNT(*) AS freq_item_category \n" +
            "           FROM owned_items oi JOIN items i ON (oi.item_id = i.id) \n" +
            "          WHERE oi.deal_id = " + dealID + "\n" +
            "          GROUP BY i.item_category_id) b \n" +
            "        ON a.item_category_id = b.item_category_id AND \n" +
            "            a.freq_item_category = b.freq_item_category \n" +
            "     GROUP BY deal_id \n" +
            "     HAVING row_num = (SELECT COUNT(DISTINCT i.item_category_id) \n" +
            "                         FROM owned_items oi JOIN items i ON (oi.item_id = i.id) \n" +
            "                        WHERE oi.deal_id = " + dealID + ") \n" +
            "    ) result \n" +
            ");";

        return getDealList(query);
    }


    /**
     * @param query - query to execute
     * @return List of Deals got by query
     *         Or null if some error happens
     */
    private static List<Deal> getDealList(String query) {
        List<Deal> deals = new ArrayList<>();
        try {
            PreparedStatement statement = DAO.getPreparedStatement(query);
            queryDeals(deals, statement);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return deals;
    }


    /**
     * Stores deal in the database and returns id
     * If something went wrong
     * @param deal - Deal to store into DB
     *               Filled with:
     *               1) User owner
     *               2) List<Item> ownedItems
     *               3) List<ItemCategory> wantedCategories
     *
     * @return stored Deal's ID
     *         If returned 0 some error happened
     */
    public static int storeDeal(Deal deal) {

        int dealID = 0;

        try {
            PreparedStatement statement =
                DAO.getPreparedStatement (
                "INSERT INTO deals (user_id, status_id, title, description) " +
                        "VALUES (?, ?, ?, ?);",
                    Statement.RETURN_GENERATED_KEYS
                );

            statement.setInt(1, deal.getOwnerID());
            //Freshly created deal should be ongoing
            statement.setInt(2, ProcessStatus.Status.ONGOING.getId());
            statement.setString(3, ((deal.getTitle() == null) ? "" : deal.getTitle()));
            statement.setString(4, (deal.getDescription() == null)? "" : deal.getDescription());


            if (statement.executeUpdate() == 0)
                throw new SQLException("Storing Deal failed, no rows affected.");


            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dealID = generatedKeys.getInt(1);
                    deal.setDealID(dealID);

                } else
                    throw new SQLException("Storing Deal failed, no ID obtained.");
            }


            for (Item item : deal.getOwnedItems())
                if (!ItemManager.insertOwnedItem(deal.getDealID(), item.getItemID()))
                    return 0;

            for (ItemCategory itemCategory : deal.getWantedCategories())
                if (!ItemManager.insertWantedItemCategory(deal.getDealID(), itemCategory.getId()))
                    return 0;

        }
        catch (SQLException e) { e.printStackTrace(); }

        return dealID;
    }


    /**
     * Deletes deal with given id in database
     * @param dealID - ID of Deal in DB
     * @return True iff Deal deleted successfully
     */
    public static boolean deleteDeal(int dealID) {
        List<Cycle> cycles = CycleManager.getCyclesByDealID(dealID);
        if (cycles == null) return false;
        for (Cycle cycle : cycles)
            CycleManager.deleteCycle(cycle.getCycleID());
        return DeleteManager.delete("deals", "id", dealID);
    }

}
