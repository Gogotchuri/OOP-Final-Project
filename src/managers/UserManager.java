package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import models.Deal;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class UserManager {

    private static DatabaseAccessObject DBAO = DatabaseAccessObject.getInstance();

    private static final String STORE_USER_QUERY = "INSERT INTO users (user_name , password , first_name, last_name, " +
            "email, phone_number, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    private static final String GET_LAST_ID = "SELECT MAX(id) FROM users;";
    /**
     * get User object with its username
     *
     * @param username
     * @return User -null if user wasn't found
     */

    public static User getUserByUsername(String username){
        ArrayList<User> users = getUsersByColumn("user_name", username, false);
        if(users.isEmpty()) return null;
        return users.get(0);
    }

    /**
     * Given a column name in users table and a value
     * returns users array list corresponding to given value.
     * @param column
     * @param value
     * @return
     */
    public static ArrayList<User> getUsersByColumn(String column, String value, boolean isNumeric){
        ArrayList<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users WHERE " + column + " = ?;";
            PreparedStatement st = DBAO.getPreparedStatement(query);

            if(isNumeric) st.setInt(1, Integer.parseInt(value));
            else st.setString(1, value);

            ResultSet set = st.executeQuery();
            while(set.next())
                users.add(getUserFromResultSetRow(set));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * get User object with its id
     *
     * @param id
     * @return User -null if user wasn't found
     */
    public static User getUserById(int id) {
        ArrayList<User> users = getUsersByColumn("id", ""+id, true);
        if(users.isEmpty()) return null;
        return users.get(0);
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
            PreparedStatement st = DBAO.getPreparedStatement(STORE_USER_QUERY);

            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getFirstName());
            st.setString(4, user.getLastName());
            st.setString(5, user.getEmail());
            st.setString(6, user.getPhoneNumber());
            st.setTimestamp(7, user.getCreatedDate());
            st.setTimestamp(8, user.getUpdatedDate());

            st.executeUpdate();

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
     * @return GeneratedID, taken from database
     * @throws SQLException
     */
    private static int generateID() throws SQLException {
        PreparedStatement st = DBAO.getPreparedStatement(GET_LAST_ID);
        ResultSet set = st.executeQuery();
        return (set.next()) ? 1 + set.getInt(1) : 1;
    }

    /**
     * @param set ResultSett
     * @return Parsed user, taken from resultSet
     * @throws SQLException
     */
    private static User getUserFromResultSetRow(ResultSet set) throws SQLException {
        User user = new User(
                set.getInt("id"),
                set.getString("user_name"),
                set.getString("password"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("email"),
                set.getString("phone_number"),
                new Timestamp(set.getTimestamp("updated_at").getTime()),
                new Timestamp(set.getTimestamp("created_at").getTime()));

        return user;
    }
}
