package managers;

import controllers.front.DealsController;
import controllers.front.DealsController.SearchCriteria;
import controllers.front.DealsController.SearchCriteria.Criteria;
import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import models.Deal;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DealsManager {

    private static DatabaseAccessObject DBAO = DatabaseAccessObject.getInstance();

    private static final String SELECT_DEAL_BY_USER_QUERY = "SELECT * FROM deals WHERE user_id = ?;";
    private static final String SELECT_DEAL_QUERY = "SELECT * FROM deals WHERE id = ?;";
    private static final String STORE_DEAL_QUERY = "INSERT INTO deals VALUES(?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT MAX(id) FROM ?";

    /**
     * get Deal with its id
     *
     * @param deal_id
     * @return Deal
     */
    public static Deal getDealById(int deal_id){
        Deal res = null;
        try {
            PreparedStatement st = DBAO.getPreparedStatement(SELECT_DEAL_QUERY);
            st.setInt(1, deal_id);
            ResultSet set = st.executeQuery();
            res = new Deal(deal_id, set.getBigDecimal("user_id").intValue(), set.getBigDecimal("status_id").intValue(),
                    new Timestamp(set.getDate("created_at").getTime()), new Timestamp(set.getDate("updated_at").getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  res;
    }

    /**
     * stores deal in the database and returns id
     * If something went wrong
     * @param deal
     */
    public static int storeDeal(Deal deal){
        int id = 0;
        try {
            PreparedStatement st = DBAO.getPreparedStatement(STORE_DEAL_QUERY);
            st.setInt(1, deal.getUser_id());
            st.setInt(2, deal.getStatus_id());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            st.setTimestamp(3, t);
            st.setTimestamp(4, t);
            st.setInt(5, deal.getStatus_id());
            st.executeUpdate();
            PreparedStatement statement = DBAO.getPreparedStatement(SELECT_ID);
            statement.setString(1, "deals");
            ResultSet set = statement.executeQuery();
            id = set.getInt("id");
            deal.setId(id);

            //If deals already have wanted and owned item, insert those
            List<Integer> wanted_item_ids = deal.getWanted_ids(),
                    owned_item_ids = deal.getOwned_ids();
            if(owned_item_ids != null) {
                for (int i : owned_item_ids) {
                    if(!ItemManager.insertOwnedItem(deal.getId(), i)) return 0;
                }
            }
            if(wanted_item_ids != null) {
                for (int i : wanted_item_ids)
                    ItemManager.insertWantedItem(deal.getId(), i);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    /**
     * Given a already existing deal in the database, updates entry
     * @param deal
     * @return true if update was a success
     */
    public static boolean updateDeal(Deal deal){
        UpdateForm uForm = new UpdateForm("deals", deal.getId());
        uForm.addUpdate("user_id", deal.getUser_id());
        uForm.addUpdate("status_id", deal.getStatus_id());
        uForm.addUpdate("created_at", deal.getCreated_at());
        uForm.addUpdate("updated_at", deal.getUpdated_at());
        return UpdateManager.update(uForm);
    }


    /**
     * Deletes deal with given id in database
     * @param id Deal id
     * @return true if deal deleted successfully
     */
    public static boolean deleteDeal(int id){
        return DeleteManager.delete("deals", "id", id);
    }

    /**
     * @param user User, deals of which the gather
     * @return List of deals by user
     */
    public static List<Deal> getUserDeals(User user){
        List<Deal> list = new ArrayList<>();

        try {
            PreparedStatement st = DBAO.getPreparedStatement(SELECT_DEAL_BY_USER_QUERY);
            st.setInt(1, user.getId());
            queryDeals(list,st);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * @param sc SearchCriteria, based on which we query deals
     * @return List of deals based on given criteria
     */
    public static List<Deal> getDeals(SearchCriteria sc) {
        List<Deal> list = new ArrayList<>();
        String query = "SELECT * FROM deals ";

        String join = " JOIN users ON deals.user_id = users.id";
        join += " JOIN wanted_items ON deals.id = wanted_items.deal_id";
        join += " JOIN item_categories on wanted_items.item_category_id = id ";

        String clauses = " WHERE true ";

        Iterator<Criteria> it = sc.getCriteriaIterator();

        while(it.hasNext()) {
            Criteria criteria = it.next();

            if(criteria == Criteria.USER_NAME) {

                String userName = sc.getCriteriaValue(Criteria.USER_NAME);
                clauses += " and users.user_name = " + userName + " ";

            } else if(criteria == Criteria.CATEGORY_NAME) {

                String category = sc.getCriteriaValue(Criteria.CATEGORY_NAME);
                clauses += " and item_categories.name = " + category + " ";

            } else if(criteria == Criteria.DEAL_CREATE_DATE) {

                String date = sc.getCriteriaValue(Criteria.DEAL_CREATE_DATE);
                clauses += " and created_at = " + date + " ";

            } else if(criteria == Criteria.DEAL_UPDATE_DATE) {

                String date = sc.getCriteriaValue(Criteria.DEAL_UPDATE_DATE);
                clauses += " and updated_at = " + date + " ";
            }
        }

        query += clauses + ";";

        try {
            PreparedStatement st = DBAO.getPreparedStatement(query);
            queryDeals(list,st);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Helper method, queries and adds deals to the list
     * @param list List, which we update with queried deals
     * @param st Passed preparedStatement
     * @throws SQLException
     */
    private static void queryDeals(List<Deal> list, PreparedStatement st) throws SQLException {
        ResultSet set = st.executeQuery();

        while(set.next()) {
            list.add(
                    new Deal(
                            set.getBigDecimal("id").intValue(),
                            set.getBigDecimal("user_id").intValue(),
                            set.getBigDecimal("status_id").intValue(),
                            new Timestamp(set.getDate("created_at").getTime()),
                            new Timestamp(set.getDate("updated_at").getTime())
                    ));
        }
    }

    /**
     TODO
     Returns list of deals whose
     Wanted item categories
     are equal of
     'deal's Owned item categories
     Returns at least empty list
     */
    public static List<Deal> getClients(Deal deal) {

        return new ArrayList<>();
    }
}
