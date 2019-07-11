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


    public void emptyBase() {
        DeleteManager.delete("users", "1",1);
        UpdateManager.reseedTable("users",1);
    }

    @Test
    public void addUsers() {
        emptyBase();
        assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));
        assertTrue(UserManager.storeUser(u3));
    }

    @Test
    public void getUsersByColumn() {
        assertEquals(UserManager.getUserByUsername("KING").getUsername(),u2.getUsername());
        assertEquals(UserManager.getUserByUsername("dummy"),null);
        assertEquals(UserManager.getUserByID(55555), null);
        assertEquals(UserManager.getUsersByColumn("first_name", "levan",false).get(0).getUsername(), u1.getUsername());
        assertEquals(UserManager.getUsersByColumn("last_name", "James", false).get(0).getUsername(), u2.getUsername());
        assertEquals(UserManager.getUsersByColumn("phone_number","doesnt exist",false), new ArrayList<>());
    }

    @Test
    public void updateUsers() {
        UpdateForm form = new UpdateForm("users", UserManager.getUserByUsername("KING").getUserID());
        form.addUpdate("user_name","snakeee");
        form.addUpdate("first_name","Kevin");
        form.addUpdate("last_name","Durant");
        form.addUpdate("id", 35);
        assertTrue(UpdateManager.update(form));
    }

    @Test
    public void deleteFromUsers() {
        assertTrue(DeleteManager.delete("users", "id", 35));
        assertTrue(DeleteManager.delete("users", "user_name", "LG"));
    }


    @Test
    public void customTest(){
        boolean levan = UserManager.storeUser(u1);
        assertTrue(levan);
        assertNotNull(UserManager.getUserByUsername(u1.getUsername()));
    }
}