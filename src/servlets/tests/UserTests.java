package servlets.tests;

import managers.UserManager;
import models.User;
import org.junit.jupiter.api.Test;

public class UserTests {

    @Test
    public void addUser() {
        UserManager m = new UserManager();
        User u1 = new User("LG","password", "levan","gelashvili", "lgela17","555");
        User u2 = new User("LJ","miami","Lebron","James","lj@heat.com","23");
        m.storeUser(u1);
        m.storeUser(u2);
    }
}
