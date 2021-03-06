
package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import models.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserManager{


    private static DatabaseAccessObject DAO = DatabaseAccessObject.getInstance();

    private static final String STORE_USER_QUERY = "INSERT INTO users (user_name , password , first_name, last_name, " +
            "email, phone_number, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String GET_USER_ID_BY_DEAL_QUERY = "SELECT user_id FROM deals WHERE id = ?;";


    /**
     * get User object with its id
     *
     * @param userID - ID of User in DB
     * @return User -null if user wasn't found
     */
    public static User getUserByID(int userID) {
        List<User> users = getUsersByColumn("id", "" + userID, true);
        if (users.isEmpty()) return null;
        return users.get(0);
    }


    /**
     * get owner of given deal
     *
     * @param dealID - ID of Deal in DB
     * @return Owner (User) of Deal with ID = dealID
     *         If such dealID does not exists in DB
     *         returns null
     */
    public static int getUserIDByDealID(int dealID) {

        int userID = 0;

        try {
            PreparedStatement statement = DAO.getPreparedStatement(GET_USER_ID_BY_DEAL_QUERY);
            statement.setInt(1, dealID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                userID = resultSet.getBigDecimal("user_id").intValue();
            }
        }
        catch (SQLException e) { e.printStackTrace(); }

        return userID;
    }


    /**
     * get User object with its username
     *
     * @param username - User Name of User
     * @return User -null if user wasn't found
     */
    public static User getUserByUsername(String username){
        List<User> users = getUsersByColumn("user_name", username, false);
        if (users.isEmpty()) return null;
        return users.get(0);
    }


    /**
     * Given a column name in users table and a value
     * returns users array list corresponding to given value.
     * @param column - Column name of User's table
     * @param value - Value in 'WHERE'
     * @param isNumeric - boolean value for parsing 'value' if its numeric
     * @return List of Users got by query
     */
    public static List<User> getUsersByColumn(String column, String value, boolean isNumeric) {

        List<User> users = new ArrayList<>();

        try {
            String query = "SELECT * FROM users WHERE " + column + " = ?;";
            PreparedStatement st = DAO.getPreparedStatement(query);

            if (isNumeric) st.setInt(1, Integer.parseInt(value));
            else st.setString(1, value);

            ResultSet resultSet = st.executeQuery();

            while(resultSet.next())
                users.add(getUserFromResultSetRow(resultSet));

        }
        catch (SQLException e) { e.printStackTrace(); }

        return users;
    }


    /**
     * parses user from given result set
     *
     * @param resultSet - 'Row' of 'users' table
     * @return Fully Filled User
     * @throws SQLException - If some error happens while getting params from ResultSet
     */
    private static User getUserFromResultSetRow(ResultSet resultSet)
        throws SQLException {

        int userID = resultSet.getInt("id");

        return new User (
            userID,
            resultSet.getString("user_name"),
            resultSet.getString("password"),
            resultSet.getString("first_name"),
            resultSet.getString("last_name"),
            resultSet.getString("email"),
            resultSet.getString("phone_number"),
            ImagesManager.getUserProfileImage(userID),
            DealsManager.getDealsByUserID(userID),
            ChatManager.getUserChats(userID),
            resultSet.getTimestamp("created_at"),
            resultSet.getTimestamp("updated_at")
        );
    }

    /**
     * Should Store given user to database.
     * return true is user has been store successfully,
     * otherwise (if query violated any constraint returns false)
     * @param user
     * @return
     */
    public static boolean storeUser(User user){
        try {
            PreparedStatement st = DAO.getPreparedStatement(STORE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFirstName());
            st.setString(4, user.getLastName());
            st.setString(5, user.getEmail());
            st.setString(6, user.getPhoneNumber());
            st.setTimestamp(7, user.getCreateDate());
            st.setTimestamp(8, user.getUpdateDate());

            st.executeUpdate();
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserID(generatedKeys.getInt(1));
                } else
                    throw new SQLException("Storing user failed, no ID obtained.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * @param userName Name of user
     */
    public static void deleteUserByUsername(String userName) {
        DeleteManager.delete("users","user_name", userName);
    }

    /**
     * @param email
     * @return user with specified email
     */
    public static User getUserByEmail(String email) {
        List<User> usersWithEmail = getUsersByColumn("email", email, false);
        if(usersWithEmail.isEmpty())
            return null;
        return usersWithEmail.get(0);
    }

    /**
     * Updates user information in database
     * @param user
     * @return true if update was successful
     */
    public static boolean updateExistingUser(User user) {
        UpdateForm uf = new UpdateForm("users", user.getUserID());
        uf.addUpdate("user_name", user.getUsername());
        uf.addUpdate("password", user.getPassword());
        uf.addUpdate("first_name", user.getFirstName());
        uf.addUpdate("last_name", user.getLastName());
        uf.addUpdate("email", user.getEmail());
        uf.addUpdate("phone_number", user.getPhoneNumber());
        //Updating created_at isn't really necessary, but just in case
        // uf.addUpdate("created_at", user.getCreateDate().toString());
        uf.addUpdate("updated_at", user.getUpdateDate().toString());
        return UpdateManager.update(uf);
    }
}
