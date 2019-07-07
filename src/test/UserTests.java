package test;
import database.DatabaseAccessObject;
import generalManagers.DeleteManager;
import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import managers.UserManager;
import models.User;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    private static final User u1 = new User("LG","password","levan","gelashvili", "lgela17", "555");
    private static final User u2 = new User("KING","heat","Lebron","James","ljame03","23");
    private static final User u3 = new User("a","b","c","d","e","f");

    private static final UserManager m = new UserManager();
    private static final DeleteManager d = new DeleteManager();
    private static final UpdateManager u = new UpdateManager();

    @Test
    public void emptyBase() {
        try {
            PreparedStatement st = DatabaseAccessObject.getInstance().getPreparedStatement("delete from users;");
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void addUsers() {
        assertTrue(UserManager.storeUser(u1));
        assertTrue(m.storeUser(u2));
        assertTrue(m.storeUser(u3));
    }

    @Test
    public void getUsersByColumn() {
        assertEquals(m.getUserByUsername("KING"),u2);
        assertEquals(m.getUserByUsername("dummy"),null);
        assertEquals(m.getUserByID(55555), null);
        assertEquals(m.getUsersByColumn("first_name", "levan",false).get(0), u1);
        assertEquals(m.getUsersByColumn("last_name", "James", false).get(0), u2);
        assertEquals(m.getUsersByColumn("phone_number","doesnt exist",false), new ArrayList<>());
    }

    @Test
    public void updateUsers() {
        UpdateForm form = new UpdateForm("users", m.getUserByUsername("KING").getId());
        form.addUpdate("user_name","snakeee");
        form.addUpdate("first_name","Kevin");
        form.addUpdate("last_name","Durant");
        form.addUpdate("id", 35);
        assertTrue(u.update(form));
    }

    @Test
    public void deleteFromUsers() {
        assertTrue(d.delete("users", "id", 35));
        assertTrue(d.delete("users", "user_name", "LG"));
    }


    @Test
    public void customTest(){
        boolean levan = m.storeUser(u1);
        assertTrue(levan);
        assertNotNull(m.getUserByUsername(u1.getUsername()));
    }
}