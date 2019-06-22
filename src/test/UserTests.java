package test;

import managers.UserManager;
import models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTests {

    private static final User u1 = new User("LG","password","levan","gelashvili", "lgela17", "555");
    private static final User u2 = new User("KING","heat","Lebron","James","ljame03","23");

    @Test
    public void addUsers() {
        UserManager m = new UserManager();
        assertEquals(m.storeUser(u1),true);
        assertEquals(m.storeUser(u2),true);
    }

    @Test
    public void getUsersByColumn() {
        UserManager m = new UserManager();
        assertEquals(m.getUserByUsername("KING"),u2);
        assertEquals(m.getUserByUsername("dummy"),null);
        assertEquals(m.getUserById(55), null);
        assertEquals(m.getUserById(2),u2);
        assertEquals(m.getUsersByColumn("first_name", "levan",false).get(0), u1);
        assertEquals(m.getUsersByColumn("last_name", "James", false).get(0), u2);
        assertEquals(m.getUsersByColumn("phone_number","doesnt exist",false), new ArrayList<>());
    }
}
