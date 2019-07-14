
package test.managers;

import generalManagers.UpdateForm;
import generalManagers.UpdateManager;
import managers.UserManager;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import test.Tester;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserManagerTest extends Tester {

    private static final User u1 = new User("LG","password","levan","gelashvili", "lgela17", "555");
    private static final User u2 = new User("KING","heat","Lebron","James","ljame03","23");
    private static final User u3 = new User("a","b","c","d","e","f");

    @Before
    public void setUp() throws Exception {
        emptyDataBase();
        assertTrue(UserManager.storeUser(u1));
        assertTrue(UserManager.storeUser(u2));
        assertTrue(UserManager.storeUser(u3));
    }

    @After
    public void tearDown() throws Exception {
        UpdateForm form = new UpdateForm("users", u2.getUserID());
        form.addUpdate("user_name","snake");
        form.addUpdate("first_name","Kevin");
        form.addUpdate("last_name","Durant");
        assertTrue(UpdateManager.update(form));
        assertEquals(UserManager.getUserByUsername("snake").getUserID(), u2.getUserID());
        assertEquals(UserManager.getUsersByColumn("first_name", "Kevin", false).get(0).getUserID(), u2.getUserID());

        int oldSize = Tester.tableSize("users");
        UserManager.deleteUserByUsername(u1.getUsername());
        assertEquals(oldSize, Tester.tableSize("users") + 1);

        oldSize = Tester.tableSize("users");
        UserManager.deleteUserByUsername("something wrong");
        assertEquals(oldSize, Tester.tableSize("users"));

        UserManager.updateExistingUser(u3);
    }

    @Test
    public void getUserByID() {
        assertEquals(UserManager.getUserByID(1), u1);
        assertEquals(UserManager.getUserByID(2), u2);
        assertEquals(UserManager.getUserByID(3), u3);
        assertEquals(UserManager.getUserByID(5555), null);
    }

    @Test
    public void getUserIDByDealID() {
        //Tested in dealsTests
    }

    @Test
    public void getUserByUsername() {
        assertEquals(UserManager.getUserByUsername("LG"),u1);
        assertEquals(UserManager.getUserByUsername("KING"),u2);
        assertEquals(UserManager.getUserByUsername("a"),u3);
        assertEquals(UserManager.getUserByUsername("someoneElse"),null);
    }

    @Test
    public void storeUser() {
        //Tested in @before
    }

    @Test
    public void deleteUserByUsername() {
        //Tested in @after
    }

    @Test
    public void getUserByEmail() {
        assertEquals(UserManager.getUserByEmail("lgela17"),u1);
        assertEquals(UserManager.getUserByEmail("ljame03"),u2);
        assertEquals(UserManager.getUserByEmail("e"),u3);
        assertEquals(UserManager.getUserByEmail("unknownEmail"),null);
    }

    @Test
    public void updateExistingUser() {
        //Tested in @after
    }
}
