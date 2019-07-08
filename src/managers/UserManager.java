package managers;

import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static DatabaseAccessObject DBAO = DatabaseAccessObject.getInstance();

    private static final String STORE_USER_QUERY = "INSERT INTO users (user_name , password , first_name, last_name, " +
            "email, phone_number, created_at, updated_at) VALUES(?, ?, ?, ?, ?, ?, ?, ?);";

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
    public static User getUserByID(int id) {
        ArrayList<User> users = getUsersByColumn("id", ""+id, true);
        if(users.isEmpty()) return null;
        return users.get(0);
    }


    /**
     * TODO: Krawa
     * Should Store given user to database.
     * return true is user has been store successfully,
     * otherwise (if query violated any constraint returns false)
     * @param user
     * @return
     */
    public static boolean storeUser(User user){
        /*try {
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
        }*/
        return true;
    }

    /**
     * @param userName Name of user
     */
    public static void deleteUserByUsername(String userName) {
        DeleteManager.delete("users","user_name", userName);
    }

    /**
     * TODO: Krawa
     * @param set ResultSett
     * @return Parsed user, taken from resultSet
     * @throws SQLException
     */
    private static User getUserFromResultSetRow(ResultSet set) throws SQLException {
        /*User user = new User(
                set.getInt("id"),
                set.getString("user_name"),
                set.getString("password"),
                set.getString("first_name"),
                set.getString("last_name"),
                set.getString("email"),
                set.getString("phone_number"),
                new Timestamp(set.getTimestamp("updated_at").getTime()),
                new Timestamp(set.getTimestamp("created_at").getTime()));

        return user;*/
        return null;
    }

    /**
     * @param email
     * @return user with specified email
     */
    public static User getUserByEmail(String email) {
        List<User> users = getUsersByColumn("email", email, false);
        if(users.isEmpty()) return null;
        return users.get(0);
    }

    /**
     * Updates user information in database
     * @param user
     * @return true if update was successful
     */
    public static boolean updateExistingUser(User user) {
        /*UpdateForm uf = new UpdateForm("users", user.getUserID());
        uf.addUpdate("user_name", user.getUsername());
        uf.addUpdate("password", user.getPassword());
        uf.addUpdate("first_name", user.getFirstName());
        uf.addUpdate("last_name", user.getLastName());
        uf.addUpdate("email", user.getEmail());
        uf.addUpdate("phone_number", user.getPhoneNumber());
        //Updating created_at isn't really necessary, but just in case
        uf.addUpdate("created_at", user.getCreatedDate());
        uf.addUpdate("updated_at", user.getUpdatedDate());
        return UpdateManager.update(uf);*/
        return false;
    }
}
