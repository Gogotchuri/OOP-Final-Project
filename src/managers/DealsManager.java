
package managers;

import controllers.front.DealsController.SearchCriteria;
import controllers.front.DealsController.SearchCriteria.Criteria;
import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import models.Category;
import models.Deal;
import models.Item;
import models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DealsManager {

    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    private static final String SELECT_DEAL_BY_USER_QUERY = "SELECT * FROM deals WHERE user_id = ?;";
    private static final String SELECT_DEAL_QUERY = "SELECT * FROM deals WHERE id = ?;";
    private static final String STORE_DEAL_QUERY = "INSERT INTO deals (user_id, status_id, created_at, updated_at) " +
            "VALUES(?, ?, ?, ?);";
    private static final String SELECT_ID = "SELECT MAX(id) FROM ?";


    /**
     * @param dealID - ID of Deal in DB
     * @return Fully Filled Deal object
     *         Or null if Deal with such ID does not exists
     */
    public static Deal getDealByDealID(int dealID) {

        User owner = getDealOwnerByDealID(dealID);
        List<Item> ownedItems = getDealOwnedItemsByDealID(dealID);
        List<Category> wantedCategories = getDealWantedCategoriesByDealID(dealID);

        return (owner == null ||
                 ownedItems == null ||
                  wantedCategories == null)
                ?
                null : new Deal(dealID, owner, ownedItems, wantedCategories);
    }


    /**
     * TODO: Lasha
     * @param dealID - ID of Deal in DB
     * @return Owner (User) of Deal with ID = dealID
     *         If such dealID does not exists in DB
     *         returns null
     */
    private static User getDealOwnerByDealID(int dealID) {

        /*
         სელექთი, რომელიც აბრუნებს dealID-ს მქონე Deal-ის მფლობელს.
         თუ მსგავსი dealID არ არსებობს (შესაბამისად არ არსებობს მფლობელიც)
         აბრუნებს ცარიელ ცხრილს.
         */
        String query = "";

        int userID = 0; // სელექთიდან ამოღებულ userID-ს მიანიჭებ ამას, თუ არადა დატოვებ 0-ს

        return userID == 0 ? null : UserManager.getUserByID(userID);
    }


    /**
     * TODO: Lasha
     * @param dealID - ID of Deal in DB
     * @return List of Items which Deal with dealID contains
     *         If such dealID does not exists in DB
     *         returns null
     */
    private static List<Item> getDealOwnedItemsByDealID(int dealID) {

        /*
         სელექთი, რომელის აბრუნებს იმ itemID-ებს,
         რომლების dealID-ს მქონდე Deal-ს ეკუთვნის.
         თუ მსგავსი dealID არ არსებობს, შესაბამისად
         არ არსებობს მისი itemID-ები უნდა დაბრუნდეს ცარიელი ცხრილი
         */
        String query = "";

        // !!! თუ დაბრუნდა ცარიელი ცხრილი დააბრუნე null !!!

        List<Integer> itemIDs = null; // გადაწერე ამოღებული მონაცემები აქ

        List<Item> items = new ArrayList<>(itemIDs.size());
        for (Integer itemID : itemIDs)
            items.add(ItemManager.getItemByID(itemID));

        return items;
    }


    /**
     * TODO: Lasha
     * @param dealID - ID of Deal in DB
     * @return List of Categories which Deal with dealID wants to get
     *         If such dealID does not exists in DB
     *         returns null
     */
    private static List<Category> getDealWantedCategoriesByDealID(int dealID) {

        /*
         სელექთი, რომელიც აბრუნებს ნივთების იმ კატეგორიებს,
         რომელიც dealID-ს მქონე მფლობელს უნდა.
         თუ მსგავსი dealID არ არსებობს, შესაბამისად
         არ არსებობს მისი itemID-ები უნდა დაბრუნდეს ცარიელი ცხრილი
         */
        String query = "";

        // !!! თუ დაბრუნდა ცარიელი ცხრილი დააბრუნე null !!!

        List<Integer> categoryIDs = null; // გადაწერე ამოღებული მონაცემები აქ

        List<Category> categories = new ArrayList<>(categoryIDs.size());
        for (Integer categoryID : categoryIDs)
            categories.add(CategoryManager.getCategoryByID(categoryID));

        return categories;
    }


    /**
     * TODO: Krawa
     * stores deal in the database and returns id
     * If something went wrong
     * @param deal
     */
    public static int storeDeal(Deal deal){
        int id = 0;
        /*try {
            PreparedStatement st = DBAO.getPreparedStatement(STORE_DEAL_QUERY);
            st.setInt(1, deal.getUser_id());
            st.setInt(2, deal.getStatus_id());
            Timestamp t = new Timestamp(System.currentTimeMillis());
            st.setTimestamp(3, t);
            st.setTimestamp(4, t);
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
        }*/
        return id;
    }


    /**
     * Deletes deal with given id in database
     * @param id - Deal id
     * @return true if deal deleted successfully
     */
    public static boolean deleteDeal(int id){
        return DeleteManager.delete("deals", "id", id);
    }

    /**
     * TODO: Krawa
     * @param user User, deals of which the gather
     * @return List of deals by user
     */
    public static List<Deal> getUserDeals(User user){
        List<Deal> list = new ArrayList<>();

        try {
            PreparedStatement st = DAO.getPreparedStatement(SELECT_DEAL_BY_USER_QUERY);
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
            PreparedStatement st = DAO.getPreparedStatement(query);
            queryDeals(list,st);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * TODO: Krawa
     * Helper method, queries and adds deals to the list
     * @param list List, which we update with queried deals
     * @param st Passed preparedStatement
     * @throws SQLException
     */
    private static void queryDeals(List<Deal> list, PreparedStatement st) throws SQLException {
        /*ResultSet set = st.executeQuery();

        while(set.next()) {
            list.add(
                    new Deal(
                            set.getBigDecimal("id").intValue(),
                            set.getBigDecimal("user_id").intValue(),
                            set.getBigDecimal("status_id").intValue(),
                            new Timestamp(set.getDate("created_at").getTime()),
                            new Timestamp(set.getDate("updated_at").getTime())
                    ));
        }*/
    }

    /**
     Returns list of deals whose
     Wanted item categories
     are equal of
     'deal's Owned item categories
     Returns at least empty list
     */
    public static List<Deal> getClients(Deal deal) {

        String query =
            "SELECT * FROM deals \n" +
            "WHERE id IN ( \n" +
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
            "          WHERE oi.deal_id = " + deal.getDealID() + "\n" +
            "          GROUP BY i.item_category_id) b \n" +
            "        ON a.item_category_id = b.item_category_id AND \n" +
            "            a.freq_item_category = b.freq_item_category \n" +
            "     GROUP BY deal_id \n" +
            "     HAVING row_num = (SELECT COUNT(DISTINCT i.item_category_id) \n" +
            "                         FROM owned_items oi JOIN items i ON (oi.item_id = i.id) \n" +
            "                        WHERE oi.deal_id = " + deal.getDealID() + ") \n" +
            "    ) result \n" +
            ");";

        List<Deal> deals = new ArrayList<>();

        try {
            PreparedStatement statement = DAO.getPreparedStatement(query);
            queryDeals(deals, statement);
        }
        catch (SQLException e) { e.printStackTrace(); }

        return deals;
    }
}
